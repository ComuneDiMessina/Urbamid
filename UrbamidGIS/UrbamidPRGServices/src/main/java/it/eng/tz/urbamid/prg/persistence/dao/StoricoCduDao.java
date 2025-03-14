package it.eng.tz.urbamid.prg.persistence.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import it.eng.tz.urbamid.prg.dao.GenericDao;
import it.eng.tz.urbamid.prg.persistence.model.StoricoCdu;

@Repository
public class StoricoCduDao extends GenericDao<StoricoCdu> {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String SQL_INSERT_INDICE = "INSERT INTO public.u_prg_storico_cdu(" + 
			"            foglio, numero, data_creazione, protocollo)" + 
			"    VALUES (?, ?, ?, ?);";

	public void insertStoricoCdu(StoricoCdu storico) throws Exception {
		StopWatch sw = null;
		try{
			sw = new StopWatch("Salva lo storico cdu");
			sw.start();	
				getJdbcTemplate().update(SQL_INSERT_INDICE,new Object[] { 
						storico.getFoglio(),
						storico.getNumero(),
						storico.getDataCreazione(),
						storico.getProtocollo()
				});
				if (logger.isDebugEnabled()) {
					logger.debug("esecuzione query :" + SQL_INSERT_INDICE);
				}
		}catch (Exception e){
			String message = "Errore nel salvataggio dello storico cdu :" + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
		}finally{	
			if (null != sw && sw.isRunning()){
				sw.stop();	
			}
		}
	}

}
