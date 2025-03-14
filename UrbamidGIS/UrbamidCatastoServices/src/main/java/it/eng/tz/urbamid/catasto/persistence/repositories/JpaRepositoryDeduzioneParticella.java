package it.eng.tz.urbamid.catasto.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import it.eng.tz.urbamid.catasto.persistence.model.DeduzioneParticella;

public interface JpaRepositoryDeduzioneParticella extends PagingAndSortingRepository<DeduzioneParticella, Long>, JpaSpecificationExecutor<DeduzioneParticella> {

	@Query(value="SELECT * "
			+"FROM public.u_cat_deduzioni_particella "
			+"WHERE id_immobile = :idImmobile ", nativeQuery = true)
	List<DeduzioneParticella> findByIdImmobile(@Param("idImmobile") Long idImmobile);

}
