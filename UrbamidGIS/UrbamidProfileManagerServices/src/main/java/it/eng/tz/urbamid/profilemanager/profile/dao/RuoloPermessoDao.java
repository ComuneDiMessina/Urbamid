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
import it.eng.tz.urbamid.profilemanager.persistence.model.RuoloPermesso;
import it.eng.tz.urbamid.profilemanager.web.dto.RuoloPermessoDto;

/**
 * @author Alessandro Paolillo
 */
@Repository
public class RuoloPermessoDao extends GenericDao<RuoloPermesso> {

	/** LOGGER **/
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String SQL_SELECT_RUOLO_PERMESSO = "SELECT * FROM public.u_pm_ruolo_permesso WHERE id_ruolo=? AND id_permesso=?;";
	private static final String SQL_SELECT_PERMESSO_BY_RUOLO = "SELECT * FROM public.u_pm_ruolo_permesso WHERE id_ruolo=?;";
	private static final String SQL_DELETE_RUOLO_PERMESSO = "DELETE FROM public.u_pm_ruolo_permesso WHERE id_ruolo=? AND id_permesso=?;";
	private static final String SQL_INSERT_RUOLO_PERMESSO = "INSERT INTO public.u_pm_ruolo_permesso ( id, id_ruolo, id_permesso) VALUES ( nextval('u_pm_ruolo_permesso_id_seq'), ?, ?);";
	private static final String SQL_DELETE_RUOLO_PERMESSO_BYRUOLO = "DELETE FROM public.u_pm_ruolo_permesso WHERE id_ruolo=?";
	
	public RuoloPermesso select(RuoloPermessoDto ruoloPermessoDto) throws Exception {
		StopWatch sw = null;
		RuoloPermesso model = null;
		try{
			sw = new StopWatch("Recupera associazione ruolo permesso");
			sw.start();
			List<RuoloPermesso> listaRuoloPermesso = getJdbcTemplate().query(
															SQL_SELECT_RUOLO_PERMESSO, 
															new Object[]{ruoloPermessoDto.getIdRuolo() ,ruoloPermessoDto.getIdPermesso() }, 
															new RowMapper<RuoloPermesso>() {
																@Override
																public RuoloPermesso mapRow(ResultSet rs, int rowNum) throws SQLException {
																	RuoloPermesso ruoloPermesso = new RuoloPermesso();
																	ruoloPermesso.setId(rs.getLong("id"));
																	ruoloPermesso.setIdRuolo(rs.getLong("id_ruolo"));
																	ruoloPermesso.setIdPermesso(rs.getLong("id_permesso"));
																	return ruoloPermesso;
																}
															}
			);
			model = (listaRuoloPermesso!=null && listaRuoloPermesso.size()>0) ? listaRuoloPermesso.get(0):null;
			if (logger.isDebugEnabled()) {
				logger.debug("esecuzione query :" + SQL_SELECT_RUOLO_PERMESSO);
			}
		}catch (Exception e){
			String message = "Errore nel salvataggio associazione ruolo permesso:" + e.getMessage();
			logger.error(message, e);
		}finally{	
			if (null != sw && sw.isRunning()){
				sw.stop();	
			}
		}
		return model;
	}
	
	public List<RuoloPermesso> selectByIdRuolo(Long idRuolo) throws Exception {
		StopWatch sw = null;
		List<RuoloPermesso> listaRuoloPermesso = null;
		try{
			sw = new StopWatch("Recupera associazione ruolo permesso");
			sw.start();
			listaRuoloPermesso = getJdbcTemplate().query(
															SQL_SELECT_PERMESSO_BY_RUOLO, 
															new Object[]{idRuolo }, 
															new RowMapper<RuoloPermesso>() {
																@Override
																public RuoloPermesso mapRow(ResultSet rs, int rowNum) throws SQLException {
																	RuoloPermesso ruoloPermesso = new RuoloPermesso();
																	ruoloPermesso.setId(rs.getLong("id"));
																	ruoloPermesso.setIdRuolo(rs.getLong("id_ruolo"));
																	ruoloPermesso.setIdPermesso(rs.getLong("id_permesso"));
																	return ruoloPermesso;
																}
															}
			);
			if (logger.isDebugEnabled()) {
				logger.debug("esecuzione query :" + SQL_SELECT_PERMESSO_BY_RUOLO);
			}
		}catch (Exception e){
			String message = "Errore nel recupero associazione ruolo permesso:" + e.getMessage();
			logger.error(message, e);
		}finally{	
			if (null != sw && sw.isRunning()){
				sw.stop();	
			}
		}
		return listaRuoloPermesso;
	}
	
	public void insert(RuoloPermessoDto ruoloPermessoDto) throws Exception {
		StopWatch sw = null;
		try{
			sw = new StopWatch("Salva associazione ruolo permesso");
			sw.start();
			//INSERT INTO public.u_pm_ruolo_permesso ( id_ruolo, id_permesso) VALUES ( ?, ?)
			getJdbcTemplate().update(SQL_INSERT_RUOLO_PERMESSO, new Object[] {
					ruoloPermessoDto.getIdRuolo() ,
					ruoloPermessoDto.getIdPermesso() 
			});
			if (logger.isDebugEnabled()) {
				logger.debug("esecuzione query :" + SQL_INSERT_RUOLO_PERMESSO);
			}
		}catch (Exception e){
			String message = "Errore nel salvataggio associazione ruolo permesso:" + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
		}finally{	
			if (null != sw && sw.isRunning()){
				sw.stop();	
			}
		}
	}
	
	public void delete(RuoloPermessoDto ruoloPermessoDto) throws Exception {
		StopWatch sw = null;
		try{
			sw = new StopWatch("Elimina associazione ruolo permesso");
			sw.start();
				//DELETE FROM public.u_pm_ruolo_permesso WHERE id_ruolo=? AND id_permesso=?
				getJdbcTemplate().update(SQL_DELETE_RUOLO_PERMESSO,new Object[] { 
						ruoloPermessoDto.getIdRuolo(),
						ruoloPermessoDto.getIdPermesso()
				});
				if (logger.isDebugEnabled()) {
					logger.debug("esecuzione query :" + SQL_DELETE_RUOLO_PERMESSO);
				}
		}catch (Exception e){
			String message = "Errore nell'eliminare associazione ruolo permesso:" + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
		}finally{	
			if (null != sw && sw.isRunning()){
				sw.stop();	
			}
		}
	}
	
	public void delete(long idRuolo) throws Exception {
		StopWatch sw = null;
		try{
			sw = new StopWatch("Elimina associazione utente ruolo");
			sw.start();	
				getJdbcTemplate().update(SQL_DELETE_RUOLO_PERMESSO_BYRUOLO,new Object[] { 
						idRuolo
				});
				if (logger.isDebugEnabled()) {
					logger.debug("esecuzione query :" + SQL_DELETE_RUOLO_PERMESSO_BYRUOLO);
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
