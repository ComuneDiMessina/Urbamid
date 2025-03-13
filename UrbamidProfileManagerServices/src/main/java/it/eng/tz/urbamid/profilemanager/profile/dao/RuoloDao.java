package it.eng.tz.urbamid.profilemanager.profile.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import it.eng.tz.urbamid.profilemanager.dao.GenericDao;
import it.eng.tz.urbamid.profilemanager.persistence.model.Ruolo;
import it.eng.tz.urbamid.profilemanager.web.dto.RuoloDto;

/**
 * @author Alessandro Paolillo
 */
@Repository
public class RuoloDao extends GenericDao<Ruolo> {

	/** LOGGER **/
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String SQL_UPDATE_RUOLO = "UPDATE public.u_pm_ruolo SET descrizione=?, denominazione=? WHERE codice=?";
	private static final String SQL_INSERT_RUOLO = "INSERT INTO public.u_pm_ruolo ( codice, descrizione, denominazione, ruolo_default) VALUES ( ?, ?, ?, false)";
	private static final String SQL_SELECT_RUOLO_BYCODICE = "SELECT * FROM public.u_pm_ruolo WHERE codice = ?";
	private static final String SQL_SELECT_RUOLO_BYID = "SELECT * FROM public.u_pm_ruolo WHERE id = ?";
	private static final String SQL_SELECT_RUOLO_BYIDUTENTE = "SELECT r.* FROM public.u_pm_ruolo as r INNER JOIN public.u_pm_utente_ruolo as ur on ur.id_ruolo = r.id WHERE ur.id_utente = ?";
	
	public void insert(RuoloDto ruoloDto) throws Exception {
		StopWatch sw = null;
		try{
			sw = new StopWatch("Salva ruolo");
			sw.start();
			getJdbcTemplate().update(SQL_INSERT_RUOLO, new Object[] {
					ruoloDto.getCodice(),
					ruoloDto.getDescrizione(),
					ruoloDto.getDenominazione()
			});
			if (logger.isDebugEnabled()) {
				logger.debug("esecuzione query :" + SQL_INSERT_RUOLO);
			}
		}catch (Exception e){
			String message = "Errore nel salvataggio dell' ruolo :" + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
		}finally{	
			if (null != sw && sw.isRunning()){
				sw.stop();	
			}
		}
	}
	
	public void updateRuolo(RuoloDto ruoloDto) throws Exception {
		StopWatch sw = null;
		try{
			sw = new StopWatch("Salva ruolo");
			sw.start();	
				getJdbcTemplate().update(SQL_UPDATE_RUOLO,new Object[] { 
						ruoloDto.getDescrizione(),
						ruoloDto.getDenominazione(),
						ruoloDto.getCodice()
				});
				if (logger.isDebugEnabled()) {
					logger.debug("esecuzione query :" + SQL_UPDATE_RUOLO);
				}
		}catch (Exception e){
			String message = "Errore nel salvataggio del ruolo :" + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
		}finally{	
			if (null != sw && sw.isRunning()){
				sw.stop();	
			}
		}
	}
	
	public Ruolo selectByCodice(RuoloDto ruoloDto) throws Exception {
		StopWatch sw = null;
		Ruolo model = null;
		try{
			sw = new StopWatch("Recupera ruolo con il codice");
			sw.start();
			List<Ruolo> listaRuolo = getJdbcTemplate().query(
															SQL_SELECT_RUOLO_BYCODICE, 
															new Object[]{ruoloDto.getCodice() }, 
															new RowMapper<Ruolo>() {
																@Override
																public Ruolo mapRow(ResultSet rs, int rowNum) throws SQLException {
																	Ruolo ruolo = new Ruolo();
																	ruolo.setId(rs.getLong("id"));
																	ruolo.setCodice(rs.getString("codice"));
																	ruolo.setDenominazione(rs.getString("denominazione"));
																	ruolo.setDescrizione(rs.getString("descrizione"));
																	ruolo.setRuoloDefault(rs.getBoolean("ruolo_default"));
																	return ruolo;
																}
															}
			);
			model = (listaRuolo!=null && listaRuolo.size()>0) ? listaRuolo.get(0):null;
			if (logger.isDebugEnabled()) {
				logger.debug("esecuzione query :" + SQL_SELECT_RUOLO_BYCODICE);
			}
		}catch (Exception e){
			String message = "Errore nel recupero del ruolo:" + e.getMessage();
			logger.error(message, e);
		}finally{	
			if (null != sw && sw.isRunning()){
				sw.stop();	
			}
		}
		return model;
	}
	
	public Ruolo selectById(long idRuolo) throws Exception {
		StopWatch sw = null;
		Ruolo model = null;
		try{
			sw = new StopWatch("Recupera ruolo con id");
			sw.start();
			List<Ruolo> listaRuolo = getJdbcTemplate().query(
															SQL_SELECT_RUOLO_BYID, 
															new Object[]{idRuolo }, 
															new RowMapper<Ruolo>() {
																@Override
																public Ruolo mapRow(ResultSet rs, int rowNum) throws SQLException {
																	Ruolo ruolo = new Ruolo();
																	ruolo.setId(rs.getLong("id"));
																	ruolo.setCodice(rs.getString("codice"));
																	ruolo.setDenominazione(rs.getString("denominazione"));
																	ruolo.setDescrizione(rs.getString("descrizione"));
																	ruolo.setRuoloDefault(rs.getBoolean("ruolo_default"));
																	return ruolo;
																}
															}
			);
			model = (listaRuolo!=null && listaRuolo.size()>0) ? listaRuolo.get(0):null;
			if (logger.isDebugEnabled()) {
				logger.debug("esecuzione query :" + SQL_SELECT_RUOLO_BYID);
			}
		}catch (Exception e){
			String message = "Errore nel recupero del ruolo:" + e.getMessage();
			logger.error(message, e);
		}finally{	
			if (null != sw && sw.isRunning()){
				sw.stop();	
			}
		}
		return model;
	}
	
	public Ruolo selectByIdUtente(long idUtente) throws Exception {
		StopWatch sw = null;
		Ruolo model = null;
		try{
			sw = new StopWatch("Recupera ruolo di un utente id_utente");
			sw.start();
			List<Ruolo> listaRuolo = getJdbcTemplate().query(
															SQL_SELECT_RUOLO_BYIDUTENTE, 
															new Object[]{idUtente }, 
															new RowMapper<Ruolo>() {
																@Override
																public Ruolo mapRow(ResultSet rs, int rowNum) throws SQLException {
																	Ruolo ruolo = new Ruolo();
																	ruolo.setId(rs.getLong("id"));
																	ruolo.setCodice(rs.getString("codice"));
																	ruolo.setDenominazione(rs.getString("denominazione"));
																	ruolo.setDescrizione(rs.getString("descrizione"));
																	ruolo.setRuoloDefault(rs.getBoolean("ruolo_default"));
																	return ruolo;
																}
															}
			);
			model = (listaRuolo!=null && listaRuolo.size()>0) ? listaRuolo.get(0):null;
			if (logger.isDebugEnabled()) {
				logger.debug("esecuzione query :" + SQL_SELECT_RUOLO_BYIDUTENTE);
			}
		}catch (Exception e){
			String message = "Errore nel recupero del ruolo di un utente:" + e.getMessage();
			logger.error(message, e);
		}finally{	
			if (null != sw && sw.isRunning()){
				sw.stop();	
			}
		}
		return model;
	}
}



