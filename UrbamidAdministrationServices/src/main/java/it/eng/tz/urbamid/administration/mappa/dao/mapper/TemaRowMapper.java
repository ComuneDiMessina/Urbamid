package it.eng.tz.urbamid.administration.mappa.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.urbamid.administration.mappa.dao.model.Tema;

public class TemaRowMapper implements RowMapper<Tema> {

	public Tema mapRow(ResultSet resultSet, int i) throws SQLException {

		Tema tema = new Tema();
		tema.setId(resultSet.getLong("id"));
		tema.setIdPadre(resultSet.getLong("idpadre"));
		tema.setNome(resultSet.getString("nome"));
		tema.setDescrizione(resultSet.getString("descrizione"));
		tema.setOrdinamento(resultSet.getLong("ordinamento"));
		if(resultSet.getObject("data_creazione")!=null)
		tema.setDataCreazione(resultSet.getDate("data_creazione"));
		if(resultSet.getObject("data_modifica")!=null)
		tema.setDataModifica(resultSet.getDate("data_modifica"));
		tema.setUtenteCreazione(resultSet.getString("utente_creazione"));
		tema.setUtenteModifica(resultSet.getString("utente_modifica"));
		
		return tema;
	}
}
