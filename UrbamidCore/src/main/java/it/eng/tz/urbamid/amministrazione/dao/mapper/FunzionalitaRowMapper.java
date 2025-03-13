package it.eng.tz.urbamid.amministrazione.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.urbamid.amministrazione.dao.model.Funzionalita;

public class FunzionalitaRowMapper implements RowMapper<Funzionalita> {

	public Funzionalita mapRow(ResultSet resultSet, int i) throws SQLException {

		Funzionalita funzionalita = new Funzionalita();
		funzionalita.setId(resultSet.getLong("id"));
		funzionalita.setCodice(resultSet.getString("codice"));
		funzionalita.setDenominazione(resultSet.getString("denominazione"));
		funzionalita.setImgUrl(resultSet.getString("img_url"));
		funzionalita.setLinkUrl(resultSet.getString("link_url"));
		funzionalita.setPermesso(resultSet.getString("permesso"));
		return funzionalita;
	}
}
