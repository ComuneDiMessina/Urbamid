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
import it.eng.tz.urbamid.toponomastica.filter.CippoChilometricoFilter;
import it.eng.tz.urbamid.toponomastica.service.CippoChilometricoService;
import it.eng.tz.urbamid.toponomastica.web.dto.CippoChilometricoDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.PagedResult;
import it.eng.tz.urbamid.toponomastica.web.response.ResponseData;


@RestController
@RequestMapping("/toponomastica/cippoChilometrico")
@Api(value = "urbamid toponomastica", tags= {"CippoChilometrico"})
public class CippoChilometricoController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(CippoChilometricoController.class.getName());

	public static final String SUCCESS = "Success";
	public static final String ERROR = "Error";
	
	@Autowired
	private CippoChilometricoService service;
	
	/**
	 * Metodo che visualizza in maniera paginata la tabella u_topo_cippo_chilometrico passandogli {@link filter} 
	 * @param request
	 * @param filter, filtri di ricerca
	 * @return
	 * @throws Exception
	 */
	@ApiOperation("Il metodo si occupa del recupero dei cippi chilometrici con i filtri di ricerca")
	@RequestMapping(value = "/getCippoChilometrico", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData getCippoChilometrico(HttpServletRequest request, 
			@ApiParam(value = "Filtri ricerca", required = true)
			@RequestBody(required = true) CippoChilometricoFilter filter) throws Exception {
		
		logger.debug("getCippoChilometrico");
		ResponseData response = null;
		
		try {
			
			PagedResult<CippoChilometricoDTO> listaCippoChilometrico = service.getCippo(filter);
			
			response = new ResponseData(true, listaCippoChilometrico.getContent(), (int) listaCippoChilometrico.getTotalElements(), (int) listaCippoChilometrico.getTotalElements(), null);
			response.setMessage(SUCCESS);
		} catch (ToponomasticaServiceException e) {

			logger.error("ERRORE getCippoChilometrico: ", e);
			
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
		}
		
		return response;
		
	}
	
	/**
	 * Il metodo effettua l'insert o l'update della tabella u_topo_cippo_chilometrico passandogli l'oggetto {@link cippo}
	 * @param request
	 * @param cippo, l'oggetto che incapsula i dati neccessari 
	 * @return
	 * @throws Exception
	 */
	@ApiOperation("Il metodo si occupa di creare o modificare il cippo chilometrico")
	@RequestMapping(value = "/insertOrUpdate", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData insertOrUpdate(HttpServletRequest request, 
			@ApiParam(value = "Cippo", required = true)
			@RequestBody(required = true) CippoChilometricoDTO cippo) throws Exception {
		
		logger.debug("POST insertOrUpdate");
		ResponseData response = null;
		
		try {
			
			if(cippo != null) {
				
				service.insertOrUpdate(cippo);
				response = new ResponseData(true, cippo, 1, 1, null);
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
	 * Metodo che elimina l'accesso dalla tabella u_topo_cippo_chilometrico passandogli {@link id} 
	 * @param request
	 * @param id, identificativo del cippo chilometrico
	 * @return
	 * @throws Exception 
	 */
	@ApiOperation("Il metodo si occupa dell'eliminazione del cippo chilometrico")
	@RequestMapping(value = "/eliminaCippo", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData delete(HttpServletRequest request, 
			@ApiParam(value = "Identificativo del cippo cippo chilometrico", required = true)
			@RequestParam(name="id", required = true) Long id) throws Exception {
		
		logger.debug("POST eliminaCippo");
		ResponseData response = null;
		
		try {
			
			if(id != null) {
				
				service.delete(id);
				response = new ResponseData(true, null);
				response.setMessage(SUCCESS);
			} else {
				
				logger.error("ERRORE eliminaCippo");
				response = new ResponseData(false, null);
				response.setMessage(ERROR);
			}
			
		} catch (ToponomasticaServiceException e) {
			
			logger.error("ERRORE eliminaCippo: ", e);
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
		}
		
		return response;
		
	}
	
	/**
	 * Il metodo conta gli accessi della tabella u_topo_cippo_chilometrico avendo come parametro {@link idEstesa}
	 * @param request
	 * @param idEstesa, identificativo dell'estesa amministrativa
	 * @return
	 * @throws Exception 
	 */
	@ApiOperation("Il metodo si occupa di contare i cippi collegati all'estesa amministrativa")
	@RequestMapping(value = "/countCippiByEstesa", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData countCippiByEstesa(HttpServletRequest request, 
			@ApiParam(value = "Indentificativo dell'estesa amministrativa", required = true)
			@RequestParam(name = "idEstesa", required = true) Long idEstesa) throws Exception {

		logger.debug("GET countCippiByEstesa");
		ResponseData response = null;
		
		try {
			
			if(idEstesa != null) {
				
				Long result = service.countCippiByEstesa(idEstesa);
						
				response = new ResponseData(true, result);
				response.setMessage(SUCCESS);
				
			} else {
				
				logger.error("ERRORE countCippiByEstesa");
				response = new ResponseData(false, null, 0, 0, "ERRORE countCippiByEstesa");
				response.setMessage(ERROR);
				
			}
			
		} catch (ToponomasticaServiceException e) {
			
			logger.error("ERRORE countCippiByEstesa: ", e);
			
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
			
		}
		
		return response;
		
	}
	
}
