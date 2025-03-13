package it.eng.tz.urbamid.prg.persistence.repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import it.eng.tz.urbamid.prg.persistence.model.Variante;

public interface JpaRepositoryVariante extends PagingAndSortingRepository<Variante, Long>, JpaSpecificationExecutor<Variante> {

	String 	DEFAULT_SORT_PROPERTY = "nome";
	Sort 	DEFAULT_SORT = new Sort( Sort.Direction.ASC, DEFAULT_SORT_PROPERTY );

	@Query(value="SELECT * FROM public.u_prg_variante where nome = :nome", nativeQuery = true)
	Variante cercaVarianteDaNome(@Param("nome") String nome);

	@Query(value="SELECT * FROM public.u_prg_variante ORDER BY data_del_appr DESC" ,nativeQuery = true)
	public List<Variante> findAllOrderByDate();
}
