package it.eng.tz.urbamid.catasto.shp2pgsql.service;

import java.io.File;

import it.eng.tz.urbamid.catasto.exception.CatastoServiceException;
import it.eng.tz.urbamid.catasto.util.ImportType;

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
	 * @throws CatastoServiceException
	 */
	public String convertiShapefileInScriptSQL(
			ImportType importType, String shapefile, String schema, String table, String geometryColumnName) 
					throws CatastoServiceException;
	
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
	 * @throws CatastoServiceException
	 */
	public String convertiShapefileInScriptSQL(
			ImportType importType, File shapefile, String schema, String table, String geometryColumnName) 
					throws CatastoServiceException;
	
}
