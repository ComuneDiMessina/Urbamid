package it.eng.tz.urbamid.catasto.geokettle.util;

/**
 * Enumerazione che contiene tutte le stringhe di sostituzione per costruire la mappa da dare
 * all'executor di Apache Common Exec per poi lanciare lo script kitchen.sh
 */
public enum SubstitutionParamName {
	/**
	 * Nome del parametro relativo al path del file .kjb da avviare con lo script
	 * kitchen.sh di GeoKettle.
	 */
	JOB_FILE,
	
	/**
	 * Nome del parametro relativo al livello di log da usare per l'avvio dello script
	 * kitche.sh.
	 */
	LOG_LEVEL,
	DATABASE_NAME,
	DATABASE_USER,
	DATABASE_PASS,
	DATABASE_HOST,
	DATABASE_PORT,
	BASE_JOB_DIRECTORY;
}
