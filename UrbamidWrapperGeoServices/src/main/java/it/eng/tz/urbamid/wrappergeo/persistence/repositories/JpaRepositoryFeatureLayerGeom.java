package it.eng.tz.urbamid.wrappergeo.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import it.eng.tz.urbamid.wrappergeo.persistence.model.LayerFeatureGeom;

public interface JpaRepositoryFeatureLayerGeom extends PagingAndSortingRepository<LayerFeatureGeom, Long>{

	
	@Query(value="SELECT gid, ST_AsText(geometry) AS geometry "
				+"FROM #{#tableName}  "
				+"WHERE ST_Intersects(ST_GeometryFromText(:wktGeom, 4326), geometry)"
		  ,nativeQuery = true)
	 List<LayerFeatureGeom> findLayerFeatureByGeom(
			 @Param("tableName")String tableName,
			 @Param("wktGeom")String wktGeom);
	 
}