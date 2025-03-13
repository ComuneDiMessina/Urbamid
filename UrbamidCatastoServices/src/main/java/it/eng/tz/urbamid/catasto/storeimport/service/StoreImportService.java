package it.eng.tz.urbamid.catasto.storeimport.service;

import it.eng.tz.urbamid.catasto.exception.CatastoServiceException;
import it.eng.tz.urbamid.catasto.util.ImportType;

public interface StoreImportService {
	
	/**
	 * Metodo che si occupa di settare lo SRID della colonna geometrica per le tabelle catastali.
	 * 
	 * @param importType è il tipo di import (aggiornamento/attualita). Viene utilizzato per risalire alla
	 * cartella da storicizzare.
	 * 
	 * @return una stringa con le informazioni di log.
	 * 
	 * @throws CatastoServiceException
	 */
	public String updateGeometry(ImportType importType) throws CatastoServiceException;
	
	/**
	 * Metodo che si occupa di storicizzare degli shape file utilizzate per l'import del catasto.
	 * 
	 * @param importType è il tipo di import (aggiornamento/attualita). Viene utilizzato per risalire alla
	 * cartella da storicizzare.
	 * 
	 * @return una stringa con le informazioni di log.
	 * 
	 * @throws CatastoServiceException
	 */
	public String storeImportFile(ImportType importType) throws CatastoServiceException;
	
	
	/**
	 * Metodo che si occupa di cancellare le cartelle utilizzate per l'import del catasto.
	 * 
	 * @param importType è il tipo di import (aggiornamento/attualita). Viene utilizzato per risalire alla
	 * cartella da storicizzare.
	 * 
	 * @return una stringa con le informazioni di log.
	 * 
	 * @throws CatastoServiceException
	 */
	public String deleteImportFile(ImportType importType) throws CatastoServiceException;
	
}
