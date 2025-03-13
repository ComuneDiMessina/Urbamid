package it.eng.tz.urbamid.toponomastica.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import it.eng.tz.urbamid.toponomastica.exception.ToponomasticaServiceException;
import it.eng.tz.urbamid.toponomastica.persistence.model.ClassificaFunzionale;
import it.eng.tz.urbamid.toponomastica.persistence.repositories.JpaRepositoryClassificaFunzionale;
import it.eng.tz.urbamid.toponomastica.web.dto.ClassificaFunzionaleDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.converter.ClassificaFunzionaleConverter;

/**
* @author Luca Tricarico
*/

@Service
public class ClassificaFunzionaleServiceImpl implements ClassificaFunzionaleService {

	private static final Logger logger = LoggerFactory.getLogger(ClassificaFunzionaleServiceImpl.class.getName());

	private static final String START = "START >>> {}";
	private static final String END = "END >>> {}";
	private static final String DEBUG_INFO_END = "{} >>> numero di risultati estratti: {}";
	private static final String ERROR = "ERROR {} >>> {}";
	
	@Autowired
	private JpaRepositoryClassificaFunzionale repository;
	
	@Autowired
	private ClassificaFunzionaleConverter converter;
	
	@Override
	public List<ClassificaFunzionaleDTO> findAll() throws ToponomasticaServiceException {
		
		String idLog = "findAll";
		
		try {
			
			logger.info(START, idLog);
			
			List<ClassificaFunzionale> result = repository.findAll(new Sort(Sort.Direction.ASC, "descrizione"));

			logger.debug(DEBUG_INFO_END, idLog, result.size());

			return converter.toDto(result);
			
		} catch (Exception e) {
			
			logger.error(ERROR, idLog, e.getMessage());
			throw new ToponomasticaServiceException("Non è stato possibile trovare la classifica funzionale");
		} finally {
			
			logger.info(END, idLog);
		}
		
	}

}
