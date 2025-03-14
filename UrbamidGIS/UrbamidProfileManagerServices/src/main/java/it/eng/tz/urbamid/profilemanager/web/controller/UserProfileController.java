package it.eng.tz.urbamid.profilemanager.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import it.eng.tz.urbamid.profilemanager.persistence.model.RuoloUtente;
import it.eng.tz.urbamid.profilemanager.web.dto.PermessoUtenteDto;
import it.eng.tz.urbamid.profilemanager.web.dto.ProfiloUtenteDto;
import it.eng.tz.urbamid.profilemanager.web.dto.RuoloDto;
import it.eng.tz.urbamid.profilemanager.web.dto.RuoloPermessoDto;
import it.eng.tz.urbamid.profilemanager.web.dto.RuoloUtenteDto;
import it.eng.tz.urbamid.profilemanager.web.dto.RuoloUtenteShtDto;
import it.eng.tz.urbamid.profilemanager.web.dto.UtenteDto;
import it.eng.tz.urbamid.profilemanager.web.response.BaseResponse;
import it.eng.tz.urbamid.profilemanager.web.response.ResponseData;

@RestController
@RequestMapping("/profilemanager")
public class UserProfileController extends AbstractController{

	private static final Logger logger = LoggerFactory.getLogger(UserProfileController.class.getName());
	
	public static final String SUCCESS = "Success";
	public static final String ERROR = "Error";
	
	@ApiOperation("Recupera le info di un utente dato l'username")
	@GetMapping(path = "/profiloUtente/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponse<ProfiloUtenteDto>> getUserProfilebyUsername(@PathVariable("username") String username
																		   ){
		logger.info("Start  " + getClass().getName() + " getUserProfilebyUsername");
		try {
			ProfiloUtenteDto response = utenteSvc.getProfiloUtente(username);
			return okResponse(response);
		} catch (Exception e) {
			logger.error("UserProfileController :: getUserProfilebyUsername :: ERROR ::  " + e.getMessage(),e);
			return koResponseError(e);
		}
	}
	
