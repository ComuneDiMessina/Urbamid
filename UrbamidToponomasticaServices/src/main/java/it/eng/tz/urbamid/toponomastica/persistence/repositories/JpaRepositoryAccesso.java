package it.eng.tz.urbamid.toponomastica.persistence.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.eng.tz.urbamid.toponomastica.persistence.model.Accesso;

@Repository
public interface JpaRepositoryAccesso extends PagingAndSortingRepository<Accesso, Long>, JpaRepository<Accesso, Long>, JpaSpecificationExecutor<Accesso> {

	String 	DEFAULT_SORT_PROPERTY = "toponimo";
	Sort DEFAULT_SORT = new Sort( Sort.Direction.ASC, DEFAULT_SORT_PROPERTY );
	
	@Query(value = "SELECT COUNT(*) from public.u_topo_accesso WHERE toponimo_stradale = :toponimo", nativeQuery = true)
	Long countByIdToponimo(@Param("toponimo") Long idToponimo);
	
	@Query(value = "SELECT COUNT(*) FROM public.u_topo_accesso WHERE localita = :localita", nativeQuery = true)
	Long countAccessiByIdLocalita(@Param("localita") Long localita);
	
}
