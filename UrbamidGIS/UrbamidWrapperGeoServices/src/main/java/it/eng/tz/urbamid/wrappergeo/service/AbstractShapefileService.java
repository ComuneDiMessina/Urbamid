package it.eng.tz.urbamid.wrappergeo.service;

import org.springframework.beans.factory.annotation.Value;

public abstract class AbstractShapefileService {
			  
	@Value("${wrappergeo.default.geometry.column.name}")
	protected String DEFAULT_GEOMETRY_COLUMN_NAME;
	
	@Value("${wrappergeo.default.database.import.schema}")
	protected String DEFAULT_DATABASE_SCHEMA_IMPORT;
	
//	@Value("${wrappergeo.temp.database.import.schema}")
//	protected String TEMP_DATABASE_SCHEMA_IMPORT;
	
	protected static final String PATTERN_PUNTO_SQL = ".*\\.sql";
	
	protected static final String PATTERN_PUNTO_SHP = ".*\\.shp";
	
	protected static final String PATTERN_PUNTO_ZIP = ".*\\.zip";
	
	protected static final long WATCHDOG_TIMER_TIMEOUT = 42_0__0___0____0;

	
}
