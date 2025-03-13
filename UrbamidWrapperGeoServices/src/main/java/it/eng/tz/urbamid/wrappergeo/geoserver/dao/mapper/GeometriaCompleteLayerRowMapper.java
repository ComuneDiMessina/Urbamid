package it.eng.tz.urbamid.wrappergeo.geoserver.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.urbamid.wrappergeo.persistence.model.GeometriaCompleteLayer;

public class GeometriaCompleteLayerRowMapper implements RowMapper<GeometriaCompleteLayer> {

	public GeometriaCompleteLayer mapRow(ResultSet resultSet, int i) throws SQLException {

		GeometriaCompleteLayer geometriaLayer = new GeometriaCompleteLayer();
		geometriaLayer.setId( resultSet.getLong("id") );
		geometriaLayer.setDistance( resultSet.getDouble("distance"));
		geometriaLayer.setGeom(resultSet.getString("geom"));
		return geometriaLayer;
	}
}
