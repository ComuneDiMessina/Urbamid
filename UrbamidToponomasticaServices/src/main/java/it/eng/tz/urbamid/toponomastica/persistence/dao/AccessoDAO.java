package it.eng.tz.urbamid.toponomastica.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import it.eng.tz.urbamid.toponomastica.dao.GenericDao;
import it.eng.tz.urbamid.toponomastica.persistence.model.Accesso;
import it.eng.tz.urbamid.toponomastica.persistence.model.NumCivico;

@Repository
public class AccessoDAO extends GenericDao<Accesso> {

	/** LOGGER **/
	private final Logger logger = LoggerFactory.getLogger(AccessoDAO.class.getName());
	
	private static final String SQL_INSERT_ACCESSO = "INSERT INTO public.u_topo_accesso(id, toponimo_stradale, localita, numero, esponente, passo_carrabile, metodo, geom, tipo, note, principale, stato) VALUES (nextval('u_topo_accesso_id_seq'), ?, ?, ?, ?, ?, ?, ST_GeomFromText(?), ?, ?, ?, 'PUBBLICATO')";
	private static final String SQL_UPDATE_ACCESSO = "UPDATE public.u_topo_accesso SET toponimo_stradale=?, localita=?, numero=?, esponente=?, passo_carrabile=?, metodo=?, tipo=?, note=?, principale=?, geom=ST_GeomFromText(?), stato=? WHERE id = ?";
	private static final String SQL_DELETE_ACCESSO_BY_TOPONIMO = "DELETE FROM public.u_topo_accesso WHERE id = ? AND toponimo_stradale = ?";
	private static final String SQL_DELETE_ACCESSO_BY_LOCALITA = "DELETE FROM public.u_topo_accesso WHERE id = ? AND localita = ?";
	private static final String SQL_FIND_NUMBER_BY_STREET = "SELECT id, toponimo_stradale, numero, esponente, ST_SRID(geom) AS sysref, ST_X(ST_Centroid(geom)) as longitudine, ST_Y(ST_Centroid(geom)) as latitudine\r\n" + 
			"												 FROM public.u_topo_accesso\r\n" + 
			"												 WHERE toponimo_stradale = ?\r\n" + 
			"												 ORDER BY numero, esponente";
	private static final String SQL_SELECT_DENOMINAZIONE = "SELECT DISTINCT(toponimo.denominazione_ufficiale) FROM public.u_topo_accesso accesso JOIN public.u_topo_toponimo_stradale toponimo ON accesso.toponimo_stradale = toponimo.id WHERE toponimo.id = ?";
	public Accesso insert(Accesso accesso) throws Exception {
		
		logger.debug("insertAccesso");

		KeyHolder holder = new GeneratedKeyHolder();
		
		try {
			
			getJdbcTemplate().update(new PreparedStatementCreator() {
				
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(SQL_INSERT_ACCESSO, Statement.RETURN_GENERATED_KEYS);
					
					ps.setObject(1, accesso.getToponimo().getId());
					ps.setObject(2, accesso.getLocalita().getId());
					ps.setInt(3, accesso.getNumero());
					ps.setString(4, accesso.getEsponente() != null ? accesso.getEsponente() : null);
					ps.setBoolean(5, accesso.isPassoCarrabile());
					ps.setString(6, accesso.getMetodo() != null ? accesso.getMetodo() : null);
					ps.setString(7, accesso.getGeom());
					ps.setObject(8, accesso.getTipo().getId());
					ps.setString(9, accesso.getNote() != null ? accesso.getNote() : null);
					ps.setBoolean(10, accesso.isPrincipale());
					
					return ps;
				}
			}, holder);
			
			Long id = (Long)holder.getKeyList().get(0).get("id");
			accesso.setId(id);
			
		} catch (Exception e) {
			String message = "Errore nel inserimento dell'accesso :" + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
		}
		
		return accesso;
		
	}
	
	public Accesso update(Accesso accesso) throws Exception {
		
		logger.debug("updateAccesso");
		
		KeyHolder holder = new GeneratedKeyHolder();
		try {
			
			getJdbcTemplate().update(new PreparedStatementCreator() {
				
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_ACCESSO, Statement.RETURN_GENERATED_KEYS);

					ps.setObject(1, accesso.getToponimo().getId());
					ps.setObject(2, accesso.getLocalita().getId());
					ps.setInt(3, accesso.getNumero());
					ps.setString(4, accesso.getEsponente() != null ? accesso.getEsponente() : null);
					ps.setBoolean(5, accesso.isPassoCarrabile());
					ps.setString(6, accesso.getMetodo() != null ? accesso.getMetodo() : null);
					ps.setObject(7, accesso.getTipo().getId());
					ps.setString(8, accesso.getNote() != null ? accesso.getNote() : null);
					ps.setBoolean(9, accesso.isPrincipale());
					ps.setString(10, accesso.getGeom());
					ps.setString(11, accesso.getStato() != null ? accesso.getStato() : null);
					ps.setLong(12, accesso.getId());
					
					return ps;
				}
			}, holder);
			
			Long id =  (Long)holder.getKeyList().get(0).get("id");
			accesso.setId(id);
			
		} catch (Exception e) {
			String message = "Errore nella modifica dell'accesso :" + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
		}
		
		return accesso;
		
	}
	
	public void deleteByToponimo(Long id, Long toponimo) {
		
		logger.debug("deleteByToponimo");

		getJdbcTemplate().update(SQL_DELETE_ACCESSO_BY_TOPONIMO, new Object[] { id,
																				toponimo });
		
	}
	
	public void deleteByLocalita(Long id, Long localita) {
		
		logger.debug("deleteByLocalita");

		getJdbcTemplate().update(SQL_DELETE_ACCESSO_BY_LOCALITA, new Object[] { id,
																				localita });
		
	}
	
	public String getDenominazioneByAccesso(Long idToponimo) {
		
		logger.debug("getDenominazioneByAccesso");
		
		return getJdbcTemplate().query(SQL_SELECT_DENOMINAZIONE, new ResultSetExtractor<String>() {

			@Override
			public String extractData(ResultSet rs) throws SQLException, DataAccessException {
				if(rs.next()) {
					return rs.getString("denominazione_ufficiale");
				}
				return null;
			}
			
		}, idToponimo);
	}
	
	public List<NumCivico> getNumberbyStreet(Long codStrada) {
		
		logger.debug("getNumberbyStreet");
		
		return getJdbcTemplate().query(SQL_FIND_NUMBER_BY_STREET, new RowMapper<NumCivico>() {

			@Override
			public NumCivico mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				NumCivico numCivico = new NumCivico();
				
				numCivico.setId(rs.getLong("id"));
				numCivico.setNumero(rs.getInt("numero"));
				numCivico.setEsponente(rs.getString("esponente"));
				numCivico.setSysRef(rs.getString("sysref"));
				numCivico.setLatitudine(rs.getDouble("latitudine"));
				numCivico.setLongitudine(rs.getDouble("longitudine"));
				
				return numCivico;
			}
			
		}, codStrada);
		
	}
	
}
