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
import it.eng.tz.urbamid.toponomastica.filter.PercorsoFilter;
import it.eng.tz.urbamid.toponomastica.service.PercorsoService;
import it.eng.tz.urbamid.toponomastica.web.dto.PagedResult;
import it.eng.tz.urbamid.toponomastica.web.dto.PercorsoDTO;
import it.eng.tz.urbamid.toponomastica.web.response.ResponseData;

@RestController
@RequestMapping("/toponomastica/percorso")
@Api(value = "urbamid toponomastica", tags= {"Percorso"})
public class PercorsoController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(CippoChilometricoController.class.getName());

	public static final String SUCCESS = "Success";
	public static final String ERROR = "Error";
	
	@Autowired
	private PercorsoService service;
	
	/**
	 * Metodo che recupera in maniera paginata la tabella u_topo_percorso passandogli {@link filter} come input
	 * @param request
	 * @param filter, filtri di ricerca
	 * @return
	 * @throws Exception
	 */
	@ApiOperation("Il metodo si occupa del recupero dei percorsi con i filtri di ricerca")
	@RequestMapping(value = "/getPercorso", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData getPercorso(HttpServletRequest request, 
			@ApiParam(value = "Filtri di ricerca", required = true)
			@RequestBody(required = true) PercorsoFilter filter) throws Exception {
		
		logger.debug("POST getPercorso");
		ResponseData response = null;
		
		try {
			
			PagedResult<PercorsoDTO> listaCippoChilometrico = service.getPercorso(filter);
			
			
			response = new ResponseData(true, listaCippoChilometrico.getContent(), (int) listaCippoChilometrico.getTotalElements(), (int) listaCippoChilometrico.getTotalElements(), null);
			response.setMessage(SUCCESS);
			
		} catch (ToponomasticaServiceException e) {

			logger.error("ERRORE getPercorso: ", e);
			
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
		
		}
		
		return response;
		
	}
	
	/**
	 * Il metodo effettua l'insert o l'update della tabella u_topo_percorso passandogli l'oggetto {@link percorso}
	 * @param request
	 * @param percorso, l'oggetto che incapsula i dati neccessari 
	 * @return
	 * @throws Exception
	 */
	@ApiOperation("Il metodo si occupa di creare o modificare il percorso")
	@RequestMapping(value = "/insertOrUpdate", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData insertOrUpdate(HttpServletRequest request, 
			@ApiParam(value = "Percorso", required = true)
			@RequestBody(required = true) PercorsoDTO percorso) throws Exception {
		
		logger.debug("POST insertOrUpdate");
		ResponseData response = null;
		
		try {
			
			if(percorso != null) {
				
				PercorsoDTO result = service.insertOrUpdate(percorso);
				response = new ResponseData(true, result, 1, 1, null);
				response.setMessage(SUCCESS);
				
			} else {
				
				logger.error("ERRORE insertOrUpdate");
				response = new ResponseData(false, null, 0, 0, "ERRORE insertOrUpdate");
				response.setMessage(ERROR);
				
			}
			
		} catch (ToponomasticaServiceException e) {
			
			logger.error("ERRORE insertOrUpdate: ", e);
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
			
		}
		
		return response;
		
	}
	
	/**
	 * Metodo che elimina il percorso dalla tabella u_topo_percorso passandogli {@link id} 
	 * @param request
	 * @param id, identificativo del percorso
	 * @return
	 * @throws Exception 
	 */
	@ApiOperation("Il metodo si occupa dell'eliminazione del percorso")
	@RequestMapping(value = "/eliminaPercorso", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData delete(HttpServletRequest request, 
			@ApiParam(value = "Identificato del percorso", required = true)
			@RequestParam(name = "id", required = true) Long id) throws Exception {
		
		logger.debug("POST eliminaPercorso");
		ResponseData response = null;
		
		try {
			
			if(id != null) {
				
				service.deleteById(id);
				response = new ResponseData(true, null);
				response.setMessage(SUCCESS);
				
			} else {
				
				logger.error("ERRORE eliminaPercorso");
				response = new ResponseData(false, null, 0, 0, "ERRORE eliminaPercorso");
				response.setMessage(ERROR);
				
			}
			
		} catch (ToponomasticaServiceException e) {
			
			logger.error("ERRORE eliminaPercorso: ", e);
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
			
		}
		
		return response;
		
	}
	
}
