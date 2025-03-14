package it.eng.tz.urbamid.wrappergeo.geoserver.service;

import org.springframework.beans.factory.annotation.Value;

public abstract class AbstractGeoserverRicercheService {
			  
	@Value("${wrappergeo.default.geometry.column.name}")
	protected String DEFAULT_GEOMETRY_COLUMN_NAME;
	
	@Value("${wrappergeo.default.database.import.schema}")
	protected String DEFAULT_DATABASE_SCHEMA_IMPORT;
	
	protected static final String PATTERN_PUNTO_SQL = ".*\\.sql";
	
	protected static final String PATTERN_PUNTO_SHP = ".*\\.shp";
	
	protected static final String PATTERN_PUNTO_ZIP = ".*\\.zip";
	
	protected static final long WATCHDOG_TIMER_TIMEOUT = 42_0__0___0____0;
	
	
	@Value("${urbamid.rest.geoserver.endpoint.url}")
	protected String GEOSERVER_ENDPOINT;
	protected static final String GEOSERVER_WORKSPACES				= "/workspaces/";
	protected static final String GEOSERVER_DATASTORES				= "/datastores/";
	protected static final String GEOSERVER_TABLE_SUFFIX			= "u_geo_";
	protected static final String GEOSERVER_NATIVECRS				= "EPSG:4326";
	protected static final String GEOSERVER_SRS						= "EPSG:4326";			
	protected static final boolean GEOSERVER_ENABLED				= true;

	
}
