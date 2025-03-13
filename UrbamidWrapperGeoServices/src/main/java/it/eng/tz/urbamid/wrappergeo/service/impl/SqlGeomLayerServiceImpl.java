package it.eng.tz.urbamid.wrappergeo.service.impl;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.urbamid.wrappergeo.exception.WrapperGeoServiceException;
import it.eng.tz.urbamid.wrappergeo.persistence.model.LayerFeatureGeom;
import it.eng.tz.urbamid.wrappergeo.persistence.repositories.FeatureLayerRepository;
import it.eng.tz.urbamid.wrappergeo.persistence.repositories.JpaRepositoryFeatureLayerGeom;
import it.eng.tz.urbamid.wrappergeo.service.AbstractWrapperGeoService;
import it.eng.tz.urbamid.wrappergeo.service.SqlGeomLayerService;
import it.eng.tz.urbamid.wrappergeo.web.dto.converter.FeatureGeomConverter;
import it.eng.tz.urbamid.wrappergeo.web.entities.FeatureGeomDTO;


@Service
public class SqlGeomLayerServiceImpl  extends AbstractWrapperGeoService implements SqlGeomLayerService{

	private static final Logger logger = LoggerFactory.getLogger(SqlGeomLayerServiceImpl.class.getName());

	private static final String START = "START >>> {}";
	private static final String END = "END >>> {}";
	private static final String DEBUG_INFO = "{} >>> Parametri passati in input sono: numFoglio: {} mappale: {}";
	private static final String DEBUG_INFO_END = "{} >>> numero di risultati estratti: {}";

	@Autowired
	private FeatureGeomConverter featureGeomConverter;

	@Autowired
	private JpaRepositoryFeatureLayerGeom featureGeomDao;
	
	@Autowired
	private FeatureLayerRepository featureLayerDao;
	
	@PostConstruct
	public void init() {
		if( logger.isInfoEnabled() ) {
			logger.info("init for {}", featureGeomDao);
		}
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class}, readOnly = true)
	public FeatureGeomDTO findFeatureLayerWithGeom(String tableName, String wktGeom) throws WrapperGeoServiceException {
		String idLog = "findFeatureLayerWithGeom";		
		try{
			logger.info("START >>> " + idLog);
			logger.debug("Parametri passati in input sono: tableName: " +  tableName + "wktGeom: " +  wktGeom );
			
			LayerFeatureGeom resultModel = featureLayerDao.findLayerFeatureByGeom(tableName, wktGeom);		
			FeatureGeomDTO result =  featureGeomConverter.toDto(resultModel);	
			return result;
			
		} catch (WrapperGeoServiceException e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			
			logger.info("END <<< " + idLog);
		}
	}

	
//	@Override
//	@Transactional(rollbackFor = { Exception.class}, readOnly = true)
//	public RepeatStatus executeShapeToDb(String nameShapeFile) throws WrapperGeoServiceException {
//		try {
//			LOG.debug("Esecuzione STEP di import degli shapefile per batch {}.", this.batchType.name());
//			String stepExecutionLog = this.shapefileService.importaShapefile(this.batchType);
//			salvaInformazioniDiLog(chunkContext, stepExecutionLog);
//		} catch(CatastoServiceException cse ) {
//			LOG.error("Si è verificato un errore nell'import degli shapefile nel database.");
//			throw cse;
//		}
//		return RepeatStatus.FINISHED;
//	}
}
