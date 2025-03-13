package it.eng.tz.urbamid.profilemanager.service;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.urbamid.profilemanager.exception.ProfileManagerDbException;
import it.eng.tz.urbamid.profilemanager.persistence.model.RuoloPermesso;
import it.eng.tz.urbamid.profilemanager.persistence.repositories.JpaRepositoryRuoloPermesso;
import it.eng.tz.urbamid.profilemanager.profile.dao.RuoloPermessoDao;
import it.eng.tz.urbamid.profilemanager.web.dto.RuoloPermessoDto;
import it.eng.tz.urbamid.profilemanager.web.dto.converter.RuoloPermessoConverter;

@Service
public class RuoloPermessoSvcImpl implements RuoloPermessoSvc {
	
	private static final Logger logger = LoggerFactory.getLogger(RuoloPermessoSvcImpl.class.getName());
	
	@Autowired
	private JpaRepositoryRuoloPermesso jpaRuoloPermessoDao;
	
	@Autowired
	private RuoloPermessoDao ruoloPermessoDao;
	
	@Autowired
	private RuoloPermessoConverter ruoloPermessoConverter;
	
	@PostConstruct
	public void init() {
		if( logger.isInfoEnabled() ) {
			logger.info("Ciao {}", jpaRuoloPermessoDao);
		}
	}
	
	
	@Override
	@Transactional(transactionManager = "jpaTxMgr", rollbackFor = {ProfileManagerDbException.class}, readOnly = false)
	public RuoloPermessoDto salvaRuoloPermesso(RuoloPermessoDto ruoloPermessoDto) throws ProfileManagerDbException {
		RuoloPermessoDto result = null;
		try {
			 RuoloPermesso model = ruoloPermessoDao.select(ruoloPermessoDto);
			 if (model==null)
				 ruoloPermessoDao.insert(ruoloPermessoDto);
			 RuoloPermesso ruoloPermesso = ruoloPermessoDao.select(ruoloPermessoDto);
			 result = ruoloPermessoConverter.toDto(ruoloPermesso);
		} catch (Exception e) {
			
			logger.error("Errore nel salvataggio dell'utente {}", e.getMessage(), e);
			throw new ProfileManagerDbException(e);
		}
		return result;
	}
	
	@Override
	@Transactional(transactionManager = "jpaTxMgr", rollbackFor = {ProfileManagerDbException.class}, readOnly = false)
	public void deleteRuoloPermessoById(Long id) throws ProfileManagerDbException {
		try {
			jpaRuoloPermessoDao.deleteById(id);
		} catch (Exception e) {
			
			logger.error("Errore nell'eliminazione dell'associazione ruolo permesso {}", e.getMessage(), e);
			throw new ProfileManagerDbException(e);
		}
	}
	
	@Override
	@Transactional(transactionManager = "jpaTxMgr", rollbackFor = {ProfileManagerDbException.class}, readOnly = false)
	public void deleteRuoloPermesso(RuoloPermessoDto ruoloPermessoDto) throws ProfileManagerDbException {
		try {
			RuoloPermesso model = ruoloPermessoDao.select(ruoloPermessoDto);
			 if (model!=null)
				 ruoloPermessoDao.delete(ruoloPermessoDto);
		} catch (Exception e) {
			
			logger.error("Errore nell'eliminazione dell'associazione ruolo permesso {}", e.getMessage(), e);
			throw new ProfileManagerDbException(e);
		}
	}
}
