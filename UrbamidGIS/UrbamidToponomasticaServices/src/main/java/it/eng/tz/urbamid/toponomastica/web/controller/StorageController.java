package it.eng.tz.urbamid.toponomastica.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
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
import it.eng.tz.urbamid.toponomastica.filter.DocumentoFilter;
import it.eng.tz.urbamid.toponomastica.service.StorageService;
import it.eng.tz.urbamid.toponomastica.web.dto.DocumentoDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.PagedResult;
import it.eng.tz.urbamid.toponomastica.web.response.ResponseData;

@RestController
@RequestMapping("/toponomastica/storage")
@Api(value = "urbamid toponomastica", tags= {"Storage"})
public class StorageController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(StorageController.class.getName());

	public static final String SUCCESS = "Success";
	public static final String ERROR = "Error";
	
	@Autowired
	private StorageService service;
	
	/**
	 * Il metodo recupera in maniera paginata i file caricati sul server passandogli {@link filter} come input
	 * @param request
	 * @param filter, filtri di ricerca
	 * @return
	 * @throws Exception
	 */
	@ApiOperation("Il metodo si occupa del recupero dei file caricati sul server")
	@RequestMapping(value = "/getStorage", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData getStorage(HttpServletRequest request, 
			@ApiParam(value = "Filtri di ricerca", required = true)
			@RequestBody(required = true) DocumentoFilter filter) {
		
		logger.debug("POST getStorage");
		ResponseData response = null;
		
		try {
						
			PagedResult<DocumentoDTO> lista = service.getDocumenti(filter);
			
			response = new ResponseData(true, lista.getContent(), (int) lista.getTotalElements(), (int) lista.getTotalElements(), null);
			response.setMessage(SUCCESS);
			
		} catch (Exception e) {
			
			logger.error("ERRORE getStorage: ", e);
			
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
			
		}
		
		return response;
	}
	
	/**
	 * Il metodo effettua l'upload dei file passandogli {@link file} come input
	 * @param request
	 * @param file, l'oggetto che incapsula i dati neccessari 
	 * @return
	 * @throws IOException 
	 * @throws Exception
	 */
	@ApiOperation("Il metodo si occupa dell'upload dei file sul server")
	@RequestMapping(value = "/upload", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData upload(HttpServletRequest request, 
			@ApiParam(value = "File", required = true)
			@RequestBody DocumentoDTO file) throws IOException, Exception {
		
		logger.debug("POST upload");
		ResponseData response = null;
		
		try {
		
			service.upload(file);
			
			response = new ResponseData(true, file);
			response.setMessage(SUCCESS);
			
		} catch (ToponomasticaServiceException e) {
			
			logger.error("ERRORE upload: ", e);
			
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
			
		}
		
		return response;
		
	}
	
	/**
	 * Il metodo effettua il download dei file passandogli {@link idRisorsa} e {@link tipoRisorsa}
	 * @param request
	 * @param idRisorsa, è l'indentficativo dell'entità
	 * @param tipoRisorsa, è un codice numerico che indentifica le varie entità:
	 * - 1 TOPONIMO STRADALE
	 * - 2 PERCORSO
	 * - 3 LOCALITA
	 * - 4 GIUNZIONI STRADALI
	 * - 5 ESTESA AMMINISTRATIVA
	 * - 6 AREE E ELEMENTI STRADALI
	 * - 7 CIPPO CHILOMETRICO
	 * - 8 ACCESSO
	 * - 9 SHAPE FILE
	 * @return
	 * @throws Exception
	 */
	@ApiOperation("Il metodo si occupa del download dei file")
	@RequestMapping(value = "/download/{fileName:.+}", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData download(HttpServletRequest request, @PathVariable String fileName, 
			@ApiParam(value = "Identificativo della risorsa", required = true)
			@RequestParam(value = "risorsa", required = true) Long idRisorsa,
			@ApiParam(value = "Identifica l'entità", required = true)
			@RequestParam(value = "tipo", required = true) Long tipoRisorsa) throws Exception {
		
		logger.debug("GET download");
		
		ResponseData response = null;
		
		DocumentoDTO f = null;
		
		try {
						
			f = service.download(idRisorsa, tipoRisorsa, fileName);
			
			response = new ResponseData(true, f, 1, 1, null);
			response.setMessage(SUCCESS);
			
		} catch (ToponomasticaServiceException e) {
			
			logger.error("ERRORE download: ", e);
			
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
			
		}
		
		return response;
		
	}
	
	/**
	 * Il metodo elimina i file caricati sul server passandogli {@link idRisorsa}, {@link tipoRisorsa}, {@link nomeDocumento} 
	 * @param request
	 * @param idRisorsa, è l'indentficativo dell'entità
	 * @param tipoRisorsa, è un codice numerico che indentifica le varie entità:
	 * - 1 TOPONIMO STRADALE
	 * - 2 PERCORSO
	 * - 3 LOCALITA
	 * - 4 GIUNZIONI STRADALI
	 * - 5 ESTESA AMMINISTRATIVA
	 * - 6 AREE E ELEMENTI STRADALI
	 * - 7 CIPPO CHILOMETRICO
	 * - 8 ACCESSO
	 * - 9 SHAPE FILE
	 * @param nome, è il nome del file da eliminare
	 * @return
	 * @throws Exception 
	 */
	@ApiOperation("Il metodo si occupa dell'eliminazione del file caricato sul server")
	@RequestMapping(value = "/eliminaDocumento", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData elimina(HttpServletRequest request, 
			@ApiParam(value = "Identificativo della risorsa", required = true)
			@RequestParam(value = "risorsa", required = true) Long idRisorsa,
	  		@ApiParam(value = "Identifica l'entità", required = true)  															  
			@RequestParam(value = "tipo", required = true) Long tipoRisorsa,
	  		@ApiParam(value = "Nome del file", required = true)  															  
			@RequestParam(value = "nome", required = true) String nomeDocumento) throws Exception {
		
		logger.debug("GET elimina");
				
		ResponseData response = null;
		
		try {
			
			service.elimina(idRisorsa, tipoRisorsa, nomeDocumento);
			
			response = new ResponseData(true, null);
			response.setMessage(SUCCESS);
			
		} catch (ToponomasticaServiceException e) {
			
			logger.error("ERRORE eliminaDocumento: ", e);
			
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
			
		}
		
		return response;
		
	}
	
}
