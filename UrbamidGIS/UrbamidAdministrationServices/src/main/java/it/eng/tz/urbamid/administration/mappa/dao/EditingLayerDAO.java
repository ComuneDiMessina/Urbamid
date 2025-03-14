package it.eng.tz.urbamid.administration.mappa.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import it.eng.tz.urbamid.administration.dao.GenericDao;
import it.eng.tz.urbamid.administration.filter.LayersFilter;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.LayerAttributiBean;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.LayerAttributiRowMapper;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.LayerGeometrieBean;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.LayerGeometrieRowMapper;
import it.eng.tz.urbamid.administration.mappa.dao.model.LayerAttributi;
import it.eng.tz.urbamid.administration.mappa.dao.model.LayerGeometrie;

@Component
public class EditingLayerDAO extends GenericDao<LayerAttributi>{

	/** LOG **/
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** SQL **/
	private final String SQL_INSERT_LAYERS = "INSERT INTO public.u_admin_edtl_layers(id, identificativo, nome, descrizione, note, stato) VALUES (nextval('u_admin_edtl_layers_id_seq'), ?, ?, ?, ?, 'BOZZA')";
	private final String SQL_UPDATE_LAYERS = "UPDATE public.u_admin_edtl_layers SET identificativo=?, nome=?, descrizione=?, note=?, stato=? WHERE id = ?";
	private final String SQL_DELETE_LAYERS = "DELETE FROM public.u_admin_edtl_layers WHERE id = ?";
	private final String SQL_COUNT_LAYERS = "SELECT COUNT(*) FROM public.u_admin_edtl_layers WHERE identificativo = ?";
	
	private final String SQL_SELECT_GEOMETRY = "SELECT id, id_layer, ST_AsText(geom) AS geom, nome, descrizione, tipo FROM public.u_admin_edtl_layers_attr WHERE id_layer = ?";
	private final String SQL_INSERT_GEOM_LAYERS = "INSERT INTO public.u_admin_edtl_layers_attr(id, id_layer, geom, nome, descrizione, tipo) VALUES (nextval('u_admin_edtl_layers_attr_id_seq'), ?, ST_AsText(?), ?, ?, ?)";
	private final String SQL_UPDATE_GEOM_LAYERS = "UPDATE public.u_admin_edtl_layers_attr SET geom=ST_AsText(?), nome=?, descrizione=?, tipo=? WHERE id = ? AND id_layer = ?";
	private final String SQL_DELETE_GEOM_LAYERS = "DELETE FROM public.u_admin_edtl_layers_attr WHERE id_layer = ?";
	private final String SQL_DELETE_GEOM = "DELETE FROM public.u_admin_edtl_layers_attr WHERE id = ?";
	
	public List<LayerAttributi> getLayers(LayersFilter filter) throws Exception {
		
		logger.debug("getLayers");
		
		List<LayerAttributi> result = null;
		try {
			
			String SQL_SELECT_LAYERS = "SELECT * FROM public.u_admin_edtl_layers WHERE lower(nome) LIKE '%NOME%' %DESCRIZIONE% %STATO%";
				
			if(StringUtils.hasText(filter.getNome()))
				SQL_SELECT_LAYERS = SQL_SELECT_LAYERS.replace("%NOME%", "%"+filter.getNome().toLowerCase()+"%");
			else
				SQL_SELECT_LAYERS = SQL_SELECT_LAYERS.replace("%NOME%", "%%");
			
			if(StringUtils.hasText(filter.getDescrizione()))
				SQL_SELECT_LAYERS = SQL_SELECT_LAYERS.replace("%DESCRIZIONE%", "AND (lower(descrizione) LIKE '%"+filter.getDescrizione().toLowerCase()+"%')");
			else
				SQL_SELECT_LAYERS = SQL_SELECT_LAYERS.replace("%DESCRIZIONE%", "");
			
			if(StringUtils.hasText(filter.getStato()))
				SQL_SELECT_LAYERS = SQL_SELECT_LAYERS.replace("%STATO%", "AND (lower(stato) LIKE '%"+filter.getStato().toLowerCase()+"%')");
			else
				SQL_SELECT_LAYERS = SQL_SELECT_LAYERS.replace("%STATO%", "");
			
			result = getJdbcTemplate().query(SQL_SELECT_LAYERS, new LayerAttributiRowMapper());
			
		} catch (Exception e) {
			String message = "Errore nel inserimento del layer :" + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
		}
		
		return result;
		
	}
	
	public List<LayerGeometrie> getGeometry(Long idLayer) throws Exception {
		
		logger.debug("getGeometry");
		
		List<LayerGeometrie> result = null;
		try {
			
			result = getJdbcTemplate().query(SQL_SELECT_GEOMETRY, new LayerGeometrieRowMapper(), new Object[] { idLayer });
			
		} catch (Exception e) {
			String message = "Errore nella visualizzazione delle geometrie del layer :" + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
		}
		
		return result;
		
	}
	
