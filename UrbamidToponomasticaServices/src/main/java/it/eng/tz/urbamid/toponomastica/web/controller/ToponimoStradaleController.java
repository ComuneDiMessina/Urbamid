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
import it.eng.tz.urbamid.toponomastica.filter.ToponimoFilter;
import it.eng.tz.urbamid.toponomastica.service.ToponimoStradaleService;
import it.eng.tz.urbamid.toponomastica.web.dto.PagedResult;
import it.eng.tz.urbamid.toponomastica.web.dto.ToponimoDugDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.ToponimoStradaleDTO;
import it.eng.tz.urbamid.toponomastica.web.response.ResponseData;


@RestController
@RequestMapping("/toponomastica/toponimoStradale")
@Api(value = "urbamid toponomastica", tags= {"ToponimoStradale"})
public class ToponimoStradaleController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(ToponimoStradaleController.class.getName());

	public static final String SUCCESS = "Success";
	public static final String ERROR = "Error";
	
	@Autowired
	private ToponimoStradaleService service;
	

	/**
	 * Il metodo recupera in maniera paginata la tabella u_topo_toponimo_stradale passandogli i filtri di ricerca come input
	 * @param request
	 * @param filter, filtri di ricerca
	 * @return
	 * @throws Exception
	 */
	@ApiOperation("Il metodo si occupa del recupero dei toponimi stradali con i filtri di ricerca")
	@RequestMapping(value = "/getToponimo", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData getToponimo(HttpServletRequest request, 
			@ApiParam(value = "Filtri di ricerca", required = true)
			@RequestBody(required = true) ToponimoFilter filter) throws Exception {
		
		logger.debug("getToponimo");
		ResponseData response = null;
		
		try {
						
			PagedResult<ToponimoStradaleDTO> result = service.getToponimoByFilter(filter);
			
			response = new ResponseData(true, result.getContent(), (int) result.getTotalElements(), (int) result.getTotalElements(), null);
			response.setMessage(SUCCESS);
			
		} catch (ToponomasticaServiceException e) {
			
			logger.error("Errore in getToponimo", e);
			
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
			
		}
		
		return response;
		
	}
	
	/**
	 * Il metodo recupera i toponimi stradali figli di un toponimo stradale padre tramite l'id 
	 * @param request
	 * @param idPadre, identificativo di un toponimo stradale padre
	 * @return
	 * @throws Exception
	 */
	@ApiOperation("Il metodo si occupa di recuperare i figli di un toponimo stradale padre")
	@RequestMapping(value = "/getToponimoFigli", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData getToponimoFigli(HttpServletRequest request, 
			@ApiParam(value = "Identificativo del padre", required = true)
			@RequestParam(name="idPadre") Long idPadre) throws Exception {
		
		logger.debug("POST getToponimoFigli");
		ResponseData response = null;
		try {
			List<ToponimoStradaleDTO> toponimiFigliList;
			toponimiFigliList = service.ricercaToponimoByIdPadre(idPadre);
			response = new ResponseData(true, toponimiFigliList, toponimiFigliList.size(), toponimiFigliList.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in getToponimoFigli", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	/**
	 * Il metodo effettua l'insert o l'update della tabella u_topo_toponimo_stradale passandogli l'oggetto {@link toponimo}
	 * @param request
	 * @param toponimo, l'oggetto che incapsula i dati neccessari 
	 * @return
	 * @throws Exception
	 */
	@ApiOperation("Il metodo si occupa di creare o modificare il toponimo stradale")
	@RequestMapping(value = "/insertOrUpdate", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData insertOrUpdate(HttpServletRequest request, 
			@ApiParam(value = "Toponimo stradale", required = true)
			@RequestBody(required = true) ToponimoStradaleDTO toponimo) throws Exception {
		
		logger.debug("POST insertOrUpdate");
		ResponseData response = null;
		try {
			
			if(toponimo != null) {
				
				ToponimoStradaleDTO result = service.insertOrUpdate(toponimo);
				response = new ResponseData(true, result, 1, 1, null);
				response.setMessage(SUCCESS);
				
			} else {
				
				logger.error("Error in insertOrUpdate");
				response = new ResponseData(false, null, 0, 0, "Error in insertOrUpdate");
				response.setMessage(ERROR);
			}
		} catch (ToponomasticaServiceException e) {
			
			logger.error("Error in insertOrUpdate", e);
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
		}
		return response;
	}
	
	/** 
	 * Il metodo effettua un update della tabella u_topo_toponimo_stradale, inserendo lo stato a 'PUBBLICATO', solo se i dati di delibera 
	 * e autorizzazione sono presenti
	 * @param request 
	 * @param id, identificativo del toponimoStradale
	 * @return
	 * @throws Exception 
	 */
	@ApiOperation(value = "Il metodo si occupa della pubblicazione del Toponimo Stradale.")
	@RequestMapping(value = "/pubblicaToponimoStradale", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData pubblicaToponimoStradale(HttpServletRequest request, 
			@ApiParam(value="Identificativo del toponimo stradale", required=true)
			@RequestParam(name = "id", required = true) Long id) throws Exception {
		
		logger.debug("POST pubblicaToponimoStradale");
		ResponseData response = null;
		Integer result = 0;
		try {
			
			if(id != null) {
				
				result = service.pubblicaToponimoStradale(id);
				response = new ResponseData(true, result);
				response.setMessage(SUCCESS);
			} else {
				
				logger.error("Error in pubblicaToponimoStradale");
				response = new ResponseData(false, null, 0, 0, "Error in pubblicaToponimoStradale");
				response.setMessage(ERROR);
			}
		
		} catch (ToponomasticaServiceException e) {
			
			logger.error("Error in pubblicaToponimoStradale", e);
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
		}
		
		return response;
	}
	
	/** 
	 * Il metodo effettua un controllo sui figli di un determinato {@link idPadre}, se quest'ultimi hanno lo stato impostato su 'PUBBLICATO'
	 * @param request 
	 * @param idPadre, identificativo del toponimoStradale padre
	 * @return
	 * @throws Exception 
	 */
	@ApiOperation(value = "Il metodo si occupa di effettuare un controllo dei toponimi figli, se quest'ultimi hanno lo stato impostato su 'PUBBLICATO'.")
	@RequestMapping(value = "/isFigliPubblicati", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData isFigliPubblicati(HttpServletRequest request, 
			@ApiParam(value="Identificativo del toponimo padre", required=true)
			@RequestParam(name = "idPadre", required = true) Long idPadre) throws Exception {
		
		logger.debug("POST isFigliPubblicati");
		ResponseData response = null;
		Boolean result = false;
		try {
			
			if(idPadre != null) {
				
				result = service.isFigliPubblicati(idPadre);
				response = new ResponseData(true, result, 0, 0, null);
				response.setMessage(SUCCESS);
			} else {
				
				logger.error("Error in isFigliPubblicati");
				response = new ResponseData(false, null, 0, 0, "Error in isFigliPubblicati");
				response.setMessage(ERROR);
			}
		
		} catch (ToponomasticaServiceException e) {
			
			logger.error("Error in isFigliPubblicati", e);
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
		}
		
		return response;
	}
	
	/**
	 * Il metodo archivia o elimina dalla tabella u_topo_toponimo_stradale passandogli {@link id} e {@link archivia}
	 * @param request
	 * @param id, identificato del toponimo stradale
	 * @param archivia, se impostato su false viene eseguito un update impostando lo stato su 'ARCHIVIATO', altrimenti
	 * 		  se è impostato su true viene eliminato dalla tabella u_topo_toponimo_stradale
	 * @return
	 * @throws Exception
	 */
	@ApiOperation("Il metodo si occupa dell'archiviazione o dell'eliminazione di un toponimo stradale")
	@RequestMapping(value = "/eliminaToponimo", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData eliminaToponimo(HttpServletRequest request, 
			@ApiParam(value = "Identificativo del toponimo stradale", readOnly = true)
			@RequestParam(name="id", required = true) Long id,
			@ApiParam(value = "Parametro che indica se archiviare o meno", readOnly = true)
			@RequestParam(name="archivia", required = true) Boolean archivia) throws Exception {
	
		logger.debug("eliminaToponimo");
		ResponseData response = null;
		
		try {

			if(id != null && !archivia) {
				
				service.deleteToponimoById(id);
				response = new ResponseData(true, null);
				response.setMessage(SUCCESS);
			} else if(id != null && archivia) {
				
				service.archiviaToponimoById(id);
				response = new ResponseData(true, null);
				response.setMessage(SUCCESS);
			} else {
				
				logger.error("Errore in eliminaToponimo");
				response = new ResponseData(true, null, 0, 0, "Errore in eliminaToponimo");
				response.setMessage(ERROR);
			}
		} catch (ToponomasticaServiceException e) {
			
			logger.error("Errore in eliminaToponimo", e);
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
		}
		return response;
	}
	
	/**
	 * Il metodo ricerca il toponimo stradale dalla tabella u_topo_toponimo_stradale, passandogli come input {@link toponimo}
	 * @param request
	 * @param toponimo, è la denominazione ufficiale del toponimo stradale
	 * @return
	 * @throws Exception 
	 */
	@ApiOperation("Il metodo si occupa di ricercare il toponimo stradale")
	@RequestMapping(value="/toponimoAutocomplete", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData toponimoAutocomplete(HttpServletRequest request, 
			@ApiParam(value = "Denominazione ufficiale del toponimo stradale", required = true)
			@RequestParam(name = "TOPONIMO", required = true) String toponimo) throws Exception {
		
		logger.debug("toponimoAutocomplete");
		ResponseData response = null;
		try {

			List<ToponimoDugDTO> result = service.ricercaToponimoByDug(toponimo);
			response = new ResponseData(true, result, result.size(), result.size(), null);
			response.setMessage(SUCCESS);
		} catch (ToponomasticaServiceException e) {
			
			logger.error("Errore in toponimoAutocomplete", e);
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
		}
		return response;
	}
	
}
