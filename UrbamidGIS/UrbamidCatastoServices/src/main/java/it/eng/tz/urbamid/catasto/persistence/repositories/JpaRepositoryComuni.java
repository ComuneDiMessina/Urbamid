package it.eng.tz.urbamid.catasto.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import it.eng.tz.urbamid.catasto.persistence.model.Comuni;

public interface JpaRepositoryComuni extends PagingAndSortingRepository<Comuni, Long>, JpaSpecificationExecutor<Comuni> {

	@Query(value="SELECT * FROM public.u_cat_comuni ", nativeQuery = true)
	List<Comuni> getComuni();

	@Query(value="SELECT * FROM public.u_cat_comuni WHERE id_provincia = :idProvincia order by denominazione asc", nativeQuery = true)
	List<Comuni> getComuniByProvincia(@Param("idProvincia") Long idProvincia);

}
