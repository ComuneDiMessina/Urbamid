package it.eng.tz.urbamid.catasto.persistence.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import it.eng.tz.urbamid.catasto.persistence.model.batch.BatchJobExecution;

public interface BatchJobExecutionRepository extends PagingAndSortingRepository<BatchJobExecution, Long>{
	
	Page<BatchJobExecution> findByJobInstance_nomeJob(String nomeJob, Pageable pageable);
	
	Page<BatchJobExecution> findByJobInstance_nomeJobAndParametri_valoreStringa(String nomeJob, String tenantCode, Pageable pageable);
	
	Optional<BatchJobExecution> findByJobExecutionIdAndJobInstance_nomeJob(Long id, String nomeJob);
	
	@Query(value="SELECT * FROM public.u_cat_catasto_batch_job_execution WHERE end_time IS null", nativeQuery = true)
	BatchJobExecution getExecutionJob();
	
}
