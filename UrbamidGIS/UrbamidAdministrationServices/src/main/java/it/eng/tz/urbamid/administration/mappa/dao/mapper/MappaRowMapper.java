package it.eng.tz.urbamid.administration.mappa.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.urbamid.administration.mappa.dao.model.Mappa;

public class MappaRowMapper implements RowMapper<Mappa> {

	public Mappa mapRow(ResultSet resultSet, int i) throws SQLException {

		Mappa mappa = new Mappa();
		mappa.setId(resultSet.getLong("id"));
		mappa.setCode(resultSet.getString("code"));
		mappa.setTitle(resultSet.getString("title"));
		mappa.setDescription(resultSet.getString("description"));
		mappa.setCatalog(resultSet.getString("catalog"));
		mappa.setShowCatalog(resultSet.getBoolean("show_catalog"));
		mappa.setZoom(resultSet.getInt("zoom"));
		return mappa;
	}
}
