package it.eng.tz.urbamid.toponomastica.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import it.eng.tz.urbamid.toponomastica.exception.ToponomasticaServiceException;
import it.eng.tz.urbamid.toponomastica.persistence.model.TipoFunzionale;
import it.eng.tz.urbamid.toponomastica.persistence.repositories.JpaRepositoryTipoFunzionale;
import it.eng.tz.urbamid.toponomastica.web.dto.TipoFunzionaleDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.converter.TipoFunzionaleConverter;

@Service
public class TipoFunzionaleServiceImpl implements TipoFunzionaleService {

	private static final Logger logger = LoggerFactory.getLogger(TipoFunzionaleServiceImpl.class.getName());

	private static final String START = "START >>> {}";
	private static final String END = "END >>> {}";
	private static final String DEBUG_INFO_END = "{} >>> numero di risultati estratti: {}";
	private static final String ERROR = "ERROR {} >>> {}";
	
	@Autowired
	private JpaRepositoryTipoFunzionale repository;
	
	@Autowired
	private TipoFunzionaleConverter converter;

	@Override
	public List<TipoFunzionaleDTO> findAll() throws ToponomasticaServiceException {
		
		String idLog = "findAl TipoFunzionale";
		
		try {
			
			logger.info(START, idLog);
			
			List<TipoFunzionale> result = repository.findAll(new Sort(Sort.Direction.ASC, "descrizione"));
			
			logger.debug(DEBUG_INFO_END, idLog, result.size());
			
			return converter.toDto(result);
			
		} catch (Exception e) {
			
			logger.error(ERROR, idLog, e.getMessage());
			throw new ToponomasticaServiceException("Non è stato possibile recuperare il TipoTopologico");
			
		} finally {
			
			logger.info(END, idLog);
			
		}
		
	}
	
}
