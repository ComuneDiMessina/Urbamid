package it.eng.tz.urbamid.prg.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import it.eng.tz.urbamid.prg.persistence.model.StoricoCdu;
import it.eng.tz.urbamid.prg.persistence.model.StoricoCduProtocolloData;

public interface JpaRepositoryStoricoCdu extends PagingAndSortingRepository<StoricoCdu, Long>, JpaSpecificationExecutor<StoricoCdu> {

	@Query(value="SELECT * " + 
			"  FROM public.u_prg_storico_cdu" + 
			"  WHERE foglio in :lsFogli and numero in :lsNumeri", nativeQuery = true)
	List<StoricoCdu> cercaCduPassati(@Param("lsFogli") List<String> lsFogli, @Param("lsNumeri") List<String> lsNumeri);

	@Query(value="SELECT data_creazione, protocollo " + 
			"  FROM public.u_prg_storico_cdu" + 
			"  WHERE foglio in :lsFogli and numero in :lsNumeri" + 
			"  group by data_creazione, protocollo", nativeQuery = true)
	List<StoricoCduProtocolloData> cercaCduPassatiProtocolloData(@Param("lsFogli") List<String> lsFogli, @Param("lsNumeri") List<String> lsNumeri);

	@Query(value="SELECT protocollo FROM public.u_prg_storico_cdu WHERE protocollo = :protocollo limit 1", nativeQuery = true)
	String findByProtocollo(@Param("protocollo") String protocollo);

	@Modifying
	@Query(value="DELETE FROM public.u_prg_storico_cdu WHERE protocollo = :protocollo", nativeQuery = true)
	void deleteByProtocollo(@Param("protocollo") String protocollo);

}
