package it.eng.tz.urbamid.prg.persistence.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import it.eng.tz.urbamid.prg.persistence.model.Cdu;

public interface JpaRepositoryCdu extends PagingAndSortingRepository<Cdu, Long>, JpaSpecificationExecutor<Cdu> {

	@Query(value="SELECT * FROM public.u_prg_cdu WHERE protocollo = :protocollo", nativeQuery = true)
	Cdu findByProtocollo(@Param("protocollo") String protocollo);

}
