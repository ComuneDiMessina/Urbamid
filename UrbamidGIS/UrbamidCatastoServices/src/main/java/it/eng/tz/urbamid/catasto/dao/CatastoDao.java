package it.eng.tz.urbamid.catasto.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import it.eng.tz.urbamid.catasto.dao.mapper.GeometriaLayerRowMapper;
import it.eng.tz.urbamid.catasto.persistence.model.GeometriaLayer;
import it.eng.tz.urbamid.catasto.persistence.model.ParticellaCompleteGeom;

/**
 * @author Alessandro Paolillo
 */
@Repository
public class CatastoDao extends GenericDao<GeometriaLayer> {

	/** LOGGER **/
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String SQL_FIND_COMPLETE_BY_WKT = "SELECT "+ 
																"gid, codice_com, foglio, mappale, allegato, sviluppo, htxt, rtxt, xtxt, ytxt, "+
																//"tab.geom, "+ 
																"tab.geomT as geomText, (ST_Area(geom,true)* 0.3048 ^ 2) as area, "+
																//"tab.intersection, "+ 
																"tab.intersectGeomText, (ST_Area(tab.intersection)* 0.3048 ^ 2) as intersectArea "+
															 "FROM "+
																"( "+
																  "SELECT "+
																	"gid, codice_com, foglio, mappale, allegato, sviluppo, htxt, rtxt, xtxt, ytxt, "+ 
																	"geom, ST_AsText(geom) AS geomT, "+
																	"ST_Intersection(ST_GeometryFromText(?, 4326), geom) AS intersection, "+
																	"ST_AsText(ST_Intersection(ST_GeometryFromText(?, 4326), geom)) as intersectGeomText "+
																  "FROM public.u_cat_particelle "+ 
																  "WHERE ST_Intersects(ST_GeometryFromText(?, 4326), geom) "+
																") tab";
	
	public List<GeometriaLayer> findParticellaByLayer(String tableName) {
		logger.debug("findParticellaByLayer");

		String SQL_FIND_PARTICELLA_BYLAYER = "SELECT p.gid as id, ST_AsText(p.geom) AS geometry "+ 
				"FROM public.u_cat_particelle as p inner join public.%TABLE_NAME% as f on ST_Intersects(p.geom, f.geom)";
		
		SQL_FIND_PARTICELLA_BYLAYER = SQL_FIND_PARTICELLA_BYLAYER.replace("%TABLE_NAME%", tableName);
		return getJdbcTemplate().query(SQL_FIND_PARTICELLA_BYLAYER, new GeometriaLayerRowMapper());
	}
	
	public List<ParticellaCompleteGeom> findCompleteByWKT(String wktGeom) {
		
		logger.debug("findCompleteByWKT");
		
		return getJdbcTemplate().query(SQL_FIND_COMPLETE_BY_WKT, new RowMapper<ParticellaCompleteGeom>() {

			@Override
			public ParticellaCompleteGeom mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				ParticellaCompleteGeom particella = new ParticellaCompleteGeom();
				
				particella.setGid(rs.getLong("gid"));
				particella.setCodiceCom(rs.getString("codice_com"));
				particella.setFoglio(rs.getString("foglio"));
				particella.setMappale(rs.getString("mappale"));
				particella.setAllegato(rs.getString("allegato"));
				particella.setSviluppo(rs.getString("sviluppo"));
				particella.setHtxt(rs.getBigDecimal("htxt"));
				particella.setRtxt(rs.getBigDecimal("rtxt"));
				particella.setXtxt(rs.getBigDecimal("xtxt"));
				particella.setYtxt(rs.getBigDecimal("ytxt"));
				particella.setGeomText(rs.getString("geomText"));
				particella.setArea(rs.getBigDecimal("area"));
				particella.setIntersectGeomText(rs.getString("intersectGeomText"));
				particella.setIntersectArea(rs.getBigDecimal("intersectArea"));
		
				return particella;
			}
			
		},wktGeom,wktGeom,wktGeom);
		
	}
}
