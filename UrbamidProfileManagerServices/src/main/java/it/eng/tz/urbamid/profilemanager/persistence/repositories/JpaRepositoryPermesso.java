package it.eng.tz.urbamid.profilemanager.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import it.eng.tz.urbamid.profilemanager.persistence.custom.repositories.QuerydslPredicateProjectionRepository;
import it.eng.tz.urbamid.profilemanager.persistence.model.Permesso;

public interface JpaRepositoryPermesso extends JpaRepository<Permesso, Long>, QuerydslPredicateExecutor<Permesso>,
		QuerydslPredicateProjectionRepository<Permesso> {

	@Query("SELECT p FROM it.eng.tz.urbamid.profilemanager.persistence.model.Permesso p ORDER BY p.padre ASC")
	List<Permesso> findAllOrderByPadre();	
	
	@Query(value = "SELECT * from u_pm_permesso p WHERE id = :idPermesso", nativeQuery = true)
	Permesso select(@Param("idPermesso") Long idPermesso);
}
