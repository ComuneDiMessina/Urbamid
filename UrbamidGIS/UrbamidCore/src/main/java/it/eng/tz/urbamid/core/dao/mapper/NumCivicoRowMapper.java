package it.eng.tz.urbamid.core.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.urbamid.webgis.dao.model.NumCivico;


public class NumCivicoRowMapper implements RowMapper<NumCivico> {

	public NumCivico mapRow(ResultSet resultSet, int i) throws SQLException {

		NumCivico numCivico = new NumCivico();		
		numCivico.setId(resultSet.getLong("id"));
		numCivico.setCodStrada(resultSet.getString("codStrada"));
		numCivico.setNumero(resultSet.getInt("numero"));
		numCivico.setEsponente(resultSet.getString("esponente"));
		numCivico.setSysRef(resultSet.getString("sysRef"));
		numCivico.setLatitudine(resultSet.getDouble("latitudine"));
		numCivico.setLongitudine(resultSet.getDouble("longitudine"));
		return numCivico;
	}
}
