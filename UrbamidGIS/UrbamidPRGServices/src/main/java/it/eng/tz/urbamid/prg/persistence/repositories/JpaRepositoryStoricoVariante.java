package it.eng.tz.urbamid.prg.persistence.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import it.eng.tz.urbamid.prg.persistence.model.StoricoVariante;

public interface JpaRepositoryStoricoVariante extends PagingAndSortingRepository<StoricoVariante, Long>, JpaSpecificationExecutor<StoricoVariante> {

	@Modifying
	@Query(value="DELETE FROM public.u_prg_storico_variante WHERE id_variante = :idVariante", nativeQuery = true)
	void cancellaStoricoFromIdVariante(@Param("idVariante") Long idVariante);

}
