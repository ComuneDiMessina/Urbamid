package it.eng.tz.urbamid.catasto.persistence.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import it.eng.tz.urbamid.catasto.persistence.model.StradeGeom;

public interface JpaRepositoryStradeGeom extends PagingAndSortingRepository<StradeGeom, Long>, JpaSpecificationExecutor<StradeGeom> {

}
