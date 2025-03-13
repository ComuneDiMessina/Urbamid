package it.eng.tz.urbamid.toponomastica.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
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
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.eng.tz.urbamid.toponomastica.exception.ToponomasticaServiceException;
import it.eng.tz.urbamid.toponomastica.filter.ShapeFileFilter;
import it.eng.tz.urbamid.toponomastica.filter.ToponimoFilter;
import it.eng.tz.urbamid.toponomastica.service.ShapeFileService;
import it.eng.tz.urbamid.toponomastica.web.dto.PagedResult;
import it.eng.tz.urbamid.toponomastica.web.dto.ShapeResponseDTO;
import it.eng.tz.urbamid.toponomastica.web.response.ResponseData;

@RestController
@RequestMapping("/toponomastica/shapeFile")
@Api(value = "urbamid toponomastica", tags = {"ShapeFile"})
public class ShapeFileController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(ShapeFileController.class.getName());

	public static final String START = "START >>> {}";
	public static final String SUCCESS = "SUCCESS";
	public static final String ERROR = "ERROR";
	
	@Autowired
	private ShapeFileService service;
	
	/**
	 * Il metodo recupera in maniera paginata i file caricati sul server passandogli {@link filter} come input
	 * @param request
	 * @param filter, filtri di ricerca
	 * @return
	 * @throws Exception
	 */
	@ApiOperation("Il metodo si occupa del recupero degli shape files caricati sul server")
	@RequestMapping(value = "/getShapeFiles", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData getShapeFiles(HttpServletRequest request, 
			@ApiParam(value = "Filtri di ricerca", required = true)
			@RequestBody(required = true) ShapeFileFilter filter) {
		
		String idLog = "POST getShapeFiles";
		logger.info(START, idLog);
		
		ResponseData response = null;
		
		try {
			
			PagedResult<ShapeResponseDTO> lista = service.getShapeFiles(filter);
			
			response = new ResponseData(true, lista.getContent(), (int) lista.getTotalElements(), (int) lista.getTotalElements(), null);
			response.setMessage(SUCCESS);
			
		} catch (Exception e) {
			String errorMsg = ERROR.concat(" ").concat(idLog);
			logger.error(errorMsg + "{}", e.getMessage());
			
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(errorMsg);
		}
		
		return response;
		
	}
	
	/**
	 * Il metodo importa {@link files} passati come input e inserisce e/o modifica gli attributi della tabella u_topo_toponimo_geocoding 
	 * @param request
	 * @param files
	 * @return
	 * @throws Exception
	 */
	@ApiOperation("Il metodo si occupa dell'import degli shape files")
	@RequestMapping(value = "/importShape", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData importShapeFile(HttpServletRequest request, 
			@ApiParam(value = "File", required = true)
			@RequestBody(required = true) MultipartFile file) throws Exception {
		
		String idLog = "POST importShapeFile";
		logger.debug(START, idLog);
		
		ResponseData response = null;
		try {
			
			if(file != null && file.getSize() != 0) {
				
				boolean fileCorretti = false;
				
				String extension = FilenameUtils.getExtension(file.getOriginalFilename());
				
				if(extension.equalsIgnoreCase("zip"))
					fileCorretti = true;
				
				if(fileCorretti) {
					boolean responseService = service.importShapeFile(file);
					
					response = new ResponseData(true, responseService, 1, 1, null);
					response.setMessage(SUCCESS);
					
				} else {
					response = new ResponseData(false, null, 0, 0, "Il file non è in un formato corretto! Il file dev'essere un archivio zip");
					response.setMessage(ERROR);
					
				}
				
			} else {
				response = new ResponseData(false, null, 0, 0, "La lista dei file è vuota!");
				response.setMessage(ERROR);
			
			}
	
		} catch (ToponomasticaServiceException e) {
			logger.error(ERROR);
			
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
		}
		
		return response;
	}
	
	/**
	 * Il metodo crea e esporta uno shape file dei toponimi stradali in base ai filtri di ricerca
	 * @param request
	 * @param filter filtri di ricerca
	 * @return
	 * @throws Exception
	 */
	@ApiOperation("Il metodo si occupa della creazione e dell'esportazione dello shape file dei toponimi stradali in base ai filtri di ricerca")
	@RequestMapping(value = "/exportShape", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData exportShapeFile(HttpServletRequest request, 
			@ApiParam(value = "Filtri di ricerca", required = true)
			@RequestBody(required = true) ToponimoFilter filter) throws Exception {
		
		String idLog = "POST exportShapeFile";
		logger.debug(idLog);
		ResponseData response = null;
		
		try {		
			
			ShapeResponseDTO responseShapeDTO = service.exportShapeFile(filter);
			
			if(responseShapeDTO != null) {
				
				response = new ResponseData(true, responseShapeDTO, 1, 1, null);
				response.setMessage(SUCCESS);
			} else {
				
				response = new ResponseData(false, null, 0, 0, null);
				response.setMessage(ERROR);
			}
			
			
		} catch (ToponomasticaServiceException e) {
			String errorMsg = ERROR.concat(" ").concat(idLog);
			logger.error(errorMsg + " {}", e.getMessage());
			
			
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
			
		}
		
		return response;
	}
	
	/**
	 * Il metodo elimina i file .dbf, .shp e .shx dello stesso shape file dal server passandogli in input l'id del documento
	 * @param request
	 * @param nomeDocumento nome del documento
	 * @return {@code ResponseData}
	 * @throws Exception
	 */
	@ApiOperation("Il metodo si occupa dell'eliminazione dell'archivio .zip dal server")
	@RequestMapping(value = "/eliminaShape", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData eliminaShape(HttpServletRequest request, 
			@ApiParam(value = "id del ocumento", required = true)
			@RequestParam(value = "id", required = true) Long id) throws Exception {
		
		String idLog = "POST eliminaShape";
		logger.debug(idLog);
		ResponseData response = null;
		
		try {		
			
			if(id != null) {
				service.eliminaFileZip(id);
				response = new ResponseData(true, null, 1, 1, null);
				response.setMessage(SUCCESS);
			} else {
				response = new ResponseData(false, null, 0, 0, null);
				response.setMessage(ERROR);
				
			}
			
			
		} catch (ToponomasticaServiceException e) {
			String errorMsg = ERROR.concat(" ").concat(idLog);
			logger.error(errorMsg + " {}", e.getMessage());
			
			response = new ResponseData(false, null, 0, 0, e.getMessage());
			response.setMessage(ERROR);
			
		}
		
		return response;
	}
	
}
