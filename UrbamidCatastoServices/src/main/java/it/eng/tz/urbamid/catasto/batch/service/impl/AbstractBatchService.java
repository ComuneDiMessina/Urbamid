package it.eng.tz.urbamid.catasto.batch.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import it.eng.tz.urbamid.catasto.configuration.BatchConfiguration;
import it.eng.tz.urbamid.catasto.exception.BatchAlreadyRunningException;
import it.eng.tz.urbamid.catasto.exception.BatchServiceException;
import it.eng.tz.urbamid.catasto.geokettle.util.KitchenLogLevel;
import it.eng.tz.urbamid.catasto.persistence.repositories.BatchJobExecutionContextRepository;
import it.eng.tz.urbamid.catasto.persistence.repositories.BatchJobExecutionParamRepository;
import it.eng.tz.urbamid.catasto.persistence.repositories.BatchJobExecutionRepository;
import it.eng.tz.urbamid.catasto.persistence.repositories.BatchJobInstanceRepository;
import it.eng.tz.urbamid.catasto.persistence.repositories.BatchStepExecutionContextRepository;
import it.eng.tz.urbamid.catasto.persistence.repositories.BatchStepExecutionRepository;
import it.eng.tz.urbamid.catasto.util.ImportType;
import it.eng.tz.urbamid.catasto.web.dto.converter.DettaglioBatchConverter;
import it.eng.tz.urbamid.catasto.web.dto.converter.DettaglioStepBatchConverter;
import it.eng.tz.urbamid.catasto.web.dto.converter.ParametriBatchConverter;

public abstract class AbstractBatchService {
	
	/**
	 * Logger
	 */
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * Job launcher asincrono
	 */
	@Autowired
	@Qualifier("asynchronousJobLauncher")
	protected JobLauncher asynchronousJobLauncher;	
	
	@Autowired
	protected RunIdIncrementer runIdIncrementer;
	
	/**
	 * Job builder factory
	 */
	@Autowired
	protected JobBuilderFactory jobBuilderFactory;
	
	/**
	 * Primo degli step che il batch deve eseguire e che si occupa dell'import dei dati censuari a db
	 */
	@Autowired
	@Qualifier("stepImportCatasto")
	protected Step stepImportCatasto;
	
	/**
	 * Secondo degli step che il batch deve eseguire e che si occupa della conversione dei file .CXF
	 * in shapefile.
	 */
	@Autowired
	@Qualifier("stepCxfToShape")
	protected Step stepCxfToShape;

	/**
	 * Terzo degli step che il batch deve eseguire e che si occupa dell'import degli shapefile
	 * nelle relative tabelle a DB.
	 */
	@Autowired
	@Qualifier("stepShapefileToDB")
	protected Step stepShapefileToDB;
	
	/**
	 * Ultimo degli step che il batch deve eseguire e che si occupa della storicizzazione della cartella
	 * di ATTUALITA'/AGGIORNAMENTO
	 */
	@Autowired
	@Qualifier("stepStoreImportCatasto")
	protected Step stepStoreImportCatasto;
	
	@Autowired
	protected JobRegistry jobRegistry;
	

/***
 *        ____  __________  ____  _____ ______________  ______  __
 *       / __ \/ ____/ __ \/ __ \/ ___//  _/_  __/ __ \/ __ \ \/ /
 *      / /_/ / __/ / /_/ / / / /\__ \ / /  / / / / / / /_/ /\  / 
 *     / _, _/ /___/ ____/ /_/ /___/ // /  / / / /_/ / _, _/ / /  
 *    /_/ |_/_____/_/    \____//____/___/ /_/  \____/_/ |_| /_/   
 *                                                                
 */
	
	/**
	 * Repository batch job
	 */
	@Autowired
	protected BatchJobExecutionRepository repository;

	/**
	 * Repository batch job instance
	 */
	@Autowired
	protected BatchJobInstanceRepository repositoryBatchJobInstance;
	
	/**
	 * Repository batch job execution context
	 */
	@Autowired
	protected BatchJobExecutionContextRepository repositoryBatchJobExecutionContext;
	
	/**
	 * Repository batch job execution parameters
	 */
	@Autowired
	protected BatchJobExecutionParamRepository repositoryBatchJobExecutionParam;
	
	/**
	 * Repository batch step execution
	 */
	@Autowired
	protected BatchStepExecutionRepository repositoryBatchStepExecution;
	
