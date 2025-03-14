package it.eng.tz.urbamid.toponomastica.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.eng.tz.urbamid.toponomastica.exception.ToponomasticaServiceException;
import it.eng.tz.urbamid.toponomastica.persistence.model.TipoLocalita;
import it.eng.tz.urbamid.toponomastica.persistence.repositories.JpaRepositoryTipoLocalita;
import it.eng.tz.urbamid.toponomastica.web.dto.TipoLocalitaDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.converter.TipoLocalitaConverter;

/**
 * @author Luca Tricarico
 */

@Service
public class TipoLocalitaServiceImpl implements TipoLocalitaService {

	private static final Logger logger = LoggerFactory.getLogger(TipoLocalitaServiceImpl.class.getName());
	
	private static final String START = "START >>> {}";
	private static final String END = "END >>> {}";
	private static final String DEBUG_INFO_END = "{} >>> numero di risultati estratti: {}";
	private static final String ERROR = "ERROR {} >>> {} ";
	
	@Autowired
	private JpaRepositoryTipoLocalita tipoLocalitaDao;
	
	@Autowired
	private TipoLocalitaConverter tipoLocalitaConverter;
	
	@Override
	public List<TipoLocalitaDTO> findAll() throws ToponomasticaServiceException {

		String idLog = "findAllTipoLocalita";
		
		try {
			
			logger.info(START, idLog);
			
			List<TipoLocalita> result = tipoLocalitaDao.findAll();
			
			logger.debug(DEBUG_INFO_END, idLog, result.size());

			return tipoLocalitaConverter.toDto(result);
			
		} catch (Exception e) {
			
			logger.error(ERROR, idLog, e.getMessage());
			throw new ToponomasticaServiceException("Non è stato possibile trovare il tipo località");
			
		} finally {
			
			logger.info(END, idLog);
			
		}

	}

}
