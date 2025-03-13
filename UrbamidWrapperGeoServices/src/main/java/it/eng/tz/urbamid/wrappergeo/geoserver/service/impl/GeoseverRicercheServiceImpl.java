package it.eng.tz.urbamid.wrappergeo.geoserver.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.urbamid.wrappergeo.geoserver.dao.RicercheGeometriaDao;
import it.eng.tz.urbamid.wrappergeo.geoserver.service.AbstractGeoserverRicercheService;
import it.eng.tz.urbamid.wrappergeo.geoserver.service.GeoserverRicercheService;
import it.eng.tz.urbamid.wrappergeo.persistence.model.GeometriaCompleteLayer;
import it.eng.tz.urbamid.wrappergeo.persistence.model.GeometriaLayer;
import it.eng.tz.urbamid.wrappergeo.web.dto.GeometriaLayerCompleteDTO;
import it.eng.tz.urbamid.wrappergeo.web.dto.GeometriaLayerDTO;
import it.eng.tz.urbamid.wrappergeo.web.dto.converter.GeometriaLayerCompletoConverter;
import it.eng.tz.urbamid.wrappergeo.web.dto.converter.GeometriaLayerConverter;

@Service
public class GeoseverRicercheServiceImpl extends AbstractGeoserverRicercheService implements GeoserverRicercheService {
	
	/**
	 * Logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(GeoserverRicercheService.class);

	@Autowired
	private RicercheGeometriaDao ricercheGeometriaDao;
	
	@Autowired
	private GeometriaLayerConverter geometriaLayerConverter;
	
	@Autowired
	private GeometriaLayerCompletoConverter geometriaLayerCompletoConverter;
	
	@Override
	@Transactional(rollbackFor = { Exception.class}, readOnly = true)
	public List<GeometriaLayerDTO> findGeometriaLayer(String layerName) throws Exception {
		String idLog = "findGeometriaLayer";		
		try{
			logger.info("START >>> " + idLog);
			logger.debug("Parametri passati in input sono: layerName: "+layerName);
			
			// costruisco la richiesta 
			List<GeometriaLayer> resultModel = ricercheGeometriaDao.findGeometria(layerName);		
			List<GeometriaLayerDTO> result =  geometriaLayerConverter.toDto(resultModel) ;
			return result;
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			
			logger.info("END <<< " + idLog);
		}
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class}, readOnly = true)
	public GeometriaLayerCompleteDTO findGeometriaLayerByWkt(String layerName, String wktGeom) throws Exception {
		String idLog = "findGeometriaLayer";	
		GeometriaLayerCompleteDTO result = null;
		try{
			logger.info("START >>> " + idLog);
			logger.debug("Parametri passati in input sono: layerName: "+layerName+" wktGeom " +  wktGeom);
			
			String colId = ricercheGeometriaDao.getColumnName(layerName);
			if (colId!=null) {
				// costruisco la richiesta 
				GeometriaCompleteLayer resultModel = ricercheGeometriaDao.findGeometriaByWkt(layerName, wktGeom, colId);
				resultModel.setNameColId(colId);
				result =  geometriaLayerCompletoConverter.toDto(resultModel) ;
			}
			
			return result;
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			
			logger.info("END <<< " + idLog);
		}
	}
}
