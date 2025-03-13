package it.eng.tz.urbamid.toponomastica.util;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import it.eng.tz.urbamid.toponomastica.filter.AccessoFilter;
import it.eng.tz.urbamid.toponomastica.filter.CippoChilometricoFilter;
import it.eng.tz.urbamid.toponomastica.filter.EstesaAmministrativaFilter;
import it.eng.tz.urbamid.toponomastica.filter.GiunzioneFilter;
import it.eng.tz.urbamid.toponomastica.filter.LocalitaFilter;
import it.eng.tz.urbamid.toponomastica.filter.PercorsoFilter;
import it.eng.tz.urbamid.toponomastica.filter.ToponimoFilter;
import it.eng.tz.urbamid.toponomastica.persistence.model.Accesso;
import it.eng.tz.urbamid.toponomastica.persistence.model.CippoChilometrico;
import it.eng.tz.urbamid.toponomastica.persistence.model.EstesaAmministrativa;
import it.eng.tz.urbamid.toponomastica.persistence.model.GeocodingReverseGeocoding;
import it.eng.tz.urbamid.toponomastica.persistence.model.GiunzioneStradale;
import it.eng.tz.urbamid.toponomastica.persistence.model.Localita;
import it.eng.tz.urbamid.toponomastica.persistence.model.Percorso;
import it.eng.tz.urbamid.toponomastica.persistence.model.QAccesso;
import it.eng.tz.urbamid.toponomastica.persistence.model.QCippoChilometrico;
import it.eng.tz.urbamid.toponomastica.persistence.model.QEstesaAmministrativa;
import it.eng.tz.urbamid.toponomastica.persistence.model.QGeocodingReverseGeocoding;
import it.eng.tz.urbamid.toponomastica.persistence.model.QGiunzioneStradale;
import it.eng.tz.urbamid.toponomastica.persistence.model.QLocalita;
import it.eng.tz.urbamid.toponomastica.persistence.model.QPercorso;
import it.eng.tz.urbamid.toponomastica.persistence.model.QToponimoStradale;
import it.eng.tz.urbamid.toponomastica.persistence.model.ToponimoStradale;

/**
 * Classe che racchiude una serie di metodi statici per agevolare l'esecuzione delle query sui repository JPA.
 */
@SuppressWarnings("serial")
public class RepositoryUtils {
	
	//COSTANTE PER IL CARATTERE JOLLY DELLA LIKE
	private static final String JOLLY_CHARACTER = "%";
	
	/**
	 * Metodo che restituisce una stringa con all'inizio ed alla fine il carattere jolly % per le like.
	 * @param value il valore da usare per la like
	 * @return
	 */
	private static String buildLikeValue(String value) {
		return new StringBuilder(JOLLY_CHARACTER).append(value).append(JOLLY_CHARACTER).toString();
	}
	
