package it.eng.tz.urbamid.catasto.batch.service;

import it.eng.tz.urbamid.catasto.exception.BatchServiceException;
import it.eng.tz.urbamid.catasto.exception.CatastoServiceException;
import it.eng.tz.urbamid.catasto.geokettle.util.KitchenLogLevel;
import it.eng.tz.urbamid.catasto.util.ImportType;
import it.eng.tz.urbamid.catasto.web.dto.DettaglioBatchDTO;

public interface BatchService {
	
	/**
	 * Metodo che si occupa di avviare il batch di import dei dati catastali.
	 * 
	 * @param kitchenLogLevel è il livello di log con cui impostare il log di kitchen.sh
	 * @param batchType è il tipo di batch da avviare
	 * 
	 * @return {@link DettaglioBatchDTO}
	 * 
	 * @throws BatchServiceException
	 */
	public DettaglioBatchDTO avviaBatchImportDatiCatastali(KitchenLogLevel kitchenLogLevel, ImportType batchType) throws BatchServiceException;

	/**
	 * Metodo per il recupero dei dettagli di esecuzione di un batch dato il suo id.
	 * 
	 * @param jobExecutionId è l'identificativo del batch.
	 * 
	 * @return un oggetto {@link DettaglioBatchDTO} che incapsula i dettagli di esecuzione di un batch.
	 * 
	 * @throws CatastoServiceException
	 */
	public DettaglioBatchDTO getByJobExexutionId(Long jobExecutionId) throws CatastoServiceException;
	
	/**
	 * Metodo per il recupero dei dettagli del job in corso
	 * 
	 * @return un oggetto {@link DettaglioBatchDTO} che incapsula i dettagli di esecuzione di un batch.
	 * 
	 * @throws CatastoServiceException
	 */
	public DettaglioBatchDTO getExecutionJob( ) throws CatastoServiceException;

}
