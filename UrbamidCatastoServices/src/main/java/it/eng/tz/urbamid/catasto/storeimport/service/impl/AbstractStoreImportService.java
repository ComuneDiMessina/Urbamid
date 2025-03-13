package it.eng.tz.urbamid.catasto.storeimport.service.impl;

import org.springframework.beans.factory.annotation.Value;

public abstract class AbstractStoreImportService {
	
	@Value("${catasto.default.geometry.column.name}")
	protected String DEFAULT_GEOMETRY_COLUMN_NAME;
	
	@Value("${catasto.default.database.import.schema}")
	protected String DEFAULT_DATABASE_SCHEMA_IMPORT;
	
	protected static final String PATTERN_PUNTO_SQL = ".*\\.sql";
	
	protected static final String PATTERN_PUNTO_SHP = ".*\\.shp";
	
	protected static final long WATCHDOG_TIMER_TIMEOUT = 42_0__0___0____0;

	
}
