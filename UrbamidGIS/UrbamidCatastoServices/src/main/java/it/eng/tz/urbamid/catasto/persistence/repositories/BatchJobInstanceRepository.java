package it.eng.tz.urbamid.catasto.persistence.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import it.eng.tz.urbamid.catasto.persistence.model.batch.BatchJobInstance;

public interface BatchJobInstanceRepository extends PagingAndSortingRepository<BatchJobInstance, Long>{
	
}
