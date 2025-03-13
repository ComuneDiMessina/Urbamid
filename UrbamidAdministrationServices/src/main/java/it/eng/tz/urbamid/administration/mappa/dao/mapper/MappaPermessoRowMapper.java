package it.eng.tz.urbamid.administration.mappa.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.urbamid.administration.mappa.dao.model.MappaPermesso;

public class MappaPermessoRowMapper implements RowMapper<MappaPermesso>{

	@Override
	public MappaPermesso mapRow(ResultSet rs, int rowNum) throws SQLException {
		MappaPermesso mappaPermesso = new MappaPermesso();
		
		mappaPermesso.setId_rsrs_prms(rs.getLong("id_rsrs_prms"));
		mappaPermesso.setId_risorsa(rs.getLong("id_risorsa"));
		mappaPermesso.setId_tipo_risorsa(rs.getLong("id_tipo_risorsa"));
		mappaPermesso.setId_ruolo(rs.getLong("id_ruolo"));
		mappaPermesso.setAbilita_visualizzazione(rs.getBoolean("abilita_visualizzazione"));
		mappaPermesso.setAbilita_modifica(rs.getBoolean("abilita_modifica"));
		mappaPermesso.setAbilita_permesso1(rs.getBoolean("abilita_permesso1"));
		mappaPermesso.setAbilita_permesso2(rs.getBoolean("abilita_permesso2"));
		mappaPermesso.setAbilita_permesso3(rs.getBoolean("abilita_permesso3"));
		
		return mappaPermesso;
	}

}
