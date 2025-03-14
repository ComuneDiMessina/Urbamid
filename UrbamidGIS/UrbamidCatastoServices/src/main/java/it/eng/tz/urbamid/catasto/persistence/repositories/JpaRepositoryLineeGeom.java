package it.eng.tz.urbamid.catasto.persistence.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import it.eng.tz.urbamid.catasto.persistence.model.LineeGeom;

public interface JpaRepositoryLineeGeom extends PagingAndSortingRepository<LineeGeom, Long>, JpaSpecificationExecutor<LineeGeom> {

}
