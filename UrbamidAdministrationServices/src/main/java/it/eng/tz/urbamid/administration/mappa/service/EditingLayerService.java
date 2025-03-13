package it.eng.tz.urbamid.administration.mappa.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.urbamid.administration.filter.LayersFilter;
import it.eng.tz.urbamid.administration.mappa.dao.EditingLayerDAO;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.LayerAttributiBean;
import it.eng.tz.urbamid.administration.mappa.dao.model.LayerAttributi;
import it.eng.tz.urbamid.administration.mappa.dao.model.LayerGeometrie;
import it.eng.tz.urbamid.administration.web.dto.PagedResult;

@Service
public class EditingLayerService implements IEditingLayerService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final String START = "START >>> {}";
	private static final String END = "END >>> {}";
	private static final String DEBUG_INFO_END = "{} >>> numero di risultati estratti: {}";
	private static final String ERROR = "ERROR {} >>> {}";
	
	@Autowired
	private EditingLayerDAO dao;
	
	@Override
	@Transactional(rollbackFor = { Exception.class}, readOnly = true)
	public PagedResult<LayerAttributi> findAllLayers(LayersFilter filter) throws Exception {
		String idLog = "findAllLayers";
		
		PagedResult<LayerAttributi> result = null;
		try {
			
			List<LayerAttributi> response = dao.getLayers(filter);
							
			/** CREO L'ORDINAMENTO (ASC / DESC) IN BASE AL NOME DELLE COLONNE **/
			PropertyComparator.sort(response, new MutableSortDefinition(filter.getPageOrder().getColumn(), true, filter.getPageOrder().getDir().equalsIgnoreCase("asc") ? true : false));
			PagedListHolder<LayerAttributi> paginated = new PagedListHolder<>();
			
			/** MI POPOLO L'OGGETTO PAGEDLISTHOLDER **/
			paginated.setPage(filter.getPageIndex());
			paginated.setPageSize(filter.getPageSize());
			paginated.setSource(response);
			
			logger.debug(DEBUG_INFO_END, idLog, response.size());
			
			result = new PagedResult<>(paginated);
			
		} catch (Exception e) {
			logger.error(ERROR, idLog, e.getMessage());
			throw (e);
			
		} finally {
			logger.info(END, idLog);
			
		}
		
		return result;
		
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class}, readOnly = true)
	public List<LayerGeometrie> findAllGeometry(Long idLayer) throws Exception {
		String idLog = "findAllGeometry";
		
		List<LayerGeometrie> result = null;
		try {
			
			result = dao.getGeometry(idLayer);
			
		} catch (Exception e) {
			logger.error(ERROR, idLog, e.getMessage());
			throw (e);
			
		} finally {
			logger.info(END, idLog);
			
		}
		
		return result;
		
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class}, readOnly = false)
	public LayerAttributiBean insertEditingLayer(LayerAttributiBean layers) throws Exception {
		String idLog = "insertEditingLayer";
		
		LayerAttributiBean result = null;
		try {
			
			/** UPDATE **/
			if(layers.getId() != null) {
				
				logger.info(START, " UPDATE " + idLog);
				result = dao.updateLayer(layers);
			} else { /** INSERT **/
				
				logger.info(START, " INSERT " + idLog);
				result = dao.insertLayers(layers);
			}
				
		return result;
			
		} catch (Exception e) {
			logger.error(ERROR, idLog, e.getMessage());
			throw (e);
			
		} finally {
			logger.info(END, idLog);
			
		}
		
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class}, readOnly = false)
	public void deleteEditingLayer(Long idLayer) throws Exception {
		String idLog = "deleteEditingLayer";
		logger.info(START, idLog);
		
		try {
			
			dao.deleteLayer(idLayer);	
			
		} catch (Exception e) {
			logger.error(ERROR, idLog, e.getMessage());
			throw (e);
			
		} finally {
			logger.info(END, idLog);
			
		}
		
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class}, readOnly = false)
	public void deleteEditingGeometria(Long idGeometria) throws Exception {
		String idLog = "deleteEditingGeometria";
		logger.info(START, idLog);
		
		try {
			
			dao.deleteGeometria(idGeometria);	
			
		} catch (Exception e) {
			logger.error(ERROR, idLog, e.getMessage());
			throw (e);
			
		} finally {
			logger.info(END, idLog);
			
		}
		
	}

	@Override
	public int countLayerByIdentificativo(String identificativo) throws Exception {
		String idLog = "countLayerByIdentificativo";
		logger.info(START, idLog);
		
		int result = 0;
		try {
			result = dao.countLayerByIdentificativo(identificativo);
			
		} catch (Exception e) {
			logger.error(ERROR, idLog, e.getMessage());
			throw (e);
			
		} finally {
			logger.info(END, idLog);
			
		}
		
		return result;
	}

}
