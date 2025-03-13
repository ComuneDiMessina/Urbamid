package it.eng.tz.urbamid.administration.mappa.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.urbamid.administration.mappa.dao.model.MappaRicerca;

public class MappaRicercaRowMapper implements RowMapper<MappaRicerca> {

	public MappaRicerca mapRow(ResultSet resultSet, int i) throws SQLException {

		MappaRicerca mappaRicerca = new MappaRicerca();
		mappaRicerca.setId(resultSet.getLong("id"));
		mappaRicerca.setName(resultSet.getString("name"));
		mappaRicerca.setTitle(resultSet.getString("title"));
		mappaRicerca.setIdHandle(resultSet.getString("id_handle"));
		return mappaRicerca;
	}
}
