package it.eng.tz.urbamid.toponomastica.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.eng.tz.urbamid.toponomastica.persistence.model.NumCivico;

public interface JpaRepositoryNumCivico extends JpaRepository<NumCivico, Long> {

	
	 @Query(value="select * from public.geo_numcivico n where n.codstrada = :codStrada order by n.numero, esponente is not null" , 
			 nativeQuery = true)
	 List<NumCivico> getNumberbyStreet(@Param("codStrada")String codStrada);
	 
	 
}
