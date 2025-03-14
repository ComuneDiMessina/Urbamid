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
import it.eng.tz.urbamid.toponomastica.filter.GiunzioneFilter;
import it.eng.tz.urbamid.toponomastica.persistence.model.ToponimoStradale;
import it.eng.tz.urbamid.toponomastica.service.GiunzioneStradaleService;
import it.eng.tz.urbamid.toponomastica.web.dto.GiunzioneStradaleDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.PagedResult;
import it.eng.tz.urbamid.toponomastica.web.dto.ToponimoStradaleDTO;
import it.eng.tz.urbamid.toponomastica.web.response.ResponseData;

@RestController
@RequestMapping("toponomastica/giunzioneStradale")
@Api(value = "urbamid toponomastica", tags={"GiunzioneStradale"})
public class GiunzioneStradaleController extends AbstractController{

	private static final Logger logger = LoggerFactory.getLogger(GiunzioneStradaleController.class.getName());

	public static final String SUCCESS = "Success";
	public static final String ERROR = "Error";
	
	@Autowired
	private GiunzioneStradaleService service;
	
	/**
	 * Il metodo recupera in maniera paginata la tabella u_topo_giunzione_stradale passandogli i filtri di ricerca come input
	 * @param request
	 * @param filter, filtri di ricerca
	 * @return
	 * @throws Exception
	 */
	@ApiOperation("Il metodo si occupa del recupero delle giunzioni stradali con i filtri di ricerca")
	@RequestMapping(value = "/getGiunzioni", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData getGiunzioniByFilter(HttpServletRequest request,
			@ApiParam(value = "Filtri di ricerca", required = true)
			@RequestBody(required = true) GiunzioneFilter filter) throws Exception {
				
		logger.debug("POST getGiunzioniByFilter");
		ResponseData response = null;
		
		try {
			
			PagedResult<GiunzioneStradaleDTO> result = service.getGiunzioneByFilter(filter);
			
			response = new ResponseData(true, result.getContent(), (int) result.getTotalElements(), (int) result.getTotalElements(), null);
			response.setMessage(SUCCESS);
			
		} catch (Exception e) {
			
			logger.error("Errore getGiunzioniByFilter: ", e);
			
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
			
		}
		
		return response;
	}
	
	/**
	 * Il metodo effettua l'insert o l'update della tabella u_topo_giunzione_stradale passandogli l'oggetto {@link toponimo}
	 * @param request
	 * @param toponimo, l'oggetto che incapsula i dati neccessari 
	 * @return
	 * @throws Exception
	 */
	@ApiOperation("Il metodo si occupa di creare o modificare una giunzione stradale")
	@RequestMapping(value = "/insertOrUpdate", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData insertOrUpdate(HttpServletRequest request,
			@ApiParam(value = "Giunzione stradale", required = true)
			@RequestBody(required = true) GiunzioneStradaleDTO giunzione) throws Exception {
				
		logger.debug("POST insertOrUpdate");
		ResponseData response = null;
		
		try {
			
			if(giunzione != null) {
				
				GiunzioneStradaleDTO result = service.insertOrUpdate(giunzione);
				response = new ResponseData(true, result, 1, 1, null);
				response.setMessage(SUCCESS);
				
			} else {
				
				logger.error("Error in insertOrUpdate");
				response = new ResponseData(false, null, 0, 0, "Error in insertOrUpdate");
				response.setMessage(ERROR);
				
			}
			
		} catch (Exception e) {
			
			logger.error("Error in insertOrUpdate", e);
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
			
		}
		
		return response;
			
	}
	
	/**
	 * Il metodo elimina dalla tabella u_topo_giunzione_stradale passandogli {@link id}
	 * @param request
	 * @param id, identificato della giunzione stradale
	 * @return
	 * @throws Exception
	 */
	@ApiOperation("Il metodo si occupa dell'eliminazione di una giunzione stradale")
	@RequestMapping(value = "/eliminaGiunzione", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData eliminaGiunzione(HttpServletRequest request,
			@ApiParam(value = "Indentificativo della giunzione stradale", required = true)
			@RequestParam(name = "id", required = true) Long id) {
		
		logger.debug("eliminaGiunzione");
		ResponseData response = null;
		
		try {
			
			if(id != null) {
				
				service.deleteGiunzioneById(id);
				response = new ResponseData(true, null);
				response.setMessage(SUCCESS);
			} else {

				logger.error("Errore in eliminaGiunzione");
				response = new ResponseData(true, null, 0, 0, "Errore in eliminaGiunzione");
				response.setMessage(ERROR);
			}
			
		} catch (Exception e) {
			
			logger.error("Errore in eliminaGiunzione", e);
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
		}
		
		return response;
		
	}
	
	/**
	 * Il metodo intercetta una lista di oggetti {@link ToponimoStradale} in base alla geometria passata in input
	 * @param request
	 * @param geom
	 * @return
	 * @throws Exception
	 */
	@ApiOperation("Il metodo si occupa di intercettare i toponimi stradali in base alla geometria")
	@RequestMapping(value = "/findIntersections", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData getGiunzioniByFilter(HttpServletRequest request, 
			@ApiParam(value = "Geometria", required = true)
			@RequestBody(required = true) String geom) throws Exception {
				
		logger.debug("POST findIntersections");
		ResponseData response = null;
		
		try {
			
			List<ToponimoStradaleDTO> result = service.findIntersections(geom);
			
			response = new ResponseData(true, result, result.size(), result.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			
			logger.error("Errore findIntersections: ", e);
			
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
		}
		
		return response;
	}
	
}
