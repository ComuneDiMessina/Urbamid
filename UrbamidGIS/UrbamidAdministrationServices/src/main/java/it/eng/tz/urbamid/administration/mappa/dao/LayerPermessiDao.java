package it.eng.tz.urbamid.administration.mappa.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.administration.dao.GenericDao;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.MappaPermessoBean;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.MappaPermessoRowMapper;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.UMappaLayerRowMapper;
import it.eng.tz.urbamid.administration.mappa.dao.model.MappaPermesso;
import it.eng.tz.urbamid.administration.mappa.dao.model.UMappaLayer;

/**
 * @author Luca Tricarico
 */
@Component
public class LayerPermessiDao extends GenericDao<MappaPermesso> {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final String SQL_INSERT_LAYER_PERMESSO = "INSERT INTO public.u_admin_risorsa_permesso(id_rsrs_prms, id_risorsa, id_tipo_risorsa, id_ruolo, abilita_visualizzazione, abilita_modifica) VALUES (nextval('u_admin_mappa_mappermesso_id_seq'), ?, 2, ?, ?, ?)";
	private final String SQL_INSERT_LAYER = "INSERT INTO public.u_admin_risorsa_permesso(id_rsrs_prms, id_risorsa, id_tipo_risorsa, id_ruolo, abilita_visualizzazione, abilita_modifica)\r\n" + 
			"SELECT nextval('u_admin_mappa_mappermesso_id_seq'), layer.id, 2, ?, ?, ?\r\n" + 
			"FROM public.u_admin_mappa_layer layer\r\n" + 
			"WHERE layer.nome_layer = ?";
//	private final String SQL_INSERT_LAYER_PERMESSO_MAP = "INSERT INTO public.u_admin_risorsa_permesso(id_rsrs_prms, id_risorsa, id_tipo_risorsa, id_ruolo, abilita_visualizzazione, abilita_modifica)\r\n" + 
//			"SELECT nextval('u_admin_mappa_mappermesso_id_seq'), layer.id, 2, ?, ?, ?\r\n" + 
//			"FROM public.u_admin_mappa_layer layer\r\n" + 
//			"WHERE layer.nome_layer = ? AND layer.id_mappa = ? AND tipo = 'L'";
	private final String SQL_DELETE_LAYER = "DELETE FROM public.u_admin_risorsa_permesso\r\n" + 
											"WHERE id_risorsa IN (SELECT id FROM public.u_admin_mappa_layer WHERE nome_layer = ? AND tipo = 'L') AND id_tipo_risorsa = 2 AND id_ruolo = ?;";
//	private final String SQL_DELETE_LAYER_PERMESSO = "DELETE FROM public.u_admin_risorsa_permesso WHERE id_tipo_risorsa = 2 AND id_risorsa = ? AND id_ruolo = ?";
//	private final String SQL_DELETE_LAYER_PERMESSO_MAP = "DELETE FROM public.u_admin_risorsa_permesso\r\n" + 
//			"WHERE id_risorsa IN (SELECT id FROM public.u_admin_mappa_layer WHERE nome_layer = ? AND id_mappa = ? AND tipo = 'L') AND id_tipo_risorsa = 2 AND id_ruolo = ?;";
	private final String SQL_FIND_LAYER_PERMESSO = "SELECT * FROM public.u_admin_risorsa_permesso\r\n" + 
												   "WHERE id_risorsa IN (SELECT id FROM public.u_admin_mappa_layer WHERE id_mappa = ? AND nome_layer = ? AND tipo = 'L')";
	private final String SQL_COUNT_LAYER_PERMESSO = "SELECT * FROM public.u_admin_mappa_layer WHERE nome_layer = ? AND tipo = 'L'";
	
	private final String SQL_FIND_LAYER = "SELECT DISTINCT id_ruolo, abilita_visualizzazione, abilita_modifica FROM public.u_admin_risorsa_permesso\r\n" + 
			   							  "WHERE id_risorsa IN (SELECT id FROM public.u_admin_mappa_layer WHERE nome_layer = ?)";
	
	public void insertPermessiLayer(List<MappaPermessoBean> permessi) {

		logger.debug("insertPermessiLayer");

		List<Object[]> batchInsert = new ArrayList<>();
		List<Object[]> batchDelete = new ArrayList<>();

		for(MappaPermessoBean i : permessi) {

			batchInsert.add(new Object[] { i.getId_ruolo(),
										   i.getAbilita_visualizzazione(),
										   i.getAbilita_modifica(),
										   i.getNome_layer()});

			batchDelete.add(new Object[] { i.getNome_layer(),
										   i.getId_ruolo() });

		}

		if(batchDelete.size() > 0) {

			getJdbcTemplate().batchUpdate(SQL_DELETE_LAYER, batchDelete);

		}

		if(batchInsert.size() > 0) {

			getJdbcTemplate().batchUpdate(SQL_INSERT_LAYER, batchInsert);

		}

	}
	
	public void insert(MappaPermessoBean permessi) {
		
		logger.debug("insertLayer");
		
		getJdbcTemplate().update(SQL_INSERT_LAYER, permessi);
		
	}

	public List<MappaPermesso> getLayerPermessi(Integer idMappa, String nomeLayer) {

		logger.debug("getLayerPermessi");

		return getJdbcTemplate().query(SQL_FIND_LAYER_PERMESSO, new Object[] { idMappa,
																			   nomeLayer }, new MappaPermessoRowMapper());

	}
	
	public List<MappaPermesso> getPermessi(String nomeLayer) {
		
		return getJdbcTemplate().query(SQL_FIND_LAYER, new MappaPermessoRowMapper(), nomeLayer);
		
	}

	public List<UMappaLayer> countPermessiLayer(String nomeLayer) {

		logger.debug("getLayerPermessi");

		return getJdbcTemplate().query(SQL_COUNT_LAYER_PERMESSO, new UMappaLayerRowMapper(), nomeLayer);

	}

}
