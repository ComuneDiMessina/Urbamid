package it.eng.tz.urbamid.catasto.persistence.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import it.eng.tz.urbamid.catasto.persistence.model.FoglioGeom;

public interface JpaRepositoryFoglioGeom extends PagingAndSortingRepository<FoglioGeom, Long>{

	
	@Query(value="SELECT  gid, codice_com, foglio, mappale, allegato, sviluppo, htxt, rtxt, xtxt, ytxt, ST_AsText(geom) AS geom "
					+ "FROM public.u_cat_confine "
					+ "WHERE "
						+ "foglio = :numFoglio  "
		   ,nativeQuery = true)
	 Page<FoglioGeom> findByNumFoglio(
			 @Param("numFoglio")String foglio, 
             Pageable p);
	 
	@Query(value="SELECT  gid, codice_com, foglio, mappale, allegato, sviluppo, htxt, rtxt, xtxt, ytxt, ST_AsText(geom) as geom " 
					+ "FROM public.u_cat_confine "
					+ "WHERE "
						+ "geom is not null AND "
						+ "ST_Intersects(ST_GeometryFromText(':wktGeom', 4326), geom) "
		   ,nativeQuery = true)
	 Page<FoglioGeom> findByGeom(
			 @Param("wktGeom")String wktGeom, 
             Pageable p);
}