package it.eng.tz.urbamid.profilemanager.profile.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import it.eng.tz.urbamid.profilemanager.dao.GenericDao;
import it.eng.tz.urbamid.profilemanager.persistence.model.RuoloUtente;
import it.eng.tz.urbamid.profilemanager.profile.dao.mapper.UtenteRuoloRowMapper;
import it.eng.tz.urbamid.profilemanager.web.dto.RuoloUtenteShtDto;

/**
 * @author Alessandro Paolillo
 */
@Repository
public class RuoloUtenteDao extends GenericDao<RuoloUtente> {

	/** LOGGER **/
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String SQL_SELECT_UTENTE_RUOLO = "SELECT * FROM public.u_pm_utente_ruolo WHERE id_utente=?";
	private static final String SQL_SELECT_UTENTE_BY_CODRUOLO = "SELECT ur.* "
															+ "FROM public.u_pm_utente_ruolo AS ur, public.u_pm_ruolo AS r "
															+ "WHERE "
																+ "r.id = ur.id_ruolo AND"
																+ "r.codice=?";
	private static final String SQL_SELECT_UTENTE_RUOLO_BYCODICE = "SELECT ru.* FROM public.u_pm_utente_ruolo ru, public.u_pm_ruolo r WHERE ru.id_ruolo = r.id_ruolo AND r.codice=?";
	private static final String SQL_UPDATE_UTENTE_RUOLO = "UPDATE public.u_pm_utente_ruolo SET id_ruolo=? WHERE id_utente=?";
	private static final String SQL_INSERT_UTENTE_RUOLO = "INSERT INTO public.u_pm_utente_ruolo ( id_ruolo, id_utente) VALUES ( ?, ?)";
	private static final String SQL_INSERT_UTENTE_RUOLO_CITTADINO = "INSERT INTO public.u_pm_utente_ruolo(id, id_utente, id_ruolo) VALUES ( nextval('u_pm_utente_ruolo_id_seq'), ?, 45);";
	private static final String SQL_DELETE_UTENTE_RUOLO = "DELETE FROM public.u_pm_utente_ruolo WHERE id_utente=?";
	
	public RuoloUtente select(Long idUtente) throws Exception {
		StopWatch sw = null;
		RuoloUtente model = null;
		try{
			sw = new StopWatch("Recupera associazione utente ruolo");
			sw.start();
			model = getJdbcTemplate().queryForObject(SQL_SELECT_UTENTE_RUOLO, new Object[]{idUtente}, new UtenteRuoloRowMapper());
			if (logger.isDebugEnabled()) {
				logger.debug("esecuzione query :" + SQL_SELECT_UTENTE_RUOLO);
			}
		}catch (Exception e){
			String message = "Errore nel salvataggio associazione utente ruolo:" + e.getMessage();
			logger.error(message, e);
		}finally{	
			if (null != sw && sw.isRunning()){
				sw.stop();	
			}
		}
		return model;
	}
	
	public RuoloUtente selectByCodice(String codRuolo) throws Exception {
		StopWatch sw = null;
		RuoloUtente model = null;
		try{
			sw = new StopWatch("Recupera associazione utente ruolo dato il codice del ruolo");
			sw.start();
			model = getJdbcTemplate().queryForObject(SQL_SELECT_UTENTE_BY_CODRUOLO, new Object[]{codRuolo}, new UtenteRuoloRowMapper());
			if (logger.isDebugEnabled()) {
				logger.debug("esecuzione query :" + SQL_SELECT_UTENTE_BY_CODRUOLO);
			}
		}catch (Exception e){
			String message = "Errore nel recuper dell'associazione utente ruolo dato il codice:" + e.getMessage();
			logger.error(message, e);
		}finally{	
			if (null != sw && sw.isRunning()){
				sw.stop();	
			}
		}
		return model;
	}
	
	public void insertCittadino(Long idUtente) throws Exception {
		StopWatch sw = null;
		try{
			
			sw = new StopWatch("Salva associazione utente ruolo");
			sw.start();
			getJdbcTemplate().update(SQL_INSERT_UTENTE_RUOLO_CITTADINO, new Object[] {idUtente});
			if (logger.isDebugEnabled()) {
				logger.debug("esecuzione query :" + SQL_INSERT_UTENTE_RUOLO);
			}
		}catch (Exception e){
			String message = "Errore nel salvataggio associazione utente ruolo cittadino:" + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
		}finally{	
			if (null != sw && sw.isRunning()){
				sw.stop();	
			}
		}
	}
	
	public void insert(Long idUtente, Long idRuolo) throws Exception {
		StopWatch sw = null;
		try{
			
			sw = new StopWatch("Salva associazione utente ruolo");
			sw.start();
			getJdbcTemplate().update(SQL_INSERT_UTENTE_RUOLO, new Object[] {idRuolo, idUtente});
			if (logger.isDebugEnabled()) {
				logger.debug("esecuzione query :" + SQL_INSERT_UTENTE_RUOLO);
			}
		}catch (Exception e){
			String message = "Errore nel salvataggio associazione utente ruolo cittadino:" + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
		}finally{	
			if (null != sw && sw.isRunning()){
				sw.stop();	
			}
		}
	}
	
	public void update(Long idRuolo, Long idUtente) throws Exception {
		StopWatch sw = null;
		try{
			sw = new StopWatch("Salva associazione utente ruolo");
			sw.start();	
				getJdbcTemplate().update(SQL_UPDATE_UTENTE_RUOLO,new Object[] { 
						idRuolo,
						idUtente
				});
				if (logger.isDebugEnabled()) {
					logger.debug("esecuzione query :" + SQL_UPDATE_UTENTE_RUOLO);
				}
		}catch (Exception e){
			String message = "Errore nel salvataggio associazione utente ruolo:" + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
		}finally{	
			if (null != sw && sw.isRunning()){
				sw.stop();	
			}
		}
	}
	
	public void delete(long idUtente) throws Exception {
		StopWatch sw = null;
		try{
			sw = new StopWatch("Elimina associazione utente ruolo");
			sw.start();	
				getJdbcTemplate().update(SQL_DELETE_UTENTE_RUOLO,new Object[] { 
						idUtente
				});
				if (logger.isDebugEnabled()) {
					logger.debug("esecuzione query :" + SQL_DELETE_UTENTE_RUOLO);
				}
		}catch (Exception e){
			String message = "Errore nel eliminare associazione utente ruolo:" + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
		}finally{	
			if (null != sw && sw.isRunning()){
				sw.stop();	
			}
		}
	}
}
