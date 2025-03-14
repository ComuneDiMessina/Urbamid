package it.eng.tz.urbamid.catasto.persistence.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import it.eng.tz.urbamid.catasto.persistence.model.batch.BatchJobExecutionContext;

public interface BatchJobExecutionContextRepository extends PagingAndSortingRepository<BatchJobExecutionContext, Long>{

}
