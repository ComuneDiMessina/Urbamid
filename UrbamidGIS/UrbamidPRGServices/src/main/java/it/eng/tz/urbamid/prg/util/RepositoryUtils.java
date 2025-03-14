package it.eng.tz.urbamid.prg.util;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import it.eng.tz.urbamid.prg.filter.CduFilter;
import it.eng.tz.urbamid.prg.filter.TipoDocumentoFilter;
import it.eng.tz.urbamid.prg.filter.VarianteFilter;
import it.eng.tz.urbamid.prg.persistence.model.Cdu;
import it.eng.tz.urbamid.prg.persistence.model.QTipoDocumento;
import it.eng.tz.urbamid.prg.persistence.model.TipoDocumento;
import it.eng.tz.urbamid.prg.persistence.model.Variante;

/**
 * Classe che racchiude una serie di metodi statici per agevolare l'esecuzione delle query sui repository JPA.
 */
@SuppressWarnings("serial")
public class RepositoryUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(RepositoryUtils.class.getName());
	
	//COSTANTI PER I NOMI DELLE COLONNE DELLE TABELLE
	
	//SOGGETTI
	protected static final String VARIANTE_COLUMN_ENTE = "codComune";
	protected static final String VARIANTE_COLUMN_NOME = "nome";
	protected static final String VARIANTE_COLUMN_DESCRIZIONE = "descrizione";
	protected static final String VARIANTE_COLUMN_DATA_ADOZ = "dataDelAdoz";
	protected static final String VARIANTE_COLUMN_DATA_APPR = "dataDelAppr";

	protected static final String DOCUMENTO_COLUMN_CODICE = "codice";
	protected static final String DOCUMENTO_COLUMN_DESCRIZIONE = "descrizione";
	protected static final String DOCUMENTO_COLUMN_DESCRIZIONE_CDU = "descrizione_cdu";
	protected static final String DOCUMENTO_COLUMN_NOTE = "note";

	protected static final String CDU_COLUMN_PROTOCOLLO = "protocollo";
	protected static final String CDU_COLUMN_DATA_CREAZIONE = "dataCreazione";

	//COSTANTE PER IL CARATTERE JOLLY DELLA LIKE
	protected static final String JOLLY_CHARACTER = "%";
	
	/**
	 * Metodo che restituisce una stringa con all'inizio ed alla fine il carattere jolly % per le like.
	 * @param value il valore da usare per la like
	 * @return
	 */
	public static String buildLikeValue(String value) {
		return new StringBuilder(JOLLY_CHARACTER).append(value).append(JOLLY_CHARACTER).toString();
	}
	
	/**
	 * Costruisce l'oggetto {@link Specification} per eseguire la query di ricerca dei pass, costruendo i predicati della clausola
	 * where attraverso l'oggetto {@link PassFilter} che incapsula i filtri di ricerca.
	 * @param filter oggetto che incapsula i filtri di ricerca.
	 * @return l'oggetto {@link Specification}
	 */
	public static Specification<Variante> buildVariantePredicate(VarianteFilter filter) {
		return new Specification<Variante>() {
			
			public Predicate toPredicate( Root<Variante> root, CriteriaQuery<?> query, CriteriaBuilder builder ) {
				Predicate predicate = builder.conjunction();
				Expression<?> expression = null;
				
				if(StringUtils.hasText(filter.getEnte())) {
					predicate = builder.and(predicate, builder.equal(root.get(VARIANTE_COLUMN_ENTE), filter.getEnte().toUpperCase()));
				}

				if(StringUtils.hasText(filter.getNome())) {
					predicate = builder.and(predicate, builder.like(builder.upper(root.get(VARIANTE_COLUMN_NOME)), buildLikeValue(filter.getNome().toUpperCase())));
				}

				if(StringUtils.hasText(filter.getDescrizione())) {
					predicate = builder.and(predicate, builder.like(builder.upper(root.get(VARIANTE_COLUMN_DESCRIZIONE)), buildLikeValue(filter.getDescrizione().toUpperCase())));
				}

				//INTERVALLO VALIDITA
				Predicate validIntervalPredicateAdoz = buildIntervalPredicate(
						filter.getDataAdozioneDal(), filter.getDataAdozioneAl(),
						VARIANTE_COLUMN_DATA_ADOZ, VARIANTE_COLUMN_DATA_ADOZ,
						builder, root);
				if(validIntervalPredicateAdoz != null)
					predicate = builder.and(predicate, validIntervalPredicateAdoz);

				Predicate validIntervalPredicateAppr = buildIntervalPredicate(
						filter.getDataApprovazioneDal(), filter.getDataApprovazioneAl(),
						VARIANTE_COLUMN_DATA_APPR, VARIANTE_COLUMN_DATA_APPR,
						builder, root);
				if(validIntervalPredicateAppr != null)
					predicate = builder.and(predicate, validIntervalPredicateAppr);

				/** ORDINAMENTO **/
				if(filter.getNumColonna().equalsIgnoreCase("0")) {
					expression = root.get(VARIANTE_COLUMN_NOME);
				} else if(filter.getNumColonna().equalsIgnoreCase("1")) {
					expression = root.get(VARIANTE_COLUMN_DATA_ADOZ);
				} else if(filter.getNumColonna().equalsIgnoreCase("2")) {
					expression = root.get(VARIANTE_COLUMN_DATA_APPR);
				} else if(filter.getNumColonna().equalsIgnoreCase("3")) {
					expression = root.get(VARIANTE_COLUMN_DESCRIZIONE);
				}
				
				if(filter.getDir().equalsIgnoreCase("asc")) {
					query.orderBy(builder.asc(expression));
				} else {
					query.orderBy(builder.desc(expression));
				}
				
//				query.distinct(true);
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
	public static Predicate buildIntervalPredicate(
			final Date startRange, Date endRange, 
			final String fromColumn, final String toColumn, 
			final CriteriaBuilder builder, final Root<?> root ){

		Predicate predicate = null;
		if(endRange != null && startRange != null) {
			predicate = builder.and(builder.greaterThanOrEqualTo(root.get(fromColumn), startRange), 
								    builder.lessThanOrEqualTo(root.get(toColumn), endRange));
			
		} else if(startRange != null) {
			predicate = builder.greaterThanOrEqualTo(root.<Date>get(fromColumn), startRange);
		} else if (endRange != null) {
			predicate = builder.lessThanOrEqualTo(root.<Date>get(toColumn), endRange);
		}
		
		return predicate;
		
	}
	
	/**
	 * Costruisce l'oggetto {@link Specification} per eseguire la query di ricerca dei tipo di documento, costruendo i predicati della clausola
	 * where attraverso l'oggetto {@link filter} che incapsula i filtri di ricerca.
	 * @param filter oggetto che incapsula i filtri di ricerca.
	 * @return l'oggetto {@link Specification}
	 */
	public static Specification<TipoDocumento> buildDocumentoPredicate(TipoDocumentoFilter filter) {
		return new Specification<TipoDocumento>() {

			public Predicate toPredicate(Root<TipoDocumento> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				
				Predicate predicate = criteriaBuilder.conjunction();
				
				QTipoDocumento tipoDocumento = QTipoDocumento.tipoDocumento;
				
				if(StringUtils.hasText(filter.getCodice())) {
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.upper(root.get(tipoDocumento.codice.getMetadata().getName())), buildLikeValue(filter.getCodice().toUpperCase())));
				}
				
				if(StringUtils.hasText(filter.getDescrizione())) {
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.upper(root.get(tipoDocumento.descrizione.getMetadata().getName())), buildLikeValue(filter.getDescrizione().toUpperCase())));
				}
				
				if(StringUtils.hasText(filter.getDescrizioneCDU())) {
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.upper(root.get(tipoDocumento.descrizioneCDU.getMetadata().getName())), buildLikeValue(filter.getDescrizioneCDU().toUpperCase())));
				}
				
				if(StringUtils.hasText(filter.getNote())) {
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.upper(root.get(tipoDocumento.note.getMetadata().getName())), buildLikeValue(filter.getNote().toUpperCase())));
				}

				return predicate;
			}
		};
	}

	public static Specification<Cdu> buildCduPredicate(CduFilter filter) {
		return new Specification<Cdu>() {
			
			public Predicate toPredicate( Root<Cdu> root, CriteriaQuery<?> query, CriteriaBuilder builder ) {
				Predicate predicate = builder.conjunction();
				Expression<?> expression = null;
				
				if(StringUtils.hasText(filter.getProtocollo())) {
					predicate = builder.and(predicate, builder.like(builder.upper(root.get(CDU_COLUMN_PROTOCOLLO)), buildLikeValue(filter.getProtocollo().toUpperCase())));
				}

				if(filter.getDataCreazione() != null) {
					predicate = builder.and(predicate, builder.equal(root.get(CDU_COLUMN_DATA_CREAZIONE), filter.getDataCreazione()));
				}

				return predicate;
			}
		};
	}

}
