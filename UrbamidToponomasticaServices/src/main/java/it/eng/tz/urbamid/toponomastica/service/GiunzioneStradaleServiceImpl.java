package it.eng.tz.urbamid.toponomastica.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.urbamid.toponomastica.exception.ToponomasticaServiceException;
import it.eng.tz.urbamid.toponomastica.filter.GiunzioneFilter;
import it.eng.tz.urbamid.toponomastica.persistence.dao.GiunzioneStradaleDAO;
import it.eng.tz.urbamid.toponomastica.persistence.model.GiunzioneStradale;
import it.eng.tz.urbamid.toponomastica.persistence.model.ToponimoStradale;
import it.eng.tz.urbamid.toponomastica.persistence.repositories.JpaRepositoryGiunzioneStradale;
import it.eng.tz.urbamid.toponomastica.util.RepositoryUtils;
import it.eng.tz.urbamid.toponomastica.web.dto.GiunzioneStradaleDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.PagedResult;
import it.eng.tz.urbamid.toponomastica.web.dto.ToponimoStradaleDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.converter.GiunzioneStradaleConverter;
import it.eng.tz.urbamid.toponomastica.web.dto.converter.ToponimoStradaleConverter;

@Service
public class GiunzioneStradaleServiceImpl implements GiunzioneStradaleService {

	private static final Logger logger = LoggerFactory.getLogger(GiunzioneStradaleServiceImpl.class.getName());

	private static final String START = "START >>> {}";
	private static final String END = "END >>> {}";
	private static final String DEBUG_INFO_END = "{} >>> numero di risultati estratti: {}";
	private static final String ERROR = "ERROR {} >>> {}";
	
	@Autowired
	private JpaRepositoryGiunzioneStradale repository;
	
	@Autowired
	private GiunzioneStradaleDAO giunzioneDAO;
	
	@Autowired
	private GiunzioneStradaleConverter converter;
	
	@Autowired
	private ToponimoStradaleConverter toponimoConverter;

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public PagedResult<GiunzioneStradaleDTO> getGiunzioneByFilter(GiunzioneFilter filter) throws ToponomasticaServiceException {
		
		String idLog = "getGiunzioneByFilter";
		try {
			
			logger.info(START, idLog);
			Page<GiunzioneStradale> pagedResult = repository.findAll(RepositoryUtils.buildGiunzioniPredicate(filter), PageRequest.of(filter.getPageIndex() / filter.getPageSize(), filter.getPageSize()));
			
			logger.debug(DEBUG_INFO_END, idLog, pagedResult.getNumberOfElements());
			return converter.toDTO(new PagedResult<>(pagedResult));
			
		} catch (Exception e) {
			
			logger.error(ERROR, idLog, e.getMessage());
			throw new ToponomasticaServiceException("Non è stato possibile recuperare la giunzione stradale");
		
		} finally {
		
			logger.info(END);
		}
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = false)
	public GiunzioneStradaleDTO insertOrUpdate(GiunzioneStradaleDTO giunzioneDTO) throws ToponomasticaServiceException{
		
		String idLog = "InsertOrUpdate";
		
		try {
			
			if(giunzioneDTO.getId() != null) {
				
				logger.debug(START + " UPDATE ", idLog);
				GiunzioneStradale model = giunzioneDAO.update( converter.toModel(giunzioneDTO) );
				
				return converter.toDTO(model);
			} else {
				
				logger.debug(START + " UPDATE ", idLog);
				GiunzioneStradale model = giunzioneDAO.insert( converter.toModel(giunzioneDTO) );
				
				return converter.toDTO(model);
			}
			
		} catch (Exception e) {
			
			logger.debug(ERROR, idLog, e.getMessage());
			throw new ToponomasticaServiceException("Non è stato possibile inserire/modifica una giunzione stradale: " + e.getMessage());
			
		} finally {
			
			logger.info(END, idLog);
			
		}
		
	}
	
	@Override
	public void deleteGiunzioneById(Long id) throws ToponomasticaServiceException {
		
		String idLog = "deleteGiunzioneById";
		
		try {
			
			logger.debug(START, idLog);
			repository.deleteById(id);
			
		} catch (Exception e) {
			
			logger.error(ERROR, idLog, e.getMessage());
			throw new ToponomasticaServiceException("Non è stato possibile eliminare la giunzione stradale con id "+id);
			
		} finally {
			
			logger.info(END, idLog);
			
		}
		
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = false)
	public List<ToponimoStradaleDTO> findIntersections(String geom) throws ToponomasticaServiceException {
		
		String idLog = "intersezioniToponimi";
		
		try {
			
			logger.info(START, idLog);
			
			List<ToponimoStradale> result = giunzioneDAO.findIntersections(geom);
			
			logger.debug(DEBUG_INFO_END, idLog, result.size());
			return toponimoConverter.toDTO(result);
			
		} catch (Exception e) {
			
			logger.error(ERROR, idLog, e.getMessage());
			throw new ToponomasticaServiceException("Non è stato possibile intercettare i toponimi stradali");
			
		} finally {
			
			logger.info(END);
			
		}
		
		
	}
	
}
