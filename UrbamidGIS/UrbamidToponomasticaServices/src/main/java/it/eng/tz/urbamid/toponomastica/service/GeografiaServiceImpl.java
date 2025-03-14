package it.eng.tz.urbamid.toponomastica.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.urbamid.toponomastica.persistence.model.Comuni;
import it.eng.tz.urbamid.toponomastica.persistence.model.Province;
import it.eng.tz.urbamid.toponomastica.persistence.repositories.JpaRepositoryComuni;
import it.eng.tz.urbamid.toponomastica.persistence.repositories.JpaRepositoryProvince;
import it.eng.tz.urbamid.toponomastica.web.dto.TipologicaDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.converter.ComuniConverter;
import it.eng.tz.urbamid.toponomastica.web.dto.converter.ProvinceConverter;

@Service
public class GeografiaServiceImpl implements GeografiaService {

	private static final Logger logger = LoggerFactory.getLogger(GeografiaServiceImpl.class.getName());

	private static final String START = "START >>> {}";
	private static final String END = "END >>> {}";
	private static final String DEBUG_INFO_END = "{} >>> numero di risultati estratti: {}";

	@Autowired
	private JpaRepositoryProvince provinceDao;

	@Autowired
	private JpaRepositoryComuni comuniDao;

	@Autowired
	private ComuniConverter comuniConverter;

	@Autowired
	private ProvinceConverter provinceConverter;

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<TipologicaDTO> getComuni() throws Exception {
		String idLog = "getComuni";
		try{
			logger.info(START, idLog);
			
			List<Comuni> resultModel = comuniDao.getComuni();
			
			logger.debug(DEBUG_INFO_END, idLog, resultModel.size());
			
			return comuniConverter.toDto(resultModel);	
			
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<TipologicaDTO> getProvince() throws Exception {
		String idLog = "getComuni";
		try{
			logger.info(START, idLog);
			
			List<Province> resultModel = provinceDao.getProvince();
			
			logger.debug(DEBUG_INFO_END, idLog, resultModel.size());
			
			return provinceConverter.toDto(resultModel);	
			
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<TipologicaDTO> getComuniByProvincia(Long idProvincia) throws Exception {
		String idLog = "getComuni";
		try{
			logger.info(START, idLog);
			
			List<Comuni> resultModel = comuniDao.getComuniByProvincia(idProvincia);
			
			logger.debug(DEBUG_INFO_END, idLog, resultModel.size());
			
			return comuniConverter.toDto(resultModel);	
			
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<TipologicaDTO> getComuniByMessina() throws Exception {
		String idLog = "getComuni";
		try{
			logger.info(START, idLog);
			
			List<Comuni> resultModel = comuniDao.getComuniByMessina();
			
			logger.debug(DEBUG_INFO_END, idLog, resultModel.size());
			
			return comuniConverter.toDto(resultModel);	
			
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}


}
