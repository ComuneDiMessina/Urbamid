package it.eng.tz.urbamid.profilemanager.service;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.urbamid.profilemanager.exception.ProfileManagerDbException;
import it.eng.tz.urbamid.profilemanager.persistence.model.RuoloUtente;
import it.eng.tz.urbamid.profilemanager.persistence.repositories.JpaRepositoryRuoloUtente;
import it.eng.tz.urbamid.profilemanager.profile.dao.RuoloUtenteDao;
import it.eng.tz.urbamid.profilemanager.web.dto.RuoloUtenteShtDto;

@Service
public class RuoloUtenteSvcImpl implements RuoloUtenteSvc {
	
	private static final Logger logger = LoggerFactory.getLogger(RuoloUtenteSvcImpl.class.getName());
	
	@Autowired
	private JpaRepositoryRuoloUtente jpaRuoloUtenteDao;
	
	@Autowired
	private RuoloUtenteDao ruoloUtenteDao;
	
	@PostConstruct
	public void init() {
		if( logger.isInfoEnabled() ) {
			logger.info("Ciao {}", ruoloUtenteDao);
		}
	}
	
	@Override
	@Transactional(transactionManager = "jpaTxMgr", readOnly = true)
	public void updateRuoloUtente(RuoloUtenteShtDto ruoloUtente) throws ProfileManagerDbException {
		try {
			
			RuoloUtente model = ruoloUtenteDao.select(ruoloUtente.getIdUtente());
			if (model!=null) {
				ruoloUtenteDao.update(ruoloUtente.getIdRuolo(), ruoloUtente.getIdUtente());
			} else {
				this.salvaUtenteRuolo(ruoloUtente.getId(),ruoloUtente.getIdUtente(),ruoloUtente.getIdRuolo(),ruoloUtente.getCodRuolo());
			}
		} catch (Exception e) {
			
			logger.error("Errore nel salvataggio dell'utente {}", e.getMessage(), e);
			throw new ProfileManagerDbException(e);
		}
	}
	
	@Override
	@Transactional(transactionManager = "jpaTxMgr", rollbackFor = {ProfileManagerDbException.class}, readOnly = false)
	public RuoloUtente salvaUtenteRuolo(RuoloUtenteShtDto utenteRuoloDto) throws ProfileManagerDbException {
		
		RuoloUtente ruoloUtente = null;
		try {
			 RuoloUtente model = null; 
			 if (utenteRuoloDto.getCodRuolo()!=null) {
				 
				 ruoloUtenteDao.insert(utenteRuoloDto.getIdUtente(),utenteRuoloDto.getIdRuolo());
				 ruoloUtente = ruoloUtenteDao.selectByCodice(utenteRuoloDto.getCodRuolo());
			 } else {
				 
				 model = ruoloUtenteDao.select(utenteRuoloDto.getIdUtente());
				 if (model!=null) {
					 ruoloUtenteDao.update(utenteRuoloDto.getIdRuolo(),utenteRuoloDto.getIdUtente());
				 } else {
					 Long idRuolo = new Long(utenteRuoloDto.getIdRuolo());
					 if ( idRuolo!=null) {
						 ruoloUtenteDao.insert(utenteRuoloDto.getIdUtente(),utenteRuoloDto.getIdRuolo());
					 } else {
						 ruoloUtenteDao.insertCittadino(utenteRuoloDto.getIdUtente());
					 }
					 
				 }
				 ruoloUtente = ruoloUtenteDao.select(utenteRuoloDto.getIdUtente());
			 }
		} catch (Exception e) {
			
			logger.error("Errore nel salvataggio del ruolo dell'utente {}", e.getMessage(), e);
			throw new ProfileManagerDbException(e);
		}
		return ruoloUtente;
	}
	
	@Override
	@Transactional(transactionManager = "jpaTxMgr", rollbackFor = {ProfileManagerDbException.class}, readOnly = false)
	public RuoloUtente salvaUtenteRuolo(Long id,Long idUtente, Long idRuolo, String codRuolo) throws ProfileManagerDbException {
		
		RuoloUtente ruoloUtente = null;
		try {
			 RuoloUtente model = null; 
			 if (codRuolo!=null) {
				 
				 ruoloUtenteDao.insert(idUtente, idRuolo);
				 ruoloUtente = ruoloUtenteDao.selectByCodice(codRuolo);
			 } else {
				 
				 model = ruoloUtenteDao.select(idUtente);
				 if (model!=null) {
					 ruoloUtenteDao.update(idRuolo, idUtente);
				 } else {
					 if ( idRuolo!=null) {
						 ruoloUtenteDao.insert(idUtente, idRuolo);
					 } else {
						 ruoloUtenteDao.insertCittadino(idUtente);
					 }
				 }
				 ruoloUtente = ruoloUtenteDao.select(idUtente);
			 }
		} catch (Exception e) {
			
			logger.error("Errore nel salvataggio del ruolo dell'utente {}", e.getMessage(), e);
			throw new ProfileManagerDbException(e);
		}
		return ruoloUtente;
	}
	
	@Override
	@Transactional(transactionManager = "jpaTxMgr", readOnly = true)
	public void deleteRuoloUtenteById(Long id) throws ProfileManagerDbException {
		try {
			jpaRuoloUtenteDao.deleteById(id);
		} catch (Exception e) {
			
			logger.error("Errore nel salvataggio dell'utente {}", e.getMessage(), e);
			throw new ProfileManagerDbException(e);
		}
	}
}
