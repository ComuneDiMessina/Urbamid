package it.eng.tz.urbamid.catasto.persistence.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import it.eng.tz.urbamid.catasto.persistence.model.RiservaUnitaImmobiliare;

public interface JpaRepositoryRiservaUnitaImmobiliare extends PagingAndSortingRepository<RiservaUnitaImmobiliare, Long>, JpaSpecificationExecutor<RiservaUnitaImmobiliare> {

}
