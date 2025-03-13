package it.eng.tz.urbamid.catasto.persistence.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import it.eng.tz.urbamid.catasto.persistence.model.ParticellaCompleteGeom;
import it.eng.tz.urbamid.catasto.persistence.model.ParticellaGeom;

public interface JpaRepositoryParticellaGeom extends PagingAndSortingRepository<ParticellaGeom, Long>{

	
	@Query(value="SELECT  gid, codice_com, foglio, mappale, allegato, sviluppo, htxt, rtxt, xtxt, ytxt, ST_AsText(geom) AS geom "
			+"FROM public.u_cat_particelle "
			+"WHERE foglio = :foglio AND mappale = :mappale "
			,nativeQuery = true)
	 Page<ParticellaGeom> findByFoglioAndMappale(
			 @Param("foglio")String foglio, 
			 @Param("mappale")String mappale, 
	         Pageable p);
	
	@Query(value="SELECT  gid, codice_com, foglio, mappale, allegato, sviluppo, htxt, rtxt, xtxt, ytxt, ST_AsText(geom) AS geom "
				+"FROM public.u_cat_particelle "
				+"WHERE foglio = :foglio "
		  ,nativeQuery = true, countQuery=" SELECT count(*) FROM public.u_cat_particelle WHERE foglio = :foglio ")
	 Page<ParticellaGeom> findByFoglio(
			 @Param("foglio")String foglio,  
	         Pageable p);
	
	@Query(value="SELECT  gid, codice_com, foglio, mappale, allegato, sviluppo, htxt, rtxt, xtxt, ytxt, ST_AsText(geom) AS geom "
			+"FROM public.u_cat_particelle "
			+"WHERE mappale = :mappale "
		  ,nativeQuery = true, countQuery=" SELECT count(*) FROM public.u_cat_particelle WHERE mappale = :mappale ")
	 Page<ParticellaGeom> findByMappale( 
			 @Param("mappale")String mappale, 
	         Pageable p);
	
	@Query(value="SELECT gid, codice_com, foglio, mappale, allegato, sviluppo, htxt, rtxt, xtxt, ytxt, ST_AsText(geom) AS geom "  
			    +"FROM public.u_cat_particelle "
				+"WHERE ST_Intersects(ST_GeometryFromText(:wktGeom, 4326), geom)" 
		   ,nativeQuery = true, countQuery=" SELECT count(*) FROM public.u_cat_particelle WHERE ST_Intersects(ST_GeometryFromText(:wktGeom, 4326), geom) ")
	 Page<ParticellaGeom> findByGeom(
			 @Param("wktGeom")String wktGeom,
             Pageable p);
	
	@Query(value="SELECT gid, codice_com, foglio, mappale, allegato, sviluppo, htxt, rtxt, xtxt, ytxt, ST_AsText(geom) AS geom "  
				    +"FROM public.u_cat_particelle "
					+"WHERE ST_Intersects(ST_GeometryFromText(:wktGeom, 4326), geom)" 
			,nativeQuery = true)
	List<ParticellaGeom> findByWKT(
		 @Param("wktGeom")String wktGeom);

	@Query(value="SELECT "+ 
					"gid, codice_com, foglio, mappale, allegato, sviluppo, htxt, rtxt, xtxt, ytxt, "+
					"--tab.geom, "+ 
					"tab.geomT as geomText, ST_Area(geom) as area, "+
					"--tab.intersection, "+ 
					"tab.intersectGeomText, ST_Area(tab.intersection) as intersectArea "+
				 "FROM "+
					"( "+
					  "SELECT "+
						"gid, codice_com, foglio, mappale, allegato, sviluppo, htxt, rtxt, xtxt, ytxt, "+ 
						"geom, ST_AsText(geom) AS geomT, "+
						"ST_Intersection(ST_GeometryFromText(:wktGeom, 4326), geom) AS intersection, "+
						"ST_AsText(ST_Intersection(ST_GeometryFromText(:wktGeom, 4326), geom)) as intersectGeomText "+
					  "FROM public.u_cat_particelle "+ 
					  "WHERE ST_Intersects(ST_GeometryFromText(:wktGeom, 4326), geom) "+
					") tab"
					,nativeQuery = true)
	List<ParticellaCompleteGeom> findCompleteByWKT(
			@Param("wktGeom")String wktGeom);
	
	@Query(value="SELECT gid, codice_com, foglio, mappale, allegato, sviluppo, htxt, rtxt, xtxt, ytxt, ST_AsText(geom) AS geom "  
		    +"FROM public.u_cat_particelle, :tableName as layer "
			+"WHERE ST_Intersects(part.geom, layer.geom)" ,nativeQuery = true)
	List<ParticellaGeom> findByLayer(
		 @Param("tableName")String tableName);
	
	@Query(value="SELECT  gid, codice_com, foglio, mappale, allegato, sviluppo, htxt, rtxt, xtxt, ytxt, ST_AsText(geom) as geom "
			+ "FROM public.u_cat_particelle "
			+ "WHERE mappale = :mappale "
			, nativeQuery = true, countQuery=" SELECT count(*) FROM public.u_cat_particelle WHERE mappale = :mappale ")
	List<ParticellaGeom> ricercaByMappale(@Param("mappale") String mappale);

	@Query(value="SELECT  gid, codice_com, foglio, mappale, allegato, sviluppo, htxt, rtxt, xtxt, ytxt, ST_AsText(geom) as geom "
			+ "FROM public.u_cat_particelle "
			+ "WHERE foglio = :foglio ", nativeQuery = true)
	List<ParticellaGeom> ricercaByFoglio(@Param("foglio")String foglio);

	@Query(value="SELECT  gid, codice_com, foglio, mappale, allegato, sviluppo, htxt, rtxt, xtxt, ytxt, ST_AsText(geom) as geom "
			+ "FROM public.u_cat_particelle "
			+ "WHERE foglio = :foglio  "
			+ "and mappale = :mappale ", nativeQuery = true)
	List<ParticellaGeom> ricercaByFoglioMappale(
			@Param("foglio")String foglio, 
			@Param("mappale")String mappale);

	@Query(value="SELECT  gid, codice_com, foglio, mappale, allegato, sviluppo, htxt, rtxt, xtxt, ytxt, ST_AsText(geom) AS geom "
			+"FROM public.u_cat_particelle "
			+"WHERE gid = :gid "
	  ,nativeQuery = true)
	List<ParticellaGeom> findByGid(
		 @Param("gid")Long gid);
	
	@Query(value="SELECT  gid, codice_com, foglio, mappale, allegato, sviluppo, htxt, rtxt, xtxt, ytxt, ST_AsText(geom) AS geom "
			+"FROM public.u_cat_particelle "
			+"WHERE gid IN (:gids) "
	  ,nativeQuery = true)
	List<ParticellaGeom> findByGids(
		 @Param("gids")List<Long> gids);
}