package it.eng.tz.urbamid.wrappergeo.shp2pgsql.service;

import java.io.File;

import it.eng.tz.urbamid.wrappergeo.exception.WrapperGeoServiceException;

/**
 * Interfaccia che definisce i metodi del service di conversione degli shapefile
 * in script SQL.
 */
public interface Shp2PgsqlService {
	
	/**
	 * Converte uno shapefile in uno script SQL.
	 * 
	 * @param importType è il tipo di import (ATTUALITA/AGGIORNAMENTO)
	 * @param shapefile è lo shapefile da convertire
	 * @param schema è lo schema del database nella quale importare i dati
	 * @param table è la tabella del database nella quale importare i dati
	 * @param geometryColumnName è il nome della colonna che conterrà la geometria
	 * @param sqlStatementsMode è la modalità di creazione degli statement (DROP TABLE, APPEND etc..)
	 * 
	 * @throws WrapperGeoServiceException
	 */
	public String convertiShapefileInScriptSQL(String nameShapeFile, String shapefile, String schema, String table, String geometryColumnName) 
			throws WrapperGeoServiceException;
	
	/**
	 * Converte uno shapefile in uno script SQL.
	 * 
	 * @param importType è il tipo di import (ATTUALITA/AGGIORNAMENTO)
	 * @param shapefile è lo shapefile da convertire
	 * @param schema è lo schema del database nella quale importare i dati
	 * @param table è la tabella del database nella quale importare i dati
	 * @param geometryColumnName è il nome della colonna che conterrà la geometria
	 * @param sqlStatementsMode è la modalità di creazione degli statement (DROP TABLE, APPEND etc..)
	 * 
	 * @throws WrapperGeoServiceException
	 */
	public String convertiShapefileInScriptSQL(String nameShapeFile, File shapefile, String schema, String table, String geometryColumnName) 
			throws WrapperGeoServiceException;
	
	public String reproject (String nameShapeFile, File shapefile, String schema, String table, String geometryColumnName) throws WrapperGeoServiceException;
	
}
