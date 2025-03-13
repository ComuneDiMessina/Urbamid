package it.eng.tz.urbamid.webgis.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.urbamid.webgis.dao.model.LayerAttribute;

public class LayerAttributeRowMapper implements RowMapper<LayerAttribute> {

	public LayerAttribute mapRow(ResultSet resultSet, int i) throws SQLException {

		LayerAttribute layerAttr = new LayerAttribute();
		layerAttr.setColumnName(resultSet.getString("column_name"));
		layerAttr.setDataType(resultSet.getString("data_type"));
		return layerAttr;
	}
}
