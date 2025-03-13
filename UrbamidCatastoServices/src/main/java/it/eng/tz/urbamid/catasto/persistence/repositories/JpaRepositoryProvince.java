package it.eng.tz.urbamid.catasto.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import it.eng.tz.urbamid.catasto.persistence.model.Province;

public interface JpaRepositoryProvince extends PagingAndSortingRepository<Province, Long>, JpaSpecificationExecutor<Province> {

	@Query(value="SELECT * FROM public.u_cat_province order by denominazione asc", nativeQuery = true)
	List<Province> getProvince();

}
