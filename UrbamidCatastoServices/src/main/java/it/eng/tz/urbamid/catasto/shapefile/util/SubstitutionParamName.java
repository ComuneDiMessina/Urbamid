package it.eng.tz.urbamid.catasto.shapefile.util;

/**
 * Enumerazione che contiene tutte le stringhe di sostituzione per costruire la mappa da dare
 * all'executor di Apache Common Exec per poi lanciare lo script shp2pgsql
 */
public enum SubstitutionParamName {
	SHAPEFILE,
	GEOMETRY_COLUMN,
	ENCODING,
	SRID,
	DATABASE_HOST,
	DATABASE_NAME,
	DATABASE_SCHEMA,
	DATABASE_TABLE,
	DATABASE_USER,
	DATABASE_PASSWORD;
}
