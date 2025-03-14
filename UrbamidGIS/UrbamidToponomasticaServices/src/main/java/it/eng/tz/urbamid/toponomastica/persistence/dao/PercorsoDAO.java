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
import it.eng.tz.urbamid.toponomastica.persistence.model.Percorso;

@Repository
public class PercorsoDAO extends GenericDao<Percorso> {
	
	/** LOGGER **/
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final String SQL_INSERT_PERCORSO = "INSERT INTO public.u_topo_percorso(id, sigla, descrizione, note, geom, toponimo_stradale, estesa_amministrativa, stato) VALUES (nextval('u_topo_percorso_id_seq'), ?, ?, ?, ST_AsText(?), ?, ?, 'PUBBLICATO')";
	private static final String SQL_UPDATE_PERCORSO = "UPDATE public.u_topo_percorso SET sigla=?, descrizione=?, note=?, geom=ST_AsText(?), toponimo_stradale=?, estesa_amministrativa=?, stato=? WHERE id = ?";
	
	public Percorso insertPercorso(Percorso percorso) throws Exception {
		
		logger.debug("insertPercorso");
		
		KeyHolder holder = new GeneratedKeyHolder();
		
		try {
			
			getJdbcTemplate().update(new PreparedStatementCreator() {
				
				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					
					PreparedStatement ps = con.prepareStatement(SQL_INSERT_PERCORSO, Statement.RETURN_GENERATED_KEYS);

					ps.setString(1, percorso.getSigla() != null ? percorso.getSigla() : null);
					ps.setString(2, percorso.getDescrizione() != null ? percorso.getDescrizione() : null);
					ps.setString(3, percorso.getNote() != null ? percorso.getNote() : null);
					ps.setString(4, percorso.getGeom());
					ps.setObject(5, percorso.getToponimo().getId());
					ps.setObject(6, percorso.getEstesaAmministrativa().getId());
					
					return ps;
				}
			}, holder);
			
			Long id = (Long)holder.getKeyList().get(0).get("id");
			percorso.setId(id);
			
		} catch (Exception e) {
			
			String message = "Errore nel inserimento del percorso :" + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
			
		}
		
		return percorso;
		
	}
	
	public Percorso updatePercorso(Percorso percorso) throws Exception {
		
		logger.debug("updatePercorso");
		
		KeyHolder holder = new GeneratedKeyHolder();
		
		try {
			
			getJdbcTemplate().update(new PreparedStatementCreator() {
				
				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					
					PreparedStatement ps = con.prepareStatement(SQL_UPDATE_PERCORSO, Statement.RETURN_GENERATED_KEYS);

					ps.setString(1, percorso.getSigla() != null ? percorso.getSigla() : null);
					ps.setString(2, percorso.getDescrizione() != null ? percorso.getDescrizione() : null);
					ps.setString(3, percorso.getNote() != null ? percorso.getNote() : null);
					ps.setString(4, percorso.getGeom());
					ps.setObject(5, percorso.getToponimo().getId());
					ps.setObject(6, percorso.getEstesaAmministrativa().getId());
					ps.setString(7, percorso.getStato());
					ps.setLong(8, percorso.getId());
					
					return ps;
				}
			}, holder);
			
			Long id = (Long)holder.getKeyList().get(0).get("id");
			percorso.setId(id);
			
		} catch (Exception e) {
			
			String message = "Errore nella modifica del percorso :" + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
			
		}
		
		return percorso;
		
	}

}
