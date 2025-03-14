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
import it.eng.tz.urbamid.toponomastica.filter.EstesaAmministrativaFilter;
import it.eng.tz.urbamid.toponomastica.persistence.dao.EstesaAmministrativaDAO;
import it.eng.tz.urbamid.toponomastica.persistence.model.EstesaAmministrativa;
import it.eng.tz.urbamid.toponomastica.persistence.repositories.JpaRepositoryEstesaAmministrativa;
import it.eng.tz.urbamid.toponomastica.util.RepositoryUtils;
import it.eng.tz.urbamid.toponomastica.web.dto.EstesaAmministrativaDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.PagedResult;
import it.eng.tz.urbamid.toponomastica.web.dto.converter.EstesaAmministrativaConverter;

@Service
public class EstesaAmministrativaServiceImpl implements EstesaAmministrativaService {

	private static final Logger logger = LoggerFactory.getLogger(EstesaAmministrativaServiceImpl.class.getName());
	
	private static final String START = "START >>> {}";
	private static final String END = "END >>> {}";
	private static final String DEBUG_INFO_END = "{} >>> numero di risultati estratti: {}";
	private static final String ERROR = "ERROR {} >>> {}";
	
	@Autowired
	private JpaRepositoryEstesaAmministrativa repository;
	
	@Autowired
	private EstesaAmministrativaDAO dao;
	
	@Autowired
	private EstesaAmministrativaConverter converter;
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public PagedResult<EstesaAmministrativaDTO> getEstesaAmministrativa(EstesaAmministrativaFilter filter) throws ToponomasticaServiceException {
		
		String idLog = "getEstesaAmministrativa";
		
		try {
			
			logger.info(START, idLog);
			
			Page<EstesaAmministrativa> pagedResult = repository.findAll(RepositoryUtils.buildEstesaAmministrativaPredicate(filter), PageRequest.of(filter.getPageIndex() / filter.getPageSize(), filter.getPageSize()));
			
			logger.debug(DEBUG_INFO_END, idLog, pagedResult.getNumberOfElements());

			return converter.toDTO(new PagedResult<>(pagedResult));
			
		} catch (Exception e) {
			
			logger.error(ERROR, idLog, e);
			throw new ToponomasticaServiceException("Non è stato possibile recuperare le estese amministrative");
		} finally {
			
			logger.info(END, idLog);
			
		}
		
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<EstesaAmministrativaDTO> findAll(String descrizione) throws ToponomasticaServiceException {
		
		String idLog = "findAll";
		
		List<EstesaAmministrativaDTO> result;
		
		try {
			
			logger.debug(START, idLog);
			
			result = dao.ricerca(descrizione);
			
			return result;
			
		} catch (Exception e) {
			
			logger.error(ERROR, idLog, e.getMessage());
			throw new ToponomasticaServiceException("Non è stato possibile trovare l'estesa amministrativa");
			
		} finally {
			
			logger.info(END, idLog);
			
		}
		
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<EstesaAmministrativaDTO> findSigla(Long id) throws ToponomasticaServiceException {
		
		String idLog = "findAll";
		
		List<EstesaAmministrativaDTO> result;
		
		try {
			
			logger.debug(START, idLog);
			
			result = dao.ricercaSigla(id);
			
			return result;
			
		} catch (Exception e) {
			
			logger.error(ERROR, idLog, e.getMessage());
			throw new ToponomasticaServiceException("Non è stato possibile trovare l'estesa amministrativa");
			
		} finally {
			
			logger.info(END, idLog);
			
		}
		
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = false)
	public EstesaAmministrativaDTO insertOrUpdate(EstesaAmministrativaDTO estesa) throws ToponomasticaServiceException {
		
		String idLog = "insertOrUpdate";
		
		try {
			
			if(estesa.getId() != null) {
				
				logger.debug(START, " UPDATE " + idLog);
				
				EstesaAmministrativa model = dao.update(converter.toModel(estesa));
				return converter.toDTO(model);
			} else {
				
				logger.debug(START, " INSERT " + idLog);
				
				EstesaAmministrativa model = dao.insert(converter.toModel(estesa));
				return converter.toDTO(model);
			}
			
		} catch (Exception e) {
			
			logger.error(ERROR, idLog, e.getMessage());
			throw new ToponomasticaServiceException("Non è stato possibile inserire o modificare un estesa amministrativa");
			
		} finally {
			
			logger.info(END, idLog);
			
		}
		
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = false)
	public void deleteById(Long id) throws ToponomasticaServiceException {
		
		String idLog = "delete";
		
		try {
			
			logger.debug(START, idLog);
			
			repository.deleteById(id);
			
		} catch (Exception e) {

			logger.error(ERROR, idLog, e.getMessage());
			throw new ToponomasticaServiceException("Non è stato possibile eliminare l'estesa amministrativa");
			
		} finally {
			
			logger.info(END, idLog);
			
		}
		
	}

}
