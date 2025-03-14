package it.eng.tz.urbamid.toponomastica.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.eng.tz.urbamid.toponomastica.persistence.model.GeocodingReverseGeocoding;

@Repository
public interface JpaRepositoryGeocodingReverseGeocoding extends JpaRepository<GeocodingReverseGeocoding, Long>, JpaSpecificationExecutor<GeocodingReverseGeocoding> {
	
	@Query(value = "SELECT id, comune_label, denominazione_ufficiale, shape_leng, cap, compendi, precdenomi, quartiere, id_comune, note, codice, id_padre, stato, data_fine_validita, ST_AsText(geom) As geom, id_toponimo, id_accesso\n"
			+	   "FROM public.u_topo_toponimo_geocoding\n"
			+ 	   "WHERE ST_DWithin(ST_GeomFromText(ST_AsText(geom)), ST_POINT(:x, :y), :gradi)", nativeQuery = true)
	public List<GeocodingReverseGeocoding> reverseGeocoding(@Param(value = "x") Double lat, @Param(value = "y") Double lon, @Param(value = "gradi") Double gradi);
	
	@Query(value = "SELECT  id, comune_label, denominazione_ufficiale, shape_leng, cap, compendi, precdenomi, quartiere, id_comune, note, codice, id_padre, stato, data_fine_validita, ST_AsText(geom) As geom, id_toponimo, id_accesso\n"
			  + "FROM public.u_topo_toponimo_geocoding\n"
			  + "WHERE round(CAST(ST_DistanceSphere(ST_Centroid(geom), ST_POINT(:lat, :lon)) As numeric),2) < :distance", nativeQuery = true)
	public List<GeocodingReverseGeocoding> rGeocoding(@Param(value = "lat") Double lat, @Param(value = "lon") Double lon, @Param(value = "distance") Integer gradi);
	
		
}
