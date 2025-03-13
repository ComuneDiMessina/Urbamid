package it.eng.tz.urbamid.administration.mappa.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.urbamid.administration.mappa.dao.model.LayerGeometrie;

public class LayerGeometrieRowMapper implements RowMapper<LayerGeometrie> {

	@Override
	public LayerGeometrie mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		LayerGeometrie layerGeometrie = new LayerGeometrie();
		
		layerGeometrie.setId(rs.getLong("id"));
		layerGeometrie.setIdLayer(rs.getLong("id_layer"));
		layerGeometrie.setGeom(rs.getString("geom"));
		layerGeometrie.setNome(rs.getString("nome"));
		layerGeometrie.setDescrizione(rs.getString("descrizione"));
		layerGeometrie.setTipo(rs.getString("tipo"));
		
		return layerGeometrie;
	}

}
