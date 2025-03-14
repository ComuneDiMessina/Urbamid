package it.eng.tz.urbamid.catasto.util;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import it.eng.tz.urbamid.catasto.filter.SoggettoFilter;
import it.eng.tz.urbamid.catasto.persistence.model.Soggetto;

/**
 * Classe che racchiude una serie di metodi statici per agevolare l'esecuzione delle query sui repository JPA.
 */
@SuppressWarnings("serial")
public class RepositoryUtils {
	
	//COSTANTI PER I NOMI DELLE COLONNE DELLE TABELLE
	
	//SOGGETTI
	protected static final String SG_COLUMN_COD_COMUNE = "codComune";
	protected static final String SG_COLUMN_SEZIONE = "sezione";
	protected static final String SG_COLUMN_TIPO_SOGGETTO = "tipoSoggetto";
	protected static final String SG_COLUMN_COGNOME = "cognome";
	protected static final String SG_COLUMN_NOME = "nome";
	protected static final String SG_COLUMN_SESSO = "sesso";
	protected static final String SG_COLUMN_DATA_NASCITA = "dataNascita";
	protected static final String SG_COLUMN_LUOGO_NASCITA = "luogoNascita";
	protected static final String SG_COLUMN_CODICE_FISCALE = "codiceFiscale";
	protected static final String SG_COLUMN_DENOMINAZIONE = "denominazione";
	protected static final String SG_COLUMN_SEDE = "sede";
	protected static final String SG_COLUMN_INDICAZIONI_SUPPLEMENTARI = "indicazioniSupplementari";

	//UNITA IMMOBILIARI
	protected static final String UIU_COLUMN_ID_IMMOBILE = "idImmobile";
	protected static final String UIU_COLUMN_COD_COMUNE = "codComune";
	protected static final String UIU_COLUMN_ZONA = "zona";
	protected static final String UIU_COLUMN_CATEGORIA = "categoria";
	protected static final String UIU_COLUMN_CLASSE = "classe";
	protected static final String UIU_COLUMN_INDIRIZZO = "indirizzo";
	protected static final String UIU_COLUMN_CIVICO1 = "civico1";
	protected static final String UIU_COLUMN_CIVICO2 = "civico2";
	protected static final String UIU_COLUMN_SEZIONE = "sezione";
	protected static final String UIU_COLUMN_FOGLIO = "foglio";
	protected static final String UIU_COLUMN_NUMERO = "numero";
	protected static final String UIU_COLUMN_SUBALTERNO = "subalterno";
	protected static final String UIU_COLUMN_CONSISTENZA = "consistenza";
	protected static final String UIU_COLUMN_SUPERFICIE = "superficie";
	protected static final String UIU_COLUMN_RENDITA_LIRE = "renditaLire";
	protected static final String UIU_COLUMN_RENDITA_EURO = "renditaEuro";
	protected static final String UIU_COLUMN_PARTITA = "partita";
	protected static final String UIU_COLUMN_SOPPRESSO = "idMutazFin";

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
	public static Specification<Soggetto> buildSoggettoPredicate(SoggettoFilter filter) {
		return new Specification<Soggetto>() {
			
			public Predicate toPredicate( Root<Soggetto> root, CriteriaQuery<?> query, CriteriaBuilder builder ) {
				Predicate predicate = builder.conjunction();

				if(StringUtils.hasText(filter.getNome())) {
					predicate = builder.and(
							predicate, 
							builder.equal(
									root.get(SG_COLUMN_NOME), 
									filter.getNome( ).toUpperCase() 
									)
							);
				}

				if(StringUtils.hasText( filter.getCognome() ) ) {
					predicate = builder.and(
							predicate, 
							builder.equal(
									root.get(SG_COLUMN_COGNOME), 
									filter.getCognome( ).toUpperCase()  
									)
							);
				}

				if(StringUtils.hasText( filter.getSesso() ) ) {
					predicate = builder.and(
							predicate, 
							builder.equal(
									root.get(SG_COLUMN_SESSO), 
									filter.getSesso( ) 
									)
							);
				}

				if(StringUtils.hasText( filter.getCodiceFiscale() ) ) {
					predicate = builder.and(
							predicate, 
							builder.equal(
									root.get(SG_COLUMN_CODICE_FISCALE), 
									filter.getCodiceFiscale( ).toUpperCase() 
									)
							);
				}

				if(StringUtils.hasText( filter.getNote() ) ) {
					predicate = builder.and(
							predicate, 
							builder.equal(
									root.get(SG_COLUMN_INDICAZIONI_SUPPLEMENTARI), 
									filter.getNote( ) 
									)
							);
				}

				if(StringUtils.hasText(filter.getDenominazione())) {
					predicate = builder.and(
							predicate, 
							builder.like(
									root.get(SG_COLUMN_DENOMINAZIONE), 
									"%" + filter.getDenominazione( ).toUpperCase() +"%" 
									)
							);
				}
				
				
				if(filter.getTipo().equals("P")) {
					predicate = builder.and(predicate, builder.equal(root.get(SG_COLUMN_TIPO_SOGGETTO), "P"));
					
					if(StringUtils.hasText( filter.getComune() ) ) {
						predicate = builder.and(
								predicate, 
								builder.equal(
										root.get(SG_COLUMN_LUOGO_NASCITA), 
										filter.getComune( ) 
										)
								);
					}
				} else if (filter.getTipo().equals("G") ) {
					predicate = builder.and(predicate, builder.equal(root.get(SG_COLUMN_TIPO_SOGGETTO), "G"));

					if(StringUtils.hasText( filter.getComune() ) ) {
						predicate = builder.and(
								predicate, 
								builder.equal(
										root.get(SG_COLUMN_SEDE), 
										filter.getComune( ) 
										)
								);
					}
				}
				//INTERVALLO VALIDITA
				Predicate validIntervalPredicate = buildIntervalPredicate(
						filter.getDataNascitaDa(), filter.getDataNascitaA(),
						SG_COLUMN_DATA_NASCITA, SG_COLUMN_DATA_NASCITA,
						builder, root);
				if(validIntervalPredicate != null)
					predicate = builder.and(predicate, validIntervalPredicate);
				query.distinct(true);
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
			final Date startRange, final Date endRange, 
			final String fromColumn, final String toColumn, 
			final CriteriaBuilder builder, final Root<?> root ){
		
		Predicate predicate = null;
		if( endRange != null && startRange != null){
			//escludo gli intervalli non validi (2) piuttosto che considerare gli intervalli validi (4)
			predicate = builder.not(
					builder.or(
							builder.greaterThanOrEqualTo( root.get(fromColumn), endRange ),
							builder.lessThanOrEqualTo( root.get(toColumn), startRange )
							)
					);
		} else if( startRange != null ) {
			predicate = builder.greaterThanOrEqualTo( root.<Date>get(fromColumn), startRange );
		} else if( endRange != null ) {
			predicate = builder.lessThanOrEqualTo( root.<Date>get(toColumn), endRange );
		}
		
		return predicate;
		
	}

}
