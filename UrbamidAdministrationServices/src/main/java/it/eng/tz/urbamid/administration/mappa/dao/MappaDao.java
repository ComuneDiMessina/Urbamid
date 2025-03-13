package it.eng.tz.urbamid.administration.mappa.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import it.eng.tz.urbamid.administration.dao.GenericDao;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.MappaBean;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.MappaRowMapper;
import it.eng.tz.urbamid.administration.mappa.dao.model.Mappa;

/**
 * @author Alessandro Paolillo
 */
@Repository
public class MappaDao extends GenericDao<Mappa> {

	/** LOGGER **/
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String SQL_INSERT_MAP = "INSERT INTO public.u_admin_mappa(id, title, catalog, code, description, data_creazione, utente_creazione) VALUES (nextval('u_admin_mappa_id_seq'), ?, ?, ?, ?, ?, ?, ?, ?);";
	private static final String SQL_UPDATE_MAP = "UPDATE public.u_admin_mappa SET id=?, title=?, catalog=?, code=?, description=?, data_modifica=?, utente_modifica=? WHERE id=?";
	private static final String SQL_ALL_MAPS = "SELECT * FROM u_admin_mappa ORDER BY title ASC";
	private static final String SQL_FIND_MAPPA = "SELECT * FROM u_admin_mappa WHERE code = ?";
	private static final String SQL_TITLE_MAPPA_FIND = "SELECT title FROM u_admin_mappa WHERE code = ?";
	
	public Mappa getMap(String codeMappa) {
		logger.debug("getMap");
		return (Mappa) getJdbcTemplate().queryForObject(SQL_FIND_MAPPA, new MappaRowMapper(), codeMappa);
	}
	
	public List<Mappa> getAllMaps() {
		logger.debug("getAllMaps");
		return getJdbcTemplate().query(SQL_ALL_MAPS, new MappaRowMapper());
	}
	
	public void insert(Mappa mappa) { 
		getJdbcTemplate().update(SQL_INSERT_MAP, new Object[] {mappa.getTitle(),
																mappa.getCatalog(),
																mappa.getCode()+" COPIA",
																mappa.getDescription()});
	}
	
	public String saveOrUpdateMappa(MappaBean mappa) throws Exception {
		StopWatch sw = null;
		try{
			sw = new StopWatch("Salva la mappa");
			sw.start();	
			if( mappa.getIsNew() ){
				
				if (logger.isDebugEnabled()) {
					logger.debug("esecuzione query :" + SQL_INSERT_MAP);
				}
				getJdbcTemplate().update(SQL_INSERT_MAP,new Object[] { 
						mappa.getTitle(), 
						mappa.getCatalog(), 
						mappa.getCode(),
						mappa.getDescription(),
						mappa.getDataCreazione(),
						mappa.getUtenteCreazione()});
				if (logger.isDebugEnabled()) {
					logger.debug("esecuzione query :" + SQL_TITLE_MAPPA_FIND);
				}
				List<String> listaId = getJdbcTemplate().query(SQL_TITLE_MAPPA_FIND, new Object[] { mappa.getCode()}, new RowMapper<String>() {
					@Override
					public String mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getString("NAME");
					}
				});
				return listaId.get(0);
			} else {
				
				if (logger.isDebugEnabled()) {
					logger.debug("esecuzione query :" + SQL_UPDATE_MAP);
				}
				getJdbcTemplate().update(SQL_UPDATE_MAP,new Object[] { 
						mappa.getTitle(), 
						mappa.getCatalog(), 
						mappa.getCode(),
						mappa.getDescription(),
						mappa.getDataModifica(),
						mappa.getUtenteModifica(),
						mappa.getId()});
				 return mappa.getTitle();
			}
		}catch (Exception e){
			String message = "Errore nel salvataggio della mappa :" + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
		}finally{	
			if (null != sw && sw.isRunning()){
				sw.stop();	
			}
		}
	}
}
