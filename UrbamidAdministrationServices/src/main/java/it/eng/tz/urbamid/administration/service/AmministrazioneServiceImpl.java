package it.eng.tz.urbamid.administration.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.urbamid.administration.persistence.model.Funzionalita;
import it.eng.tz.urbamid.administration.persistence.model.MenuFunzionalita;
import it.eng.tz.urbamid.administration.persistence.repositories.JpaRepositoryFunzionalita;
import it.eng.tz.urbamid.administration.persistence.repositories.JpaRepositoryMenuFunzionalita;
import it.eng.tz.urbamid.administration.web.dto.FunzionalitaDto;
import it.eng.tz.urbamid.administration.web.dto.MenuFunzionalitaDto;
import it.eng.tz.urbamid.administration.web.dto.converter.FunzionalitaConverter;
import it.eng.tz.urbamid.administration.web.dto.converter.MenuFunzionalitaConverter;

/**
 * Service per la gestione dei grafici
 * @author Alessandro Paolillo
 */
@Service
public class AmministrazioneServiceImpl implements AmministrazioneService {

	private static final Logger logger = LoggerFactory.getLogger(AmministrazioneServiceImpl.class.getName());


	@Autowired
	private JpaRepositoryFunzionalita funzionalitaDao;
	
	@Autowired
	private JpaRepositoryMenuFunzionalita menuFunzionalitaDao;
	
	@Autowired
	private FunzionalitaConverter funzionalitaConverter;
	
	@Autowired
	private MenuFunzionalitaConverter menuFunzionalitaConverter;
	
	@Override
	@Transactional(value = "jpaTxMgr", rollbackFor = { Exception.class}, readOnly = true)
	public List<FunzionalitaDto> getFunzionalita(List<String> authorities) throws Exception {
		String idLog = "getFunzionalita";		
		try{
			logger.info("START >>> " + idLog);
			if (!authorities.isEmpty()) {
				for(String authority : authorities) {logger.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>authority:"+authority);}
			}else logger.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>authorities is empty");
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
	
	@Override
	@Transactional(value = "jpaTxMgr", rollbackFor = { Exception.class}, readOnly = true)
	public MenuFunzionalitaDto getMenuFunzionalita(String authority) throws Exception {
		String idLog = "getMenuFunzionalita";		
		try{
			logger.info("START >>> " + idLog);
			
			MenuFunzionalita resultModel = menuFunzionalitaDao.findMenuByPermissions(authority);		
			MenuFunzionalitaDto result =  menuFunzionalitaConverter.toDto(resultModel);	
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
