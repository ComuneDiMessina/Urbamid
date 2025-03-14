package it.eng.tz.urbamid.profilemanager.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import it.eng.tz.urbamid.profilemanager.exception.ProfileManagerDbException;
import it.eng.tz.urbamid.profilemanager.persistence.model.Permesso;
import it.eng.tz.urbamid.profilemanager.persistence.model.Ruolo;
import it.eng.tz.urbamid.profilemanager.persistence.model.RuoloPermesso;
import it.eng.tz.urbamid.profilemanager.persistence.model.Utente;
import it.eng.tz.urbamid.profilemanager.persistence.repositories.JpaRepositoryPermesso;
import it.eng.tz.urbamid.profilemanager.persistence.repositories.JpaRepositoryUtente;
import it.eng.tz.urbamid.profilemanager.profile.dao.RuoloDao;
import it.eng.tz.urbamid.profilemanager.profile.dao.RuoloPermessoDao;
import it.eng.tz.urbamid.profilemanager.profile.dao.RuoloUtenteDao;
import it.eng.tz.urbamid.profilemanager.profile.dao.UtenteDao;
import it.eng.tz.urbamid.profilemanager.web.dto.ProfiloUtenteDto;
import it.eng.tz.urbamid.profilemanager.web.dto.UtenteDto;
import it.eng.tz.urbamid.profilemanager.web.dto.converter.ProfiloUtenteConverter;
import it.eng.tz.urbamid.profilemanager.web.dto.converter.RuoloConverter;
import it.eng.tz.urbamid.profilemanager.web.dto.converter.UtenteConverter;

@Service
public class UtenteSvcImpl implements UtenteSvc {
	
	private static final Logger logger = LoggerFactory.getLogger(UtenteSvcImpl.class.getName());
	
	@Autowired
	private JpaRepositoryUtente jpaUtenteDao;
	
	@Autowired
	private UtenteDao utenteDao;
	
	@Autowired
	private UtenteConverter utenteConverter;
	
	@Autowired
	private ProfiloUtenteConverter profiloUtenteConverter;

	@Autowired
	private RuoloConverter ruoloConverter;
	
	@Autowired
	private RuoloUtenteDao ruoloUtenteDao;
	
	@Autowired
	private RuoloDao ruoloDao;
	
	@Autowired
	private RuoloPermessoSvc ruoloPermessoSvc;
	
	@Autowired
	private RuoloPermessoDao ruoloPermessoDao;
	
	@Autowired
	private JpaRepositoryPermesso jpaRepositoryPermesso;
	
	@Override
	@Transactional(transactionManager = "jpaTxMgr", rollbackFor = {ProfileManagerDbException.class}, readOnly = false)
	public UtenteDto salvaUtente(UtenteDto utenteDto) throws ProfileManagerDbException {
		
		UtenteDto result = null;
		try {
			 
			 Utente model = jpaUtenteDao.findByUsername(utenteDto.getUsername());
			 if (model!=null)
				 utenteDao.updateUtente(utenteDto);
			 else {
				 utenteDao.insert(utenteDto);
			 }
			 Utente user = jpaUtenteDao.findByUsername(utenteDto.getUsername());
			 result = utenteConverter.toDto(user);
		} catch (Exception e) {
			
			logger.error("Errore nel salvataggio dell'utente {}", e.getMessage(), e);
			throw new ProfileManagerDbException(e);
		}
		return result;
	}
	
	@Override
	@Transactional(transactionManager = "jpaTxMgr", rollbackFor = {ProfileManagerDbException.class}, readOnly = false)
	public void deleteUtente(UtenteDto utenteDto) throws ProfileManagerDbException {
		
		try {
			 
			 Utente model = jpaUtenteDao.findByUsername(utenteDto.getUsername());
			 if (model!=null) {
				 ruoloUtenteDao.delete(model.getId());
				 jpaUtenteDao.delete(model);
			 }
		} catch (Exception e) {
			
			logger.error("Errore nella cancellazione dell'utente {}", e.getMessage(), e);
			throw new ProfileManagerDbException(e);
		}
	}
	
