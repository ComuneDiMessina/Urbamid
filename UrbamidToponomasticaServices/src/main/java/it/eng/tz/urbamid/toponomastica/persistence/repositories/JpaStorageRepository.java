package it.eng.tz.urbamid.toponomastica.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.eng.tz.urbamid.toponomastica.persistence.model.Documento;

@Repository
public interface JpaStorageRepository extends PagingAndSortingRepository<Documento, Long>, JpaRepository<Documento, Long>, JpaSpecificationExecutor<Documento>{
	
	@Query(value = "SELECT valore_parametro FROM public.u_topo_documenti WHERE tipo_risorsa = :path", nativeQuery = true)
	public String findPath(@Param("path") Long tipo);
	
}
