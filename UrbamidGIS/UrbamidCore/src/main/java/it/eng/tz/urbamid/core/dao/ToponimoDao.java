package it.eng.tz.urbamid.core.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.eng.tz.urbamid.core.dao.mapper.ToponimoRowMapper;
import it.eng.tz.urbamid.webgis.dao.model.Toponimo;

/**
 * @author Rocco Russo
 */
@Repository
public class ToponimoDao extends GenericDao<Toponimo> {
 
	/** LOGGER **/
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final String SQL_ALL_STREETS = "SELECT gid as id, objectid as codStrada, toponimo,  "
			+ "Find_SRID('public', 'geo_strade', 'geom') as srid, "
			+ "ST_X(ST_Centroid(geom)) as longitudine, " 
			+ "ST_Y(ST_Centroid(geom)) as latitudine " 
			+ "FROM public.geo_strade "
			+ "ORDER BY toponimo";
	//private final String SQL_FIND_STREET = "SELECT * FROM u_toponimo where denominazione ilike ? order by denominazione";
	
	private final String SQL_FIND_STREET = "SELECT  toponimo, objectid as codStrada, gid as id, " +
			"Find_SRID('public', 'geo_strade', 'geom') as srid, " +
			"ST_X(ST_Centroid(geom)) as longitudine, " + 
			"ST_Y(ST_Centroid(geom)) as latitudine " + 
			"FROM public.geo_strade " + 
			"where toponimo ilike ? " + 
			"ORDER BY toponimo;";
	
	
	public List<Toponimo> getStreet(String streetName) {
		logger.debug("getStreet");
		return getJdbcTemplate().query(SQL_FIND_STREET, new ToponimoRowMapper(), "%" + streetName.trim() + "%");
	}
	
	
	public List<Toponimo> getAllStreet() {
		logger.debug("getAllStreet");
		return getJdbcTemplate().query(SQL_ALL_STREETS, new ToponimoRowMapper());
	}
	
}
