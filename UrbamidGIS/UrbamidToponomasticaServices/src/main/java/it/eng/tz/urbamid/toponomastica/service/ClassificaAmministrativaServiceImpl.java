package it.eng.tz.urbamid.toponomastica.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import it.eng.tz.urbamid.toponomastica.exception.ToponomasticaServiceException;
import it.eng.tz.urbamid.toponomastica.persistence.model.ClassificaAmministrativa;
import it.eng.tz.urbamid.toponomastica.persistence.repositories.JpaRepositoryClassificaAmministrativa;
import it.eng.tz.urbamid.toponomastica.web.dto.ClassificaAmministrativaDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.converter.ClassificaAmministrativaConverter;

/**
* @author Luca Tricarico
*/
@Service
public class ClassificaAmministrativaServiceImpl implements ClassificaAmministrativaService {

	private static final Logger logger = LoggerFactory.getLogger(ClassificaAmministrativaServiceImpl.class.getName());

	private static final String START = "START >>> {}";
	private static final String END = "END >>> {}";
	private static final String DEBUG_INFO_END = "{} >>> numero di risultati estratti: {}";
	private static final String ERROR = "ERRORE {} >>> {}";
	
	@Autowired
	private JpaRepositoryClassificaAmministrativa repository;
	
	@Autowired
	private ClassificaAmministrativaConverter converter;
	
	@Override
	public List<ClassificaAmministrativaDTO> findAll() throws ToponomasticaServiceException {
		
		String idLog = "findAll";
		
		try {
			
			logger.info(START, idLog);
			
			List<ClassificaAmministrativa> result = repository.findAll(new Sort(Sort.Direction.ASC, "descrizione"));

			logger.debug(DEBUG_INFO_END, idLog, result.size());

			return converter.toDto(result);
			
		} catch (Exception e) {
			
			logger.error(ERROR, idLog, e.getMessage());
			throw new ToponomasticaServiceException("Non è stato possibile trovare la classifica amministrativa");
			
		} finally {
			
			logger.info(END, idLog);
			
		}
		
	}

}
