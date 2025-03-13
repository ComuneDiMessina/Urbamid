package it.eng.tz.urbamid.prg.persistence.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import it.eng.tz.urbamid.prg.persistence.model.CatalogoLayer;

public interface JpaRepositoryCatalogoLayer extends PagingAndSortingRepository<CatalogoLayer, Long>, JpaSpecificationExecutor<CatalogoLayer> {

}
