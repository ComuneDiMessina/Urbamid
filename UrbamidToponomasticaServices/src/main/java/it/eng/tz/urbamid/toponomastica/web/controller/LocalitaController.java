package it.eng.tz.urbamid.toponomastica.web.controller;

import java.util.List;

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
import it.eng.tz.urbamid.toponomastica.filter.LocalitaFilter;
import it.eng.tz.urbamid.toponomastica.service.LocalitaService;
import it.eng.tz.urbamid.toponomastica.web.dto.LocalitaDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.PagedResult;
import it.eng.tz.urbamid.toponomastica.web.response.ResponseData;

/**
 * @author Luca Tricarico
 */

@RestController
@RequestMapping("/toponomastica/localita")
@Api(value = "urbamid toponomastica", tags= {"Localita"})
public class LocalitaController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(LocalitaController.class.getName());

	public static final String SUCCESS = "Success";
	public static final String ERROR = "Error";
	
	@Autowired
	private LocalitaService localitaService;
	
	/**
	 * Metodo che recupera in maniera paginata la tabella u_topo_localita passandogli {@link filter} come input
	 * @param request
	 * @param filter, filtri di ricerca
	 * @return 
	 * @throws Exception
	 */
	@ApiOperation("Il metodo si occupa del recupero delle località con i filtri di ricerca")
	@RequestMapping(value = "/getLocalita", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData getLocalita(HttpServletRequest request, 
			@ApiParam(value = "Filtri di ricerca", required = true)
			@RequestBody(required = true) LocalitaFilter filter) throws ToponomasticaServiceException {
		
		logger.debug("POST getLocalita");
		ResponseData response = null;
		try {
			
			PagedResult<LocalitaDTO> listaLocalita = localitaService.getLocalita(filter);

			response = new ResponseData(true, listaLocalita.getContent(), (int) listaLocalita.getTotalElements(), (int) listaLocalita.getTotalElements(), null);
			response.setMessage(SUCCESS);
		} catch (ToponomasticaServiceException e) {
			
			logger.error("ERRORE getLocalita: ", e);
			
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
		}
		
		return response;
	}
	
	/**
	 * Il metodo effettua l'insert o l'update della tabella u_topo_localita passandogli l'oggetto {@link localita}
	 * @param request
	 * @param localita, l'oggetto che incapsula i dati neccessari 
	 * @return
	 * @throws Exception
	 */
	@ApiOperation("Il metodo si occupa di creare o modificare la località")
	@RequestMapping(value = "/insertOrUpdate", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData insertOrUpdate(HttpServletRequest request, 
			@ApiParam(value = "Località", required = true)
			@RequestBody(required = true) LocalitaDTO localita) throws Exception {
		
		logger.debug("POST insertOrUpdate");
		ResponseData response = null;
		try {
			
			LocalitaDTO result = localitaService.insertOrUpdate(localita);
			
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (ToponomasticaServiceException e) {
			
			logger.error("ERRORE insertOrUpdate: ", e);
			
			response = new ResponseData(false, null,0, 0, e.getMessage());
			response.setMessage(ERROR);
		}
		
		return response;
	}
	
	
	/**
	 * Metodo che elimina il percorso dalla tabella u_topo_localita passandogli {@link id} 
	 * @param request
	 * @param id, identificativo della località
	 * @return
	 * @throws Exception 
	 */
	@ApiOperation("Il metodo si occupa dell'eliminazione della località")
	@RequestMapping(value = "/eliminaLocalita", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData elimina(HttpServletRequest request, 
			@ApiParam(value = "Identificativo della località", required = true)
			@RequestParam(name = "id", required = true) Long id) throws Exception {
		
		logger.debug("eliminaLocalita");
		ResponseData response = null;
		try {

			localitaService.deleteById(id);
			
			response = new ResponseData(true, null);
			response.setMessage(SUCCESS);
		} catch (ToponomasticaServiceException e) {
			
			logger.error("ERRORE eliminaLocalita: ", e);
			
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
		}
		
		return response;
	}
	
	/**
	 * Metodo che recupera tutte le località
	 * @param request
	 * @return 
	 * @throws Exception
	 */
	@ApiOperation("Il metodo si occupa del recupero delle località")
	@RequestMapping(value = "/findAllLocalita", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData findAllLocalita(HttpServletRequest request) {
		
		logger.debug("POST findAllLocalita");
		ResponseData response = null;
		try {

			List<LocalitaDTO> result = localitaService.findAll();
			
			response = new ResponseData(true, result, result.size(), result.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			
			logger.error("ERRORE findAllLocalita", e);
			
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
		}
		
		return response;
	}
	
}
