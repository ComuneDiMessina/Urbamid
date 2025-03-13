package it.eng.tz.urbamid.catasto.geokettle.service;

import it.eng.tz.urbamid.catasto.exception.CatastoServiceException;
import it.eng.tz.urbamid.catasto.geokettle.util.GeoKettleJobType;
import it.eng.tz.urbamid.catasto.geokettle.util.KitchenLogLevel;
import it.eng.tz.urbamid.catasto.web.dto.ParametriAvvioJobDTO;

public interface GeoKettleService {
	
	/**
	 * Esegue uno script kitchen.sh con i parametri in argomento.
	 * 
	 * @param kitchenLogLevel è il livello di log di GeoKettle.
	 * @param tipo è il tipo di job da eseguire
	 * @param databaseName è il nome del database
	 * @param databaseHost è l'hostname del database
	 * @param databasePassword è la password dello user di connessione al database
	 * @param databasePort è il numero di porto del database
	 * @param databaseUser è lo user di connsessione al database
	 * 
	 * @return una stringa col log di GeoKettle (con la quale si puo' pensare di creare un report di resoconto)
	 * 
	 * @throws CatastoServiceException
	 */
	String eseguiScriptKitchenSH( KitchenLogLevel kitchenLogLevel, GeoKettleJobType tipo, String databaseName,
			String databaseHost, String databasePassword, String databasePort, String databaseUser) 
					throws CatastoServiceException;
	
	/**
	 * Esegue uno script kitchen.sh con i parametri in argomento.
	 * 
	 * @param parametri è un oggetto wrapper che incapsula i parametri da passare allo script.
	 * @param tipo è il tipo di job da eseguire.
	 * 
	 * @return una stringa col log di GeoKettle (con la quale si puo' pensare di creare un report di resoconto)
	 * 
	 * @throws CatastoServiceException
	 */
	String eseguiScriptKitchenSH(ParametriAvvioJobDTO parametri, GeoKettleJobType tipo) throws CatastoServiceException;
	
	/**
	 * Esegue uno script kitchen.sh desumendo i parametri di esecuzione dall'environment di Spring.
	 * 
	 * @param kitchenLogLevel livello di log prodotto da kitchen.sh
	 * @param tipo è il tipo di job da eseguire
	 * 
	 * @throws CatastoServiceException
	 */
	String eseguiScriptKitchenSH(KitchenLogLevel kitchenLogLevel, GeoKettleJobType tipo) throws CatastoServiceException;
	
//	String eseguiScriptKitchenVersionSH(
//			KitchenLogLevel kitchenLogLevel, GeoKettleJobType tipo, String databaseName,
//			String databaseHost, String databasePassword, String databasePort, String databaseUser) 
//					throws CatastoServiceException;
//	String eseguiScriptKitchenVersionSH(KitchenLogLevel kitchenLogLevel, GeoKettleJobType tipo) throws CatastoServiceException;
//	String eseguiScriptKitchenVersionSH( ParametriAvvioJobDTO parametri, GeoKettleJobType tipo) 
//			throws CatastoServiceException;
}
