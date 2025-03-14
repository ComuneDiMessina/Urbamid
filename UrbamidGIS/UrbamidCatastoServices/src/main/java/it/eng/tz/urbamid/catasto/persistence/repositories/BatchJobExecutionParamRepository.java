package it.eng.tz.urbamid.catasto.persistence.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import it.eng.tz.urbamid.catasto.persistence.model.batch.BatchJobExecutionParam;

public interface BatchJobExecutionParamRepository extends PagingAndSortingRepository<BatchJobExecutionParam, Long>{
	Long deleteByJobExecution_jobExecutionId(Long jobExecutionId);
	boolean existsByJobExecution_jobExecutionId(Long jobExecutionId);
}
