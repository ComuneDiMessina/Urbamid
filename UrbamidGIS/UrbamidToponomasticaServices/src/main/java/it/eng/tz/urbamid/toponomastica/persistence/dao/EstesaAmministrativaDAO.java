package it.eng.tz.urbamid.toponomastica.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import it.eng.tz.urbamid.toponomastica.dao.GenericDao;
import it.eng.tz.urbamid.toponomastica.persistence.model.EstesaAmministrativa;
import it.eng.tz.urbamid.toponomastica.web.dto.EstesaAmministrativaDTO;

@Repository
public class EstesaAmministrativaDAO extends GenericDao<EstesaAmministrativa> {

	/** LOGGER **/
	private final Logger logger = LoggerFactory.getLogger(EstesaAmministrativaDAO.class.getName());
	
	private static final String SQL_INSERT_ESTESA_AMMINISTRATIVA = "INSERT INTO public.u_topo_estesa_amministrativa(id, sigla, descrizione, classifica_amministrativa, codice, estensione, tronco, patrimonialita, ente_gestore, classifica_funzionale, provincia, comune, geom, note, stato, is_circle) VALUES (nextval('u_topo_estesa_amministrativa_id_seq'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ST_AsText(?), ?, 'PUBBLICATO', ?)";
	private static final String SQL_UPDATE_ESTESA_AMMINISTRATIVA = "UPDATE public.u_topo_estesa_amministrativa SET sigla=?, descrizione=?, classifica_amministrativa=?, codice=?, estensione=?, tronco=?, patrimonialita=?, ente_gestore=?, classifica_funzionale=?, provincia=?, comune=?, geom=ST_AsText(?), note=?, stato=?, is_circle=? WHERE id = ?";
//	private static final String SQL_SELECT_ESTESA_AMMINISTRATIVA = "SELECT ea.id, ea.descrizione\r\n" + 
//			"														FROM public.u_topo_estesa_amministrativa ea JOIN public.u_topo_classifica_amministrativa ca ON ea.classifica_amministrativa = ca.id\r\n" + 
//			"	 																								JOIN public.u_topo_patrimonialita pa ON ea.patrimonialita = pa.id\r\n" + 
//			"																									JOIN public.u_topo_ente_gestore eg ON ea.ente_gestore = eg.id\r\n" + 
//			"																									JOIN public.u_topo_classifica_funzionale cf ON ea.classifica_funzionale = cf.id\r\n" + 
//			"														WHERE UPPER(ea.descrizione) LIKE ?";
	private static final String SQL_SELECT_ESTESA_AMMINISTRATIVA	= "SELECT MAX(id) AS id, sigla, descrizione FROM public.u_topo_estesa_amministrativa WHERE UPPER(descrizione) like UPPER(?) GROUP BY descrizione, sigla";
	private static final String SQL_SELECT_SIGLA_ESTESA_AMMINISTRATIVA = "SELECT sigla FROM public.u_topo_estesa_amministrativa WHERE id = ?";
	
	
	public EstesaAmministrativa insert(EstesaAmministrativa estesa) throws Exception {
		
		logger.debug("Insert");
		
		KeyHolder holder = new GeneratedKeyHolder();
		try {
			
			getJdbcTemplate().update(new PreparedStatementCreator() {
				
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(SQL_INSERT_ESTESA_AMMINISTRATIVA, Statement.RETURN_GENERATED_KEYS);

					ps.setString(1, estesa.getSigla() != null ? estesa.getSigla() : null);
					ps.setString(2, estesa.getDescrizione() != null ? estesa.getDescrizione() : null);
					ps.setObject(3, estesa.getClassificaAmministrativa().getId());
					ps.setString(4, estesa.getCodice() != null ? estesa.getCodice() : null);
					ps.setString(5, estesa.getEstensione() != null ? estesa.getEstensione() : null);
					ps.setString(6, estesa.getTronco() != null ? estesa.getTronco() : null);
					ps.setObject(7, estesa.getPatrimonialita().getId());
					ps.setObject(8, estesa.getEnteGestore().getId());
					ps.setObject(9, estesa.getClassificaFunzionale().getId());
					ps.setObject(10, estesa.getProvincia().getIdProvincia());
					ps.setObject(11, estesa.getComune().getIdComune());
					ps.setString(12, estesa.getGeom());
					ps.setString(13, estesa.getNote() != null ? estesa.getNote() : null);
					ps.setBoolean(14, estesa.getIsCircle());
					
					return ps;
				}
			}, holder);
			
			Long id = (Long)holder.getKeyList().get(0).get("id");
			estesa.setId(id);
			
		} catch (Exception e) {
			String message = "Errore nel inserimento dell'estesa amministrativa :" + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
		}
		
