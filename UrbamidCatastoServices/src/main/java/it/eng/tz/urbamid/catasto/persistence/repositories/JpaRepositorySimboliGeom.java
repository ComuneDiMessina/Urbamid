package it.eng.tz.urbamid.catasto.persistence.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import it.eng.tz.urbamid.catasto.persistence.model.SimboliGeom;

public interface JpaRepositorySimboliGeom extends PagingAndSortingRepository<SimboliGeom, Long>, JpaSpecificationExecutor<SimboliGeom> {

}
