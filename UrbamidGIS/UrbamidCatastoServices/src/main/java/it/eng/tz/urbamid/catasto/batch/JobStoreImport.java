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
import it.eng.tz.urbamid.catasto.storage.service.ImportCatastoPathService;
import it.eng.tz.urbamid.catasto.storeimport.service.StoreImportService;
import it.eng.tz.urbamid.catasto.util.ImportType;

public class JobStoreImport 
	extends AbstractJob
		implements Tasklet, StepExecutionListener {

	/**
	 * Logger
	 */
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * Tipo di batch job
	 */
	private ImportType batchType;
	
	/**
	 * Service per l'esecuzione dello script kitchen.sh
	 */
	private final StoreImportService storeImportService;
	
	/**
	 * Service per la navigazione di path/file applicativi.
	 */
	private final ImportCatastoPathService pathService;
	
	/**
	 * Costruttore.
	 * 
	 * @param storeImportService service per la storicizzazione dell'import effettuato
	 */
	public JobStoreImport(StoreImportService storeImportService, ImportCatastoPathService pathService) {
		Assert.notNull(storeImportService, "StoreImportService MUST not be null but don't panic!");
		this.storeImportService = storeImportService;
		this.pathService = pathService;
	}

	@Override
	public void beforeStep(StepExecution stepExecution) {
		LOG.debug("Step per la storicizzazione delle folder import iniziato per job con id {}", stepExecution.getJobExecutionId());
		//recupero il tipo di job dai parametri di avvio del batch
		String tipoJob = stepExecution.getJobExecution().getJobParameters().getString(BatchConfiguration.JOB_PARAMETER_TIPO_JOB);
		this.batchType = ImportType.fromString(tipoJob);
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws CatastoServiceException {
		StringBuilder log = new StringBuilder();
		try {
			LOG.debug("Esecuzione STEP per la storicizzazione degli shape file e la cancellazione della folder import per batch {}.", this.batchType.name());
			log.append(
					this.storeImportService.updateGeometry(this.batchType)
				);
			log.append(
						this.storeImportService.storeImportFile(this.batchType)
					);
			log.append(
					this.storeImportService.deleteImportFile(this.batchType)
					);
			
			
			
			salvaInformazioniDiLog(chunkContext, log.toString());
		} catch(CatastoServiceException cse ) {
			LOG.error("Si è verificato un errore nel salvataggio degli shapefile.");
			throw cse;
		}
		return RepeatStatus.FINISHED;
	}
	
	@Override
	public ExitStatus afterStep(StepExecution stepExecution) { 
		LOG.debug("Step per la storicizzazione degli shape file del job con id {} completato con codice di uscita {}.", 
				stepExecution.getJobExecutionId(), stepExecution.getExitStatus());
		return stepExecution.getExitStatus();
	}
	
	public StoreImportService getStoreImportService() {
		return this.storeImportService;
	}

	public ImportType getBatchType() {
		return batchType;
	}
	
	public ImportCatastoPathService getPathService() {
		return pathService;
	}
	
	public Logger getLogger() {
		return this.LOG;
	}

}