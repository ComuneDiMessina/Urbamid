package it.eng.tz.urbamid.catasto.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.urbamid.catasto.persistence.model.ParticellaGeom;

public class ParticellaGeomRowMapper implements RowMapper<ParticellaGeom> {

	public ParticellaGeom mapRow(ResultSet resultSet, int i) throws SQLException {
					
		ParticellaGeom particellaGeom = new ParticellaGeom();
		particellaGeom.setGid(resultSet.getLong("gid"));
		particellaGeom.setCodiceCom(resultSet.getString("codice_com"));
		particellaGeom.setCodiceCom(resultSet.getString("foglio"));
		particellaGeom.setCodiceCom(resultSet.getString("mappale"));
		particellaGeom.setCodiceCom(resultSet.getString("allegato"));
		particellaGeom.setCodiceCom(resultSet.getString("sviluppo"));
		particellaGeom.setCodiceCom(resultSet.getString("htxt"));
		particellaGeom.setCodiceCom(resultSet.getString("rtxt"));
		particellaGeom.setCodiceCom(resultSet.getString("xtxt"));
		particellaGeom.setCodiceCom(resultSet.getString("ytxt"));
		particellaGeom.setGeom(resultSet.getString("geom"));
		particellaGeom.setCodiceCom(resultSet.getString("supGeo"));
		return particellaGeom;
	}
}
