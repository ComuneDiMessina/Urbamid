package it.eng.tz.urbamid.toponomastica.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.urbamid.toponomastica.exception.ToponomasticaDbException;
import it.eng.tz.urbamid.toponomastica.exception.ToponomasticaServiceException;
import it.eng.tz.urbamid.toponomastica.filter.PercorsoFilter;
import it.eng.tz.urbamid.toponomastica.persistence.dao.PercorsoDAO;
import it.eng.tz.urbamid.toponomastica.persistence.model.Percorso;
import it.eng.tz.urbamid.toponomastica.persistence.repositories.JpaRepositoryPercorso;
import it.eng.tz.urbamid.toponomastica.util.RepositoryUtils;
import it.eng.tz.urbamid.toponomastica.web.dto.PagedResult;
import it.eng.tz.urbamid.toponomastica.web.dto.PercorsoDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.converter.PercorsoConverter;

@Service
public class PercorsoServiceImpl implements PercorsoService {

	private static final Logger logger = LoggerFactory.getLogger(PercorsoServiceImpl.class.getName());

	private static final String START = "START >>> {}";
	private static final String END = "END >>> {}";
	private static final String DEBUG_INFO_END = "{} >>> numero di risultati estratti: {}";
	private static final String ERROR = "ERROR {} >>> {}";

	@Autowired
	private JpaRepositoryPercorso repository;
	
	@Autowired
	private PercorsoDAO dao;
	
	@Autowired
	private PercorsoConverter converter;
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public PagedResult<PercorsoDTO> getPercorso(PercorsoFilter filter) throws ToponomasticaServiceException {
		
		String idLog = "getPercorso";

		try {

			logger.info(START, idLog);
			Page<Percorso> pagedResult = repository.findAll(RepositoryUtils.buildPercorsoPredicate(filter), PageRequest.of(filter.getPageIndex() / filter.getPageSize(), filter.getPageSize()));
			logger.debug(DEBUG_INFO_END, idLog, pagedResult.getNumberOfElements());
			return converter.toDTO(new PagedResult<>(pagedResult));
		} catch (ToponomasticaDbException e) {
			
			logger.error(ERROR, idLog, e.getMessage());
			throw new ToponomasticaServiceException("Non è stato possibile recuperare i percorsi");
		} finally {

			logger.info(END, idLog);

		}
		
	}

	@Override
	public PercorsoDTO insertOrUpdate(PercorsoDTO percorso) throws ToponomasticaServiceException {
		
		String idLog = "insertOrUpdate";
		
		try {
			
			if(percorso.getId() != null) {
				
				logger.debug(START, " UPDATE " + idLog);
				
				Percorso model = dao.updatePercorso(converter.toModel(percorso));					
				return converter.toDTO(model);
			} else {
				
				logger.debug(START, " INSERT " + idLog);
				
				Percorso model = dao.insertPercorso(converter.toModel(percorso));					
				return converter.toDTO(model);
			}
			
		} catch (Exception e) {
			
			logger.error(ERROR, idLog, e.getMessage());
			throw new ToponomasticaServiceException("Non è stato possibile inserire o modificare un percorso");
			
		} finally {
			
			logger.debug(END, idLog);
			
		}
		
	}

	@Override
	public void deleteById(Long id) throws ToponomasticaServiceException {
		
		String idLog = "delete";
		
		try {
			
			logger.debug(START, idLog);
			
			repository.deleteById(id);
			
		} catch (Exception e) {
			
			logger.error(ERROR, idLog, e.getMessage());
			throw new ToponomasticaServiceException("Non è stato possibile eliminare il percorso con l'id: " + id);
			
		} finally {
			
			logger.info(END, idLog);
			
		}
		
	}

}
