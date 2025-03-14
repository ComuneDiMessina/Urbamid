package it.eng.tz.urbamid.profilemanager.profile.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.urbamid.profilemanager.persistence.model.RuoloPermesso;


public class RuoloPermessoRowMapper implements RowMapper<RuoloPermesso> {

	public RuoloPermesso mapRow(ResultSet resultSet, int i) throws SQLException {

		RuoloPermesso ruoloPermesso = new RuoloPermesso();
		ruoloPermesso.setId(resultSet.getLong("id"));
		ruoloPermesso.setIdRuolo(resultSet.getLong("id_ruolo"));
		ruoloPermesso.setIdPermesso(resultSet.getLong("id_permesso"));
		return ruoloPermesso;
	}
}
