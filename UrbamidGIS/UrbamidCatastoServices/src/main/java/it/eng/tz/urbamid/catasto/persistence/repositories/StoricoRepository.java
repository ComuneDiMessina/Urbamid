package it.eng.tz.urbamid.catasto.persistence.repositories;

import java.util.Date;
import java.util.EnumMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import it.eng.tz.urbamid.catasto.persistence.util.TabellaStorico;

/**
 * Repository per la storicizzazione delle tabelle con le informazioni geografiche.
 */
@Component
public class StoricoRepository {
	
	/**
	 * Logger
	 */
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	//PARAMETRI QUERY STORICIZZAZIONE
	private static final String PARAMETRO_VERSIONE = "versione";
	private static final String PARAMETRO_DATA_INIZIO_VALIDITA = "dataInizioValidita";
	private static final String PARAMETRO_DATA_FINE_VALIDITA = "dataFineValidita";
	
	//QUERY RECUPERO NUMERO VERSIONE
	//Recupero il numero di versione come il valore massimo attuale per la versione incrementato di 1
	public static final String QUERY_NUMERO_VERSIONE = 
			"SELECT COALESCE( ( SELECT MAX(versione)+1 FROM storico.%s ), 1);";

	/**
	 * Persistence context
	 */
	@PersistenceContext
	EntityManager entityManager;
	
	/**
	 * Storicizza una tabella tra quelle contenenti dati geografici.
	 * 
	 * @param tabellaStorico è il valore dell'enumerazione {@link TabellaStorico} che indica la tabella da 
	 * storicizzare.
	 * @param dataInizioValidita è la data di inizio validità.
	 * @param dataFineValidita è la data di fine validità.
	 * 
	 * @return il numero di righe affette dalla storicizzazione.
	 */
	@Transactional
	public Long storicizzaTabellaStorico(
			TabellaStorico tabellaStorico, 
			Date dataInizioValidita, Date dataFineValidita) {
		
		Assert.notNull(tabellaStorico, "enum value for TabellaStorico MUST not be null!");
		
		String tabella = tabellaStorico.table();
		String queryStoricizzazione = tabellaStorico.queryStoricizzazione();
		
		LOG.debug("Effettuo la storicizzazione della tabella {}.", tabella);
		
		Query queryNumeroVersione = this.entityManager.createNativeQuery(
				String.format(QUERY_NUMERO_VERSIONE, tabella));
		Number versione = (Number) queryNumeroVersione.getSingleResult();
		LOG.debug("Numero di versione per la storicizzazione: {}.", versione);
		int righeAffette = 
				entityManager.createNativeQuery(queryStoricizzazione)
					.setParameter(PARAMETRO_VERSIONE, versione)
					.setParameter(PARAMETRO_DATA_INIZIO_VALIDITA, dataInizioValidita)
					.setParameter(PARAMETRO_DATA_FINE_VALIDITA, dataFineValidita)
						.executeUpdate();
		LOG.debug("Sono state storicizzate {} righe.", righeAffette);
		return Long.valueOf(righeAffette);
	}
	
	/**
	 * Storicizza tutte le tabelle contenenti dati geografici.
	 * 
	 * @param dataInizioValidita è la data di inizio validità.
	 * @param dataFineValidita è la data di fine validità.
	 * 
	 * @return una mappa che indica, per ogni tabella, quante righe sono state inserite dall'operazione di
	 * storicizzazione.
	 */
	@Transactional
	public Map<TabellaStorico, Long> storicizzaTabelleGeografiche(Date dataInizioValidita, Date dataFineValidita) {
		EnumMap<TabellaStorico, Long> mappaRigheAffette = new EnumMap<>(TabellaStorico.class);
		for(TabellaStorico tabellaStorico : TabellaStorico.values()) {
			Long righeAffette = this.storicizzaTabellaStorico(tabellaStorico, dataInizioValidita, dataFineValidita);
			mappaRigheAffette.put(tabellaStorico, righeAffette);
		}
		return mappaRigheAffette;
	}
	
}

