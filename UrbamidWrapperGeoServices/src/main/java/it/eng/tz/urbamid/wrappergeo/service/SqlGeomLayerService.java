package it.eng.tz.urbamid.wrappergeo.service;

import org.springframework.stereotype.Service;

import it.eng.tz.urbamid.wrappergeo.exception.WrapperGeoServiceException;
import it.eng.tz.urbamid.wrappergeo.web.entities.FeatureGeomDTO;


@Service
public interface SqlGeomLayerService {
	
	public FeatureGeomDTO findFeatureLayerWithGeom(String tableName, String wktGeom) throws WrapperGeoServiceException;
	
//	public RepeatStatus executeShapeToDb(String nameShapeFile) throws WrapperGeoServiceException;
}