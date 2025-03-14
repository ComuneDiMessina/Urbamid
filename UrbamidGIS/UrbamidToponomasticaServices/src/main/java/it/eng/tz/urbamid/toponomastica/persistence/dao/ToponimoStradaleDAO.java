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
import it.eng.tz.urbamid.toponomastica.persistence.model.Toponimo;
import it.eng.tz.urbamid.toponomastica.persistence.model.ToponimoStradale;
import it.eng.tz.urbamid.toponomastica.web.dto.ToponimoDto;
import it.eng.tz.urbamid.toponomastica.web.dto.ToponimoDugDTO;

/**
 * @author Luca Tricarico
 */

@Repository
public class ToponimoStradaleDAO extends GenericDao<ToponimoStradale>{

	/** LOGGER **/
	private final Logger logger = LoggerFactory.getLogger(ToponimoStradaleDAO.class.getName());
	
	private static final String SQL_INSERT_TOPONIMO_STRADALE = "INSERT INTO public.u_topo_toponimo_stradale(id, comune_label, denominazione, denominazione_ufficiale, classe_label, shape_leng, cap, compendi, precdenomi, quartiere, geom, numero_delibera, data_delibera, codice_autorizzazione, data_autorizzazione, id_comune, id_classe, note, tipo, codice, stato, id_padre, is_circle) VALUES (nextval('u_topo_toponimo_stradale_id_seq'), ?, ?, ?, ?, ST_Length(ST_GeomFromText(?)::geography), ?, ?, ?, ?, ST_AsText(?), ?, ?, ?, ?, ?, ?, ?, ?, ?, 'BOZZA',?,?);";
	private static final String SQL_UPDATE_TOPONIMO_STRADALE = "UPDATE public.u_topo_toponimo_stradale SET comune_label=?, denominazione=?, denominazione_ufficiale=?, classe_label=?, shape_leng=ST_Length(ST_GeomFromText(?)::geography), cap=?, compendi=?, precdenomi=?, quartiere=?, geom=ST_GeomFromText(?), numero_delibera=?, data_delibera=?, codice_autorizzazione=?, data_autorizzazione=?, id_comune=?, id_classe=?, note=?, tipo=?, codice=?, stato=?, id_padre=?, is_circle=? WHERE id = ?";
	private static final String SQL_UPDATE_TOPONIMO_STRADALE_STATO = "UPDATE public.u_topo_toponimo_stradale SET stato = 'PUBBLICATO' WHERE id = ? AND data_delibera IS NOT NULL AND numero_delibera IS NOT NULL AND data_autorizzazione IS NOT NULL AND codice_autorizzazione IS NOT NULL";
	private static final String SQL_DELETE_TOPONIMO_STRADALE = "DELETE FROM public.u_topo_toponimo_stradale WHERE id = ?";
	private static final String SQL_ARCHIVIA_TOPONIMO_STRADALE = "UPDATE public.u_topo_toponimo_stradale SET stato = 'ARCHIVIATO', data_fine_validita = NOW() WHERE id = ?";
	private static final String SQL_SELECT_TOPONIMO_STRADALE_DUG = "SELECT t.id, d.dug, t.denominazione_ufficiale FROM public.u_topo_toponimo_stradale t INNER JOIN public.u_topo_dug d ON t.id_classe = d.id WHERE UPPER(t.denominazione_ufficiale) LIKE ?";
//	private static final String SQL_SELECT_FIND_BY_NAME	= "SELECT id, codice, denominazione_ufficiale, ST_SRID(geom) AS sysref, ST_X(ST_Centroid(geom)) as longitudine, ST_Y(ST_Centroid(geom)) as latitudine\r\n" + 
//			"											   FROM public.u_topo_toponimo_stradale\r\n" + 
//			"											   WHERE UPPER(denominazione_ufficiale) like UPPER(?) ORDER BY denominazione_ufficiale ASC";
	private static final String SQL_SELECT_FIND_BY_NAME	= "SELECT id, codice, denominazione_ufficiale, "+
															"CASE WHEN (ST_SRID(geom) IS NULL OR ST_SRID(geom) = 0) THEN '4326' ELSE ST_SRID(geom) END AS sysref, "+  
															"ST_X(ST_Centroid(geom)) as longitudine, ST_Y(ST_Centroid(geom)) as latitudine "+
															"FROM public.u_topo_toponimo_stradale "+
															"WHERE UPPER(denominazione_ufficiale) like UPPER(?) ORDER BY denominazione_ufficiale ASC";
	private static final String SQL_SELECT_FIND_BY_ID 	= "SELECT * FROM public.u_topo_toponimo_stradale WHERE id=?";
	
