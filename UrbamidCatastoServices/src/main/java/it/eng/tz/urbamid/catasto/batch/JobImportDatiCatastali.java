package it.eng.tz.urbamid.catasto.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.util.Assert;

import it.eng.tz.urbamid.catasto.configuration.BatchConfiguration;
import it.eng.tz.urbamid.catasto.exception.CatastoServiceException;
import it.eng.tz.urbamid.catasto.exception.KettleJobExecutionException;
import it.eng.tz.urbamid.catasto.geokettle.service.GeoKettleService;
import it.eng.tz.urbamid.catasto.geokettle.util.GeoKettleJobType;
import it.eng.tz.urbamid.catasto.geokettle.util.KitchenLogLevel;
import it.eng.tz.urbamid.catasto.storage.service.ImportCatastoPathService;
import it.eng.tz.urbamid.catasto.util.ImportType;

public class JobImportDatiCatastali 
	extends AbstractJob 
		implements Tasklet, StepExecutionListener {

	/**
	 * Logger
	 */
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * Service per l'esecuzione dello script kitchen.sh
	 */
	private final GeoKettleService geoKettleService;
	
	/**
	 * Service per la navigazione di path/file applicativi.
	 */
	private final ImportCatastoPathService pathService;
	
	/**
	 * Livello di log
	 */
	private KitchenLogLevel kitchenLogLevel;
	
	/**
	 * Tipo di batch job
	 */
	private ImportType batchType;
	
	public JobImportDatiCatastali(GeoKettleService geoKettleService, ImportCatastoPathService pathService) {
		Assert.notNull(geoKettleService, "GeoKettleService MUST not be null but don't panic!");
		Assert.notNull(pathService, "ImportCatastoPathService MUST not be null but don't panic!");
		this.geoKettleService = geoKettleService;
		this.pathService = pathService;
	}

	@Override
	public void beforeStep(StepExecution stepExecution) {
		LOG.debug("Step di import dati catastali iniziato per il job con id {}", stepExecution.getJobExecutionId());
		//recupero il livello di log dai parametri di avvio del batch
		String logLevel = stepExecution.getJobExecution().getJobParameters().getString(BatchConfiguration.JOB_PARAMETER_LIVELLO_LOG);
		String tipoJob = stepExecution.getJobExecution().getJobParameters().getString(BatchConfiguration.JOB_PARAMETER_TIPO_JOB);
		this.kitchenLogLevel = KitchenLogLevel.fromString(logLevel);
		this.batchType = ImportType.fromString(tipoJob);
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws CatastoServiceException {
		String stepExecutionLog = null;
//		try {
//			LOG.debug("Esecuzione JOB_AGGIORNAMENTO_IMPORT_CATASTO...");
//			stepExecutionLog = this.geoKettleService.eseguiScriptKitchenSH(kitchenLogLevel, GeoKettleJobType.JOB_IMPORT_CATASTO_VERSION);
//		} catch(KettleJobExecutionException kjee ) {
//			LOG.error("Si è verificato un errore nell'esecuzione dello script kitchen.sh per la conversione dei file CXF in SHAPEFILE.");
//			throw kjee;
//		}
		
		try {
			if(ImportType.AGGIORNAMENTO.equals(this.batchType)) {
				LOG.debug("Esecuzione JOB_AGGIORNAMENTO_IMPORT_CATASTO...");
				stepExecutionLog = this.geoKettleService.eseguiScriptKitchenSH(kitchenLogLevel, GeoKettleJobType.JOB_AGGIORNAMENTO_IMPORT_CATASTO);
			}
			if(ImportType.ATTUALITA.equals(this.batchType)) {
				LOG.debug("Esecuzione JOB_ATTUALITA_IMPORT_CATASTO...");
				stepExecutionLog = this.geoKettleService.eseguiScriptKitchenSH(kitchenLogLevel, GeoKettleJobType.JOB_ATTUALITA_IMPORT_CATASTO);
			}
		} catch(KettleJobExecutionException kjee ) {
			LOG.error("Si è verificato un errore nell'esecuzione dello script kitchen.sh per la conversione dei file CXF in SHAPEFILE.");
			throw kjee;
		}
		salvaInformazioniDiLog(chunkContext, stepExecutionLog);
		return RepeatStatus.FINISHED;
	}
	
	@Override
	public ExitStatus afterStep(StepExecution stepExecution) { 
		LOG.debug("Step di import dati catastali per il job con id {} completato con codice di uscita {}.", 
				stepExecution.getJobExecutionId(), stepExecution.getExitStatus());
		return stepExecution.getExitStatus();
	}

	public GeoKettleService getGeoKettleService() {
		return geoKettleService;
	}

	public KitchenLogLevel getKitchenLogLevel() {
		return kitchenLogLevel;
	}

	public ImportType getBatchType() {
		return batchType;
	}

	public ImportCatastoPathService getPathService() {
		return this.pathService;
	}

	public Logger getLogger() {
		return this.LOG;
	}

}