		return estesa;
		
	}
	
	public EstesaAmministrativa update(EstesaAmministrativa estesa) throws Exception {
		
		logger.debug("Update");
		
		KeyHolder holder = new GeneratedKeyHolder();
		try {
			
			getJdbcTemplate().update(new PreparedStatementCreator() {
				
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_ESTESA_AMMINISTRATIVA, Statement.RETURN_GENERATED_KEYS);

					ps.setString(1, estesa.getSigla() != null ? estesa.getSigla() : null);
					ps.setString(2, estesa.getDescrizione() != null ? estesa.getDescrizione() : null);
					ps.setObject(3, estesa.getClassificaAmministrativa().getId());
					ps.setString(4, estesa.getCodice() != null ? estesa.getCodice() : null);
					ps.setString(5, estesa.getEstensione() != null ? estesa.getEstensione() : null);
					ps.setString(6, estesa.getTronco());
					ps.setObject(7, estesa.getPatrimonialita().getId());
					ps.setObject(8, estesa.getEnteGestore().getId());
					ps.setObject(9, estesa.getClassificaFunzionale().getId());
					ps.setObject(10, estesa.getProvincia().getIdProvincia());
					ps.setObject(11, estesa.getComune().getIdComune());
					ps.setString(12, estesa.getGeom());
					ps.setString(13, estesa.getNote() != null ? estesa.getNote() : null);
					ps.setString(14, estesa.getStato() != null ? estesa.getStato() : null);
					ps.setBoolean(15, estesa.getIsCircle());
					ps.setLong(16, estesa.getId());
					
					return ps;
				}
			}, holder);
			
			Long id = (Long)holder.getKeyList().get(0).get("id");
			estesa.setId(id);
			
		} catch (Exception e) {
			String message = "Errore nel inserimento dell'estesa amministrativa :" + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
		}
		
		return estesa;
		
	}
	
	public List<EstesaAmministrativaDTO> ricerca(String descrizione) {
		
		logger.debug("Ricerca estesaAmministrativa");
		
		return getJdbcTemplate().query(SQL_SELECT_ESTESA_AMMINISTRATIVA, new RowMapper<EstesaAmministrativaDTO>() {

			@Override
			public EstesaAmministrativaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				EstesaAmministrativaDTO estesaAmministrativa = new EstesaAmministrativaDTO();
				
				estesaAmministrativa.setId(rs.getLong("id"));
				estesaAmministrativa.setSigla(rs.getString("sigla"));
				estesaAmministrativa.setDescrizione(rs.getString("descrizione"));
				
				return estesaAmministrativa;
				
			}
			
		}, "%" + descrizione + "%");
		
	}
	
	public List<EstesaAmministrativaDTO> ricercaSigla(Long id) {
		
		logger.debug("Ricerca estesaAmministrativa");
		
		return getJdbcTemplate().query(SQL_SELECT_SIGLA_ESTESA_AMMINISTRATIVA, new RowMapper<EstesaAmministrativaDTO>() {

			@Override
			public EstesaAmministrativaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				EstesaAmministrativaDTO estesaAmministrativa = new EstesaAmministrativaDTO();
	
				estesaAmministrativa.setSigla(rs.getString("sigla"));
				
				return estesaAmministrativa;
				
			}
			
		}, id);
		
	}
	
}
