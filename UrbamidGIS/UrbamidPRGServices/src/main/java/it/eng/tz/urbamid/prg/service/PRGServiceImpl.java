package it.eng.tz.urbamid.prg.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.urbamid.prg.persistence.model.Funzionalita;
import it.eng.tz.urbamid.prg.persistence.repositories.JpaRepositoryFunzionalita;
import it.eng.tz.urbamid.prg.web.dto.FunzionalitaDto;
import it.eng.tz.urbamid.prg.web.dto.converter.FunzionalitaConverter;

/**
 * Service per la gestione dei grafici
 * @author Alessandro Paolillo
 */
@Service
public class PRGServiceImpl implements PRGService {

	private static final Logger logger = LoggerFactory.getLogger(PRGServiceImpl.class.getName());


	@Autowired
	private JpaRepositoryFunzionalita funzionalitaDao;
	
	@Autowired
	private FunzionalitaConverter funzionalitaConverter;
	
	
	@Override
	@Transactional(value = "jpaTxMgr", rollbackFor = { Exception.class}, readOnly = true)
	public List<FunzionalitaDto> getFunzionalita(List<String> authorities) throws Exception {
		String idLog = "getFunzionalita";		
		try{
			logger.info("START >>> " + idLog);
			
			List<Funzionalita> resultModel = funzionalitaDao.findByPermissions(authorities);		
			List<FunzionalitaDto> result =  funzionalitaConverter.toDto(resultModel);	
			return result;
			
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			
			logger.info("END <<< " + idLog);
		}
	}
	
	@PostConstruct
	public void init() {
		if( logger.isInfoEnabled() ) {
			logger.info("Ciao {}", funzionalitaDao);
		}
	}
	
	
	
}
