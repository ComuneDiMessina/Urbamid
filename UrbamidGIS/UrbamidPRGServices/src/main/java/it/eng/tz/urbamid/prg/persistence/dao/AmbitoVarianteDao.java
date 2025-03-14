package it.eng.tz.urbamid.prg.persistence.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import it.eng.tz.urbamid.prg.dao.GenericDao;
import it.eng.tz.urbamid.prg.persistence.model.AmbitoVariante;
import it.eng.tz.urbamid.prg.web.dto.AmbitoVarianteDTO;

@Repository
public class AmbitoVarianteDao extends GenericDao<AmbitoVariante> {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String SQL_UPDATE_AMBITO = "UPDATE public.u_prg_ambito_variante" + 
			"   SET id_variante=?, geom=ST_GeomFromText(?)" + 
			"			WHERE id_ambito = ?";
	private static final String SQL_INSERT_AMBITO = "INSERT INTO public.u_prg_ambito_variante(" + 
			"            id_variante, geom)" + 
			"    VALUES (?, ST_GeomFromText(?))";

	public void updateAmbito(AmbitoVarianteDTO data, Long idAmbito) throws Exception {
		StopWatch sw = null;
		try{
			sw = new StopWatch("Salva l'utente");
			sw.start();	
				getJdbcTemplate().update(SQL_UPDATE_AMBITO,new Object[] { 
						data.getIdVariante(),
						data.getWktGeom(),
						idAmbito
						
				});
				if (logger.isDebugEnabled()) {
					logger.debug("esecuzione query :" + SQL_UPDATE_AMBITO);
				}
		}catch (Exception e){
			String message = "Errore nella modifica della variante :" + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
		}finally{	
			if (null != sw && sw.isRunning()){
				sw.stop();	
			}
		}
	}

	public void insertAmbito(Long idVariante, String wkt) throws Exception {
		StopWatch sw = null;
		try{
			sw = new StopWatch("Salva l'utente");
			sw.start();	
				getJdbcTemplate().update(SQL_INSERT_AMBITO,new Object[] { 
						idVariante,
						wkt
				});
				if (logger.isDebugEnabled()) {
					logger.debug("esecuzione query :" + SQL_INSERT_AMBITO);
				}
		}catch (Exception e){
			String message = "Errore nel salvataggio della variante :" + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
		}finally{	
			if (null != sw && sw.isRunning()){
				sw.stop();	
			}
		}
	}

}
