package it.eng.tz.urbamid.profilemanager.profile.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.urbamid.profilemanager.persistence.model.RuoloUtente;


public class UtenteRuoloRowMapper implements RowMapper<RuoloUtente> {

	public RuoloUtente mapRow(ResultSet resultSet, int i) throws SQLException {

		RuoloUtente ruoloUtente = new RuoloUtente();
		ruoloUtente.setId(resultSet.getLong("id"));
		ruoloUtente.setIdRuolo(resultSet.getLong("id_ruolo"));
		ruoloUtente.setIdUtente(resultSet.getLong("id_utente"));
		return ruoloUtente;
	}
}
