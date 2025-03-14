package it.eng.tz.urbamid.catasto.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.urbamid.catasto.persistence.model.GeometriaLayer;

public class GeometriaLayerRowMapper implements RowMapper<GeometriaLayer> {

	public GeometriaLayer mapRow(ResultSet resultSet, int i) throws SQLException {

		GeometriaLayer geometriaLayer = new GeometriaLayer();
		geometriaLayer.setGid(resultSet.getLong("id"));
		geometriaLayer.setGeom(resultSet.getString("geometry"));
		return geometriaLayer;
	}
}
