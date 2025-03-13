package it.eng.tz.urbamid.profilemanager.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import it.eng.tz.urbamid.profilemanager.persistence.custom.repositories.QuerydslPredicateProjectionRepository;
import it.eng.tz.urbamid.profilemanager.persistence.model.Utente;

public interface JpaRepositoryUtente extends JpaRepository<Utente, Long>, QuerydslPredicateExecutor<Utente>,
		QuerydslPredicateProjectionRepository<Utente> {

	
	 @Query("select u from it.eng.tz.urbamid.profilemanager.persistence.model.Utente u where u.username = :username")
	 Utente findByUsername(@Param(value = "username") String username);
	 
	 @Query("select u from it.eng.tz.urbamid.profilemanager.persistence.model.Utente u where u.id = :id")
	 Utente findById(@Param(value = "id") String id);
	 
	 @Query("select u from it.eng.tz.urbamid.profilemanager.persistence.model.Utente u where u.username = :username and u.abilitato=true")
	 Utente findAbilitatoByUsername(@Param(value = "username") String username);
}
