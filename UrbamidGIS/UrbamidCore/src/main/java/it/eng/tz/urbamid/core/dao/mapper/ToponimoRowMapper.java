package it.eng.tz.urbamid.core.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.urbamid.webgis.dao.model.Toponimo;

public class ToponimoRowMapper implements RowMapper<Toponimo> {

	public Toponimo mapRow(ResultSet resultSet, int i) throws SQLException {

		Toponimo toponimo = new Toponimo();		
		toponimo.setId(resultSet.getLong("id"));
		toponimo.setCodStrada(resultSet.getString("codStrada"));
		toponimo.setDenominazione(resultSet.getString("toponimo"));
		toponimo.setSysRef(resultSet.getString("srid"));
		toponimo.setLatitudine(resultSet.getDouble("latitudine"));
		toponimo.setLongitudine(resultSet.getDouble("longitudine"));
		return toponimo;
	}
}
