package it.eng.tz.urbamid.catasto.batch;

import java.util.Map;

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
import it.eng.tz.urbamid.catasto.persistence.util.TabellaStorico;
import it.eng.tz.urbamid.catasto.python.service.PythonService;
import it.eng.tz.urbamid.catasto.storage.service.ImportCatastoPathService;
import it.eng.tz.urbamid.catasto.storico.service.StoricoService;
import it.eng.tz.urbamid.catasto.util.ImportType;

public class JobCxfToShape 
	extends AbstractJob 
		implements Tasklet, StepExecutionListener {

	/**
	 * Logger
	 */
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * Service per l'esecuzione dello script kitchen.sh CXF_TO_SHAPE.
	 */
	private final GeoKettleService geoKettleService;
	
	/**
	 * Service per l'esecuzione dello script sh che lancia la procedura python.
	 */
	private final PythonService pythonService;
	
	/**
	 * Service per l'esecuzione dello script sh che lancia la procedura python.
	 */
	private final StoricoService storicoService;
	
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
	
	/**
	 * Costruttore.
	 * @param geoKettleService è il service
	 */
	public JobCxfToShape(
			GeoKettleService geoKettleService, PythonService pythonService, 
			StoricoService storicoService, ImportCatastoPathService pathService ) {
		Assert.notNull(geoKettleService, "GeoKettleService MUST not be null but don't panic!");
		Assert.notNull(pythonService, "PythonService MUST not be null but don't panic!");
		Assert.notNull(storicoService, "StoricoService MUST not be null but don't panic!");
		Assert.notNull(pathService, "ImportCatastoPathService MUST not be null but don't panic!");
		this.geoKettleService = geoKettleService;
		this.pythonService = pythonService;
		this.storicoService = storicoService;
		this.pathService = pathService;
	}

	@Override
	public void beforeStep(StepExecution stepExecution) {
		LOG.debug("Step conversione da CXF a SHAPEFILE iniziato per il job con id {}", stepExecution.getJobExecutionId());
		//recupero il livello di log dai parametri di avvio del batch
		String logLevel = stepExecution.getJobExecution().getJobParameters().getString(BatchConfiguration.JOB_PARAMETER_LIVELLO_LOG);
		String tipoJob = stepExecution.getJobExecution().getJobParameters().getString(BatchConfiguration.JOB_PARAMETER_TIPO_JOB);
		this.kitchenLogLevel = KitchenLogLevel.fromString(logLevel);
		this.batchType = ImportType.fromString(tipoJob);
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws CatastoServiceException {
		StringBuilder log = new StringBuilder();
		try {
			if(ImportType.AGGIORNAMENTO.equals(this.batchType)) {
				LOG.debug("Esecuzione JOB_AGGIORNAMENTO_CXF_TO_SHAPE...");
				LOG.debug("ESEGUO LA STORICIZZAZIONE DELLE TABELLE...");
				Map<TabellaStorico, Long> risultato = this.storicoService.storicizza();
				for (Map.Entry<TabellaStorico,Long> entry : risultato.entrySet()) {
					String tabella = entry.getKey().table();
					LOG.debug("Storicizzati {} elementi della tabella {}.", entry.getValue(), tabella);
				}
				log.append(
						this.geoKettleService.eseguiScriptKitchenSH(kitchenLogLevel, GeoKettleJobType.JOB_AGGIORNAMENTO_CXF_TO_SHAPE)
						);
			}
			if(ImportType.ATTUALITA.equals(this.batchType)) {
				LOG.debug("Esecuzione JOB_ATTUALITA_CXF_TO_SHAPE...");
				log.append(
				this.geoKettleService.eseguiScriptKitchenSH(kitchenLogLevel, GeoKettleJobType.JOB_ATTUALITA_CXF_TO_SHAPE)
				);
			}
			log.append(
					this.pythonService.eseguiScriptPython(this.batchType)
					);
			salvaInformazioniDiLog(chunkContext, log.toString());
		} catch(KettleJobExecutionException kjee ) {
			LOG.error("Si è verificato un errore nell'esecuzione dello script kitchen.sh per la conversione dei file CXF in SHAPEFILE.");
			throw kjee;
		}
		return RepeatStatus.FINISHED;
	}
	
	@Override
	public ExitStatus afterStep(StepExecution stepExecution) { 
		LOG.debug("Step conversione da CXF a SHAPEFILE per il job con id {} completato con codice di uscita {}.", 
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

	public PythonService getPythonService() {
		return pythonService;
	}

	public StoricoService getStoricoService() {
		return storicoService;
	}

	public ImportCatastoPathService getPathService() {
		return pathService;
	}

	public Logger getLogger() {
		return this.LOG;
	}

}