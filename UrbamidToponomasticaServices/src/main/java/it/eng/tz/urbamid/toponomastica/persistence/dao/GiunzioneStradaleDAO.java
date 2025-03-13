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
import it.eng.tz.urbamid.toponomastica.persistence.model.Comuni;
import it.eng.tz.urbamid.toponomastica.persistence.model.Dug;
import it.eng.tz.urbamid.toponomastica.persistence.model.GiunzioneStradale;
import it.eng.tz.urbamid.toponomastica.persistence.model.TipoToponimo;
import it.eng.tz.urbamid.toponomastica.persistence.model.ToponimoStradale;

@Repository
public class GiunzioneStradaleDAO extends GenericDao<GiunzioneStradale>{

	/** LOGGER **/
	private final Logger logger = LoggerFactory.getLogger(ToponimoStradaleDAO.class.getName());
	
	private static final String SQL_INSERT_GIUNZIONE_STRADALE = "INSERT INTO public.u_topo_giunzioni_stradali(id, descrizione, tipo_topologico, tipo_funzionale, limite_amministrativo, inizio_fine_strada, geom, stato, note, is_circle) VALUES (nextval('u_topo_giunzioni_stradali_id_seq'), ?, ?, ?, ?, ?, ST_GeomFromText(?), 'PUBBLICATO', ?, ?)";
	private static final String SQL_UPDATE_GIUNZIONE_STRADALE = "UPDATE public.u_topo_giunzioni_stradali SET descrizione=?, tipo_topologico=?, tipo_funzionale=?, limite_amministrativo=?, inizio_fine_strada=?, geom=ST_GeomFromText(?), stato=?, note=?, is_circle=? WHERE id = ?";
	private static final String SQL_INTERSEZIONE_GIUNZIONE_STRADALE = "SELECT id, comune_label, denominazione_ufficiale, denominazi, classe_label, shape_leng, cap, compendi, precdenomi, quartiere, ST_AsText(geom) AS geom, numero_delibera, data_delibera, codice_autorizzazione, data_autorizzazione, id_comune, id_classe, note, tipo, codice, id_padre, stato\r\n" + 
																	  "FROM public.u_topo_toponimo_stradale\r\n" + 
																	  "WHERE ST_Intersects(ST_GeomFromText(?)::geometry, ST_AsText(geom)::geometry) AND stato = 'PUBBLICATO'";
	
	public GiunzioneStradale insert(GiunzioneStradale giunzione) throws Exception {
		
		KeyHolder holder = new GeneratedKeyHolder();
		
		try {
			
			getJdbcTemplate().update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement ps = con.prepareStatement(SQL_INSERT_GIUNZIONE_STRADALE, Statement.RETURN_GENERATED_KEYS);

					ps.setString(1, giunzione.getDescrizione() != null ? giunzione.getDescrizione() : null);
					ps.setObject(2, giunzione.getTipoTopologico().getId());
					ps.setObject(3, giunzione.getTipoFunzionale().getId());
					ps.setBoolean(4, giunzione.isLimiteAmministrativo());
					ps.setBoolean(5, giunzione.isInizioFineStrada());
					ps.setString(6, giunzione.getGeom());
					ps.setString(7, giunzione.getNote() != null ? giunzione.getNote() : null);
					ps.setBoolean(8, giunzione.getIsCircle());
					
					return ps;
				}
			}, holder);
			
			Long id =  (Long)holder.getKeyList().get(0).get("id");
			giunzione.setId(id);
			
		} catch (Exception e) {
			
			String message = "Errore nel inserimento della giunzione stradale: " + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
		
		}
		
		return giunzione;
		
	}
	
	public GiunzioneStradale update(GiunzioneStradale giunzione) throws Exception {
		
		KeyHolder holder = new GeneratedKeyHolder();
		
		try {
			
			getJdbcTemplate().update(new PreparedStatementCreator() {
				
				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement ps = con.prepareStatement(SQL_UPDATE_GIUNZIONE_STRADALE, Statement.RETURN_GENERATED_KEYS);

					ps.setString(1, giunzione.getDescrizione() != null ? giunzione.getDescrizione() : null);
					ps.setObject(2, giunzione.getTipoTopologico().getId());
					ps.setObject(3, giunzione.getTipoFunzionale().getId());
					ps.setBoolean(4, giunzione.isLimiteAmministrativo());
					ps.setBoolean(5, giunzione.isInizioFineStrada());
					ps.setString(6, giunzione.getGeom());
					ps.setString(7, giunzione.getStato() != null ? giunzione.getStato() : null);
					ps.setString(8, giunzione.getNote() != null ? giunzione.getNote() : null);
					ps.setBoolean(9, giunzione.getIsCircle());
					ps.setLong(10, giunzione.getId());
					
					return ps;
				}
			}, holder);
			
			Long id =  (Long)holder.getKeyList().get(0).get("id");
			giunzione.setId(id);
			
		} catch (Exception e) {
			
			String message = "Errore nella modifica della giunzione stradale: " + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
			
		}
		
		return giunzione;
		
	}
	
	public List<ToponimoStradale> findIntersections(String geom) {
		
		logger.debug("intersezioneToponimi");
		
		return getJdbcTemplate().query(SQL_INTERSEZIONE_GIUNZIONE_STRADALE, new RowMapper<ToponimoStradale>() {

			@Override
			public ToponimoStradale mapRow(ResultSet rs, int rowNum) throws SQLException {				
				ToponimoStradale toponimo = new ToponimoStradale();
				
				Comuni comune = new Comuni();
				comune.setIdComune(rs.getLong("id_comune"));
				
				Dug dug = new Dug();
				dug.setId(rs.getLong("id_classe"));
				
				TipoToponimo tipo = new TipoToponimo();
				tipo.setId(rs.getLong("tipo"));
				
				toponimo.setId(rs.getLong("id"));
				toponimo.setComuneLabel(rs.getString("comune_label"));
				toponimo.setDenominazioneUfficiale(rs.getString("denominazione_ufficiale"));
				toponimo.setClasseLabel(rs.getString("classe_label"));
				toponimo.setShapeLeng(rs.getString("shape_leng"));
				toponimo.setCap(rs.getString("cap"));
				toponimo.setCompendi(rs.getString("compendi"));
				toponimo.setPrecdenomi(rs.getString("precdenomi"));
				toponimo.setQuartiere(rs.getString("quartiere"));
				toponimo.setGeom(rs.getString("geom"));
				toponimo.setNumeroDelibera(rs.getString("numero_delibera"));
				toponimo.setDataDelibera(rs.getDate("data_delibera"));
				toponimo.setCodiceAutorizzazione(rs.getString("codice_autorizzazione"));
				toponimo.setDataAutorizzazione(rs.getDate("data_autorizzazione"));
				toponimo.setComune(comune);
				toponimo.setClasse(dug);
				toponimo.setNote(rs.getString("note"));
				toponimo.setStato(rs.getString("stato"));
				toponimo.setTipo(tipo);
				toponimo.setCodice(rs.getString("codice"));
				toponimo.setIdPadre(rs.getLong("id_padre"));
				
				return toponimo;
			}
			
		}, geom);
		
	}
	
}
