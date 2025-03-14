package it.eng.tz.urbamid.catasto.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import it.eng.tz.urbamid.catasto.persistence.model.AggiornamentoCatasto;

public interface AggiornamentoCatastoRepository extends PagingAndSortingRepository<AggiornamentoCatasto, Long>{

	List<AggiornamentoCatasto> findTop2ByOrderByDataCreazioneDesc();
	
}
