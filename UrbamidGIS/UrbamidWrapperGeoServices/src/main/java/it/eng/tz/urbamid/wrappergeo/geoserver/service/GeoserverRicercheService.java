package it.eng.tz.urbamid.wrappergeo.geoserver.service;

import java.util.List;

import it.eng.tz.urbamid.wrappergeo.web.dto.GeometriaLayerCompleteDTO;
import it.eng.tz.urbamid.wrappergeo.web.dto.GeometriaLayerDTO;

public interface GeoserverRicercheService {
	
	public List<GeometriaLayerDTO> findGeometriaLayer(String layerName) throws Exception;
	
	public GeometriaLayerCompleteDTO findGeometriaLayerByWkt(String layerName, String wktGeom) throws Exception;
}
