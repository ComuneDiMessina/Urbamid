package it.eng.tz.urbamid.toponomastica.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import it.eng.tz.urbamid.toponomastica.dao.GenericDao;
import it.eng.tz.urbamid.toponomastica.persistence.model.CippoChilometrico;

@Repository
public class CippoChilometricoDAO extends GenericDao<CippoChilometrico> {
	
	/** LOGGER **/
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final String SQL_INSERT_CIPPO = "INSERT INTO public.u_topo_cippo_chilometrico(id, estesa_amministrativa, misura, geom, note, stato, codice) VALUES (nextval('u_topo_cippo_chilometrico_id_seq'), ?, ?, ST_AsText(?), ?, 'PUBBLICATO', ?)";
	private static final String SQL_UPDATE_CIPPO = "UPDATE public.u_topo_cippo_chilometrico SET estesa_amministrativa=?, misura=?, geom=ST_AsText(?), note=?, codice=? WHERE id=?";

	public CippoChilometrico insertCippo(CippoChilometrico cippo) throws Exception {
		
		logger.debug("insertCippo");
		
		KeyHolder holder = new GeneratedKeyHolder();
		
		try {
			
			getJdbcTemplate().update(new PreparedStatementCreator() {
				
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					
					PreparedStatement ps = connection.prepareStatement(SQL_INSERT_CIPPO, Statement.RETURN_GENERATED_KEYS);

					ps.setObject(1, cippo.getEstesaAmministrativa().getId());
					ps.setObject(2, cippo.getMisura());
					ps.setString(3, cippo.getGeom());
					ps.setString(4, cippo.getNote() != null ? cippo.getNote() : null);
					ps.setString(5, cippo.getCodice() != null ? cippo.getCodice() : null);
					
					return ps;
				}
			}, holder);
			
			Long id = (Long)holder.getKeyList().get(0).get("id");
			cippo.setId(id);
			
		} catch (Exception e) {
			String message = "Errore nel inserimento del cippo :" + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
		}
		
		return cippo;
		
	}
	
	public CippoChilometrico updateCippo(CippoChilometrico cippo) throws Exception {
		
		logger.debug("updateCippo");
		
		KeyHolder holder = new GeneratedKeyHolder();
		
		try {
			
			getJdbcTemplate().update(new PreparedStatementCreator() {
				
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_CIPPO, Statement.RETURN_GENERATED_KEYS);

					ps.setObject(1, cippo.getEstesaAmministrativa().getId());
					ps.setObject(2, cippo.getMisura());
					ps.setString(3, cippo.getGeom());
					ps.setString(4, cippo.getNote() != null ? cippo.getNote() : null);
					ps.setString(5, cippo.getCodice() != null ? cippo.getCodice() : null);
					ps.setLong(6, cippo.getId());
					
					return ps;
				}
			}, holder);
			
			Long id = (Long)holder.getKeyList().get(0).get("id");
			cippo.setId(id);
			
		} catch (Exception e) {
			String message = "Errore nella modifica del cippo :" + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
		}
		
		return cippo;
		
	}
	
}