	@Override
	@Transactional(transactionManager = "jpaTxMgr", rollbackFor = {ProfileManagerDbException.class}, readOnly = false)
	public void deleteUtenteById(UtenteDto utenteDto) throws ProfileManagerDbException {
		
		try {
			 
			 Utente model = utenteDao.findById( utenteDto.getId() );
			 if (model!=null) {
				 ruoloUtenteDao.delete(model.getId());
				 jpaUtenteDao.delete(model);
			 }
		} catch (Exception e) {
			
			logger.error("Errore nella cancellazione dell'utente {}", e.getMessage(), e);
			throw new ProfileManagerDbException(e);
		}
	}
	
	@Override
	@Transactional(transactionManager = "jpaTxMgr", rollbackFor = {ProfileManagerDbException.class}, readOnly = false)
	public UtenteDto findUtente(String username) throws ProfileManagerDbException {
		
		UtenteDto result = null;
		try {
			 
			 Utente user = jpaUtenteDao.findByUsername(username);
			 result = utenteConverter.toDto(user);
		} catch (Exception e) {
			
			logger.error("Errore nel recupero dell'utente {}", e.getMessage(), e);
			throw new ProfileManagerDbException(e);
		}
		return result;
	}
	
	@Override
	@Transactional(transactionManager = "jpaTxMgr", rollbackFor = {ProfileManagerDbException.class}, readOnly = false)
	public UtenteDto findUtenteById(String id) throws ProfileManagerDbException {
		
		UtenteDto result = null;
		try {
			 
			 Utente user = utenteDao.findById(Long.parseLong(id));
			 result = utenteConverter.toDto(user);
		} catch (Exception e) {
			
			logger.error("Errore nel recupero dell'utente {}", e.getMessage(), e);
			throw new ProfileManagerDbException(e);
		}
		return result;
	}
	
	@PostConstruct
	public void init() {
		if( logger.isInfoEnabled() ) {
			logger.info("Ciao {}", utenteDao);
		}
	}
	
	
	
	@Override
	@Transactional(transactionManager = "jpaTxMgr", readOnly = true)
	public ProfiloUtenteDto getProfiloUtente(String username) throws ProfileManagerDbException {
		try {

			if(!StringUtils.hasText(username)) return null;
			ProfiloUtenteDto profiloDto = null;
			
			/**Recupero l'utente**/
			Utente utente = jpaUtenteDao.findAbilitatoByUsername(username);
			if(utente!=null) {
				
				/**Recupero ruoli**/
				Ruolo r = ruoloDao.selectByIdUtente( utente.getId() );
				
				if (r!=null) {
					
					/**Recupero permessi legati al ruolo**/
					List<RuoloPermesso> rpList = ruoloPermessoDao.selectByIdRuolo(r.getId());
					if (rpList!=null && !rpList.isEmpty()) {
						
						Set<Ruolo> ruoli = new HashSet<Ruolo>();
						Set<Permesso> permessi = new HashSet<Permesso>();
						for(RuoloPermesso rP : rpList) {
							
							Permesso p = jpaRepositoryPermesso.select(rP.getIdPermesso());
							permessi.add(p);
						}
						r.setPermessi( permessi );
						ruoli.add(r);
						utente.setRuoli( ruoli );
					}
				}
				
				/**Preparo il DTO **/
				profiloDto = profiloUtenteConverter.toDto(utente);
			}

			return profiloDto;
		} catch (Exception e) {
			
			logger.error("getProfiloUtente -->Errore nel recupero del profilo utente {}", e.getMessage(), e);
			throw new ProfileManagerDbException(e);
		}
	}
	
	
	@Override
	@Transactional(transactionManager = "jpaTxMgr", readOnly = true)
	public List<ProfiloUtenteDto> getProfiloUtenti() throws ProfileManagerDbException {
		List<ProfiloUtenteDto> profiloUtenti = new ArrayList<ProfiloUtenteDto>();
		try {

			
			List<Utente> utenti = jpaUtenteDao.findAll();
			if(utenti!=null & !utenti.isEmpty()) {
				for(Utente utente : utenti) {
					profiloUtenti.add( profiloUtenteConverter.toDto(utente) );
				}
			}

			return profiloUtenti;
		} catch (Exception e) {
			
			logger.error("getProfiloUtenti -->Errore nel recupero degli utenti {}", e.getMessage(), e);
			throw new ProfileManagerDbException(e);
		}
	}
}