	public static Specification<GeocodingReverseGeocoding> buildGeocoding(String[] denominazione) {
		return new Specification<GeocodingReverseGeocoding>() {

			@Override
			public Predicate toPredicate(Root<GeocodingReverseGeocoding> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				
				QGeocodingReverseGeocoding geocoding = QGeocodingReverseGeocoding.geocodingReverseGeocoding;
				Predicate predicate = criteriaBuilder.conjunction();
				
				for (String denom : denominazione) {
						predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.upper(root.get(geocoding.denominazioneUfficiale.getMetadata().getName())), buildLikeValue(denom.toUpperCase())));
		
				}
				
				
				return predicate;
			}
			
		};
		
	}
	
	/**
	 * Costruisce l'oggetto {@link Specification} per eseguire la query di ricerca del toponimo stradale, costruendo i predicati della clausola
	 * where attraverso l'oggetto {@link filter} che incapsula i filtri di ricerca.
	 * @param filter oggetto che incapsula i filtri di ricerca.
	 * @return l'oggetto {@link Specification}
	 */
	public static Specification<ToponimoStradale> buildToponimoPredicate(ToponimoFilter filter) {
		return new Specification<ToponimoStradale>() {
			
			@Override
			public Predicate toPredicate(Root<ToponimoStradale> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				
				QToponimoStradale toponimo = QToponimoStradale.toponimoStradale;
				Predicate predicate = criteriaBuilder.conjunction();
				
				if(StringUtils.hasText(filter.getStato()))
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get(toponimo.stato.getMetadata().getName()), buildLikeValue(filter.getStato().toUpperCase())));
				
				if(StringUtils.hasText(filter.getClasse()))
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.upper(root.join(toponimo.classe.getMetadata().getName()).get("dug")), filter.getClasse().toUpperCase() + "%"));
				
				if(StringUtils.hasText(filter.getDenominazioneUfficiale()))
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.upper(root.get(toponimo.denominazioneUfficiale.getMetadata().getName())), buildLikeValue(filter.getDenominazioneUfficiale().toUpperCase())));
				
				if(StringUtils.hasText(filter.getComune()))
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.join(toponimo.comune.getMetadata().getName()).get("idComune"), filter.getComune()));
				
				if(StringUtils.hasText(filter.getCodiceAutorizzazione()))
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.upper(root.get(toponimo.codiceAutorizzazione.getMetadata().getName())), buildLikeValue(filter.getCodiceAutorizzazione().toUpperCase())));
				
				if(StringUtils.hasText(filter.getNumeroDelibera()))
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.upper(root.get(toponimo.numeroDelibera.getMetadata().getName())), buildLikeValue(filter.getNumeroDelibera().toUpperCase())));
				
				
				//INTERVALLO PER LA RICERCA DEL NUMERO CIVICO
				if(filter.getNumCivicoDxDa() != null && filter.getNumCivicoDxA() != null && filter.getNumCivicoSxDa() != null && filter.getNumCivicoSxA() != null) {
					
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.join(toponimo.accesso.getMetadata().getName()).get("numero"), Integer.min(filter.getNumCivicoDxDa(), filter.getNumCivicoSxDa()), Integer.max(filter.getNumCivicoDxA(), filter.getNumCivicoSxA())));
					
				} else if((filter.getNumCivicoDxDa() != null | filter.getNumCivicoDxA() != null) && (filter.getNumCivicoSxDa() != null | filter.getNumCivicoSxA() != null)) {
					
					if((filter.getNumCivicoDxDa() != null && filter.getNumCivicoDxA() != null) & (filter.getNumCivicoSxDa() != null | filter.getNumCivicoSxA() != null))
						predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.join(toponimo.accesso.getMetadata().getName()).get("numero"), Integer.min(filter.getNumCivicoDxDa(), filter.getNumCivicoSxDa() != null ? filter.getNumCivicoSxDa() : filter.getNumCivicoSxA()), 
																																							  Integer.max(filter.getNumCivicoDxA(), filter.getNumCivicoSxDa() != null ? filter.getNumCivicoSxDa() : filter.getNumCivicoSxA())));
					
					else if((filter.getNumCivicoSxDa() != null && filter.getNumCivicoSxA() != null) & (filter.getNumCivicoDxDa() != null | filter.getNumCivicoDxA() != null))
						predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.join(toponimo.accesso.getMetadata().getName()).get("numero"), Integer.min(filter.getNumCivicoSxDa(), filter.getNumCivicoDxDa() != null ? filter.getNumCivicoDxDa() : filter.getNumCivicoDxA()), 
																																							  Integer.max(filter.getNumCivicoSxA(), filter.getNumCivicoDxDa() != null ? filter.getNumCivicoDxDa() : filter.getNumCivicoDxA())));
					
					else if(filter.getNumCivicoDxDa() != null && filter.getNumCivicoSxDa() != null)
						predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.join(toponimo.accesso.getMetadata().getName()).get("numero"), Integer.max(filter.getNumCivicoDxDa(), filter.getNumCivicoSxDa())));
					
					else if(filter.getNumCivicoDxDa() != null && filter.getNumCivicoSxA() != null)
						predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.join(toponimo.accesso.getMetadata().getName()).get("numero"), Integer.min(filter.getNumCivicoDxDa(), filter.getNumCivicoSxA()), 
																																							  Integer.max(filter.getNumCivicoDxDa(), filter.getNumCivicoSxA())));
					
					else if(filter.getNumCivicoDxA() != null && filter.getNumCivicoSxDa() != null)
						predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.join(toponimo.accesso.getMetadata().getName()).get("numero"), Integer.min(filter.getNumCivicoDxA(), filter.getNumCivicoSxDa()), 
																																							  Integer.max(filter.getNumCivicoDxA(), filter.getNumCivicoSxDa())));
					
					else if(filter.getNumCivicoDxA() != null && filter.getNumCivicoSxA() != null)
						predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.join(toponimo.accesso.getMetadata().getName()).get("numero"), Integer.max(filter.getNumCivicoDxA(), filter.getNumCivicoSxA())));
					
					
				} else if(filter.getNumCivicoDxDa() != null || filter.getNumCivicoDxA() != null) {
					
					if(filter.getNumCivicoDxDa() != null && filter.getNumCivicoDxA() != null)
						predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.join(toponimo.accesso.getMetadata().getName()).get("numero"), filter.getNumCivicoDxDa(), filter.getNumCivicoDxA()),
																   criteriaBuilder.equal(criteriaBuilder.mod(root.join(toponimo.accesso.getMetadata().getName()).get("numero"), 2), 0));
						
					else if(filter.getNumCivicoDxDa() != null)
						predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.join(toponimo.accesso.getMetadata().getName()).get("numero"), filter.getNumCivicoDxDa()),
																   criteriaBuilder.equal(criteriaBuilder.mod(root.join(toponimo.accesso.getMetadata().getName()).get("numero"), 2), 0));
						
					else if(filter.getNumCivicoDxA() != null)
						predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.join(toponimo.accesso.getMetadata().getName()).get("numero"), filter.getNumCivicoDxA()),
																   criteriaBuilder.equal(criteriaBuilder.mod(root.join(toponimo.accesso.getMetadata().getName()).get("numero"), 2), 0));
					
				} else if(filter.getNumCivicoSxDa() != null || filter.getNumCivicoSxA() != null) {
					
					if(filter.getNumCivicoSxDa() != null && filter.getNumCivicoSxA() != null)
						predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.join(toponimo.accesso.getMetadata().getName()).get("numero"), filter.getNumCivicoSxDa(), filter.getNumCivicoSxA()),
								   								   criteriaBuilder.notEqual(criteriaBuilder.mod(root.join(toponimo.accesso.getMetadata().getName()).get("numero"), 2), 0));
						
					else if(filter.getNumCivicoSxDa() != null)
						predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.join(toponimo.accesso.getMetadata().getName()).get("numero"), filter.getNumCivicoSxDa()),
								   								   criteriaBuilder.notEqual(criteriaBuilder.mod(root.join(toponimo.accesso.getMetadata().getName()).get("numero"), 2), 0));
						
					else if(filter.getNumCivicoSxA() != null)
						predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.join(toponimo.accesso.getMetadata().getName()).get("numero"), filter.getNumCivicoSxA()),
								   								   criteriaBuilder.notEqual(criteriaBuilder.mod(root.join(toponimo.accesso.getMetadata().getName()).get("numero"), 2), 0));
				}			
				
				//INTERVALLO DATA
				Predicate validIntervalPredicateDelibera = buildIntervalPredicate(DateUtils.stringToDate(filter.getDataDeliberaDal(), DateUtils.PATTERN_YYYY_MM_DD_SLASH_REVERSED), DateUtils.stringToDate(filter.getDataDeliberaAl(), DateUtils.PATTERN_YYYY_MM_DD_SLASH_REVERSED), toponimo.dataDelibera.getMetadata().getName(), toponimo.dataDelibera.getMetadata().getName(), criteriaBuilder, root);
				
				if(validIntervalPredicateDelibera != null)
					predicate = criteriaBuilder.and(predicate, validIntervalPredicateDelibera);
				
				Predicate validIntervalPredicateAutorizzazione = buildIntervalPredicate(DateUtils.stringToDate(filter.getDataAutorizzazioneDal(), DateUtils.PATTERN_YYYY_MM_DD_SLASH_REVERSED), DateUtils.stringToDate(filter.getDataAutorizzazioneAl(), DateUtils.PATTERN_YYYY_MM_DD_SLASH_REVERSED), toponimo.dataAutorizzazione.getMetadata().getName(), toponimo.dataAutorizzazione.getMetadata().getName(), criteriaBuilder, root);
				
				if(validIntervalPredicateAutorizzazione != null)
					predicate = criteriaBuilder.and(predicate, validIntervalPredicateAutorizzazione);
				
				
				/** ORDINAMENTO IN BASE ALLE COLONNE SCELTE **/
				if(filter.getPageOrder() != null) {
					Expression<?> expression = null;
					
					if(filter.getPageOrder().getColumn().equalsIgnoreCase("tipo"))
						expression = root.join(filter.getPageOrder().getColumn(), JoinType.LEFT).get(toponimo.tipo.descrizione.getMetadata().getName());
					else
						expression = root.get(filter.getPageOrder().getColumn());
						
					/** CONTROLLO LA DIREZIONE SE ASC O DESC **/
					if(filter.getPageOrder().getDir().equalsIgnoreCase("ASC"))
						query.orderBy(criteriaBuilder.asc(expression));
					else
						query.orderBy(criteriaBuilder.desc(expression));
				
				}
									
				query.distinct(true);
				return predicate;
			
			}

		};
	}
	
	/**
	 * Costruisce l'oggetto {@link Specification} per eseguire la query di ricerca della località, costruendo i predicati della clausola
	 * where attraverso l'oggetto {@link filter} che incapsula i filtri di ricerca.
	 * @param filter oggetto che incapsula i filtri di ricerca.
	 * @return l'oggetto {@link Specification}
	 */
	public static Specification<Localita> buildLocalitaPredicate(LocalitaFilter filter) {
		return new Specification<Localita>() {
			
			@Override
			public Predicate toPredicate(Root<Localita> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				
				QLocalita localita = QLocalita.localita;
				Predicate predicate = criteriaBuilder.conjunction();
				
				if(StringUtils.hasText(filter.getDescrizione()))
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.upper(root.get(localita.descrizione.getMetadata().getName())),  buildLikeValue(filter.getDescrizione().toUpperCase())));
				
				if(StringUtils.hasText(filter.getFrazione()))
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.upper(root.get(localita.frazione.getMetadata().getName())), buildLikeValue(filter.getFrazione().toUpperCase())));
				
				if(StringUtils.hasText(filter.getStato()))
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get(localita.stato.getMetadata().getName()), buildLikeValue(filter.getStato())));
				
				
				/** ORDINAMENTO PER LE COLONNE SCELTE*/
				if(filter.getPageOrder() != null) {
					Expression<?> expression = null;
					
					if(filter.getPageOrder().getColumn().equalsIgnoreCase("tipo"))
						expression = root.join(filter.getPageOrder().getColumn(), JoinType.LEFT).get(localita.tipo.id.getMetadata().getName());
					
					else if(filter.getPageOrder().getColumn().equalsIgnoreCase("comune"))
						expression = root.join(filter.getPageOrder().getColumn(), JoinType.LEFT).get(localita.comune.idComune.getMetadata().getName());
					
					else
						expression = root.get(filter.getPageOrder().getColumn());
						
					
					if(filter.getPageOrder().getDir().equalsIgnoreCase("ASC"))
						query.orderBy(criteriaBuilder.asc(expression));
					
					else
						query.orderBy(criteriaBuilder.desc(expression));
					
					
				}
				
				return predicate;
				
			}

		};
	
	}
	
	/**
	 * Costruisce l'oggetto {@link Specification} per eseguire la query di ricerca dell'Estesa Amministrativa, costruendo i predicati della clausola
	 * where attraverso l'oggetto {@link filter} che incapsula i filtri di ricerca.
	 * @param filter oggetto che incapsula i filtri di ricerca.
	 * @return l'oggetto {@link Specification}
	 */
	public static Specification<EstesaAmministrativa> buildEstesaAmministrativaPredicate(EstesaAmministrativaFilter filter) {
		return new Specification<EstesaAmministrativa>() {
			
			@Override
			public Predicate toPredicate(Root<EstesaAmministrativa> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				
				QEstesaAmministrativa estesaAmministrativa = QEstesaAmministrativa.estesaAmministrativa;
				Predicate predicate = criteriaBuilder.conjunction();				
				
				if(StringUtils.hasText(filter.getSigla()))
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.upper(root.get(estesaAmministrativa.sigla.getMetadata().getName())), buildLikeValue(filter.getSigla().toUpperCase())));
				
				if(StringUtils.hasText(filter.getDescrizione()))
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.upper(root.get(estesaAmministrativa.descrizione.getMetadata().getName())), buildLikeValue(filter.getDescrizione().toUpperCase())));
				
				if(StringUtils.hasText(filter.getCodice()))
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.upper(root.get(estesaAmministrativa.codice.getMetadata().getName())),  buildLikeValue(filter.getCodice().toUpperCase())));
				
				if(StringUtils.hasText(filter.getStato()))
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get(estesaAmministrativa.stato.getMetadata().getName()), buildLikeValue(filter.getStato())));
				
				if(filter.getClassificaAmministrativa() != null)
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get(estesaAmministrativa.classificaAmministrativa.getMetadata().getName()), filter.getClassificaAmministrativa()));
				
				if(filter.getClassificaFunzionale() != null)
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get(estesaAmministrativa.classificaFunzionale.getMetadata().getName()), filter.getClassificaFunzionale()));
				
				/** ORDINAMENTO */
				if(filter.getPageOrder() != null) {
					Expression<?> expression = null;
					
					if(filter.getPageOrder().getColumn().equalsIgnoreCase("classificaAmministrativa"))
						expression = root.join(filter.getPageOrder().getColumn(), JoinType.LEFT).get(estesaAmministrativa.classificaAmministrativa.descrizione.getMetadata().getName());
					
					else if(filter.getPageOrder().getColumn().equalsIgnoreCase("classificaFunzionale"))
						expression = root.join(filter.getPageOrder().getColumn(), JoinType.LEFT).get(estesaAmministrativa.classificaFunzionale.descrizione.getMetadata().getName());
					
					else
						expression = root.get(filter.getPageOrder().getColumn());
				
					
					if(filter.getPageOrder().getDir().equalsIgnoreCase("ASC"))
						query.orderBy(criteriaBuilder.asc(expression));
					
					else
						query.orderBy(criteriaBuilder.desc(expression));
					
				}
				
				return predicate;
			
			}

		};
	
	}
	
	/**
	 * Costruisce l'oggetto {@link Specification} per eseguire la query di ricerca del Cippo Chilometrico, costruendo i predicati della clausola
	 * where attraverso l'oggetto {@link filter} che incapsula i filtri di ricerca.
	 * @param filter oggetto che incapsula i filtri di ricerca.
	 * @return l'oggetto {@link Specification}
	 */
	public static Specification<CippoChilometrico> buildCippoChilometricoPredicate(CippoChilometricoFilter filter) {
		return new Specification<CippoChilometrico>() {
			
			@Override
			public Predicate toPredicate(Root<CippoChilometrico> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				
				QCippoChilometrico cippoChilometrico = QCippoChilometrico.cippoChilometrico;
				Predicate predicate = criteriaBuilder.conjunction();
				
				if(StringUtils.hasText(filter.getCodice()))
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get(cippoChilometrico.codice.getMetadata().getName()), buildLikeValue(filter.getCodice())));
				
				if(filter.getIdEstesa() != null)
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get(cippoChilometrico.estesaAmministrativa.getMetadata().getName()).get("id"), filter.getIdEstesa()));
				
				if(StringUtils.hasText(filter.getEstesaAmministrativa()))
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.upper(root.join(cippoChilometrico.estesaAmministrativa.getMetadata().getName()).get("descrizione")),  buildLikeValue(filter.getEstesaAmministrativa().toUpperCase())));
				
				if(filter.getMisura() != 0)
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get(cippoChilometrico.misura.getMetadata().getName()),  filter.getMisura()));
				
				if(StringUtils.hasText(filter.getStato()))
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get(cippoChilometrico.stato.getMetadata().getName()), buildLikeValue(filter.getStato())));
				
				/** ORDINAMENTO */
				if(filter.getPageOrder() != null) {
					Expression<?> expression = null;
					
					if(filter.getPageOrder().getColumn().equalsIgnoreCase("estesaAmministrativa"))
						expression = root.join(filter.getPageOrder().getColumn(), JoinType.LEFT).get(cippoChilometrico.estesaAmministrativa.descrizione.getMetadata().getName());
					
					else
						expression = root.get(filter.getPageOrder().getColumn());
									
					
					if(filter.getPageOrder().getDir().equalsIgnoreCase("ASC"))
						query.orderBy(criteriaBuilder.asc(expression));
					else
						query.orderBy(criteriaBuilder.desc(expression));
					
				}
				
				return predicate;
			
			}

		};
	
	}
	
	/**
	 * Costruisce l'oggetto {@link Specification} per eseguire la query di ricerca del percorso, costruendo i predicati della clausola
	 * where attraverso l'oggetto {@link filter} che incapsula i filtri di ricerca.
	 * @param filter oggetto che incapsula i filtri di ricerca.
	 * @return l'oggetto {@link Specification}
	 */
	public static Specification<Percorso> buildPercorsoPredicate(PercorsoFilter filter) {
		return new Specification<Percorso>() {
			
			@Override
			public Predicate toPredicate(Root<Percorso> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				
				Predicate predicate = criteriaBuilder.conjunction();
				
				QPercorso percorso = QPercorso.percorso;
				
				if(filter.isTipo() != null) {
					
					if(filter.isTipo())
						predicate = criteriaBuilder.and(predicate, criteriaBuilder.isNull(root.get(percorso.toponimo.getMetadata().getName())));
					else
						predicate = criteriaBuilder.and(predicate, criteriaBuilder.isNull(root.get(percorso.estesaAmministrativa.getMetadata().getName())));
				
				}
				
				if(StringUtils.hasText(filter.getSigla()))
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.upper(root.get(percorso.sigla.getMetadata().getName())),  buildLikeValue(filter.getSigla().toUpperCase())));
				
				if(StringUtils.hasText(filter.getDescrizione()))
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.upper(root.get(percorso.descrizione.getMetadata().getName())),  buildLikeValue(filter.getDescrizione().toUpperCase())));
				
				if(StringUtils.hasText(filter.getStato()))
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get(percorso.stato.getMetadata().getName()), buildLikeValue(filter.getStato())));
				
				return predicate;
			
			}

		};
	
	}
	
	/**
	 * Costruisce l'oggetto {@link Specification} per eseguire la query di ricerca dell'accesso, costruendo i predicati della clausola
	 * where attraverso l'oggetto {@link filter} che incapsula i filtri di ricerca.
	 * @param filter oggetto che incapsula i filtri di ricerca.
	 * @return l'oggetto {@link Specification}
	 */
	public static Specification<Accesso> buildAccessoPredicate(AccessoFilter filter) {
		return new Specification<Accesso>() {
			
			@Override
			public Predicate toPredicate(Root<Accesso> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				
				Predicate predicate = criteriaBuilder.conjunction();
				
				QAccesso accesso = QAccesso.accesso;
				
				if(filter.getIdToponimo() != null)
					predicate = criteriaBuilder.equal(root.join(accesso.toponimo.getMetadata().getName()).get(accesso.toponimo.id.getMetadata().getName()), filter.getIdToponimo());
				
				if(StringUtils.hasText(filter.getToponimo()))
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.join(accesso.toponimo.getMetadata().getName()).get("denominazioneUfficiale"), filter.getToponimo()));
				
				if(filter.getLocalita() != null)
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.join(accesso.localita.getMetadata().getName()).get("id"), filter.getLocalita()));
				
				if(filter.getTipo() != null)
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.join(accesso.tipo.getMetadata().getName()).get("id"), filter.getTipo()));
				
				if(StringUtils.hasText(filter.getStato()))
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get(accesso.stato.getMetadata().getName()), buildLikeValue(filter.getStato())));
				
				/** ORDINAMENTO **/
				if(filter.getPageOrder() != null) {
					Expression<?> expression = null;
					
					if(filter.getPageOrder().getColumn().equalsIgnoreCase("toponimo"))
						expression = root.join(filter.getPageOrder().getColumn(), JoinType.LEFT).get("denominazioneUfficiale");
					
					else if(filter.getPageOrder().getColumn().equalsIgnoreCase("localita"))
						expression = root.join(filter.getPageOrder().getColumn(), JoinType.LEFT).get("descrizione");
					
					else if(filter.getPageOrder().getColumn().equalsIgnoreCase("tipo"))
						expression = root.join(filter.getPageOrder().getColumn(),JoinType.LEFT).get("descrizione");
					
					else
						expression = root.get(filter.getPageOrder().getColumn());
					
					
					if(filter.getPageOrder().getDir().equalsIgnoreCase("ASC"))
						query.orderBy(criteriaBuilder.asc(expression));
					
					else
						query.orderBy(criteriaBuilder.desc(expression));
					
				}
				
				return predicate;
			
			}

		};
	
	}
	
	/**
	 * Costruisce l'oggetto {@link Specification} per eseguire la query di ricerca della giunzione stradale, costruendo i predicati della clausola
	 * where attraverso l'oggetto {@link filter} che incapsula i filtri di ricerca.
	 * @param filter oggetto che incapsula i filtri di ricerca.
	 * @return l'oggetto {@link Specification}
	 */
	public static Specification<GiunzioneStradale> buildGiunzioniPredicate(GiunzioneFilter filter) {
		return new Specification<GiunzioneStradale>() {

			@Override
			public Predicate toPredicate(Root<GiunzioneStradale> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				
				Predicate predicate = criteriaBuilder.conjunction();
				
				QGiunzioneStradale giunzione = QGiunzioneStradale.giunzioneStradale;
				
				if(StringUtils.hasText(filter.getStato()))
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get(giunzione.stato.getMetadata().getName()), buildLikeValue(filter.getStato())));
				
				if(StringUtils.hasText(filter.getDescrizione()) )
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get(giunzione.descrizione.getMetadata().getName()), buildLikeValue(filter.getDescrizione())));

				if(filter.getTipoFunzionale() != null)
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.join(giunzione.tipoFunzionale.getMetadata().getName()).get("id"), filter.getTipoFunzionale()));
				
				if(filter.getTipoTopologico() != null)
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.join(giunzione.tipoTopologico.getMetadata().getName()).get("id"), filter.getTipoTopologico()));
				
				/** ORDINAMENTO **/
				if(filter.getPageOrder() != null) {
					Expression<?> expression = null;
					
					if(filter.getPageOrder().getColumn().equalsIgnoreCase("tipoTopologico"))
						expression = root.join(filter.getPageOrder().getColumn(), JoinType.LEFT).get(giunzione.tipoTopologico.descrizione.getMetadata().getName());
					
					else if(filter.getPageOrder().getColumn().equalsIgnoreCase("tipoFunzionale"))
						expression = root.join(filter.getPageOrder().getColumn(), JoinType.LEFT).get(giunzione.tipoFunzionale.descrizione.getMetadata().getName());
					
					else
						expression = root.get(filter.getPageOrder().getColumn());
					
					
					if(filter.getPageOrder().getDir().equalsIgnoreCase("ASC"))
						query.orderBy(criteriaBuilder.asc(expression));
					else
						query.orderBy(criteriaBuilder.desc(expression));
					
				}
				
				return predicate;
				
			}

		};
	
	}
	
	/**
	 * Metodo interno che restituisce un predicato per il filtraggio di un intervallo di date
	 * @param startRange filtro per la data iniziale
	 * @param endRange filtro per la data finale
	 * @param fromColumn nome della colonna per l'inizio dell'intervallo
	 * @param toColumn nome della colonna per la fine dell'intervallo
	 * @param builder criteria builder
	 * @param root root
	 * @return il predicato per il filtraggio dell'intervallo
	 */
	private static Predicate buildIntervalPredicate(final Date startRange, final Date endRange, final String fromColumn, final String toColumn, final CriteriaBuilder builder, final Root<?> root) {
		
		Predicate predicate = null;
		if(endRange != null && startRange != null)
			predicate = builder.and(builder.greaterThanOrEqualTo(root.get(fromColumn), startRange), 
								    builder.lessThanOrEqualTo(root.get(toColumn), endRange));
			
		else if(startRange != null)
			predicate = builder.greaterThanOrEqualTo(root.<Date>get(fromColumn), startRange);
		
		else if (endRange != null)
			predicate = builder.greaterThanOrEqualTo(root.<Date>get(toColumn), endRange);
		
		return predicate;
		
	}

}
