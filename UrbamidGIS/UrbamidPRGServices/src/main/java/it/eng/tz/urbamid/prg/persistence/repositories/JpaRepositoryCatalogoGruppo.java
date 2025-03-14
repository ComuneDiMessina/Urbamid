package it.eng.tz.urbamid.prg.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import it.eng.tz.urbamid.prg.persistence.model.CatalogoGruppo;

public interface JpaRepositoryCatalogoGruppo extends PagingAndSortingRepository<CatalogoGruppo, Long>, JpaSpecificationExecutor<CatalogoGruppo> {

	@Query(value="SELECT * FROM public.u_prg_catalogo_gruppo ORDER BY nome_gruppo ASC", nativeQuery = true)
	List<CatalogoGruppo> getAll();

}
