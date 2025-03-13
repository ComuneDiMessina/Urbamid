package it.eng.tz.urbamid.toponomastica.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.urbamid.toponomastica.exception.ToponomasticaServiceException;
import it.eng.tz.urbamid.toponomastica.filter.CippoChilometricoFilter;
import it.eng.tz.urbamid.toponomastica.persistence.dao.CippoChilometricoDAO;
import it.eng.tz.urbamid.toponomastica.persistence.model.CippoChilometrico;
import it.eng.tz.urbamid.toponomastica.persistence.repositories.JpaRepositoryCippoChilometrico;
import it.eng.tz.urbamid.toponomastica.util.RepositoryUtils;
import it.eng.tz.urbamid.toponomastica.web.dto.CippoChilometricoDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.PagedResult;
import it.eng.tz.urbamid.toponomastica.web.dto.converter.CippoChilometricoConverter;

@Service
public class CippoChilometricoServiceImpl implements CippoChilometricoService {

	private static final Logger logger = LoggerFactory.getLogger(CippoChilometricoServiceImpl.class.getName());
	
	private static final String START = "START >>> {}";
	private static final String END = "END >>> {}";
	private static final String DEBUG_INFO_END = "{} >>> numero di risultati estratti: {}";
	private static final String ERROR = "ERROR {} >>> {}";
	
	@Autowired
	private JpaRepositoryCippoChilometrico repository;
	
	@Autowired
	private CippoChilometricoDAO dao;
	
	@Autowired
	private CippoChilometricoConverter converter;
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public PagedResult<CippoChilometricoDTO> getCippo(CippoChilometricoFilter filter) throws ToponomasticaServiceException {

		String idLog = "getCippo";
		
		try {
			
			logger.info(START, idLog);
			
			Page<CippoChilometrico> pagedResult = repository.findAll(RepositoryUtils.buildCippoChilometricoPredicate(filter), PageRequest.of(filter.getPageIndex() / filter.getPageSize(), filter.getPageSize()));
			
			logger.debug(DEBUG_INFO_END, idLog, pagedResult.getNumberOfElements());

			return converter.toDTO(new PagedResult<>(pagedResult));
			
		} catch (Exception e) {
			
			logger.error(ERROR, idLog, e.getMessage());
			throw new ToponomasticaServiceException("Non è stato possibile recuperare i cippi chilometrici");
		} finally {

			logger.info(END, idLog);
		
		}
		
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = false)
	public CippoChilometricoDTO insertOrUpdate(CippoChilometricoDTO cippo) throws ToponomasticaServiceException {
		
		String idLog = "insertOrUpdate";
		
		try {
			
			if(cippo.getId() != null) {
				
				logger.debug(START, " UPDATE " + idLog);
				
				CippoChilometrico model = dao.updateCippo(converter.toModel(cippo));
				return converter.toDTO(model);
			} else {
				
				logger.debug(START, " INSERT " + idLog);
				CippoChilometrico model = dao.insertCippo(converter.toModel(cippo));
				return converter.toDTO(model);
			}
			
		} catch (Exception e) {
			
			logger.error(ERROR, idLog, e.getMessage());
			throw new ToponomasticaServiceException("Non è stato possibile inserire o aggiornare il cippo chilometrico");
		} finally {
			
			logger.debug(END, idLog);
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = false)
	public void delete(Long id) throws ToponomasticaServiceException {
		
		String idLog = "delete";
		
		try {
			
			logger.debug(START, idLog);
			repository.deleteById(id);
		} catch (Exception e) {
			
			logger.error(ERROR, idLog, e.getMessage());
			throw new ToponomasticaServiceException("Non è stato possibile eliminare il cippo chilometrico con l'id: " + id);
		} finally {
			
			logger.info(END, idLog);
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class }, readOnly = true)
	public Long countCippiByEstesa(Long idEstesa) throws ToponomasticaServiceException {
		
		String idLog = "countCippiByIdEstesa";
		
		try {

			logger.debug(START, idLog);
			Long result = repository.countCippiByIdEstesa(idEstesa);
			return result;
		} catch (Exception e) {

			logger.error(ERROR, idLog, e.getMessage());
			throw new ToponomasticaServiceException("Non è stato possibile contare i cippi chilometrico con l'id estesa: " + idEstesa);
		} finally {
			
			logger.info(END, idLog);
		}
	}

}
