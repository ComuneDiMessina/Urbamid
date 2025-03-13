package it.eng.tz.urbamid.prg.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import it.eng.tz.urbamid.prg.persistence.model.AmbitoVariante;

public interface JpaRepositoryAmbitoVariante extends PagingAndSortingRepository<AmbitoVariante, Long>, JpaSpecificationExecutor<AmbitoVariante> {

	@Query(value="SELECT * FROM public.u_prg_ambito_variante where id_variante = :idVariante", nativeQuery = true)
	List<AmbitoVariante> findByIdVariante(@Param("idVariante") Long idVariante);

	@Modifying
	@Query(value="DELETE FROM public.u_prg_ambito_variante WHERE id_variante = :idVariante", nativeQuery = true)
	void deleteAllAmbitoByIdVariante(@Param("idVariante") Long idVariante);

}
