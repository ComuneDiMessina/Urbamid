package it.eng.tz.urbamid.catasto.persistence.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import it.eng.tz.urbamid.catasto.persistence.model.UtilitaUnitaImmobiliare;

public interface JpaRepositoryUtilitaUnitaImmobiliare extends PagingAndSortingRepository<UtilitaUnitaImmobiliare, Long>, JpaSpecificationExecutor<UtilitaUnitaImmobiliare> {

}
