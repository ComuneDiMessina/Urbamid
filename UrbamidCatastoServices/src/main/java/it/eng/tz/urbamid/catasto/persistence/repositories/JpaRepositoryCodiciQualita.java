package it.eng.tz.urbamid.catasto.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import it.eng.tz.urbamid.catasto.persistence.model.CodiciQualita;

public interface JpaRepositoryCodiciQualita extends PagingAndSortingRepository<CodiciQualita, Long>, JpaSpecificationExecutor<CodiciQualita> {

	@Query(value="SELECT * FROM public.u_cat_codici_qualita ", nativeQuery = true)
	List<CodiciQualita> codiciQualita();

}
