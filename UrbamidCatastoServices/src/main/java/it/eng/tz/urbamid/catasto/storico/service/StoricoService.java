package it.eng.tz.urbamid.catasto.storico.service;

import java.util.Date;
import java.util.Map;

import it.eng.tz.urbamid.catasto.exception.CatastoServiceException;
import it.eng.tz.urbamid.catasto.persistence.util.TabellaStorico;

public interface StoricoService {
	
	/**
	 * Storicizza le tabelle geometriche impostando una data di inizio/fine validità
	 * 
	 * @param dataInizioValidita è la data di inizio validità
	 * @param dataFineValidita è la data di fine validità
	 * 
	 * @return una mappa con i risultati di storicizzazione
	 * 
	 * @throws CatastoServiceException
	 */
	Map<TabellaStorico, Long> storicizza(Date dataInizioValidita, Date dataFineValidita) throws CatastoServiceException;
	
	/**
	 * Storicizza le tabelle geometriche recuperando le date di inizio/fine validità dalla tabella
	 * u_cat_aggiornamenti (dove vengono salvate dal job di import)
	 * 
	 * @return una mappa con i risultati di storicizzazione
	 * 
	 * @throws CatastoServiceException
	 */
	Map<TabellaStorico, Long> storicizza() throws CatastoServiceException;
	
}
