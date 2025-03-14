package it.eng.tz.urbamid.administration.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import it.eng.tz.urbamid.administration.persistence.custom.repositories.QuerydslPredicateProjectionRepository;
import it.eng.tz.urbamid.administration.persistence.model.MenuFunzionalita;

public interface JpaRepositoryMenuFunzionalita extends JpaRepository<MenuFunzionalita, Long>, QuerydslPredicateExecutor<MenuFunzionalita>,
		QuerydslPredicateProjectionRepository<MenuFunzionalita> {
	
	 @Query(value="SELECT * FROM public.u_admin_menu_funzionalita where permesso = :permesso", nativeQuery = true)
	 MenuFunzionalita findMenuByPermissions(@Param("permesso")String authority);
	 
	 
}
