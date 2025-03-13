package it.eng.tz.urbamid.administration.mappa.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.urbamid.administration.mappa.dao.model.Ruolo;

public class RuoloRowMapper implements RowMapper<Ruolo> {

	@Override
	public Ruolo mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Ruolo ruolo = new Ruolo();
		ruolo.setId(rs.getLong("id"));
		ruolo.setCodice(rs.getString("codice"));
		ruolo.setDenominazione(rs.getString("denominazione"));
		ruolo.setDescrizione(rs.getString("descrizione"));
		
		return ruolo;
	}
	
}
