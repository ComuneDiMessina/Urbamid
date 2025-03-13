package it.eng.tz.urbamid.administration.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import it.eng.tz.urbamid.administration.persistence.custom.repositories.QuerydslPredicateProjectionRepository;
import it.eng.tz.urbamid.administration.persistence.model.Funzionalita;

public interface JpaRepositoryFunzionalita extends JpaRepository<Funzionalita, Long>, QuerydslPredicateExecutor<Funzionalita>,
		QuerydslPredicateProjectionRepository<Funzionalita> {

	
	 @Query("select f from it.eng.tz.urbamid.administration.persistence.model.Funzionalita f where f.permesso in :permessi order by f.ordine")
	 List<Funzionalita> findByPermissions(@Param("permessi")List<String> authorities);
	 
	 
}
