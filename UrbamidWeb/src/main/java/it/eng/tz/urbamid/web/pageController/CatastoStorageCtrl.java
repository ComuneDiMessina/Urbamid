package it.eng.tz.urbamid.web.pageController;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import it.eng.tz.urbamid.exception.UrbamidServiceException;
import it.eng.tz.urbamid.web.dto.DettaglioFileDTO;
import it.eng.tz.urbamid.web.services.CatastoStorageService;

@RestController
@RequestMapping("/catasto/storage")
public class CatastoStorageCtrl {
	
	/**
	 * Logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger(CatastoStorageCtrl.class.getName());
	
	/**
	 * Service
	 */
	private final CatastoStorageService service;
	
	/**
	 * Costruttore
	 * 
	 * @param service {@link CatastoStorageService}
	 */
	public CatastoStorageCtrl(CatastoStorageService service) {
		Assert.notNull(service, "StorageCatastoService MUST not be null but don't panic!");
		this.service = service;
	}
	
	@GetMapping(value="/{importType}/{folder}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<DettaglioFileDTO>> elencoFileInCartella(
			@PathVariable(value="importType", required=true) final String importType,
			@PathVariable(value="folder", required=true) final String folder ) throws UrbamidServiceException {
		if(LOG.isDebugEnabled())
			LOG.debug("CATASTO STORAGE PROXY --> GET REQUEST recupero file di tipo {} in cartella {}.", importType, folder);
		List<DettaglioFileDTO> files = this.service.elencoFileInCartella(importType, folder);
		return ResponseEntity.ok(files);
	}
	
	@PostMapping(value="/{importType}/{folder}")
	public ResponseEntity<String> uploadSingolo(
			@PathVariable(value="importType", required=true) final String importType,
			@PathVariable(value="folder", required=true) final String folder, 
			HttpServletRequest request) throws UrbamidServiceException {
		
		LOG.debug("CATASTO STORAGE PROXY --> POST REQUEST per upload file nella cartella {} per {}.", folder, importType);

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = multipartRequest.getFile("file");
		this.service.upload(importType, folder, multipartFile);
		
		LOG.debug("CATASTO STORAGE PROXY --> POST REQUEST eseguita con successo.");
		
		return ResponseEntity.ok("42");
		
	}
	
	@DeleteMapping(value="/{importType}/{folder}/{file:.+}")
	public ResponseEntity<String> rimuoviFileInCartella(
			
			@PathVariable(value="importType", required=true) final String importType,
			@PathVariable(value="folder", required=true) final String folder,
			@PathVariable(value="file", required=true) final String file) throws UrbamidServiceException {
		
		LOG.debug("CATASTO STORAGE PROXY --> DELETE REQUEST per la cancellazione dei file {} nella cartella {} di tipo {}.", 
				file, folder, importType);
		this.service.rimuovi(importType, folder, file);
		LOG.debug("CATASTO STORAGE PROXY --> DELETE REQUEST eseguita con successo.");
		
		return ResponseEntity.ok("42");
		
	}
	
}
