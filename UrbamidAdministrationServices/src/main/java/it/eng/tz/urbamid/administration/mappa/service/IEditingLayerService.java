package it.eng.tz.urbamid.administration.mappa.service;

import java.util.List;

import org.springframework.stereotype.Service;

import it.eng.tz.urbamid.administration.filter.LayersFilter;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.LayerAttributiBean;
import it.eng.tz.urbamid.administration.mappa.dao.model.LayerAttributi;
import it.eng.tz.urbamid.administration.mappa.dao.model.LayerGeometrie;
import it.eng.tz.urbamid.administration.web.dto.PagedResult;

@Service
public interface IEditingLayerService {

	public PagedResult<LayerAttributi> findAllLayers(LayersFilter filter) throws Exception;
	
	public List<LayerGeometrie> findAllGeometry(Long idLayer) throws Exception;
	
	public LayerAttributiBean insertEditingLayer(LayerAttributiBean layers) throws Exception;
	
	public void deleteEditingLayer(Long idLayer) throws Exception;

	public void deleteEditingGeometria(Long idGeometria) throws Exception;
	
	public int countLayerByIdentificativo(String identificativo) throws Exception;
}
