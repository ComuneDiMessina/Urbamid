package it.eng.tz.urbamid.toponomastica.persistence.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import it.eng.tz.urbamid.toponomastica.persistence.model.EstesaAmministrativa;

@Repository
public interface JpaRepositoryEstesaAmministrativa extends PagingAndSortingRepository<EstesaAmministrativa, Long>, JpaRepository<EstesaAmministrativa, Long>, JpaSpecificationExecutor<EstesaAmministrativa>{

	String 	DEFAULT_SORT_PROPERTY = "sigla";
	Sort DEFAULT_SORT = new Sort( Sort.Direction.ASC, DEFAULT_SORT_PROPERTY );
	
//	@Query(value = "SELECT ea.descrizione\r\n" + 
//			"FROM public.u_topo_estesa_amministrativa ea JOIN public.u_topo_classifica_amministrativa ca ON ea.classifica_amministrativa = ca.id\r\n" + 
//			"	 										JOIN public.u_topo_patrimonialita pa ON ea.patrimonialita = pa.id\r\n" + 
//			"											JOIN public.u_topo_ente_gestore eg ON ea.ente_gestore = eg.id\r\n" + 
//			"											JOIN public.u_topo_classifica_funzionale cf ON ea.classifica_funzionale = cf.id\r\n" + 
//			"WHERE UPPER(ea.descrizione) LIKE :descrizione%", nativeQuery = true)
//	List<EstesaAmministrativa> findAll(@Param("descrizione") String descrizione);
	
}
