package it.eng.tz.urbamid.administration.mappa.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import it.eng.tz.urbamid.administration.dao.GenericDao;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.MappaPermessoBean;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.MappaPermessoRowMapper;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.RuoloRowMapper;
import it.eng.tz.urbamid.administration.mappa.dao.model.MappaPermesso;
import it.eng.tz.urbamid.administration.mappa.dao.model.Ruolo;
import it.eng.tz.urbamid.administration.persistence.model.Permesso;

/**
 * @author Luca Tricarico
 */
@Repository
public class MappaPermessiDao extends GenericDao<MappaPermesso> {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final String SQL_INSERT_MAP_PERMESSO = "INSERT INTO public.u_admin_risorsa_permesso(id_rsrs_prms, id_risorsa, id_tipo_risorsa, id_ruolo, abilita_visualizzazione, abilita_modifica) VALUES (nextval('u_admin_mappa_mappermesso_id_seq'), ?, ?, ?, ?, false)";
	private final String SQL_UPDATE_MAP_PERMESSO = "UPDATE public.u_admin_risorsa_permesso SET abilita_visualizzazione=? WHERE id_risorsa = ? AND id_tipo_risorsa = ? AND id_ruolo = ?";
	private final String SQL_DELETE_MAP_PERMESSO = "DELETE FROM public.u_admin_risorsa_permesso WHERE id_risorsa = ? AND id_tipo_risorsa = ? AND id_ruolo = ?";
	private final String SQL_FIND_MAP_PERMESSO = "SELECT * FROM public.u_admin_risorsa_permesso WHERE id_tipo_risorsa = 1 AND id_risorsa = ?";
	private final String SQL_FIND_RUOLO_MAPPA = "SELECT * FROM public.u_admin_risorsa_permesso WHERE id_ruolo = ?";
	private final String SQL_FIND_ALL_RUOLO = "SELECT * FROM u_pm_ruolo";
	
	public void insertPermesso(List<MappaPermessoBean> permessi) throws Exception {
			
		logger.debug("insertPermesso");

		List<Object[]> batch = new ArrayList<>();
		List<Object[]> batchUpdate = new ArrayList<>();
		
		for(MappaPermessoBean i : permessi) {
			
			if(i.getIsNew()) {
				
				batch.add(new Object[] { i.getId_risorsa(),
						 			     i.getId_tipo_risorsa(),
						 			     i.getId_ruolo(),
						 			     i.getAbilita_visualizzazione() });	
			} else {
				
				batchUpdate.add(new Object[] { i.getId_risorsa(),
										 	   i.getId_tipo_risorsa(),
										 	   i.getId_ruolo() });
			}
			
		}
		
		if(batchUpdate.size() > 0) {
			getJdbcTemplate().batchUpdate(SQL_DELETE_MAP_PERMESSO, batchUpdate);
		}
		
		if(batch.size() > 0) {
			getJdbcTemplate().batchUpdate(SQL_INSERT_MAP_PERMESSO, batch);
		}		
	}
	
	public List<MappaPermesso> getMapPermessi(Integer idMappa) {
		
		logger.debug("getAllPermessi");
		
		return getJdbcTemplate().query(SQL_FIND_MAP_PERMESSO, new MappaPermessoRowMapper(), idMappa);
		
	}
	
	public List<MappaPermesso> getRuoloMappa(Integer idRuolo) {
		
		logger.debug("getRuoloMappa");

		return getJdbcTemplate().query(SQL_FIND_RUOLO_MAPPA, new MappaPermessoRowMapper(), idRuolo);
	
	}
	
	public List<Ruolo> getAllRuolo() {
		
		logger.debug("getAllRuolo");
		
		return getJdbcTemplate().query(SQL_FIND_ALL_RUOLO, new RuoloRowMapper());
		
	}
	
}
