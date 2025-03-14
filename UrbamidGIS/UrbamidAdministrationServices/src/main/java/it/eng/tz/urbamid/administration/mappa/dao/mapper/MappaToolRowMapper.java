package it.eng.tz.urbamid.administration.mappa.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.urbamid.administration.mappa.dao.model.MappaTool;

public class MappaToolRowMapper implements RowMapper<MappaTool> {

	public MappaTool mapRow(ResultSet resultSet, int i) throws SQLException {

		MappaTool mappaTool = new MappaTool();
		mappaTool.setId(resultSet.getLong("id"));
		mappaTool.setName(resultSet.getString("name"));
		mappaTool.setTitle(resultSet.getString("title"));
		return mappaTool;
	}
}
