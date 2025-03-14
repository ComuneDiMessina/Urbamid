package it.eng.tz.urbamid.administration.mappa.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.urbamid.administration.mappa.dao.model.LayerAttributi;

public class LayerAttributiRowMapper implements RowMapper<LayerAttributi> {

	@Override
	public LayerAttributi mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		LayerAttributi layerAttributi = new LayerAttributi();
		
		layerAttributi.setId(rs.getLong("id"));
		layerAttributi.setIdentificativo(rs.getString("identificativo"));
		layerAttributi.setNome(rs.getString("nome"));
		layerAttributi.setDescrizione(rs.getString("descrizione"));
		layerAttributi.setNote(rs.getString("note"));
		layerAttributi.setStato(rs.getString("stato"));
		
		return layerAttributi;
	}
	
}
