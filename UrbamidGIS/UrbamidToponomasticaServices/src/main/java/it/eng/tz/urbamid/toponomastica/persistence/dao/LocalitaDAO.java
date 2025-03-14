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
import it.eng.tz.urbamid.toponomastica.persistence.model.Localita;

@Repository
public class LocalitaDAO extends GenericDao<Localita> {

	/** LOGGER **/
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final String SQL_INSERT_LOCALITA = "INSERT INTO public.u_topo_localita(id, descrizione, frazione, tipo, comune, geom, note, stato) VALUES (nextval('u_topo_localita_id_seq'), ?, ?, ?, ?, ST_GeomFromText(?), ?, 'PUBBLICATO')";
	private static final String SQL_INSERT_LOCALITA_NOGEOM = "INSERT INTO public.u_topo_localita(id, descrizione, frazione, tipo, comune, note, stato) VALUES (nextval('u_topo_localita_id_seq'), ?, ?, ?, ?, ?, 'PUBBLICATO')";
	private static final String SQL_UPDATE_LOCALITA = "UPDATE public.u_topo_localita SET descrizione=?, frazione=?, tipo=?, comune=?, geom=ST_GeomFromText(?), note=?, stato=? WHERE id = ? ";
	
	public Localita insertLocalita(Localita localita) throws Exception {
		
		logger.debug("insertLocalita");
		
		KeyHolder holder = new GeneratedKeyHolder();
		
		try {
			
			getJdbcTemplate().update(new PreparedStatementCreator() {
				
				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement ps = con.prepareStatement(SQL_INSERT_LOCALITA, Statement.RETURN_GENERATED_KEYS);

					ps.setString(1, localita.getDescrizione() != null ? localita.getDescrizione() : null);
					ps.setString(2, localita.getFrazione() != null ? localita.getFrazione() : null);
					ps.setObject(3, localita.getTipo().getId());
					ps.setObject(4, localita.getComune().getIdComune());
					ps.setString(5, localita.getGeom());
					ps.setString(6, localita.getNote() != null ? localita.getNote() : null);
					
					return ps;
				}
			}, holder);
			
			Long id =  (Long)holder.getKeyList().get(0).get("id");
			localita.setId(id);
			
		} catch (Exception e) {
			
			String message = "Errore nel inserimento della località: " + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
			
		}
		
		return localita;
		
	}
	
	public Localita insertLocalitaNoGeom(Localita localita) throws Exception {
		
		logger.debug("insertLocalitaNoGeom");
		
		KeyHolder holder = new GeneratedKeyHolder();
		
		try {
			
			getJdbcTemplate().update(new PreparedStatementCreator() {
				
				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement ps = con.prepareStatement(SQL_INSERT_LOCALITA_NOGEOM, Statement.RETURN_GENERATED_KEYS);

					ps.setString(1, localita.getDescrizione() != null ? localita.getDescrizione() : null);
					ps.setString(2, localita.getFrazione() != null ? localita.getFrazione() : null);
					ps.setObject(3, localita.getTipo().getId());
					ps.setObject(4, localita.getComune().getIdComune());
					ps.setString(5, localita.getNote() != null ? localita.getNote() : null);
					
					return ps;
				}
			}, holder);
			
			Long id =  (Long)holder.getKeyList().get(0).get("id");
			localita.setId(id);
			
		} catch (Exception e) {
			
			String message = "Errore nel inserimento della località: " + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
			
		}
		
		return localita;
		
	}

	public Localita updateLocalita(Localita localita) throws Exception {

		logger.debug("updateLocalita");
		
		KeyHolder holder = new GeneratedKeyHolder();
		
		try {
			
			getJdbcTemplate().update(new PreparedStatementCreator() {
				
				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement ps = con.prepareStatement(SQL_UPDATE_LOCALITA, Statement.RETURN_GENERATED_KEYS);

					ps.setString(1, localita.getDescrizione() != null ? localita.getDescrizione() : null);
					ps.setString(2, localita.getFrazione() != null ? localita.getFrazione() : null);
					ps.setObject(3, localita.getTipo().getId());
					ps.setObject(4, localita.getComune().getIdComune());
					ps.setString(5, localita.getGeom());
					ps.setString(6, localita.getNote() != null ? localita.getNote() : null);
					ps.setString(7, localita.getStato());
					ps.setLong(8, localita.getId());
					
					return ps;
				}
			}, holder);
			
			Long id =  (Long)holder.getKeyList().get(0).get("id");
			localita.setId(id);
			
		} catch (Exception e) {
		
			String message = "Errore nella modifica della località: " + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
			
		}
				
		return localita;
		
	}
	
}
