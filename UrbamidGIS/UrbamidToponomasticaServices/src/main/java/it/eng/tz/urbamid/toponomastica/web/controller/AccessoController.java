package it.eng.tz.urbamid.toponomastica.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.eng.tz.urbamid.toponomastica.exception.ToponomasticaServiceException;
import it.eng.tz.urbamid.toponomastica.filter.AccessoFilter;
import it.eng.tz.urbamid.toponomastica.service.AccessoService;
import it.eng.tz.urbamid.toponomastica.web.dto.AccessoDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.PagedResult;
import it.eng.tz.urbamid.toponomastica.web.response.ResponseData;

@RestController
@RequestMapping("/toponomastica/accesso")
@Api(value = "urbamid toponomastica", tags= {"Accesso"})
public class AccessoController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(AccessoController.class.getName());

	public static final String SUCCESS = "Success";
	public static final String ERROR = "Error";
	
	@Autowired
	private AccessoService service;
	
	/**
	 * Il metodo recupera in maniera paginata la tabella u_topo_accesso passandogli {@link filter} come input
	 * @param request
	 * @param filter, filtri di ricerca
	 * @return
	 * @throws Exception
	 */
	@ApiOperation("Il metodo si occupa del recupero degli accessi con i filtri di ricerca")
	@RequestMapping(value = "/getAccesso", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData getAccesso(HttpServletRequest request, 
			@ApiParam(value = "Filtri di ricerca", required = true)
			@RequestBody(required = true) AccessoFilter filter) throws Exception{
		
		logger.debug("POST getAccesso");
		ResponseData response = null;
		
		try {
			
			PagedResult<AccessoDTO> listaCippoChilometrico = service.getAccesso(filter);
			
			response = new ResponseData(true, listaCippoChilometrico.getContent(), (int) listaCippoChilometrico.getTotalElements(), (int) listaCippoChilometrico.getTotalElements(), null);
			response.setMessage(SUCCESS);
		} catch (ToponomasticaServiceException e) {

			logger.error("ERRORE getAccesso: ", e);
			
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
		}
		
		return response;
		
	}
	
	/**
	 * Il metodo effettua l'insert o l'update della tabella u_topo_accesso passandogli l'oggetto {@link accesso}
	 * @param request
	 * @param accesso, l'oggetto che incapsula i dati neccessari 
	 * @return
	 * @throws Exception
	 */
	@ApiOperation("Il metodo si occupa di creare o modificare l'accesso")
	@RequestMapping(value = "/insertOrUpdate", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData insertOrUpdate(HttpServletRequest request, 
			@ApiParam(value = "Accesso", required = true)
			@RequestBody(required = true) AccessoDTO accesso) throws Exception {
		
		logger.debug("POST insertOrUpdate");
		ResponseData response = null;
		
		try {
			
			if(accesso != null) {
				
				AccessoDTO result = service.insertOrUpdate(accesso);
				response = new ResponseData(true, result, 1, 1, null);
				response.setMessage(SUCCESS);
				
			} else {
				
				logger.error("ERRORE insertOrUpdate" );
				response = new ResponseData(false, null, 0, 0, "ERRORE insertOrUpdate");
				response.setMessage(ERROR);
				
			}
			
		
		} catch (ToponomasticaServiceException e) {
			
			logger.error("ERRORE insertOrUpdate ", e);
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
		
		}
		
		return response;
	}
	
	/**
	 * Il metodo elimina l'accesso dalla tabella u_topo_accesso passandogli {@link id} 
	 * @param request
	 * @param id, identificativo dell'accesso
	 * @return
	 * @throws Exception 
	 */
	@ApiOperation("Il metodo si occupa dell'eliminazione dell'accesso")
	@RequestMapping(value = "/eliminaAccesso", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData eliminaToponimo(HttpServletRequest request, 
			@ApiParam(value = "Identificativo dell'accesso", required = true)
			@RequestParam(name="id") Long id) throws Exception {
		
		logger.debug("POST eliminaAccesso");
		ResponseData response = null;
		
		try {

			if(id != null) {
				
				service.deleteAccessoById(id);
				
				response = new ResponseData(true, null);
				response.setMessage(SUCCESS);
				
			} else {
				
				logger.error("ERRORE eliminaAccesso");
				
				response = new ResponseData(false, null, 0, 0, "ERRORE eliminaAccesso");
				response.setMessage(ERROR);
				
			}
			
		} catch (ToponomasticaServiceException e) {
			
			logger.error("ERRORE eliminaAccesso: ", e);
			
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
			
		}
		
		return response;
		
	}
	
	/**
	 * Il metodo elimina l'accesso, collegato al toponimo stradale, dalla tabella u_topo_accesso tramite {@link id} e {@link toponimo} 
	 * @param request
	 * @param id, identificato dell'accesso
	 * @param toponimo, identificativo del toponimo stradale
	 * @return
	 * @throws Exception
	 */
	@ApiOperation("Il metodo si occupa dell'eliminazione dell'accesso collegato al toponimo stradale")
	@RequestMapping(value = "/eliminaAccessoByToponimo", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData eliminaAccessoByToponimo(HttpServletRequest request, 
			@ApiParam(value = "Identificato dell'accesso", required = true)
			@RequestParam(name = "id", required = true) Long id,
			@ApiParam(value = "Identificativo del toponimo stradale", required = true)
			@RequestParam(name = "toponimo", required = true) Long toponimo) throws Exception {
		
		logger.debug("POST eliminaAccessoByToponimo");
		ResponseData response = null;
		
		try {

			if(id != null && toponimo != null) {
				
				service.deleteByToponimo(id, toponimo);
				
				response = new ResponseData(true, null);
				response.setMessage(SUCCESS);
				
			} else {
				
				logger.error("ERRORE eliminaAccessoByToponimo");
				
				response = new ResponseData(false, null, 0, 0, "ERRORE eliminaAccessoByToponimo");
				response.setMessage(ERROR);
				
			}
			
		} catch (ToponomasticaServiceException e) {
			
			logger.error("ERRORE eliminaAccessoByToponimo: ", e);
			
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
			
		}
		
		return response;
		
	}
	
	/**
	 * Il metodo elimina l'accesso, collegato alla località, dalla tabella u_topo_accesso tramite {@link id} e {@link localita} 
	 * @param request
	 * @param id, identificato dell'accesso
	 * @param localita, identificativo della località
	 * @return
	 * @throws Exception
	 */
	@ApiOperation("Il metodo si occupa dell'eliminazione dell'accesso collegato alla località")
	@RequestMapping(value = "/eliminaAccessoByLocalita", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData eliminaAccessoByLocalita(HttpServletRequest request, 
			@ApiParam(value = "Identificativo dell'accesso", required = true)
			@RequestParam(name = "id", required = true) Long id,
			@ApiParam(value = "Identificato della località", required = true)
			@RequestParam(name = "localita", required = true) Long localita) throws Exception {
		
		logger.debug("POST eliminaAccessoByLocalita");
		ResponseData response = null;
		
		try {

			if(id != null && localita != null) {
				
				service.deleteByLocalita(id, localita);
				
				response = new ResponseData(true, null);
				response.setMessage(SUCCESS);
				
			} else {
				
				logger.error("ERRORE eliminaAccessoByToponimo");
				
				response = new ResponseData(false, null, 0, 0, "ERRORE eliminaAccessoByLocalita");
				response.setMessage(ERROR);
				
			}
			
		} catch (ToponomasticaServiceException e) {
			
			logger.error("ERRORE eliminaAccessoByLocalita: ", e);
			
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
			
		}
		
		return response;
		
	}
	
	/**
	 * Il metodo conta gli accessi della tabella u_topo_accessi avendo come parametro {@link toponimo}
	 * @param request
	 * @param toponimo, identificativo del toponimo stradale
	 * @return
	 * @throws Exception 
	 */
	@ApiOperation("Il metodo si occupa di contare gli accessi collegati al toponimo stradale")
	@RequestMapping(value = "/countAccessiByToponimo", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData countAccessiByToponimo(HttpServletRequest request, 
			@ApiParam(value = "Indentificativo del toponimo stradale", required = true)
			@RequestParam(name = "toponimo", required = true) Long toponimo) throws Exception {

		logger.debug("GET countAccessiByToponimo");
		ResponseData response = null;
		
		try {
			
			if(toponimo != null) {
				
				Long result = service.countAccessiByIdToponimo(toponimo);
				
				response = new ResponseData(true, result);
				response.setMessage(SUCCESS);
				
			} else {
				
				logger.error("ERRORE countAccessiByToponimo");
				response = new ResponseData(false, null, 0, 0, "ERRORE countAccessiByToponimo");
				response.setMessage(ERROR);
				
			}
			
		} catch (ToponomasticaServiceException e) {
			
			logger.error("ERRORE countAccessiByToponimo: ", e);
			
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
			
		}
		
		return response;
		
	}
	
	/**
	 * Il metodo conta gli accessi della tabella u_topo_accessi avendo {@link localita}
	 * @param request
	 * @param localita, identificativo della località
	 * @return
	 * @throws Exception 
	 */
	@ApiOperation("Il metodo si occupa di contare gli accessi collegati alla località")
	@RequestMapping(value = "/countAccessiByLocalita", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData countAccessiByLocalita(HttpServletRequest request, 
			@ApiParam(value = "Identificativo della località", required = true)
			@RequestParam(name = "localita", required = true) Long localita) throws Exception {

		logger.debug("GET countAccessiByLocalita");
		ResponseData response = null;
		
		try {
			
			if(localita != null) {
				
				Long result = service.countAccessiByIdLocalita(localita);
				
				response = new ResponseData(true, result);
				response.setMessage(SUCCESS);
				
			} else {
				logger.error("ERRORE countAccessiByLocalita");
				response = new ResponseData(false, null, 0, 0, "ERRORE countAccessiByLocalita");
				response.setMessage(ERROR);
				
			}
			
		} catch (ToponomasticaServiceException e) {
			
			logger.error("ERRORE countAccessiByLocalita: ", e);
			
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
			
		}
		
		return response;
		
	}
	
}
