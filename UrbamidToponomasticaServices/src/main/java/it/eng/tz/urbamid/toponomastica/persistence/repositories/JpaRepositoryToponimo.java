package it.eng.tz.urbamid.toponomastica.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.eng.tz.urbamid.toponomastica.persistence.model.Toponimo;

public interface JpaRepositoryToponimo extends JpaRepository<Toponimo, Long> {

	
	 @Query(value="SELECT  gid, toponimo, objectid, Find_SRID('public', 'geo_strade', 'geom') as sysref, "
	 		+ "ST_X(ST_Centroid(geom)) as longitudine, ST_Y(ST_Centroid(geom)) as latitudine FROM public.geo_strade where toponimo "
	 		+ "ilike CONCAT('%', :streetName, '%') ORDER BY toponimo ", nativeQuery = true)
	 List<Toponimo> findByName(@Param("streetName")String streetName);
	 

}
