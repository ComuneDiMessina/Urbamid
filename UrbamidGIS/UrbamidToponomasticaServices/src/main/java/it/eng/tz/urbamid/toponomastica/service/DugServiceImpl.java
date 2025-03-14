package it.eng.tz.urbamid.toponomastica.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.urbamid.toponomastica.exception.ToponomasticaServiceException;
import it.eng.tz.urbamid.toponomastica.persistence.model.Dug;
import it.eng.tz.urbamid.toponomastica.persistence.repositories.JpaRepositoryDug;
import it.eng.tz.urbamid.toponomastica.web.dto.DugDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.converter.DugConverter;

@Service
public class DugServiceImpl implements DugService {

	private static final Logger logger = LoggerFactory.getLogger(DugServiceImpl.class.getName());
	
	private static final String START = "START >>> {}";
	private static final String END = "END >>> {}";
	private static final String ERROR = "ERROR {} >>> {}";
	private static final String DEBUG_INFO_END = "{} >>> numero di risultati estratti: {}";
	
	@Autowired
	private JpaRepositoryDug dugDao;
	
	@Autowired
	private DugConverter dugConverter;
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, readOnly = true)
	public List<DugDTO> findAll(String dug) throws ToponomasticaServiceException {
		
		String idLog = "findAllDug";
		
		try {
			
			logger.info(START, idLog);
			
			List<Dug> result = dugDao.findDug(dug);
			
			logger.debug(DEBUG_INFO_END, idLog, result.size());
			
			return dugConverter.toDto(result);
			
		} catch (Exception e) {
			
			logger.error(ERROR, idLog, e.getMessage());
			throw new ToponomasticaServiceException("Non è stato possibile trovare i DUG");
			
		} finally {
			
			logger.info(END, idLog);
			
		}
		
	}

}
