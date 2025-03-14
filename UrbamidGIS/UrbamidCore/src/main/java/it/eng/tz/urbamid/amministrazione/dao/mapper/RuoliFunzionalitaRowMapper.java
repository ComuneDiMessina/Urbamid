package it.eng.tz.urbamid.amministrazione.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.urbamid.amministrazione.dao.model.RuoliFunzionalita;

public class RuoliFunzionalitaRowMapper implements RowMapper<RuoliFunzionalita> {

	public RuoliFunzionalita mapRow(ResultSet resultSet, int i) throws SQLException {

		RuoliFunzionalita ruoliFunzionalita = new RuoliFunzionalita();
		ruoliFunzionalita.setId(resultSet.getLong("id"));
		ruoliFunzionalita.setRuolo(resultSet.getString("ruolo"));
		ruoliFunzionalita.setFunzionalita(resultSet.getString("funzionalita"));
		return ruoliFunzionalita;
	}
}
