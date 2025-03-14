package it.eng.tz.urbamid.toponomastica.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.eng.tz.urbamid.toponomastica.exception.ToponomasticaServiceException;
import it.eng.tz.urbamid.toponomastica.persistence.model.TipoToponimo;
import it.eng.tz.urbamid.toponomastica.persistence.repositories.JpaRepositoryTipoToponimo;
import it.eng.tz.urbamid.toponomastica.web.dto.TipoToponimoDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.converter.TipoToponimoConverter;

/**
 * @author Luca Tricarico
 */
@Service
public class TipoToponimoServiceImpl implements TipoToponimoService {

	private static final Logger logger = LoggerFactory.getLogger(TipoToponimoServiceImpl.class.getName());

	private static final String START = "START >>> {}";
	private static final String END = "END >>> {}";
	private static final String DEBUG_INFO_END = "{} >>> numero di risultati estratti: {}";
	private static final String ERROR = "ERROR {} >>> {}";

	@Autowired
	private JpaRepositoryTipoToponimo tipoToponimoRepository;

	@Autowired
	private TipoToponimoConverter tipoToponimoConverter;

	@Override
	public List<TipoToponimoDTO> findAll() throws ToponomasticaServiceException {

		String idLog = "findAll";

		try {

			logger.info(START, idLog);

			List<TipoToponimo> result = tipoToponimoRepository.findAll();

			logger.debug(DEBUG_INFO_END, idLog, result.size());

			return tipoToponimoConverter.toDto(result);

		} catch (Exception e) {

			logger.error(ERROR, idLog, e.getMessage());
			throw new ToponomasticaServiceException("Non è stato possibile trovare il tipo di toponimo stradale");

		} finally {

			logger.info(END, idLog);

		}

	}

}
