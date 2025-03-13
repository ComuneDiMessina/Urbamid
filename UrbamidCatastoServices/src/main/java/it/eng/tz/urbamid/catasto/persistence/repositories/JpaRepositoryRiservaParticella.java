package it.eng.tz.urbamid.catasto.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import it.eng.tz.urbamid.catasto.persistence.model.RiservaParticella;

public interface JpaRepositoryRiservaParticella extends PagingAndSortingRepository<RiservaParticella, Long>, JpaSpecificationExecutor<RiservaParticella> {

	@Query(value="SELECT * "
			+"FROM public.u_cat_riserve_particella "
			+"WHERE id_immobile = :idImmobile ", nativeQuery = true)
	List<RiservaParticella> findByIdImmobile(@Param("idImmobile") Long idImmobile);

}
