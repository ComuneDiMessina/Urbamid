package it.eng.tz.urbamid.prg.persistence.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import it.eng.tz.urbamid.prg.persistence.model.TipoDocumento;

public interface JpaRepositoryTipoDocumento extends PagingAndSortingRepository<TipoDocumento, Long>, JpaSpecificationExecutor<TipoDocumento>, JpaRepository<TipoDocumento, Long>{

	String 	DEFAULT_SORT_PROPERTY = "codice";
	Sort DEFAULT_SORT = new Sort( Sort.Direction.ASC, DEFAULT_SORT_PROPERTY );
	
	@Query(value="SELECT COUNT(*) FROM public.u_prg_tipo_documento where UPPER(codice) = :codice", nativeQuery = true)
	int countTipoDocumentoDaCodice(@Param("codice") String codice);
	
}
