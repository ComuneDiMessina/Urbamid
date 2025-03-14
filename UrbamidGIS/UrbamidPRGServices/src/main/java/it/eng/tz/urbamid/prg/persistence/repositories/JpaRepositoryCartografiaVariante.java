package it.eng.tz.urbamid.prg.persistence.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import it.eng.tz.urbamid.prg.persistence.model.CartografiaVariante;

public interface JpaRepositoryCartografiaVariante extends PagingAndSortingRepository<CartografiaVariante, Long>, JpaSpecificationExecutor<CartografiaVariante> {

	@Query(value="SELECT * FROM public.u_prg_cartografia_variante WHERE id_variante = :idVariante GROUP BY id_cartografia, gruppo_layer ORDER BY gruppo_layer, descrizione_layer", nativeQuery = true)
	List<CartografiaVariante> cercaCartografieByIdVariante(@Param("idVariante") Long idVariante);

	@Modifying
	@Query(value="DELETE FROM public.u_prg_cartografia_variante WHERE id_variante = :idVariante", nativeQuery = true)
	void deleteAllCartografieByIdVariante(@Param("idVariante") Long idVariante);

	@Modifying
	@Query(value="DELETE FROM public.u_prg_cartografia_variante WHERE id_variante = :idVariante and nome_layer = :nomeLayer", nativeQuery = true)
	void deleteCartografieByIdVarianteAndLayer(@Param("idVariante") Long idVariante, @Param("nomeLayer") String nomeLayer);

	@Query(value="SELECT * FROM public.u_prg_cartografia_variante WHERE id_variante = :idVariante and nome_layer = :nomeLayer", nativeQuery = true)
	Optional<CartografiaVariante> cercaCartografiaByIdVarianteAndNomeLayer(@Param("idVariante") Long idVariante, @Param("nomeLayer") String nomeLayer);

}
