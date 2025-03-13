package it.eng.tz.urbamid.catasto.batch.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.urbamid.catasto.batch.service.BatchService;
import it.eng.tz.urbamid.catasto.configuration.BatchConfiguration;
import it.eng.tz.urbamid.catasto.exception.BatchServiceException;
import it.eng.tz.urbamid.catasto.exception.CatastoEntityNotFoundException;
import it.eng.tz.urbamid.catasto.exception.CatastoServiceException;
import it.eng.tz.urbamid.catasto.geokettle.util.KitchenLogLevel;
import it.eng.tz.urbamid.catasto.persistence.model.batch.BatchJobExecution;
import it.eng.tz.urbamid.catasto.util.ImportType;
import it.eng.tz.urbamid.catasto.web.dto.DettaglioBatchDTO;
import it.eng.tz.urbamid.catasto.web.dto.DettaglioStepBatchDTO;

@Service
public class BatchServiceImpl extends AbstractBatchService implements BatchService {
	

	@Override
	public DettaglioBatchDTO avviaBatchImportDatiCatastali(KitchenLogLevel kitchenLogLevel, ImportType batchType) throws BatchServiceException {
		try {
			Job jobImportDatiCatastali = creaNuovoJobImportDatiCatastali();
			JobParameters jobParameters = creaParametriJobImportDatiCatastali(kitchenLogLevel, batchType);
			//LET'S RUN THIS JOB!
			JobExecution jobExecution = asynchronousJobLauncher.run(jobImportDatiCatastali, jobParameters);
			Long jobExecutionId = jobExecution.getId();
			Long jobInstanceId = jobExecution.getJobInstance().getId();
			logger.debug("Il job di import dati catastali con execution id {} ed instance id {} è stato schedulato con successo.", jobExecutionId, jobInstanceId);
			DettaglioBatchDTO dettaglio = new DettaglioBatchDTO();
			dettaglio.setJobInstanceId(jobInstanceId);
			dettaglio.setJobExecutionId(jobExecutionId);
			return dettaglio;
		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			logger.error("Si è verificato un errore nel lanciare il job di import massivo.");
			throw new BatchServiceException("Si è verificato un errore nel lanciare il job di import massivo.", e);
		}
	}
	
	@Override
	@Transactional(rollbackFor=CatastoServiceException.class, readOnly=true)
	public DettaglioBatchDTO getByJobExexutionId( Long jobExecutionId ) throws CatastoServiceException {
		logger.debug("Recupero dettagli del batch con execution id {}.", jobExecutionId);
		BatchJobExecution jobExecution = 
				this.repository.findByJobExecutionIdAndJobInstance_nomeJob(jobExecutionId, BatchConfiguration.JOB_IMPORT_DATI_CATASTALI_NAME)
					.orElseThrow(CatastoEntityNotFoundException::new);
		DettaglioBatchDTO dettaglio =  converter.toDto(jobExecution);
		//PARAMETRI
		if(jobExecution.getParametri() != null && !jobExecution.getParametri().isEmpty())
			dettaglio.setParametri(converterParametri.toDto( jobExecution.getParametri()));
		//DETTAGLI STEP
		if(jobExecution.getListaStep() != null && !jobExecution.getListaStep().isEmpty()) {
			List<DettaglioStepBatchDTO> listaStep = converterStep.toDto(new ArrayList<>(jobExecution.getListaStep()));
			Collections.sort(listaStep, (item1,item2) -> item1.getDataAvvio().compareTo(item2.getDataAvvio()));
			dettaglio.setListaStep(listaStep);
		}
		return dettaglio;
	}
	
	@PostConstruct
    private void init() {
		try {
			if(logger.isDebugEnabled()) {
				logger.debug("CONCLUDO FORZATAMENTE TUTTI I BATCH JOB CHE NON SI ERANO CONCLUSI.");
			}
			
//			int count = jobExplorer.getJobInstanceCount(BatchConfiguration.JOB_IMPORT_DATI_CATASTALI_NAME);
//			
//			List<JobInstance> jobs = jobExplorer.findJobInstancesByJobName(BatchConfiguration.JOB_IMPORT_DATI_CATASTALI_NAME, 0, count);
//			for (JobInstance jobInstance : jobs) {
//				jobInstance.
//			}
			
			Set<JobExecution> jobExecutions = jobExplorer.findRunningJobExecutions(BatchConfiguration.JOB_IMPORT_DATI_CATASTALI_NAME);
			for (JobExecution jobExecution : jobExecutions) {
				logger.debug("PROVO AD AGGIORNARE LO STATO DEL JOB CON ID {}.", jobExecution.getId());
				jobExecution.upgradeStatus(BatchStatus.FAILED);
				jobExecution.setExitStatus( new ExitStatus("COMPLETED", "FALLITO FORZATAMENTE ALL'AVVIO DELL'APPLICAZIONE"));
				jobExecution.setEndTime(new Date());
				jobRepository.update(jobExecution);
				logger.debug("IL JOB CON ID {} è STATO AGGIORNATO A FAILED.",jobExecution.getId());
//				boolean stopMessageSent = jobOperator.stop(jobExecution.getId());
//				logger.debug("IL METODO STOP HA FORNITO IL SEGUENTE VALORE: {}.", stopMessageSent);
			}
//			Set<Long> runningExecs = jobOperator.getRunningExecutions(BatchConfiguration.JOB_IMPORT_DATI_CATASTALI_NAME);
//			for (Long executionId : runningExecs) {
//				logger.debug("PROVO A STOPPARE IL JOB CON ID {}.", executionId);
//				boolean stopMessageSent = jobOperator.stop(executionId);
//				logger.debug("IL METODO STOP HA FORNITO IL SEGUENTE VALORE: {}.", stopMessageSent);
//			}
		} catch (Exception e) {
			logger.error("Si è verificato un errore nello stop del batch all'avvio del microservices. ", e);
		} 
    }

	
	@Override
	@Transactional(rollbackFor=CatastoServiceException.class, readOnly=true)
	public DettaglioBatchDTO getExecutionJob( ) throws CatastoServiceException {
		
		logger.debug("Recupero dettagli del batch in corso.");
		DettaglioBatchDTO dettaglio = null;
		BatchJobExecution executionJob = 
				this.repository.getExecutionJob();
		
		if (executionJob!=null)
			dettaglio =  converter.toDto(executionJob);
			
		return dettaglio;
	}
}