	public ToponimoStradale insert(ToponimoStradale toponimo) throws Exception {	
		KeyHolder holder = new GeneratedKeyHolder();
		try {
			
			getJdbcTemplate().update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(SQL_INSERT_TOPONIMO_STRADALE, Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, toponimo.getComuneLabel());
					ps.setString(2, toponimo.getDenominazione());
					ps.setString(3, toponimo.getDenominazioneUfficiale());
					ps.setString(4, toponimo.getClasseLabel());
					ps.setString(5, toponimo.getShapeLeng());
					ps.setString(6, toponimo.getCap() != null ? toponimo.getCap() : null);
					ps.setString(7, toponimo.getCompendi() != null ? toponimo.getCompendi() : null);
					ps.setString(8, toponimo.getPrecdenomi());
					ps.setString(9, toponimo.getQuartiere());
					ps.setString(10, toponimo.getGeom());
					ps.setString(11, toponimo.getNumeroDelibera() != null ? toponimo.getNumeroDelibera() : null);
					ps.setDate(12, toponimo.getDataDelibera()!=null ? new java.sql.Date(toponimo.getDataDelibera().getTime()):null );
					ps.setString(13, toponimo.getCodiceAutorizzazione() != null ? toponimo.getCodiceAutorizzazione() : null);
					ps.setDate(14, toponimo.getDataAutorizzazione()!=null ? new java.sql.Date(toponimo.getDataAutorizzazione().getTime()) : null );
					ps.setLong(15, toponimo.getComune() != null && toponimo.getComune().getIdComune() != null ? toponimo.getComune().getIdComune() : new Long(10185));
					ps.setLong(16, toponimo.getClasse()!=null && toponimo.getClasse().getId()!=null? toponimo.getClasse().getId(): new Long(385));
					ps.setString(17, toponimo.getNote() != null ? toponimo.getNote() : null);
					ps.setLong(18, toponimo.getTipo()!=null && toponimo.getTipo().getId()!=null ? toponimo.getTipo().getId():new Long(0));
					ps.setString(19, toponimo.getCodice() != null ? toponimo.getCodice() : null);
					ps.setLong(20, toponimo.getIdPadre() != null ? toponimo.getIdPadre() : new Long(0));
					ps.setBoolean(21, toponimo.getIsCircle());
					return ps;
				}
			}, holder);
			
			Integer id =  (Integer)holder.getKeyList().get(0).get("id");
			toponimo.setId( new Long(id));
			
		} catch (Exception e) {
			String message = "Errore nel inserimento del toponimo stradale :" + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
		}

		return toponimo;
	}
	
	public ToponimoStradale update(ToponimoStradale toponimo) throws Exception {
		KeyHolder holder = new GeneratedKeyHolder();
		try {
			getJdbcTemplate().update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_TOPONIMO_STRADALE, Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, toponimo.getComuneLabel());
					ps.setString(2, toponimo.getDenominazione());
					ps.setString(3, toponimo.getDenominazioneUfficiale());
					ps.setString(4, toponimo.getClasseLabel());
					ps.setString(5, toponimo.getShapeLeng());
					ps.setString(6, toponimo.getCap() != null ? toponimo.getCap() : null);
					ps.setString(7, toponimo.getCompendi() != null ? toponimo.getCompendi() : null);
					ps.setString(8, toponimo.getPrecdenomi());
					ps.setString(9, toponimo.getQuartiere());
					ps.setString(10, toponimo.getGeom());
					ps.setString(11, toponimo.getNumeroDelibera() != null ? toponimo.getNumeroDelibera() : null);
					ps.setDate(12, toponimo.getDataDelibera()!=null ? new java.sql.Date(toponimo.getDataDelibera().getTime()):null );
					ps.setString(13, toponimo.getCodiceAutorizzazione() != null ? toponimo.getCodiceAutorizzazione() : null);
					ps.setDate(14, toponimo.getDataAutorizzazione()!=null ? new java.sql.Date(toponimo.getDataAutorizzazione().getTime()) : null  );
					ps.setLong(15, toponimo.getComune() != null && toponimo.getComune().getIdComune() != null ? toponimo.getComune().getIdComune() : new Long(10185));
					ps.setLong(16, toponimo.getClasse()!=null && toponimo.getClasse().getId()!=null? toponimo.getClasse().getId(): new Long(385));
					ps.setString(17, toponimo.getNote() != null ? toponimo.getNote() : null);
					ps.setLong(18, toponimo.getTipo()!=null && toponimo.getTipo().getId()!=null ? toponimo.getTipo().getId():new Long(0));
					ps.setString(19, toponimo.getCodice() != null ? toponimo.getCodice() : null);
					ps.setString(20, toponimo.getStato());
					ps.setLong(21, toponimo.getIdPadre() != null ? toponimo.getIdPadre() : new Long(0));
					ps.setBoolean(22, toponimo.getIsCircle());
					ps.setLong(23, toponimo.getId());
					return ps;
				}
			}, holder);
			Integer id =  (Integer)holder.getKeyList().get(0).get("id");
			toponimo.setId( new Long(id));
		}catch (Exception e){
			
			String message = "Errore nella modifica del toponimo stradale :" + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
		}
		return toponimo;
	}
	
	public int pubblicaToponimoStradale(Long id) {
		
		logger.debug("updateStato Toponimo Stradale");
		int result = getJdbcTemplate().update(SQL_UPDATE_TOPONIMO_STRADALE_STATO, new Object[] { id });
		return  result;
	}

	public void delete(Long id) {
		
		logger.debug("delete");
		getJdbcTemplate().update(SQL_DELETE_TOPONIMO_STRADALE, new Object[] { id });
	}
	
	public void archivia(Long id) {
		
		logger.debug("Archivia Toponimo Stradale");
		getJdbcTemplate().update(SQL_ARCHIVIA_TOPONIMO_STRADALE, new Object[] { id });
	}

	public List<ToponimoDto> findById(Long id) {
		
		logger.debug("findById");
		return getJdbcTemplate().query(SQL_SELECT_FIND_BY_ID, new RowMapper<ToponimoDto>() {

			@Override
			public ToponimoDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				ToponimoDto toponimo = new ToponimoDto();
				toponimo.setId(rs.getLong("id"));
				return toponimo;
			}
		}, id );
	}
	
	public List<ToponimoDugDTO> ricerca(String toponimo) {
		
		logger.debug("ricerca");
		return getJdbcTemplate().query(SQL_SELECT_TOPONIMO_STRADALE_DUG, new RowMapper<ToponimoDugDTO>() {

			@Override
			public ToponimoDugDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ToponimoDugDTO toponimo = new ToponimoDugDTO();
				toponimo.setId(rs.getLong("id"));
				toponimo.setToponimo(rs.getString("denominazione_ufficiale"));
				return toponimo;
			}
		}, "%" + toponimo.toUpperCase() + "%");
	}
	
	public List<Toponimo> findStreetByName(String streetName) {
		
		logger.debug("findStreetByName");
		return getJdbcTemplate().query(SQL_SELECT_FIND_BY_NAME, new RowMapper<Toponimo>() {

			@Override
			public Toponimo mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				Toponimo toponimo = new Toponimo();
				
				toponimo.setId(rs.getLong("id"));
				toponimo.setCodStrada(rs.getInt("codice"));
				toponimo.setDenominazione(rs.getString("denominazione_ufficiale"));
				toponimo.setSysref(rs.getString("sysref"));
				toponimo.setLatitudine(rs.getDouble("latitudine"));
				toponimo.setLongitudine(rs.getDouble("longitudine"));
				
				return toponimo;
			}
			
		}, "%" + streetName + "%");
		
	}
	
}
