package it.eng.tz.urbamid.prg.persistence.dao;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import it.eng.tz.urbamid.prg.dao.GenericDao;
import it.eng.tz.urbamid.prg.persistence.model.Cdu;

@Repository
public class CduDao extends GenericDao<Cdu> {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String SQL_INSERT_CDU_ANAGRAFICA = "INSERT INTO public.u_prg_cdu( " +
            " protocollo, data_creazione, path_documento)" + 
			" VALUES (?, ?, ?);";

	private static final String SQL_UPDATE_CDU_ANAGRAFICA = "UPDATE public.u_prg_cdu SET data_creazione=?, path_documento=? " +
			"			WHERE protocollo = ?";

	public void insertCduAnagrafica(String protocollo, Date dataCreazione, String path) throws Exception {
		StopWatch sw = null;
		try{
			sw = new StopWatch("Salva l'anagrafica del cdu");
			sw.start();	
				getJdbcTemplate().update(SQL_INSERT_CDU_ANAGRAFICA,new Object[] { 
						protocollo,
						dataCreazione,
						path
				});
				if (logger.isDebugEnabled()) {
					logger.debug("esecuzione query :" + SQL_INSERT_CDU_ANAGRAFICA);
				}
		}catch (Exception e){
			String message = "Errore nel salvataggio dell anagrafica del cdu :" + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
		}finally{	
			if (null != sw && sw.isRunning()){
				sw.stop();	
			}
		}
	}

	public void updateCduAnagrafica(String protocollo, Date dataCreazione, String path) throws Exception {
		StopWatch sw = null;
		try{
			sw = new StopWatch("Modifica l'anagrafica del cdu");
			sw.start();	
				getJdbcTemplate().update(SQL_UPDATE_CDU_ANAGRAFICA,new Object[] { 
						dataCreazione,
						path,
						protocollo
				});
				if (logger.isDebugEnabled()) {
					logger.debug("esecuzione query :" + SQL_UPDATE_CDU_ANAGRAFICA);
				}
		}catch (Exception e){
			String message = "Errore nella modifica dell anagrafica del cdu :" + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
		}finally{	
			if (null != sw && sw.isRunning()){
				sw.stop();	
			}
		}
	}

}
