package it.eng.tz.urbamid.wrappergeo.service;

import it.eng.tz.urbamid.wrappergeo.exception.WrapperGeoServiceException;

public interface ConvertShapefileService {
	
	/**
	 * Metodo che si occupa dell'import di tutti gli shapefile nel DB.
	 * Vengono elaborati gli shapefile nella relativa cartella del file system condiviso e lanciati
	 * due script tramite apache common exec: il primo si occupa di convertire lo shapefile in un file
	 * sql ed il secondo di eseguire gli statement del file sql per l'import dei dati a DB.
	 * 
	 * @param importType è il tipo di import (aggiornamento/attualita). Viene utilizzato per risalire alla
	 * cartella dove risiedono gli shapefile.
	 * 
	 * @return una stringa con le informazioni di log.
	 * 
	 * @throws CatastoServiceException
	 */
	public String importaShapefile(String nameShape) throws WrapperGeoServiceException;
	
}
