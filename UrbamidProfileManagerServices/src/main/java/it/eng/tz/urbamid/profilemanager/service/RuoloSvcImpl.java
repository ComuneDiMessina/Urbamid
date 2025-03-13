package it.eng.tz.urbamid.profilemanager.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.urbamid.profilemanager.exception.ProfileManagerDbException;
import it.eng.tz.urbamid.profilemanager.persistence.model.Permesso;
import it.eng.tz.urbamid.profilemanager.persistence.model.Ruolo;
import it.eng.tz.urbamid.profilemanager.persistence.repositories.JpaRepositoryRuolo;
import it.eng.tz.urbamid.profilemanager.persistence.repositories.JpaRepositoryRuoloPermesso;
import it.eng.tz.urbamid.profilemanager.profile.dao.RuoloDao;
import it.eng.tz.urbamid.profilemanager.profile.dao.RuoloPermessoDao;
import it.eng.tz.urbamid.profilemanager.web.dto.PermessoUtenteDto;
import it.eng.tz.urbamid.profilemanager.web.dto.RuoloDto;
import it.eng.tz.urbamid.profilemanager.web.dto.RuoloUtenteDto;
import it.eng.tz.urbamid.profilemanager.web.dto.converter.PermessoUtenteConverter;
import it.eng.tz.urbamid.profilemanager.web.dto.converter.RuoloConverter;
import it.eng.tz.urbamid.profilemanager.web.dto.converter.RuoloUtenteConverter;

@Service
public class RuoloSvcImpl implements RuoloSvc {
	
	private static final Logger logger = LoggerFactory.getLogger(RuoloSvcImpl.class.getName());
	
	@Autowired
	private JpaRepositoryRuolo jpaRuoloDao;
	
	@Autowired
	private RuoloDao ruoloDao;
	
	@Autowired
	private RuoloPermessoDao ruoloPermessoDao;
	
	@Autowired
	private RuoloConverter ruoloConverter;
	
	@Autowired
	private RuoloUtenteConverter ruoloUtenteConverter;
	
	@Autowired
	private PermessoUtenteConverter permessoUtenteConverter;
	
	@Autowired
	private JpaRepositoryRuoloPermesso jpaRuoloPermesso;
	
	@Override
	@Transactional(transactionManager = "jpaTxMgr", rollbackFor = {ProfileManagerDbException.class}, readOnly = false)
	public RuoloDto salvaRuolo( RuoloDto ruoloDto ) throws ProfileManagerDbException {
		
		RuoloDto result = null;
		try {
			 
			 Ruolo model = ruoloDao.selectByCodice(ruoloDto);
			 if (model!=null)
				 ruoloDao.updateRuolo(ruoloDto);
			 else {
				 ruoloDao.insert(ruoloDto);
			 }
			 Ruolo ruolo = ruoloDao.selectByCodice(ruoloDto);
			 result = ruoloConverter.toDto(ruolo);
		} catch (Exception e) {
			
			logger.error("Errore nel salvataggio del ruolo {}", e.getMessage(), e);
			throw new ProfileManagerDbException(e);
		}
		return result;
	}
	
	
	@PostConstruct
	public void init() {
		if( logger.isInfoEnabled() ) {
			logger.info("Ciao {}", ruoloDao);
		}
	}
	
	
	
	@Override
	@Transactional(transactionManager = "jpaTxMgr", readOnly = true)
	public List<RuoloUtenteDto> getRuoli() throws ProfileManagerDbException {
		try {

			List<RuoloUtenteDto> ruoliDto = new ArrayList<RuoloUtenteDto>();
			List<Ruolo> ruoli = jpaRuoloDao.findAll();
			if(ruoli!=null && !ruoli.isEmpty()) {
				for (Ruolo ruolo:ruoli) {
					RuoloUtenteDto ruoloUtente = ruoloUtenteConverter.toDto(ruolo);
					List<PermessoUtenteDto> permessiUtente = new ArrayList<PermessoUtenteDto>();
					for (Permesso permesso:ruolo.getPermessi()) {
						permessiUtente.add( permessoUtenteConverter.toDto(permesso) );
					}
					ruoloUtente.setListaPermessi( permessiUtente );
					ruoliDto.add( ruoloUtente );
					
				}
			}

			return ruoliDto;
		} catch (Exception e) {
			
			logger.error("getRuoli -->Errore nel recupero dei ruoli utente {}", e.getMessage(), e);
			throw new ProfileManagerDbException(e);
		}
	}
	
	@Override
	@Transactional(transactionManager = "jpaTxMgr", rollbackFor = {ProfileManagerDbException.class}, readOnly = false)
	public RuoloDto findRuolo(String codice) throws ProfileManagerDbException {
		
		RuoloDto result = null;
		try {
			RuoloDto ruoloDto = new RuoloDto(codice,null,null);
			Ruolo ruolo = ruoloDao.selectByCodice(ruoloDto);
			if (ruolo!=null)
				result = ruoloConverter.toDto(ruolo);
		} catch (Exception e) {
			
			logger.error("Errore nel salvataggio del ruolo {}", e.getMessage(), e);
			throw new ProfileManagerDbException(e);
		}
		return result;
	}
	
	@Override
	@Transactional(transactionManager = "jpaTxMgr", rollbackFor = {ProfileManagerDbException.class}, readOnly = false)
	public void deleteRuolo(RuoloDto ruoloDto) throws ProfileManagerDbException {
		
		try {
			 
			Ruolo model = ruoloDao.selectByCodice(ruoloDto);
			 if (model!=null) {
				 ruoloPermessoDao.delete(model.getId());
				 jpaRuoloDao.delete(model);
			 }
		} catch (Exception e) {
			
			logger.error("Errore nella cancellazione dell'utente {}", e.getMessage(), e);
			throw new ProfileManagerDbException(e);
		}
	}
}
