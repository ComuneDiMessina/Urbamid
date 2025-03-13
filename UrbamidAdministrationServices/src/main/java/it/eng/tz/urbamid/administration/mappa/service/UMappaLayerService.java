package it.eng.tz.urbamid.administration.mappa.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.urbamid.administration.mappa.dao.LayerPermessiDao;
import it.eng.tz.urbamid.administration.mappa.dao.UMappaLayerDao;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.MappaPermessoBean;
import it.eng.tz.urbamid.administration.mappa.dao.model.MappaPermesso;
import it.eng.tz.urbamid.administration.mappa.dao.model.UMappa;
import it.eng.tz.urbamid.administration.mappa.dao.model.UMappaLayer;
import it.eng.tz.urbamid.administration.mappa.service.converter.MappaPermessiConverter;
import it.eng.tz.urbamid.administration.web.dto.GroupMapDTO;
import it.eng.tz.urbamid.administration.web.dto.LayerDaAggiungereDTO;


@Service
public class UMappaLayerService implements IUMappaLayerService{
	private static final Logger logger = LoggerFactory.getLogger(UMappaLayerService.class.getName());

	@Autowired
	UMappaLayerDao ulayerDao;

	@Autowired
	private LayerPermessiDao permessiLayerDao;

	@Autowired
	private MappaPermessiConverter mappaPermessoConverter;

	@Override
	public List<UMappaLayer> getLayers() throws Exception {
		String idLog = "getLayers";		

		try{
			logger.info("START >>> " + idLog);
			List<UMappaLayer> resultModel =  ulayerDao.select();

			return resultModel;
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info("END <<< " + idLog);
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class}, readOnly = false)
	public String saveOrUpdate(UMappaLayer entity) throws Exception {

		try{
			logger.info("START >>> salvaOrUpdate","UMappa ID[{}]",entity!=null?entity.getId():null);

			return String.valueOf(ulayerDao.insert(entity));

		} catch (Exception e) {
			logger.error( "salvaOrUpdate  >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info("END <<< salvaOrUpdate" );
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class}, readOnly = false)
	public int[] deletes(List<Integer> ids) throws Exception {

		try{
			logger.info("START >>> deletes","List ID SIZE[{}]",ids!=null?ids.size():null);


			return ulayerDao.deletes(ids);

		} catch (Exception e) {
			logger.error( "deletes  >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info("END <<< deletes" );
		}


	}

	@Override
	@Transactional(rollbackFor = { Exception.class}, readOnly = false)
	public int delete(UMappaLayer entity) throws Exception {
		try{
			logger.info("START >>> delete","UMappa ID[{}]",entity!=null?entity.getId():null);


			return ulayerDao.delete(entity);

		} catch (Exception e) {
			logger.error( "delete  >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info("END <<< delete" );
		}

	}

	@Override
	public LinkedHashMap<String, List<UMappaLayer>> getGroupLayerByMappa(UMappa entity) throws Exception {
		try{
			logger.info("START >>> getGroupLayerByMappa","UMappa ID[{}]",entity!=null?entity.getId():null);


			return ulayerDao.getGroupLayerByMappa(entity);

		} catch (Exception e) {
			logger.error( "getGroupLayerByMappa  >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info("END <<< getGroupLayerByMappa" );
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class}, readOnly = false)
	public int updateGruppo(UMappaLayer entity) throws Exception {
		try{
			logger.info("START >>> updateGruppo","UMappaLayer ID[{}]",entity!=null?entity.getId():null);


			return ulayerDao.updateGruppo(entity);

		} catch (Exception e) {
			logger.error( "updateGruppo  >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info("END <<< updateGruppo" );
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class}, readOnly = false)
	public int[] deleteGruppo(UMappaLayer entity) throws Exception {
		try{
			logger.info("START >>> deleteGruppo","UMappaLayer ID[{}]",entity!=null?entity.getId():null);


			return ulayerDao.deleteGruppo(entity);

		} catch (Exception e) {
			logger.error( "deleteGruppo  >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info("END <<< deleteGruppo" );
		}

	}

	@Override
	@Transactional(rollbackFor = { Exception.class}, readOnly = true)
	public List<MappaPermessoBean> getLayerPermessi(Integer idMappa, String nomeLayer) throws Exception {
		String idLog = "getMapPermessi";		
		List<MappaPermessoBean> result = new ArrayList<MappaPermessoBean>();
		try{
			logger.info("START >>> " + idLog);
			List<MappaPermesso> resultList =  permessiLayerDao.getLayerPermessi(idMappa, nomeLayer);
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
	public List<MappaPermessoBean> getPermessi(String nomeLayer) throws Exception {
		String idLog = "getMapPermessi";		
		List<MappaPermessoBean> result = new ArrayList<MappaPermessoBean>();
		try{
			logger.info("START >>> " + idLog);
			List<MappaPermesso> resultList =  permessiLayerDao.getPermessi(nomeLayer);
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
	public List<UMappaLayer> countPermessiLayer(String nomeLayer) throws Exception {
		String idLog = "getMapPermessi";		
		List<UMappaLayer> result = new ArrayList<UMappaLayer>();
		try{
			logger.info("START >>> " + idLog);
			result =  permessiLayerDao.countPermessiLayer(nomeLayer);
			return result;
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info("END <<< " + idLog);
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class}, readOnly = false)
	public void insertPermesso(List<MappaPermessoBean> permesso) throws Exception {

		String idLog = "insertePermesso";

		try {

			logger.info("START >>> " + idLog);

			permessiLayerDao.insertPermessiLayer(permesso);

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

	@Override
	@Transactional(rollbackFor = { Exception.class}, readOnly = false)
	public int saveGrups(LinkedHashMap<String, List<UMappaLayer>> grups) throws Exception {
		try{
			logger.info("START >>> saveGrups","LinkedHashMap<String, List<UMappaLayer>>[{}]",grups!=null?grups.size():null);


			return ulayerDao.saveGrups(grups);

		} catch (Exception e) {
			logger.error( "saveGrups  >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info("END <<< saveGrups" );
		}
	}

	@Override
	public List<GroupMapDTO> getGroupTableMap(Integer idMappa) throws Exception {
		String idLog = "getGroupTableMap";
		try {
			logger.info("START >>> " + idLog);

			return ulayerDao.getGroupTableMap(idMappa);
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info("END <<< " + idLog);
		
		}
	}
	
	@Override
	public List<LayerDaAggiungereDTO> layerDaAggiungere(final Integer idMappa, final String nomeTavola) throws Exception {
		String idLog = "layerDaAggiungere";
		try {
			logger.info("START >>> " + idLog);

			return ulayerDao.layerDaAggiungere(idMappa, nomeTavola);
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info("END <<< " + idLog);
		
		}
	}

}
