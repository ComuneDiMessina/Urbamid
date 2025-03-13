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
import it.eng.tz.urbamid.catasto.shapefile.service.ShapefileService;
import it.eng.tz.urbamid.catasto.storage.service.ImportCatastoPathService;
import it.eng.tz.urbamid.catasto.util.ImportType;

public class JobShapeToDB 
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
	private final ShapefileService shapefileService;
	
	/**
	 * Service per la navigazione di path/file applicativi.
	 */
	private final ImportCatastoPathService pathService;
	
	/**
	 * Costruttore.
	 * 
	 * @param shapefileService service per l'import degli shapefile
	 */
	public JobShapeToDB(ShapefileService shapefileService, ImportCatastoPathService pathService) {
		Assert.notNull(shapefileService, "ShapefileService MUST not be null but don't panic!");
		this.shapefileService = shapefileService;
		this.pathService = pathService;
	}

	@Override
	public void beforeStep(StepExecution stepExecution) {
		LOG.debug("Step di import degli shapefile nel database iniziato per job con id {}", stepExecution.getJobExecutionId());
		//recupero il tipo di job dai parametri di avvio del batch
		String tipoJob = stepExecution.getJobExecution().getJobParameters().getString(BatchConfiguration.JOB_PARAMETER_TIPO_JOB);
		this.batchType = ImportType.fromString(tipoJob);
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws CatastoServiceException {
		try {
			LOG.debug("Esecuzione STEP di import degli shapefile per batch {}.", this.batchType.name());
			String stepExecutionLog = this.shapefileService.importaShapefile(this.batchType);
			salvaInformazioniDiLog(chunkContext, stepExecutionLog);
		} catch(CatastoServiceException cse ) {
			LOG.error("Si è verificato un errore nell'import degli shapefile nel database.");
			throw cse;
		}
		return RepeatStatus.FINISHED;
	}
	
	@Override
	public ExitStatus afterStep(StepExecution stepExecution) { 
		LOG.debug("Step di import degli shapefile nel database per il job con id {} completato con codice di uscita {}.", 
				stepExecution.getJobExecutionId(), stepExecution.getExitStatus());
		return stepExecution.getExitStatus();
	}
	
	public ShapefileService getShapefileService() {
		return this.shapefileService;
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