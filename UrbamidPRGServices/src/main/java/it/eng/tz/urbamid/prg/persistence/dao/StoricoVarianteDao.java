package it.eng.tz.urbamid.prg.persistence.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import it.eng.tz.urbamid.prg.dao.GenericDao;
import it.eng.tz.urbamid.prg.persistence.model.StoricoVariante;

@Repository
public class StoricoVarianteDao extends GenericDao<StoricoVariante> {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String SQL_INSERT_STORICO_VARIANTE = "INSERT INTO public.u_prg_storico_variante(" + 
			"            id_variante, azione, data_azione, descrizione_azione, note_azione)" + 
			"    VALUES (?, ?, NOW(), ?, ?);";

	public void insertStoricoVariante(Long idVariante, String azione, String descrizione, String note) throws Exception {
		StopWatch sw = null;
		try{
			sw = new StopWatch("Salva un azione ''storica'' per la variante");
			sw.start();	
				getJdbcTemplate().update(SQL_INSERT_STORICO_VARIANTE,new Object[] { 
						idVariante,
						azione,
						descrizione,
						note
				});
				if (logger.isDebugEnabled()) {
					logger.debug("esecuzione query :" + SQL_INSERT_STORICO_VARIANTE);
				}
		}catch (Exception e){
			String message = "Errore nel salvataggio dello storico della variante :" + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
		}finally{	
			if (null != sw && sw.isRunning()){
				sw.stop();	
			}
		}
	}

}
