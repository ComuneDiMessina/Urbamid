package it.eng.tz.urbamid.security.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.eng.tz.urbamid.security.model.BaseResponse;
import it.eng.tz.urbamid.security.model.PermessoUtenteDto;
import it.eng.tz.urbamid.security.model.ProfiloUtenteDto;
import it.eng.tz.urbamid.security.model.ResponseData;
import it.eng.tz.urbamid.web.dto.RuoloDto;
import it.eng.tz.urbamid.web.dto.RuoloPermessoDto;
import it.eng.tz.urbamid.web.dto.RuoloUtenteDto;
import it.eng.tz.urbamid.web.dto.RuoloUtenteShrDto;
import it.eng.tz.urbamid.web.dto.UtenteDto;
import it.eng.tz.urbamid.web.util.IConstants;

@Service
public class ProfileManagerServiceImpl implements ProfileManagerService{

	private static final Logger logger = LoggerFactory.getLogger(ProfileManagerServiceImpl.class.getName());
	
	
	@Autowired
	private Environment env;
	
	@Autowired
	protected RestService restService; 
	
	
	/**  interfacce per la gestione della profilazione  **/
	@Override
	public ProfiloUtenteDto getUtenteByUsername(String username) throws Exception {
		try{
			if(!StringUtils.hasText(username)) return null;
			ProfiloUtenteDto u = this.getByUsername(username);			
			if(u!=null) {
				if (u.getRuoli()!=null && !u.getRuoli().isEmpty()) {			
					u.getRuoli().size();
					for(RuoloUtenteDto g:u.getRuoli()) {
						if(g.getListaPermessi()!=null) {
							g.getListaPermessi().size();						
						}
					}
				}
			}
			logger.debug("getUtenteByUsername --> trovato l'utente ", u.getCodiceFiscale());
			return u;		
		}catch(Exception e){
			logger.error("getUtenteByUsername --> errore nel recupero dell'utente. ", e);
			throw new Exception(e);
		}
	}
	
	
	
	/**TODO 20210417: salvare utente se non presente**/
	private ProfiloUtenteDto getByUsername(String username) throws Exception{
		ProfiloUtenteDto profilo = null;
		String url = env.getProperty(IConstants.WS_PROFILE_MANAGER_ENDPOINT)+ env.getProperty(IConstants.WS_PROFILE_MANAGER_PROFILO_UTENTE);
		logger.error("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"+url);
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", username);
		BaseResponse response = restService.restGetPathParam(url, params);
		if(response!=null && StringUtils.hasText(response.getCodiceEsito()) && response.getCodiceEsito().equals("OK")) {
			profilo = new ObjectMapper().convertValue(response.getPayload(), ProfiloUtenteDto.class);		
		} 
		return profilo;
	}
	
	@Override
	public List<ProfiloUtenteDto> getUtenti() throws Exception{
		List<ProfiloUtenteDto> utenti = null;
		String url = env.getProperty(IConstants.WS_PROFILE_MANAGER_ENDPOINT)+ env.getProperty(IConstants.WS_PROFILE_MANAGER_UTENTI);
		BaseResponse response = restService.restGet(url);
		
		if(response!=null && StringUtils.hasText(response.getCodiceEsito()) && response.getCodiceEsito().equals("OK")) {
			utenti = new ObjectMapper().convertValue(response.getPayload(), new TypeReference<List<ProfiloUtenteDto>>() {});
		} 
		return utenti;
	}
	
