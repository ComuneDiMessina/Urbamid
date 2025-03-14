package it.eng.tz.urbamid.catasto.persistence.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import it.eng.tz.urbamid.catasto.persistence.model.AggiornamentoCatasto;

public interface JpaRepositoryAggiornamentoCatasto extends PagingAndSortingRepository<AggiornamentoCatasto, Long>, JpaSpecificationExecutor<AggiornamentoCatasto> {

	@Query(value="SELECT * FROM public.u_cat_aggiornamenti WHERE data_fine_validita is not null ORDER BY data_fine_validita desc limit 1", nativeQuery = true)
	AggiornamentoCatasto getDataUltimoAggiornamento();

	@Query(value="SELECT * FROM public.u_cat_aggiornamenti WHERE data_inizio_validita is not null ORDER BY data_inizio_validita asc limit 1", nativeQuery = true)
	AggiornamentoCatasto getDataPrimoAggiornamento();

}
