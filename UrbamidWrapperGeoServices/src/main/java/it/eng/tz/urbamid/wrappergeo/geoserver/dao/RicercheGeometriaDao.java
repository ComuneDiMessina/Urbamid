package it.eng.tz.urbamid.wrappergeo.geoserver.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.eng.tz.urbamid.wrappergeo.geoserver.dao.mapper.GeometriaCompleteLayerRowMapper;
import it.eng.tz.urbamid.wrappergeo.geoserver.dao.mapper.GeometriaLayerRowMapper;
import it.eng.tz.urbamid.wrappergeo.persistence.model.GeometriaCompleteLayer;
import it.eng.tz.urbamid.wrappergeo.persistence.model.GeometriaLayer;

/**
 * @author Alessandro Paolillo
 */
@Repository
public class RicercheGeometriaDao extends GenericDao<GeometriaCompleteLayer> {

	/** LOGGER **/
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public String getColumnName(String layerName) {
		logger.debug("getColumnName");
		String result = null;
		String SQL_GET_COLUMN_NAME = "SELECT column_name FROM INFORMATION_SCHEMA. COLUMNS WHERE TABLE_NAME = '%TABLE_NAME%'";
		SQL_GET_COLUMN_NAME = SQL_GET_COLUMN_NAME.replace("%TABLE_NAME%", layerName);
		List<String> results = getJdbcTemplate().queryForList(SQL_GET_COLUMN_NAME, String.class);
		for (String col : results) {
			if ( col.equals("gid") ) {result = "gid";break;}
			else if (col.equals("id")) {result = "id";break;}
		}
		return result;
	}
	
	public List<GeometriaLayer> findGeometria(String layerName) {
		logger.debug("findGeometria");

		String SQL_FIND_GEOMETRIA_BYLAYER = "SELECT gid as id, ST_AsText(geom) AS geom " +  
										     "FROM public.%TABLE_NAME% ";
		SQL_FIND_GEOMETRIA_BYLAYER = SQL_FIND_GEOMETRIA_BYLAYER.replace("%TABLE_NAME%", layerName);
		
		return getJdbcTemplate().query(SQL_FIND_GEOMETRIA_BYLAYER, new GeometriaLayerRowMapper());
	}
	
	public GeometriaCompleteLayer findGeometriaByWkt(String layerName, String wkt, String colId) {
		logger.debug("findGeometriaByWkt");
		logger.debug("findGeometriaByWkt>>>>layerName:"+layerName+" >>>wkt:"+wkt+" >>>colId:"+colId);
//		String SQL_FIND_GEOMETRIA_BYLAYER = "SELECT tab.id, tab.geom, tab.distance "+
//											"FROM "+
//												"(SELECT "+colId+" as id, ST_AsText(geom) AS geom, ST_Distance(gvt.geom, ST_GeometryFromText('POINT(15.554811289087738 38.19475567597226)', 4326)) as distance "+
//												 "FROM (SELECT * FROM public.u_topo_toponimo_stradale WHERE ST_SRID(geom) = 4326) AS gvt "+
//												 "WHERE ST_Distance(gvt.geom, ST_GeometryFromText('%WKT%', 4326))<16000) "+ 
//												 "AS tab "+
//											"ORDER BY tab.distance DESC "+
//											"LIMIT 1";
		
		String SQL_FIND_GEOMETRIA_BYLAYER = "SELECT tab.id as id, tab.geom as geom, tab.distance as distance "+ 
						"FROM "+ 
							"( SELECT %COL_ID% as id, ST_AsText(geom) AS geom, ST_Distance(gvt.geom, ST_GeometryFromText('%WKT%', 4326)) as distance "+ 
								"FROM "+
									"(SELECT * FROM public.%TABLE_NAME% WHERE ST_SRID(geom) = 4326) AS gvt "+ 
								"WHERE "+ 
									"gvt.geom IS NOT NULL AND "+
									"ST_Distance(gvt.geom, ST_GeometryFromText('%WKT%', 4326))<100 "+
								"ORDER BY ST_Distance(gvt.geom, ST_GeometryFromText('%WKT%', 4326)) ASC "+
								"LIMIT 10 "+
							") AS tab "+ 
						"ORDER BY tab.distance DESC "+ 
						"LIMIT 1";
		SQL_FIND_GEOMETRIA_BYLAYER = SQL_FIND_GEOMETRIA_BYLAYER.replaceAll("%WKT%", wkt).replace("%COL_ID%", colId).replace("%TABLE_NAME%", layerName);
		logger.debug("QUERY: " + SQL_FIND_GEOMETRIA_BYLAYER);
		return (GeometriaCompleteLayer) getJdbcTemplate().queryForObject(SQL_FIND_GEOMETRIA_BYLAYER, new GeometriaCompleteLayerRowMapper());
	}
}
