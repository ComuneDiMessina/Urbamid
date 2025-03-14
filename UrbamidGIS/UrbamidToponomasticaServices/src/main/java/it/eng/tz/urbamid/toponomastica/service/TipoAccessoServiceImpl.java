package it.eng.tz.urbamid.toponomastica.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.eng.tz.urbamid.toponomastica.exception.ToponomasticaServiceException;
import it.eng.tz.urbamid.toponomastica.persistence.model.TipoAccesso;
import it.eng.tz.urbamid.toponomastica.persistence.repositories.JpaRepositoryTipoAccesso;
import it.eng.tz.urbamid.toponomastica.web.dto.TipoAccessoDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.converter.TipoAccessoConverter;

@Service
public class TipoAccessoServiceImpl implements TipoAccessoService {

	private static final Logger logger = LoggerFactory.getLogger(TipoAccessoServiceImpl.class.getName());

	private static final String START = "START >>> {}";
	private static final String END = "END >>> {}";
	private static final String DEBUG_INFO_END = "{} >>> numero di risultati estratti: {}";
	private static final String ERROR = "ERROR {} >>> {}";

	@Autowired
	private JpaRepositoryTipoAccesso repository;
	
	@Autowired
	private TipoAccessoConverter converter;

	@Override
	public List<TipoAccessoDTO> findAll() throws ToponomasticaServiceException {
		
		String idLog = "findAllTipoAccesso";
		
		try {
			
			logger.info(START, idLog);
			
			List<TipoAccesso> result = repository.findAll();
			
			logger.debug(DEBUG_INFO_END, idLog, result.size());

			return converter.toDto(result);
			
		} catch (Exception e) {
			
			logger.error(ERROR, idLog, e.getMessage());
			throw new ToponomasticaServiceException("Non è stato possibile trovare il TipoAccesso");
			
		} finally {
			
			logger.info(END, idLog);
			
		}
		
	}
	
}
