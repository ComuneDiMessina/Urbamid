package it.eng.tz.urbamid.administration.mappa.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.urbamid.administration.mappa.dao.MappaDao;
import it.eng.tz.urbamid.administration.mappa.dao.MappaPermessiDao;
import it.eng.tz.urbamid.administration.mappa.dao.MappaRicercheDao;
import it.eng.tz.urbamid.administration.mappa.dao.MappaToolsDao;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.MappaBean;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.MappaPermessoBean;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.MappaRicercaBean;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.MappaToolBean;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.RicercaBean;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.RuoloBean;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.ToolBean;
import it.eng.tz.urbamid.administration.mappa.dao.model.Mappa;
import it.eng.tz.urbamid.administration.mappa.dao.model.MappaPermesso;
import it.eng.tz.urbamid.administration.mappa.dao.model.MappaRicerca;
import it.eng.tz.urbamid.administration.mappa.dao.model.MappaTool;
import it.eng.tz.urbamid.administration.mappa.dao.model.Ruolo;
import it.eng.tz.urbamid.administration.mappa.service.converter.MappaConverter;
import it.eng.tz.urbamid.administration.mappa.service.converter.MappaPermessiConverter;
import it.eng.tz.urbamid.administration.mappa.service.converter.MappaRicercheConverter;
import it.eng.tz.urbamid.administration.mappa.service.converter.MappaRuoloConverter;
import it.eng.tz.urbamid.administration.mappa.service.converter.MappaToolConverter;
/**
 * Service per la gestione dei grafici
 * @author Alessandro Paolillo
 */
@Service
public class MappaService implements IMappaService {

	private static final Logger logger = LoggerFactory.getLogger(MappaService.class.getName());

	@Autowired
	private MappaDao mappaDao;

	@Autowired
	private MappaToolsDao mappaToolsDao;

	@Autowired
	private MappaRicercheDao mappaRicercheDao;
	
	@Autowired
	private MappaPermessiDao mappaPermessiDao;

	@Autowired
	private MappaConverter mappaConverter;

	@Autowired
	private MappaRicercheConverter mappaRicercheConverter;
	
	@Autowired
	private MappaToolConverter mappaToolConverter;
	
	@Autowired
	private MappaRuoloConverter mappaRuoloConverter;
	
	@Autowired
	private MappaPermessiConverter mappaPermessoConverter;

