package it.eng.tz.urbamid.toponomastica.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.eng.tz.urbamid.toponomastica.persistence.model.Dug;

@Repository
public interface JpaRepositoryDug extends JpaRepository<Dug, Long>{

	@Query(value = "SELECT * FROM public.u_topo_dug WHERE UPPER(dug) LIKE :dug%", nativeQuery = true)
	List<Dug> findDug(@Param("dug") String dug);
	
}
