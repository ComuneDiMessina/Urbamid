package it.eng.tz.urbamid.toponomastica.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.eng.tz.urbamid.toponomastica.persistence.model.CippoChilometrico;

@Repository
public interface JpaRepositoryCippoChilometrico extends PagingAndSortingRepository<CippoChilometrico, Long>, JpaRepository<CippoChilometrico, Long>, JpaSpecificationExecutor<CippoChilometrico> {

//	String 	DEFAULT_SORT_PROPERTY = "misura";
//	Sort DEFAULT_SORT = new Sort( Sort.Direction.ASC, DEFAULT_SORT_PROPERTY );
	
	@Query(value = "SELECT COUNT(*) FROM public.u_topo_cippo_chilometrico WHERE estesa_amministrativa = :estesa", nativeQuery = true)
	Long countCippiByIdEstesa(@Param("estesa") Long estesa);
	
}
