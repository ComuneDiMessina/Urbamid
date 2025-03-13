package it.eng.tz.urbamid.toponomastica.persistence.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import it.eng.tz.urbamid.toponomastica.persistence.model.Localita;

@Repository
public interface JpaRepositoryLocalita extends PagingAndSortingRepository<Localita, Long>, JpaRepository<Localita, Long>, JpaSpecificationExecutor<Localita>{

	String 	DEFAULT_SORT_PROPERTY = "descrizione";
	Sort DEFAULT_SORT = new Sort( Sort.Direction.ASC, DEFAULT_SORT_PROPERTY );
	
}