	@Override
	@Transactional(rollbackFor = { Exception.class}, readOnly = true)
	public MappaBean getMappa(String codeMappa) throws Exception {
		String idLog = "getMappa";		
		MappaBean result = new MappaBean();
		try{
			logger.info("START >>> " + idLog);
			Mappa resultModel =  mappaDao.getMap(codeMappa);
			result = mappaConverter.toDto(resultModel);
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
	public List<MappaBean> getMappe() throws Exception {
		String idLog = "getMappa";		
		List<MappaBean> result = new ArrayList<MappaBean>();
		try{
			logger.info("START >>> " + idLog);
			List<Mappa> resultModel =  mappaDao.getAllMaps();
			for (Mappa model : resultModel) {
				MappaBean mappaBean = new MappaBean();
				mappaBean = mappaConverter.toDto(model);
				result.add(mappaBean);
			}
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
	public List<MappaToolBean> getMapTools() throws Exception {
		String idLog = "getMapTools";		
		List<MappaToolBean> result = new ArrayList<MappaToolBean>();
		try{
			logger.info("START >>> " + idLog);
			List<MappaTool> resultList =  mappaToolsDao.getAllMapTool();
			for (MappaTool mappaTool : resultList) {
				result.add( mappaToolConverter.toDto(mappaTool) );
			}
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
	public List<MappaRicercaBean> getMapAllRicerche() throws Exception {
		String idLog = "getMapRicerche";		
		List<MappaRicercaBean> result = new ArrayList<MappaRicercaBean>();
		try{
			logger.info("START >>> " + idLog);
			List<MappaRicerca> resultList =  mappaRicercheDao.getAllMapRicerca();
			for (MappaRicerca mappaRicerca : resultList) {
				result.add( mappaRicercheConverter.toDto(mappaRicerca) );
			}
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
	public List<RuoloBean> getAllRuoli() throws Exception {
		String idLog = "getAllRuoli";		
		List<RuoloBean> result = new ArrayList<RuoloBean>();
		
		try {
			
			logger.info("START >>> " + idLog);
			
			List<Ruolo> resultList = mappaPermessiDao.getAllRuolo();
			
			for(Ruolo ruolo: resultList) {
				
				result.add(mappaRuoloConverter.toDto(ruolo));
				
			}
			
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
	public List<MappaRicercaBean> getMapRicerche(Integer idMappa) throws Exception {
		String idLog = "getMapRicerche";		
		List<MappaRicercaBean> result = new ArrayList<MappaRicercaBean>();
		try{
			logger.info("START >>> " + idLog);
			List<MappaRicerca> resultList =  mappaRicercheDao.getMappaID(idMappa);
			for (MappaRicerca mappaRicerca : resultList) {
				result.add( mappaRicercheConverter.toDto(mappaRicerca) );
			}
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
	public List<MappaToolBean> getMapTool(Integer idMappa) throws Exception {
		String idLog = "getMapTool";		
		List<MappaToolBean> result = new ArrayList<MappaToolBean>();
		try{
			logger.info("START >>> " + idLog);
			List<MappaTool> resultList =  mappaToolsDao.getMappaID(idMappa);
			for (MappaTool mappaTool : resultList) {
				result.add( mappaToolConverter.toDto(mappaTool) );
			}
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
	public List<MappaPermessoBean> getMapPermessi(Integer idMappa) throws Exception {
		String idLog = "getMapPermessi";		
		List<MappaPermessoBean> result = new ArrayList<MappaPermessoBean>();
		try{
			logger.info("START >>> " + idLog);
			List<MappaPermesso> resultList =  mappaPermessiDao.getMapPermessi(idMappa);
			for (MappaPermesso mappaPermesso : resultList) {
				result.add(mappaPermessoConverter.toDto(mappaPermesso));
			}
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
	public List<MappaPermessoBean> getRuoloMappa(Integer idRuolo) throws Exception {
		String idLog = "getRuoloMappa";		
		List<MappaPermessoBean> result = new ArrayList<MappaPermessoBean>();
		try{
			logger.info("START >>> " + idLog);
			List<MappaPermesso> resultList =  mappaPermessiDao.getRuoloMappa(idRuolo);
			for (MappaPermesso mappaPermesso : resultList) {
				result.add(mappaPermessoConverter.toDto(mappaPermesso));
			}
			return result;
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info("END <<< " + idLog);
		}
	}
	
	@Transactional(rollbackFor = { Exception.class}, readOnly = false)
	@Override
	public void insertJoinRicerca(List<RicercaBean> ricerca) throws Exception {
		String idLog = "saveRicerca";

		try {

			logger.info("START >>> " + idLog);
			
			mappaRicercheDao.insertRicercaJoin(ricerca);

			if (logger.isDebugEnabled()) {
				logger.debug(idLog+" >>> Salvo la ricerca");
			}

		} catch (Exception e) {

			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info("END <<< " + idLog);
		}

	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class}, readOnly = false)
	public void insertOrUpdatePermesso(List<MappaPermessoBean> permesso) throws Exception {
		
		String idLog = "inserteOrUpdatePermesso";
		
		try {
			
			logger.info("START >>> " + idLog);
			
			mappaPermessiDao.insertPermesso(permesso);
			if (logger.isDebugEnabled()) {
				logger.debug(idLog+" >>> Salvo i permessi");
			}
			
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info("END <<< " + idLog);
		}
		
	}

	@Transactional(rollbackFor = { Exception.class}, readOnly = false)
	@Override
	public void insertJoinTool(List<ToolBean> tools) throws Exception {
		String idLog = "saveJoinTool";

		try {

			logger.info("START >>> " + idLog);

			mappaToolsDao.insertToolJoin(tools);

			if (logger.isDebugEnabled()) {
				logger.debug(idLog+" >>> Salvo i tool passati in input");
			}

		} catch (Exception e) {

			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info("END <<< " + idLog);
		}

	}
	
	@Override
	@Transactional(value = "transactionManager", rollbackFor = { Exception.class}, readOnly = false)
	public void duplicaMappa( MappaBean mappaBean) throws Exception {
		String idLog = "duplicaMappa";		
		try{
			logger.info("START >>> " + idLog);
			Mappa mappa = mappaConverter.toModel( mappaBean );
			mappaDao.insert(mappa);
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info("END <<< " + idLog);
		}
	}

	@Override
	@Transactional(value = "transactionManagerAdmin", rollbackFor = { Exception.class}, readOnly = false)
	public String saveOrUpdateMappa (MappaBean mappaBean) throws Exception {

		String idLog = "saveOrUpdateMappa";
		if(mappaBean.getIsNew()){
			mappaBean.setDataCreazione(new Date());
		}else{
			mappaBean.setDataModifica(new Date());
		}
		try{

			logger.info("START >>> " + idLog);
			String result = mappaDao.saveOrUpdateMappa(mappaBean);
			if (logger.isDebugEnabled()) {
				logger.debug(idLog+" >>> Salvo la mappa passata in input");
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
