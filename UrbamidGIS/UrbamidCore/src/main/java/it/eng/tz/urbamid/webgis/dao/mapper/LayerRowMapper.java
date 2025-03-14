package it.eng.tz.urbamid.webgis.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.urbamid.webgis.dao.model.Layer;

public class LayerRowMapper implements RowMapper<Layer> {

	public Layer mapRow(ResultSet resultSet, int i) throws SQLException {

		Layer layer = new Layer();
//		layer.setId(resultSet.getLong("id"));
//		layer.setGeom(resultSet.getString("myGeom"));
		return layer;
	}
}
