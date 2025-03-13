package it.eng.tz.urbamid.catasto.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import it.eng.tz.urbamid.catasto.persistence.model.CodiciDiritto;

public interface JpaRepositoryCodiciDiritto extends PagingAndSortingRepository<CodiciDiritto, String>, JpaSpecificationExecutor<CodiciDiritto> {

	@Query(value="SELECT * FROM public.u_cat_codici_diritto ", nativeQuery = true)
	List<CodiciDiritto> codiciDiritto();

}
