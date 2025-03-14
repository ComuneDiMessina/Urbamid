package it.eng.tz.urbamid.catasto.geokettle.util;

/**
 * Enumerazione che riporta i parametri previsti dal job (file .kjt) di GeoKettle per l'import 
 * dei dati catastali (si possono quindi consultare anche avviando GeoKettle ed andando a vedere i parametri del
 * JOB). Questi parametri vengono passati poi da linea di comando mediante l'esecuzione dello script kitchen.sh 
 * (opzione -param).
 */
public enum GeoKettleJobImportParameter {
	
	//DB CONNECTION
	DATABASE_HOST,
	DATABASE_NAME,
	DATABASE_PASSWORD,
	DATABASE_PORT,
	DATABASE_USER,
	//DIRECTOTY DI BASE DOVE TROVARE I FILE KJB & KTR
	BASE_JOB_DIRECTORY,
	//DIRECTORY DI BASE PER COSTRUIRE I PATH PER I DATI SULLA QUALE AGISCE LA PROCEDURA
	BASE_JOB_DATA_DIRECTORY,
	//INDICA IL TIPO DI JOB, OVVERO ATTUALITA/AGGIORNAMENTO
	JOB_IMPORT_TYPE;
}