	public int countLayerByIdentificativo(String identificativo) throws Exception {
		
		logger.debug("countLayerByIdentificativo");
		
		int result = 0;
		try {
			
			result = getJdbcTemplate().queryForObject(SQL_COUNT_LAYERS, new Object[] { identificativo }, Integer.class);
			
		} catch (Exception e) {
			String message = "Errore nel contare i layer :" + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
			
		}
		
		return result;
	}
	
	public LayerAttributiBean insertLayers(LayerAttributiBean layers) throws Exception {
		
		logger.debug("insertLayers");
		
		KeyHolder holder = new GeneratedKeyHolder();
		
		try {
			
			getJdbcTemplate().update(new PreparedStatementCreator() {
				
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(SQL_INSERT_LAYERS, Statement.RETURN_GENERATED_KEYS);
					
					ps.setString(1, layers.getIdentificativo());
					ps.setString(2, layers.getNome());
					ps.setString(3, layers.getDescrizione() != null ? layers.getDescrizione() : null);
					ps.setString(4, layers.getNote() != null ? layers.getNote() : null);
					
					return ps;
				}
			}, holder);
			
			Long id = (Long) holder.getKeyList().get(0).get("id");
		
			if(layers.getGeometrie() != null && !layers.getGeometrie().isEmpty()) {
				
				List<Object[]> batch = new ArrayList<>();
				
				for (LayerGeometrieBean geometrie : layers.getGeometrie()) {
					
					batch.add(new Object[] { id,
											 geometrie.getGeom(),
											 geometrie.getNome(),
											 geometrie.getDescrizione() != null ? geometrie.getDescrizione() : null,
											 geometrie.getTipo() });
					
				}
				
				if(batch.size() > 0)
					getJdbcTemplate().batchUpdate(SQL_INSERT_GEOM_LAYERS, batch);
				
			}
			
			layers.setId(id);
			
			return layers;
			
		} catch (Exception e) {
			String message = "Errore nel inserimento del layer :" + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
			
		}
		
	}
	
	public LayerAttributiBean updateLayer(LayerAttributiBean layers) throws Exception {
		
		logger.debug("updateLayer");
		
		KeyHolder holder = new GeneratedKeyHolder();
		
		try {
			
			getJdbcTemplate().update(new PreparedStatementCreator() {
				
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_LAYERS, Statement.RETURN_GENERATED_KEYS);
					
					ps.setString(1, layers.getIdentificativo());
					ps.setString(2, layers.getNome());
					ps.setString(3, layers.getDescrizione() != null ? layers.getDescrizione() : null);
					ps.setString(4, layers.getNote() != null ? layers.getNote() : null);
					ps.setString(5, layers.getStato());
					ps.setLong(6, layers.getId());
					
					return ps;
				}
			}, holder);
			
			Long id = (Long) holder.getKeyList().get(0).get("id");
		
			if(layers.getGeometrie() != null && !layers.getGeometrie().isEmpty()) {
				
				List<Object[]> batchInsert = new ArrayList<>();
				List<Object[]> batchUpdate = new ArrayList<>();
				
				for (LayerGeometrieBean geometrie : layers.getGeometrie()) {
					
					if(geometrie.getId() != null) {
						
						batchUpdate.add(new Object[] { geometrie.getGeom(),
													   geometrie.getNome(),
													   geometrie.getDescrizione() != null ? geometrie.getDescrizione() : null,
													   geometrie.getTipo(),
													   geometrie.getId(),
													   id });
						
					} else {
						
						batchInsert.add(new Object[] { id,
													   geometrie.getGeom(),
													   geometrie.getNome(),
													   geometrie.getDescrizione() != null ? geometrie.getDescrizione() : null,
													   geometrie.getTipo()});
					}
					
					
				}
				
				if(batchUpdate.size() > 0)
					getJdbcTemplate().batchUpdate(SQL_UPDATE_GEOM_LAYERS, batchUpdate);
				
				if(batchInsert.size() > 0)
					getJdbcTemplate().batchUpdate(SQL_INSERT_GEOM_LAYERS, batchInsert);
				
			}
			
			layers.setId(id);
			
			return layers;
			
		} catch (Exception e) {
			String message = "Errore nell'update del layer :" + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
			
		}
		
	}
	
	public void deleteLayer(Long idLayer) throws Exception {
		
		logger.debug("deleteLayer");
		
		try {
			
			getJdbcTemplate().update(SQL_DELETE_GEOM_LAYERS, new Object[] { idLayer });
			getJdbcTemplate().update(SQL_DELETE_LAYERS, new Object[] { idLayer });
			
		} catch (Exception e) {
			String message = "Errore nella cancellazione del layer :" + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
			
		}
		
	}
	
	public void deleteGeometria(Long idGeometria) throws Exception {
		
		logger.debug("deleteGeometria");
		
		try {
			
			getJdbcTemplate().update(SQL_DELETE_GEOM, new Object[] { idGeometria });
			
		} catch (Exception e) {
			String message = "Errore nella cancellazione della geometria :" + e.getMessage();
			logger.error(message, e);
			throw new Exception(message);
			
		}
		
	}
	
}
