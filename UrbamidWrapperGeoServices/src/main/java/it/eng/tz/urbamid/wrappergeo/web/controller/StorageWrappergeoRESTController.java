package it.eng.tz.urbamid.wrappergeo.web.controller;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.eng.tz.urbamid.wrappergeo.exception.WrapperGeoServiceException;
import it.eng.tz.urbamid.wrappergeo.service.ImportDatiWrappergeoService;
import it.eng.tz.urbamid.wrappergeo.util.DateUtils;
import it.eng.tz.urbamid.wrappergeo.web.entities.DettaglioFileDTO;
import it.eng.tz.urbamid.wrappergeo.web.response.ResponseData;

/**
 * REST Controller per l'upload/download dei file sulla quale operano le procedure del catasto.
 */
@RestController
@RequestMapping("/wrappergeo/rest/api/storage")
@Api(value = "urbamid storage", tags= {"StorageWrapperGeo"})
public class StorageWrappergeoRESTController {
	
	private static final Logger LOG = LoggerFactory.getLogger(StorageWrappergeoRESTController.class.getClass());
	
	private static final String FILE = "file";
	public static final String SUCCESS = "Success";
	public static final String ERROR = "Error";

	/**
	 * Service per l'upload ed il recupero di informazioni sui file.
	 */
	private final ImportDatiWrappergeoService importDatiService;
	
	
	public StorageWrappergeoRESTController(ImportDatiWrappergeoService importDatiService) {
		Assert.notNull(importDatiService, "ImportDatiWrappergeoService must not be null but don't panic!");
		this.importDatiService = importDatiService;
	}
	
	/**
	 * 
	 * @param importType
	 * @param folder
	 * @param files
	 * @return
	 * @throws WrapperGeoServiceException
	 */
	@ApiOperation(value="Effettua l'upload di una lista di file in una determinata cartella", response=Void.class, code=200)
	@ApiResponses(value = { 
		@ApiResponse(code = 500, message = "Internal server error"),
		@ApiResponse(code = 404, message = "Not found") 
	})
	@PostMapping(value="/multiplo", consumes={MediaType.MULTIPART_FORM_DATA_VALUE})
	public @ResponseBody ResponseData upload(
			
			@ApiParam(value="Lista files da importare", required=true)
			@RequestParam(value="files", required=true) final MultipartFile[] files,
			@ApiParam(value="Impostando il valore a true si andrà a sovrascrivere i files caricati", required=true)
			@RequestParam(value="overwrite", required=true) final boolean overwrite) throws WrapperGeoServiceException {
		
		LOG.debug("POST REQUEST per import dati di {} file.", files.length);
		ResponseData response = null;
		try {
			
			importDatiService.importFiles(files, overwrite);
			response = new ResponseData(true, null, files.length, files.length, null);
			response.setMessage(SUCCESS);
			LOG.debug("POST REQUEST eseguita con successo. Salvati correttamente {} files.", files.length);
		} catch (WrapperGeoServiceException ex){
			
			LOG.error("Error in upload", ex);
			response = new ResponseData(false, null,0, 0, ex.getMessage());
			response.setMessage(ERROR);	
		}
		return response;
	}

	/**
	 * 
	 * @param importType
	 * @param folder
	 * @param request
	 * @return
	 * @throws WrapperGeoServiceException
	 */
	@ApiOperation(value="Effettua l'upload di un singolo file in una determinata cartella", response=DettaglioFileDTO.class, code=200)
	@ApiResponses(value = { 
		@ApiResponse(code = 500, message = "Internal server error"),
		@ApiResponse(code = 404, message = "Not found") 
	})
	@PostMapping(value="/singolo")
	public @ResponseBody ResponseData uploadSingolo(
			
			@ApiParam(value="File da importare", required=true)
			@RequestParam(FILE) MultipartFile file,
			@ApiParam(value="Impostando il valore a true si andrà a sovrascrivere il file caricato", required=true)
			@RequestParam(value="overwrite", required=true) final boolean overwrite) 
	
					throws WrapperGeoServiceException {
		
		LOG.debug("POST REQUEST per import dati di un file nella cartella.");
		ResponseData response = null;
		try {
			
			importDatiService.importFile(file, overwrite);
			response = new ResponseData(true, null, 1, 1, null);
			response.setMessage(SUCCESS);
			LOG.debug("POST REQUEST eseguita con successo. Salvati correttamente {} files.", 1);
		} catch (WrapperGeoServiceException ex){
			
			LOG.error("Error in upload", ex);
			response = new ResponseData(false, null,0, 0, ex.getMessage());
			response.setMessage(ERROR);	
		}		
		return response;
	}
	
	
	/**
	 * 
	 * @param importType
	 * @param folder
	 * @param request
	 * @return
	 * @throws WrapperGeoServiceException
	 */
	@ApiOperation(value="Effettua un test per capire se è raggiungibile il servizio", response=DettaglioFileDTO.class, code=200)
	@ApiResponses(value = { 
		@ApiResponse(code = 500, message = "Internal server error"),
		@ApiResponse(code = 404, message = "Not found") 
	})
	@PostMapping(value="/test")
	public ResponseEntity<DettaglioFileDTO> uploadSingolo() 
	
					throws WrapperGeoServiceException {
		
		LOG.debug("POST REQUEST per import dati di un file nella cartella.");

		LOG.debug("POST REQUEST eseguita con successo. Salvati correttamente .");
		
		return ResponseEntity.ok( new DettaglioFileDTO() );
		
	}

	/**
	 * Metodo privato di utilità
	 * @param multipartFile è il multipart file
	 * @return {@link DettaglioFileDTO}
	 */
	private DettaglioFileDTO get(@NotNull MultipartFile multipartFile) {
		DettaglioFileDTO dettaglio = new DettaglioFileDTO();
		dettaglio.setNome(multipartFile.getOriginalFilename());
		dettaglio.setDimensione(multipartFile.getSize());
		dettaglio.setTipo(multipartFile.getContentType());
		dettaglio.setDataCreazione(DateUtils.dateToString(
					new Date(), 
					DateUtils.PATTERN_DD_MM_YYYY_SLASH));
		dettaglio.setSuccess(true);
		return dettaglio;
	}
	
	/**
	 * Metodo privato di utilità
	 * @param multipartFile è il multipart file
	 * @return {@link DettaglioFileDTO}
	 */
	private DettaglioFileDTO get(@NotNull MultipartFile[] multipartFile) {
		DettaglioFileDTO dettaglio = new DettaglioFileDTO();
		String nome = "| ";
		Long size = new Long(0);
		String tipo = "| ";
		String dataCreazione = "| ";
		for (MultipartFile file :multipartFile) {
			nome += file.getOriginalFilename() + " | ";
			size += file.getSize();
			tipo += file.getContentType() + " | ";
			dataCreazione += DateUtils.dateToString(
					new Date(), 
					DateUtils.PATTERN_DD_MM_YYYY_SLASH) + " | ";
		}
		dettaglio.setNome(nome);
		dettaglio.setDimensione(size);
		dettaglio.setTipo(tipo);
		dettaglio.setDataCreazione(dataCreazione);
		dettaglio.setSuccess(true);
		return dettaglio;
	}
	
}
