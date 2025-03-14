package it.eng.tz.urbamid.catasto.persistence.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import it.eng.tz.urbamid.catasto.persistence.model.FabbricatiGeom;

public interface JpaRepositoryFabbricatiGeom extends PagingAndSortingRepository<FabbricatiGeom, Long>, JpaSpecificationExecutor<FabbricatiGeom> {

	@Query(value="SELECT  gid, codice_com, foglio, mappale, allegato, sviluppo, htxt, rtxt, xtxt, ytxt, ST_AsText(geom) AS geom "
			+"FROM public.u_cat_fabbricati "
			+"WHERE foglio = :foglio AND mappale = :mappale "
			,nativeQuery = true)
	 Page<FabbricatiGeom> findByFoglioAndMappale(
			 @Param("foglio")String foglio, 
			 @Param("mappale")String mappale, 
	         Pageable p);

}
