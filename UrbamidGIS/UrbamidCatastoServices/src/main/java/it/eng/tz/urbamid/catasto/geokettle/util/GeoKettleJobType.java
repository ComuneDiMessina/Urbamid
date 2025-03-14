package it.eng.tz.urbamid.catasto.geokettle.util;

public enum GeoKettleJobType {
	
//	JOB_IMPORT_CATASTO_VERSION("VERSIONE", "JOB_IMPORT_CATASTO_VERSIONE"),
	JOB_ATTUALITA_IMPORT_CATASTO("ATTUALITA", "JOB_IMPORT_CATASTO"),
	JOB_ATTUALITA_CXF_TO_SHAPE("ATTUALITA", "JOB_CXF_TO_SHAPE"),
	JOB_AGGIORNAMENTO_IMPORT_CATASTO("AGGIORNAMENTO", "JOB_IMPORT_CATASTO"),
	JOB_AGGIORNAMENTO_CXF_TO_SHAPE("AGGIORNAMENTO", "JOB_CXF_TO_SHAPE");
	
	/**
	 * Tipo di job di import (ATTUALITA/AGGIORNAMENTO)
	 */
	private String jobImportType;
	
	/**
	 * Filename per il job (attualità ed aggiornamento vengono eseguiti sugli stessi file .kjb
	 * ma specificando o meno se troncare o meno le tabelle di partenza.
	 */
	private String filename;
	
	/**
	 * Estensione per i file di tipo job geo kettle
	 */
	private static final String GEO_KETTLE_JOB_FILE_EXTENSION = ".kjb";
	
	/**
	 * Costruttore.
	 * 
	 * @param jobImportType tipo di job di import.
	 */
	private GeoKettleJobType(String jobImportType, String filename) {
		this.jobImportType = jobImportType;
		this.filename = filename;
	}
	
	/**
	 * 
	 * @return
	 */
	public String filename() {
		StringBuilder stringBuilder = new StringBuilder(this.filename);
		stringBuilder.append(GEO_KETTLE_JOB_FILE_EXTENSION);
		return stringBuilder.toString();
	}
	
	/**
	 * 
	 * @return
	 */
	public String jobImportType() {
		return this.jobImportType;
	}
}
