package it.eng.tz.urbamid.toponomastica.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import it.eng.tz.urbamid.toponomastica.persistence.model.GiunzioneStradale;

@Repository
public interface JpaRepositoryGiunzioneStradale extends PagingAndSortingRepository<GiunzioneStradale, Long>, JpaRepository<GiunzioneStradale, Long>, JpaSpecificationExecutor<GiunzioneStradale> {

}
