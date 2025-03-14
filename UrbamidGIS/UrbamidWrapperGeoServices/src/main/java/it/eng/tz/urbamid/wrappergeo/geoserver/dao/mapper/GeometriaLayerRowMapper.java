package it.eng.tz.urbamid.wrappergeo.geoserver.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.urbamid.wrappergeo.persistence.model.GeometriaLayer;

public class GeometriaLayerRowMapper implements RowMapper<GeometriaLayer> {

	public GeometriaLayer mapRow(ResultSet resultSet, int i) throws SQLException {

		GeometriaLayer geometriaLayer = new GeometriaLayer();
		geometriaLayer.setId( resultSet.getLong("id") );
		geometriaLayer.setGeom(resultSet.getString("geom"));
		return geometriaLayer;
	}
}
