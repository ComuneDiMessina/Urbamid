package it.eng.tz.urbamid.profilemanager.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import it.eng.tz.urbamid.profilemanager.persistence.custom.repositories.QuerydslPredicateProjectionRepository;
import it.eng.tz.urbamid.profilemanager.persistence.model.RuoloPermesso;

public interface JpaRepositoryRuoloPermesso extends JpaRepository<RuoloPermesso, Long>, QuerydslPredicateExecutor<RuoloPermesso>,
		QuerydslPredicateProjectionRepository<RuoloPermesso> {
	
}