	/**
	 * Repository batch step execution context
	 */
	@Autowired
	protected BatchStepExecutionContextRepository repositoryBatchStepExecutionContext;
	

/***
 *       __________  _   ___    ____________  ________________ 
 *      / ____/ __ \/ | / / |  / / ____/ __ \/_  __/ ____/ __ \
 *     / /   / / / /  |/ /| | / / __/ / /_/ / / / / __/ / /_/ /
 *    / /___/ /_/ / /|  / | |/ / /___/ _, _/ / / / /___/ _, _/ 
 *    \____/\____/_/ |_/  |___/_____/_/ |_| /_/ /_____/_/ |_|  
 *                                                             
 */

	/**
	 * Converter dettaglio batch job
	 */
	@Autowired
	protected DettaglioBatchConverter converter;
	
	/**
	 * Converter dettaglio degli step del batch job
	 */
	@Autowired
	protected DettaglioStepBatchConverter converterStep;
	
	/**
	 * Converter dei parametri di avvio di un batch job
	 */
	@Autowired
	protected ParametriBatchConverter converterParametri;
	
	@Autowired
	protected JobExplorer jobExplorer;
	
	@Autowired
	protected JobRepository jobRepository;
	
	@Autowired
	protected JobOperator jobOperator;
	
	/**
	 * Crea un nuovo Job di import dati catastali da dare in pasto allo scheduler
	 * @return il nuovo Job da lanciare
	 * @throws IOException 
	 */
	protected Job creaNuovoJobImportDatiCatastali() throws BatchServiceException {
		
		try {
			Set<Long> runningExecutions = jobOperator.getRunningExecutions(BatchConfiguration.JOB_IMPORT_DATI_CATASTALI_NAME);
			if(!runningExecutions.isEmpty()) {
				throw new BatchAlreadyRunningException();
			}
		} catch (NoSuchJobException e) {
			logger.debug("Si è verificata una eccezione nel recupero delle running execution del job.", e);
			logger.debug("NOTA BENE: PROBABILMENTE SI È VERIFICATA PERCHÈ NON CI SONO JOB IN ESECUZIONE.");
			//DO NOTHING
		}
		// @formatter:off
		Job job = jobBuilderFactory
				.get(BatchConfiguration.JOB_IMPORT_DATI_CATASTALI_NAME)
					//.incrementer(runIdIncrementer)
						.start(stepImportCatasto)
							.next(stepCxfToShape)
								.next(stepShapefileToDB)
									.next(stepStoreImportCatasto)
										.build(); 
		if(logger.isDebugEnabled())
			logger.debug("Creata una nuova istanza di Job col nome {} tramite il JobBuilderFactory.", job.getName());
		return job;
		// @formatter:on
	}
	
	/**
	 * Crea i parametri del job di import massivo.
	 * I parametri sono quattro:
	 * <ol>
	 * 	<li><b>UUID</b></li>
	 * 	<li><b>Data avvio</b></li>
	 *  <li><b>Livello log</b></li>
	 *  <li><b>Tipo job</b></li>
	 * </ol>
	 * 
	 * @return i parametri del job.
	 */
	protected JobParameters creaParametriJobImportDatiCatastali(KitchenLogLevel kitchenLogLevel, ImportType batchType) {
		String uuid = UUID.randomUUID().toString();
		Date now = new Date();
		kitchenLogLevel = kitchenLogLevel != null ? kitchenLogLevel : KitchenLogLevel.BASIC;
		if(logger.isDebugEnabled()) {
			logger.debug("Parametri del job di import massivo:");
			logger.debug("UUID: {}.", uuid);
			logger.debug("Data avvio: {}.", now);
			logger.debug("Livello log: {}", kitchenLogLevel.name());
			logger.debug("Tipo batch: {}", batchType.name());
		}
		// @formatter:off
		return new JobParametersBuilder()
				.addString(BatchConfiguration.JOB_PARAMETER_UUID, uuid)
				.addDate(BatchConfiguration.JOB_PARAMETER_DATA_AVVIO, now)
				.addString(BatchConfiguration.JOB_PARAMETER_LIVELLO_LOG, kitchenLogLevel.level())
				.addString(BatchConfiguration.JOB_PARAMETER_TIPO_JOB, batchType.name())
					.toJobParameters();
		// @formatter:on
	}

}
