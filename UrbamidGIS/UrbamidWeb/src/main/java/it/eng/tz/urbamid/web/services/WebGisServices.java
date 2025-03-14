
package it.eng.tz.urbamid.web.services;

import java.util.List;

import it.eng.tz.urbamid.web.dto.GeometriaLayerCompletoDTO;
import it.eng.tz.urbamid.web.dto.GeometriaLayerDTO;

public interface WebGisServices {

	public List<GeometriaLayerDTO> findGeometryLayer(String layerName) throws Exception;
	
	public List<GeometriaLayerCompletoDTO> findGeometryLayerByWkt(String layerName, String wktGeom) throws Exception;
	
	public boolean addLayer(Long idLayer, String nomeLayer, String identificativo) throws Exception;

	public boolean deleteLayer(String identificativo) throws Exception;
}
