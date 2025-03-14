package it.eng.tz.urbamid.profilemanager.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import it.eng.tz.urbamid.profilemanager.exception.ProfileManagerDbException;
import it.eng.tz.urbamid.profilemanager.persistence.model.Permesso;
import it.eng.tz.urbamid.profilemanager.persistence.repositories.JpaRepositoryPermesso;
import it.eng.tz.urbamid.profilemanager.web.dto.PermessoUtenteDto;
import it.eng.tz.urbamid.profilemanager.web.dto.converter.PermessoUtenteConverter;

@Service
public class PermessoSvcImpl implements PermessoSvc {
	
	private static final Logger logger = LoggerFactory.getLogger(PermessoSvcImpl.class.getName());
	
	@Autowired
	private JpaRepositoryPermesso permessoDao;
	
	@Autowired
	private PermessoUtenteConverter permessoUtenteConverter;
	
	@Override
	@Transactional(transactionManager = "jpaTxMgr", rollbackFor = {ProfileManagerDbException.class}, readOnly = false)
	public void salvaPermesso(PermessoUtenteDto permesso) throws ProfileManagerDbException {
		try {
			// FIXME:creare Permesso da PermessoUtenteDto 
			Permesso model = null;
			permessoDao.save(model);
		} catch (Exception e) {
			
			logger.error("Errore nel salvataggio dell'utente {}", e.getMessage(), e);
			throw new ProfileManagerDbException(e);
		}
	}
	@PostConstruct
	public void init() {
		if( logger.isInfoEnabled() ) {
			logger.info("Ciao");
		}
	}
	
	
	
	@Override
	@Transactional(transactionManager = "jpaTxMgr", readOnly = true)
	public PermessoUtenteDto getPermessoUtente(String username) throws ProfileManagerDbException {
		try {

			if(!StringUtils.hasText(username)) return null;
			PermessoUtenteDto permessoDto = null;
			Permesso permesso = null;
			if(permesso!=null) {
				permessoDto = permessoUtenteConverter.toDto(permesso);
			}

			return permessoDto;
		} catch (Exception e) {
			
			logger.error("getProfiloUtente -->Errore nel recupero del profilo utente {}", e.getMessage(), e);
			throw new ProfileManagerDbException(e);
		}
	}
	
	@Override
	@Transactional(transactionManager = "jpaTxMgr", readOnly = true)
	public List<PermessoUtenteDto> getPermessi() throws ProfileManagerDbException {
		try {

			List<PermessoUtenteDto> permessiDto = new ArrayList<PermessoUtenteDto>();
			List<Permesso> permessi = permessoDao.findAllOrderByPadre();
			if(permessi!=null && !permessi.isEmpty()) {
				for (Permesso permesso : permessi) {
					permessiDto.add( permessoUtenteConverter.toDto(permesso) );
				}
			}

			return permessiDto;
		} catch (Exception e) {
			
			logger.error("getPermessi -->Errore nel recupero dei permessi utente {}", e.getMessage(), e);
			throw new ProfileManagerDbException(e);
		}
	}
	
}
