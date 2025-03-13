package it.eng.tz.urbamid.catasto.persistence.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import it.eng.tz.urbamid.catasto.persistence.model.batch.BatchStepExecution;

public interface BatchStepExecutionRepository extends PagingAndSortingRepository<BatchStepExecution, Long> {
	
	boolean existsByJobExecution_jobExecutionId(Long jobExecutionId);
	
}