	@Override
	public UtenteDto salvaUtente (UtenteDto utente) throws Exception {
		UtenteDto result = null;
		String url = env.getProperty(IConstants.WS_PROFILE_MANAGER_ENDPOINT)+ env.getProperty(IConstants.WS_PROFILE_MANAGER_SALVA_UTENTE);	 
		ResponseData response = restService.restPostTable(url, utente);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<UtenteDto>() {});		
		}
		return result;
	}
	
	@Override
	public RuoloUtenteShrDto salvaUtenteRuolo (RuoloUtenteShrDto ruoloUtente) throws Exception {
		RuoloUtenteShrDto result = null;
		String url = env.getProperty(IConstants.WS_PROFILE_MANAGER_ENDPOINT)+ env.getProperty(IConstants.WS_PROFILE_MANAGER_SALVA_UTENTE_RUOLO);	 
		ResponseData response = restService.restPostTable(url, ruoloUtente);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<RuoloUtenteShrDto>() {});		
		}
		return result;
	}
	
	@Override
	public UtenteDto eliminaUtente (String username) throws Exception {
		UtenteDto result = null;
		String url = env.getProperty(IConstants.WS_PROFILE_MANAGER_ENDPOINT)+ env.getProperty(IConstants.WS_PROFILE_MANAGER_ELIMINA_UTENTE);	 
		ResponseData response = restService.restPostTable(url, username);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<UtenteDto>() {});		
		}
		return result;
	}
	
	@Override
	public UtenteDto eliminaUtenteById (String id) throws Exception {
		UtenteDto result = null;
		String url = env.getProperty(IConstants.WS_PROFILE_MANAGER_ENDPOINT)+ env.getProperty(IConstants.WS_PROFILE_MANAGER_ELIMINA_UTENTE_BY_ID);	 
		ResponseData response = restService.restPostTable(url, id);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<UtenteDto>() {});		
		}
		return result;
	}
	
	@Override
	public RuoloUtenteShrDto eliminaUtenteRuolo (RuoloUtenteShrDto ruoloUtente) throws Exception {
		RuoloUtenteShrDto result = null;
		String url = env.getProperty(IConstants.WS_PROFILE_MANAGER_ENDPOINT)+ env.getProperty(IConstants.WS_PROFILE_MANAGER_ELIMINA_UTENTE_RUOLO);	 
		ResponseData response = restService.restPostTable(url, ruoloUtente);
		if(response!=null && response.isSuccess()) {
			result = ruoloUtente;		
		}
		return result;
	}
	
	@Override
	public List<PermessoUtenteDto> getPermessi() throws Exception {
		List<PermessoUtenteDto> permessi = null;
		String url = env.getProperty(IConstants.WS_PROFILE_MANAGER_ENDPOINT)+ env.getProperty(IConstants.WS_PROFILE_MANAGER_PERMESSI);	 
		BaseResponse<?> response = restService.restGet(url);
		
		if(response!=null && StringUtils.hasText(response.getCodiceEsito()) && response.getCodiceEsito().equals("OK")) {
			permessi = new ObjectMapper().convertValue(response.getPayload(), new TypeReference<List<PermessoUtenteDto>>() {});		
		}
		
		return permessi;
	}
	
	@Override
	public List<RuoloUtenteDto> getRuoli() throws Exception {
		List<RuoloUtenteDto> ruoli = null;
		String url = env.getProperty(IConstants.WS_PROFILE_MANAGER_ENDPOINT)+ env.getProperty(IConstants.WS_PROFILE_MANAGER_RUOLI);	 
		BaseResponse<?> response = restService.restGet(url);
		
		if(response!=null && StringUtils.hasText(response.getCodiceEsito()) && response.getCodiceEsito().equals("OK")) {
			ruoli = new ObjectMapper().convertValue(response.getPayload(), new TypeReference<List<RuoloUtenteDto>>() {});		
		}
		
		return ruoli;
	}
	
	@Override
	public RuoloPermessoDto salvaRuoloPermesso(String idRuolo, String idPermesso) throws Exception {
		RuoloPermessoDto result = null;
		String url = env.getProperty(IConstants.WS_PROFILE_MANAGER_ENDPOINT)+ env.getProperty(IConstants.WS_PROFILE_MANAGER_SALVA_RUOLO_PERMESSO)+"?"+
							"idRuolo="+idRuolo+"&idPermesso="+idPermesso;
		ResponseData response = restService.restPostTable(url, null);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<RuoloPermessoDto>() {});		
		}
		return result;
	}
	
	@Override
	public boolean deleteRuoloPermesso(String idRuolo, String idPermesso) throws Exception {
		Boolean result = false;
		String url = env.getProperty(IConstants.WS_PROFILE_MANAGER_ENDPOINT)+ env.getProperty(IConstants.WS_PROFILE_MANAGER_DELETE_RUOLO_PERMESSO)+"?"+
							"idRuolo="+idRuolo+"&idPermesso="+idPermesso;
		ResponseData response = restService.restPostTable(url, null);
		if(response!=null && response.isSuccess()) {
			result = true;		
		}
		return result;
	}
	
	@Override
	public RuoloDto salvaRuolo(RuoloDto ruolo) throws Exception {
		RuoloDto result = null;
		String url = env.getProperty(IConstants.WS_PROFILE_MANAGER_ENDPOINT)+ env.getProperty(IConstants.WS_PROFILE_MANAGER_SALVA_RUOLO);	 
		ResponseData response = restService.restPostTable(url, ruolo);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<RuoloDto>() {});		
		}
		return result;
	}
	
	@Override
	public RuoloDto deleteRuolo(String codice) throws Exception {
		RuoloDto result = null;
		String url = env.getProperty(IConstants.WS_PROFILE_MANAGER_ENDPOINT)+ env.getProperty(IConstants.WS_PROFILE_MANAGER_DELETE_RUOLO);	 
		ResponseData response = restService.restPostTable(url, codice);
		if(response!=null && response.isSuccess()) {
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<RuoloDto>() {});		
		}
		return result;
	}
}
