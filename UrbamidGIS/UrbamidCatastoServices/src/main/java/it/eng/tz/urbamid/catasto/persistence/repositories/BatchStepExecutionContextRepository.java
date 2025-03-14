package it.eng.tz.urbamid.catasto.persistence.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import it.eng.tz.urbamid.catasto.persistence.model.batch.BatchStepExecutionContext;

public interface BatchStepExecutionContextRepository  extends PagingAndSortingRepository<BatchStepExecutionContext, Long> {

	boolean existsByStepExecution_JobExecution_jobExecutionId(Long jobExecutionId);
	
}
