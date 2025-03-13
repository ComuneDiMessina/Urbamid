package it.eng.tz.urbamid.web.pageController;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import it.eng.tz.urbamid.security.model.PermessoUtenteDto;
import it.eng.tz.urbamid.security.model.ProfiloUtenteDto;
import it.eng.tz.urbamid.security.service.ProfileManagerService;
import it.eng.tz.urbamid.web.dao.bean.search.ResponseData;
import it.eng.tz.urbamid.web.dto.RuoloDto;
import it.eng.tz.urbamid.web.dto.RuoloPermessoDto;
import it.eng.tz.urbamid.web.dto.RuoloUtenteDto;
import it.eng.tz.urbamid.web.dto.RuoloUtenteShrDto;
import it.eng.tz.urbamid.web.dto.UtenteDto;

/**
 * @author Alessandro Paolillo
 */
@Controller(value = "ProfilatoreController")
@RequestMapping(value = "/profileManagerController", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class ProfileManagerCtrl extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(ProfileManagerCtrl.class.getName());
	
	@Autowired
	private ProfileManagerService profileManagerService;
	
	@GetMapping("/index")
	public ModelAndView loadPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView("index");
	}
	
	/**
	 * Recupero degli utenti censiti in UrbamidWeb
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/utente", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData getUtente(HttpServletRequest request,
			@RequestParam(value="id", required=false) Long id) {

		String idLog = "getUtente";
		logger.info("Start {}", idLog);
		List<String> authorities = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) 
        {
        	authorities = new ArrayList<String>();
        	for (GrantedAuthority auth : authentication.getAuthorities()) {
        		authorities.add(auth.getAuthority());
            }
        }

		ResponseData response = null;
		ProfiloUtenteDto pu = null;
		try {
			List<ProfiloUtenteDto> res = profileManagerService.getUtenti();
			for (ProfiloUtenteDto u :res) {
				if (u.getId()==id) {
					pu = u;
					break;
				}
			}
			
			if (pu!=null) {
				
				response = new ResponseData(true, pu, 1, 1, null);
				response.setMessage("success");
			} else {
				
				response = new ResponseData(false, null,0, 0, null);
				response.setMessage("error");
			}
		} catch (Exception e) {
			logger.error("Error in getUtente", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}
	
	/**
	 * Recupero degli utenti censiti in UrbamidWeb
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/utenti", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData getUtenti(HttpServletRequest request) {

		String idLog = "getUtenti";
		logger.info("Start {}", idLog);
		List<String> authorities = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) 
        {
        	authorities = new ArrayList<String>();
        	for (GrantedAuthority auth : authentication.getAuthorities()) {
        		authorities.add(auth.getAuthority());
            }
        }

		ResponseData response = null;
		try {
			List<ProfiloUtenteDto> res = profileManagerService.getUtenti();
			response = new ResponseData(true, res, res.size(), res.size(), null);
			response.setMessage("success");
		} catch (Exception e) {
			logger.error("Error in getUtenti", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}
	
	/**
	 * Salvataggio utente in UrbamidWeb
	 * @param request
	 * @param username
	 * @param codiceFiscale
	 * @param nome
	 * @param cognome
	 * @param note
	 * @param email
	 * @param abilitato
	 * @param ruolo
	 * @return
	 */
	@PostMapping(value = "/salvaUtente", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData salvaUtente (HttpServletRequest request,
			@RequestParam(value="username", required=false) String username,
			@RequestParam(value="codiceFiscale", required=false) String codiceFiscale,
			@RequestParam(value="nome", required=false) String nome,
			@RequestParam(value="cognome", required=false) String cognome,
			@RequestParam(value="note", required=false) String note,
			@RequestParam(value="email", required=false) String email,
			@RequestParam(value="abilitato", required=false) Boolean abilitato,
			
			@RequestParam(value="ruolo", required=false) String ruolo) {
		
		ResponseData response = null;
		try{
			UtenteDto utenteDto = new UtenteDto(username,codiceFiscale, nome, cognome, note, email, abilitato);
			utenteDto = profileManagerService.salvaUtente(utenteDto);
			
			RuoloUtenteShrDto utenteRuoloDto = new RuoloUtenteShrDto(utenteDto.getId(), Long.parseLong(ruolo) );
			profileManagerService.salvaUtenteRuolo(utenteRuoloDto);
			
			response = new ResponseData(true, utenteDto, 1, 1, null);
			response.setMessage("success");
		} catch (Exception e) {
			logger.error("Error in salvaUtente", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}
	
	/**
	 * Eliminazione di un utente in UrbamidWeb
	 * @param request
	 * @param username
	 * @param ruolo
	 * @return
	 */
	@PostMapping(value = "/eliminaUtente", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData eliminaUtente (HttpServletRequest request,
			@RequestParam(value="username", required=false) String username,
			@RequestParam(value="ruolo", required=false) String ruolo) {
		
		ResponseData response = null;
		try{
			UtenteDto utenteDto = new UtenteDto();
			utenteDto = profileManagerService.eliminaUtente(username);
			
			RuoloUtenteShrDto utenteRuoloDto = new RuoloUtenteShrDto(utenteDto.getId(), Long.parseLong(ruolo) );
			profileManagerService.eliminaUtenteRuolo(utenteRuoloDto);
			
			response = new ResponseData(true, utenteDto, 1, 1, null);
			response.setMessage("success");
		} catch (Exception e) {
			logger.error("Error in salvaUtente", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}
	
	/**
	 * Eliminazione di un utente in UrbamidWeb
	 * @param request
	 * @param username
	 * @param ruolo
	 * @return
	 */
	@PostMapping(value = "/eliminaUtenteById", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData eliminaUtenteById (HttpServletRequest request,
			@RequestParam(value="id", required=false) String id,
			@RequestParam(value="ruolo", required=false) String ruolo) {
		
		ResponseData response = null;
		try{
			UtenteDto utenteDto = new UtenteDto();
			utenteDto = profileManagerService.eliminaUtenteById(id);
			
			RuoloUtenteShrDto utenteRuoloDto = new RuoloUtenteShrDto(utenteDto.getId(), Long.parseLong(ruolo) );
			profileManagerService.eliminaUtenteRuolo(utenteRuoloDto);
			
			response = new ResponseData(true, utenteDto, 1, 1, null);
			response.setMessage("success");
		} catch (Exception e) {
			logger.error("Error in salvaUtente", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}
	
	/**
	 * Recupero dei permessi censiti su UrbamidWeb
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/permessi", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData getPermessi(HttpServletRequest request) {

		String idLog = "getPermessi";
		logger.info("Start {}", idLog);
		List<String> authorities = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) 
        {
        	authorities = new ArrayList<String>();
        	for (GrantedAuthority auth : authentication.getAuthorities()) {
        		authorities.add(auth.getAuthority());
            }
        }

		ResponseData response = null;
		try {
			List<PermessoUtenteDto> res = profileManagerService.getPermessi();
			response = new ResponseData(true, res, 1, 1, null);
			response.setMessage("success");
		} catch (Exception e) {
			logger.error("Error in getPermessi", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}
	
	/**
	 * Recupero dei ruoli censiti su UrbamidWeb
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/ruoli", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData getRuoli(HttpServletRequest request) {

		String idLog = "getRuoli";
		logger.info("Start {}", idLog);
		List<String> authorities = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) 
        {
        	authorities = new ArrayList<String>();
        	for (GrantedAuthority auth : authentication.getAuthorities()) {
        		authorities.add(auth.getAuthority());
            }
        }

		ResponseData response = null;
		try {
			List<RuoloUtenteDto> res = profileManagerService.getRuoli();
			response = new ResponseData(true, res, 1, 1, null);
			response.setMessage("success");
		} catch (Exception e) {
			logger.error("Error in getRuoli", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}
	
	/**
	 * Salvataggio di associazione ruolo e permesso
	 * @param request
	 * @param id
	 * @param idRuolo
	 * @param idPermesso
	 * @return
	 */
	@RequestMapping(value="/salvaRuoloPermesso", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData salvaRuoloPermesso(HttpServletRequest request,
				@RequestParam(value="id", required=false) String id,
				@RequestParam(value="idRuolo", required=false) String idRuolo,
				@RequestParam(value="idPermesso", required=false) String idPermesso) {
		
		String idLog = "salvaRuoloPermesso";
		logger.info("Start {}", idLog);

		ResponseData response = null;
		
		try {
			
			RuoloPermessoDto res = profileManagerService.salvaRuoloPermesso(idRuolo,idPermesso);
			response = new ResponseData(true, res, 1, 1, null);
			response.setMessage("success");
		} catch (Exception e) {
			
			logger.error("Error in salvaRuoloPermesso", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}
	
	/**
	 * Eliminazione associazione ruolo e permesso
	 * @param request
	 * @param idRuolo
	 * @param idPermesso
	 * @return
	 */
	@RequestMapping(value="/deleteRuoloPermesso", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData deleteRuoloPermesso(HttpServletRequest request, 
			@RequestParam(name = "idRuolo") String idRuolo,
			@RequestParam(name = "idPermesso") String idPermesso) {

		String idLog = "deleteRuoloPermesso";
		logger.info("Start {}", idLog);
		List<String> authorities = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) 
        {
        	authorities = new ArrayList<String>();
        	for (GrantedAuthority auth : authentication.getAuthorities()) {
        		authorities.add(auth.getAuthority());
            }
        }

		ResponseData response = null;
		try {
			Boolean res = profileManagerService.deleteRuoloPermesso(idRuolo, idPermesso);
			response = new ResponseData(true, res, 1, 1, null);
			response.setMessage("success");
		} catch (Exception e) {
			logger.error("Error in deleteRuoloPermesso", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}
	
	/**
	 * Salvataggio di un ruolo in UrbamidWeb
	 * @param request
	 * @param codice
	 * @param denominazione
	 * @param descrizione
	 * @return
	 */
	@RequestMapping(value="/salvaRuolo", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData salvaRuolo(HttpServletRequest request,
				@RequestParam(value="codice", required=false) String codice,
				@RequestParam(value="denominazione", required=false) String denominazione,
				@RequestParam(value="descrizione", required=false) String descrizione) {
		
		String idLog = "salvaRuolo";
		logger.info("Start {}", idLog);

		ResponseData response = null;
		
		try {
			RuoloDto ruoloDto = new RuoloDto(codice,denominazione, descrizione );
			ruoloDto = profileManagerService.salvaRuolo(ruoloDto);
			
			response = new ResponseData(true, ruoloDto, 1, 1, null);
			response.setMessage("success");
		} catch (Exception e) {
			
			logger.error("Error in salvaRuoloPermesso", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}
	
	/**
	 * Eliminazione di un Ruolo e delle relative associazioni con Permessi.
	 * Se il Ruolo è associato ad un utente si blocca l'eliminazione.
	 * @param request
	 * @param codice
	 * @return
	 */
	@RequestMapping(value="/deleteRuolo", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData deleteRuolo(HttpServletRequest request, 
			@RequestParam(name = "codice") String codice) {

		String idLog = "deleteRuolo";
		logger.info("Start {}", idLog);
		ResponseData response = null;
		try {
			RuoloDto res = profileManagerService.deleteRuolo(codice);
			response = new ResponseData(true, res, 1, 1, null);
			response.setMessage("success");
		} catch (Exception e) {
			logger.error("Error in deleteRuoloPermesso", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage("error");
		}
		return response;
	}
}
