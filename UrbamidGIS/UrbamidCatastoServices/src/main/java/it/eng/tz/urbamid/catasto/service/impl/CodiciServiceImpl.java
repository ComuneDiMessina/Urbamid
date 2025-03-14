package it.eng.tz.urbamid.catasto.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.urbamid.catasto.persistence.model.CategorieCatastali;
import it.eng.tz.urbamid.catasto.persistence.model.CodiciDiritto;
import it.eng.tz.urbamid.catasto.persistence.model.CodiciQualita;
import it.eng.tz.urbamid.catasto.persistence.model.Comuni;
import it.eng.tz.urbamid.catasto.persistence.model.Province;
import it.eng.tz.urbamid.catasto.persistence.repositories.JpaRepositoryCategorieCatastali;
import it.eng.tz.urbamid.catasto.persistence.repositories.JpaRepositoryCodiciDiritto;
import it.eng.tz.urbamid.catasto.persistence.repositories.JpaRepositoryCodiciQualita;
import it.eng.tz.urbamid.catasto.persistence.repositories.JpaRepositoryComuni;
import it.eng.tz.urbamid.catasto.persistence.repositories.JpaRepositoryProvince;
import it.eng.tz.urbamid.catasto.service.AbstractCatastoService;
import it.eng.tz.urbamid.catasto.service.CodiciService;
import it.eng.tz.urbamid.catasto.web.dto.TipologicaDTO;
import it.eng.tz.urbamid.catasto.web.dto.converter.CategorieCatastaliConverter;
import it.eng.tz.urbamid.catasto.web.dto.converter.CodiciDirittoConverter;
import it.eng.tz.urbamid.catasto.web.dto.converter.CodiciQualitaConverter;
import it.eng.tz.urbamid.catasto.web.dto.converter.ComuniConverter;
import it.eng.tz.urbamid.catasto.web.dto.converter.ProvinceConverter;

@Service
public class CodiciServiceImpl extends AbstractCatastoService implements CodiciService {

	private static final Logger logger = LoggerFactory.getLogger(CodiciServiceImpl.class.getName());

	private static final String START = "START >>> {}";
	private static final String END = "END >>> {}";
	private static final String DEBUG_INFO_END = "{} >>> numero di risultati estratti: {}";

	@Autowired
	private JpaRepositoryCategorieCatastali categorieCatastaliDao;

	@Autowired
	private JpaRepositoryCodiciDiritto codiciDirittoDao;

	@Autowired
	private JpaRepositoryCodiciQualita codiciQualitaDao;

	@Autowired
	private JpaRepositoryProvince provinceDao;

	@Autowired
	private JpaRepositoryComuni comuniDao;

	@Autowired
	private CategorieCatastaliConverter ccConverter;

	@Autowired
	private CodiciDirittoConverter cdConverter;

	@Autowired
	private CodiciQualitaConverter cqConverter;

	@Autowired
	private ComuniConverter comuniConverter;

	@Autowired
	private ProvinceConverter provinceConverter;

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<TipologicaDTO> categorieCatastali() throws Exception {
		String idLog = "categorieCatastali";
		try{
			logger.info(START, idLog);
			
			List<CategorieCatastali> resultModel = categorieCatastaliDao.categorieCatastali();
			
			logger.debug(DEBUG_INFO_END, idLog, resultModel.size());
			
			return ccConverter.toDto(resultModel);	
			
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<TipologicaDTO> codiciDiritto() throws Exception {
		String idLog = "codiciDiritto";
		try{
			logger.info(START, idLog);
			
			List<CodiciDiritto> resultModel = codiciDirittoDao.codiciDiritto();
			
			logger.debug(DEBUG_INFO_END, idLog, resultModel.size());
			
			return cdConverter.toDto(resultModel);	
			
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<TipologicaDTO> codiciQualita() throws Exception {
		String idLog = "codiciQualita";
		try{
			logger.info(START, idLog);
			
			List<CodiciQualita> resultModel = codiciQualitaDao.codiciQualita();
			
			logger.debug(DEBUG_INFO_END, idLog, resultModel.size());
			
			return cqConverter.toDto(resultModel);	
			
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info(END, idLog);
		}
	}

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

}
