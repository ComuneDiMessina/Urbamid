package it.eng.tz.urbamid.catasto.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import it.eng.tz.urbamid.catasto.persistence.model.CategorieCatastali;

public interface JpaRepositoryCategorieCatastali extends PagingAndSortingRepository<CategorieCatastali, String>, JpaSpecificationExecutor<CategorieCatastali> {

	@Query(value="SELECT * FROM public.u_cat_categorie_catastali order by codice asc", nativeQuery = true)
	List<CategorieCatastali> categorieCatastali();

}