	@ApiOperation("Recupera tutti gli utenti")
	@GetMapping(path = "/utenti", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponse<List<ProfiloUtenteDto>>> getUtenti(){
		logger.info("Start  " + getClass().getName() + " getUtenti");
		try {
			List<ProfiloUtenteDto> response = utenteSvc.getProfiloUtenti();
			return okResponse(response);
		} catch (Exception e) {
			logger.error("UserProfileController :: getPermessi :: ERROR ::  " + e.getMessage(),e);
			return koResponseError(e);
		}
	}
	
	@ApiOperation("Salvataggio di un utente")
	@PostMapping("/salvaUtente")
	public @ResponseBody ResponseData salvaUtente(HttpServletRequest request, 
			@RequestBody(required = true) UtenteDto utenteDto) {
		logger.debug("POST salvaUtente");
		ResponseData response = null;
		try {
			
			UtenteDto res = utenteSvc.salvaUtente(utenteDto);
			response = new ResponseData(true, res, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in salvaUtente", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@ApiOperation("Salvataggio associazione ruolo utente")
	@PostMapping("/salvaUtenteRuolo")
	public @ResponseBody ResponseData salvaUtenteRuolo(HttpServletRequest request, 
			@RequestBody(required = true) RuoloUtenteShtDto ruoloUtenteShtDto) {
		logger.debug("POST salvaUtenteRuolo");
		ResponseData response = null;
		try {
			
			RuoloUtente ruoloUtente = ruoloUtenteSvc.salvaUtenteRuolo(ruoloUtenteShtDto.getId(),ruoloUtenteShtDto.getIdUtente(),
					ruoloUtenteShtDto.getIdRuolo(), ruoloUtenteShtDto.getCodRuolo());
			response = new ResponseData(true, ruoloUtente, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in salvaUtenteRuolo", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@ApiOperation("Eliminazione di un utente")
	@PostMapping("/eliminaUtente")
	public @ResponseBody ResponseData eliminaUtente(HttpServletRequest request, 
			@RequestBody(required = true) String username) {
		logger.debug("POST eliminaUtente");
		ResponseData response = null;
		try {
			
			UtenteDto utenteDto = utenteSvc.findUtente(username);
			utenteSvc.deleteUtente(utenteDto);
			response = new ResponseData(true, utenteDto, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in eliminaUtente", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@ApiOperation("Eliminazione di un utente")
	@PostMapping("/eliminaUtenteById")
	public @ResponseBody ResponseData eliminaUtenteById(HttpServletRequest request, 
			@RequestBody(required = true) String id) {
		logger.debug("POST eliminaUtenteById");
		ResponseData response = null;
		try {
			
			UtenteDto utenteDto = utenteSvc.findUtenteById(id);
			utenteSvc.deleteUtenteById(utenteDto);
			response = new ResponseData(true, utenteDto, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in eliminaUtenteById", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@ApiOperation("Elimina associazione di un ruolo ad un utente")
	@PostMapping("/eliminaUtenteRuolo")
	public @ResponseBody ResponseData eliminaUtenteRuolo(HttpServletRequest request, 
			@RequestBody(required = true) RuoloUtenteShtDto ruoloUtenteShtDto) {
		logger.debug("POST eliminaUtenteRuolo");
		ResponseData response = null;
		try {
			
			ruoloUtenteSvc.deleteRuoloUtenteById(ruoloUtenteShtDto.getIdUtente());
			response = new ResponseData(true, null, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in eliminaUtenteRuolo", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@ApiOperation("Recupera tutti i permessi")
	@GetMapping(path = "/permessi", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponse<List<PermessoUtenteDto>>> getPermessi(){
		logger.info("Start  " + getClass().getName() + " getPermessi");
		try {
			List<PermessoUtenteDto> response = permessoSvc.getPermessi();
			return okResponse(response);
		} catch (Exception e) {
			logger.error("UserProfileController :: getPermessi :: ERROR ::  " + e.getMessage(),e);
			return koResponseError(e);
		}
	}
	
	@ApiOperation("Recupera tutti i ruoli")
	@GetMapping(path = "/ruoli", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponse<List<RuoloUtenteDto>>> getRuoli(){
		logger.info("Start  " + getClass().getName() + " getRuoli");
		try {
			List<RuoloUtenteDto> response = ruoloSvc.getRuoli();
			return okResponse(response);
		} catch (Exception e) {
			logger.error("UserProfileController :: getPermessi :: ERROR ::  " + e.getMessage(),e);
			return koResponseError(e);
		}
	}
	
	@ApiOperation("Salva associazione di un ruolo ad un permesso")
	@RequestMapping(value="/salvaRuoloPermesso", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData salvaRuoloPermesso(HttpServletRequest request, 
			@RequestParam(name="idRuolo") String idRuolo,
			@RequestParam(name="idPermesso") String idPermesso) {
		
		logger.info("Start  " + getClass().getName() + " salvaRuoloPermesso");
		ResponseData response = null;
		try {
			RuoloPermessoDto ruoloPermesso = new RuoloPermessoDto( Long.parseLong(idRuolo), Long.parseLong(idPermesso) );
			ruoloPermesso = ruoloPermessoSvc.salvaRuoloPermesso(ruoloPermesso);
			response = new ResponseData(true, ruoloPermesso, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("UserProfileController :: saveRuoloPermesso :: ERROR ::  " + e.getMessage(),e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@ApiOperation("Elimina associazione di un ruolo ad un permesso")
	@RequestMapping(value="/deleteRuoloPermesso", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
	public @ResponseBody ResponseData deleteRuoloPermesso(HttpServletRequest request, 
			@RequestParam(name="idRuolo") String idRuolo,
			@RequestParam(name="idPermesso") String idPermesso) {
		
		logger.info("Start  " + getClass().getName() + " deleteRuoloPermesso");
		ResponseData response = null;
		try {
			RuoloPermessoDto ruoloPermesso = new RuoloPermessoDto( Long.parseLong(idRuolo), Long.parseLong(idPermesso) );
			ruoloPermessoSvc.deleteRuoloPermesso(ruoloPermesso);
			response = new ResponseData(true, ruoloPermesso, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("UserProfileController :: deleteRuoloPermesso :: ERROR ::  " + e.getMessage(),e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	
	@ApiOperation("Salvataggio di un ruolo")
	@PostMapping("/salvaRuolo")
	public @ResponseBody ResponseData salvaRuolo(HttpServletRequest request, 
			@RequestBody(required = true) RuoloDto ruoloDto) {
		logger.debug("POST salvaRuolo");
		ResponseData response = null;
		try {
			
			RuoloDto res = ruoloSvc.salvaRuolo(ruoloDto);
			response = new ResponseData(true, res, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in salvaUtente", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@ApiOperation("Eliminazione di un ruolo")
	@PostMapping("/eliminaRuolo")
	public @ResponseBody ResponseData eliminaRuolo(HttpServletRequest request, 
			@RequestBody(required = true) String codice) {
		logger.debug("POST eliminaRuolo");
		ResponseData response = null;
		try {
			
			RuoloDto ruoloDto = ruoloSvc.findRuolo(codice);
			ruoloSvc.deleteRuolo(ruoloDto);
			response = new ResponseData(true, ruoloDto, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in eliminaRuolo", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
}
