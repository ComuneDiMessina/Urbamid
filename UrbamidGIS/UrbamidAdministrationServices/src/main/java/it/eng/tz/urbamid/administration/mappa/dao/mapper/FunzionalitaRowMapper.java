package it.eng.tz.urbamid.administration.mappa.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.urbamid.administration.persistence.model.Funzionalita;

public class FunzionalitaRowMapper implements RowMapper<Funzionalita>{

	@Override
	public Funzionalita mapRow(ResultSet rs, int rowNum) throws SQLException {
		Funzionalita funzionalita = new Funzionalita();
		
		funzionalita.setCodice(rs.getString("codice"));
		funzionalita.setDenominazione(rs.getString("denominazione"));
		funzionalita.setId(rs.getLong("id"));
		funzionalita.setImgUrl(rs.getString("img_url"));
		funzionalita.setLinkUrl(rs.getString("link_url"));
		funzionalita.setLocked(rs.getBoolean("locked"));
		funzionalita.setMappa(rs.getString("mappa"));
		funzionalita.setOrdine(rs.getString("ordine"));
		funzionalita.setPermesso(rs.getString("permesso"));
		
		return funzionalita;
	}

}
