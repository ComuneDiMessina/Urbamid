package it.eng.tz.urbamid.catasto.web.controller;

import java.util.List;

import javax.validation.constraints.NotNull;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;
import it.eng.tz.urbamid.catasto.exception.CatastoServiceException;
import it.eng.tz.urbamid.catasto.service.ImportDatiCatastaliService;
import it.eng.tz.urbamid.catasto.storage.service.ImportCatastoPathService;
import it.eng.tz.urbamid.catasto.storage.util.StorageFolder;
import it.eng.tz.urbamid.catasto.util.ImportType;
import it.eng.tz.urbamid.catasto.web.dto.DettaglioEliminazioneFileDTO;
import it.eng.tz.urbamid.catasto.web.dto.DettaglioFileDTO;

/**
 * REST Controller per l'upload/download dei file sulla quale operano le procedure del catasto.
 */
@RestController
@RequestMapping("/catasto/rest/api/storage")
@Api(value = "urbamid storage", tags= {"StorageCatasto"})
public class StorageCatastoRESTController {
	
	private static final Logger LOG = LoggerFactory.getLogger(StorageCatastoRESTController.class.getClass());
	
	private static final String FILE = "file";

	/**
	 * Service per l'upload ed il recupero di informazioni sui file.
	 */
	private final ImportDatiCatastaliService importDatiService;
	
	/**
	 * Service per il recupero dei path applicativi.
	 */
	private final ImportCatastoPathService pathService;
	
	public StorageCatastoRESTController(ImportDatiCatastaliService importDatiService, ImportCatastoPathService pathService) {
		Assert.notNull(importDatiService, "ImportDatiCatastaliService must not be null but don't panic!");
		Assert.notNull(pathService, "ImportCatastoPathService must not be null but don't panic!");
		this.importDatiService = importDatiService;
		this.pathService = pathService;
	}
	
	/**
	 * 
	 * @param importType
	 * @param folder
	 * @param files
	 * @return
	 * @throws CatastoServiceException
	 */
	@ApiOperation(value="Effettua l'upload di una lista di file in una determinata cartella", response=Void.class, code=200)
	@ApiResponses(value = { 
		@ApiResponse(code = 500, message = "Internal server error"),
		@ApiResponse(code = 404, message = "Not found") 
	})
	@PostMapping(value="/{importType}/{folder}/multiplo", consumes={MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<Void> upload(
			
			@ApiParam(value="Tipo di import", required=true, example="ATTUALITA", examples=@Example({
				@ExampleProperty("ATTUALITA"),
				@ExampleProperty("AGGIORNAMENTO")})
			)
			@PathVariable(value="importType", required=true) final ImportType importType,
			
			@ApiParam(value="Cartella di upload", required=true, example="CARTOGRAFIA", examples=@Example({
				@ExampleProperty("CARTOGRAFIA"),
				@ExampleProperty("FABBRICATI"),
				@ExampleProperty("PLANIMETRIE"),
				@ExampleProperty("TERRENI")})
			)
			@PathVariable(value="folder", required=true) final String folder,
			
			@ApiParam(value="Lista files da importare", required=true)
			@RequestParam(value="files", required=true) final MultipartFile[] files) throws CatastoServiceException {
		
		LOG.debug("POST REQUEST per import dati di {} file nella cartella {}.", files.length, folder);
		
		importDatiService.importFiles(files, getFolder(importType, folder));
		
		LOG.debug("POST REQUEST eseguita con successo. Salvati correttamente {} file nella cartella {}.",
				files.length, folder);
		
		return ResponseEntity.ok().build();
		
	}

	/**
	 * 
	 * @param importType
	 * @param folder
	 * @param request
	 * @return
	 * @throws CatastoServiceException
	 */
	@ApiOperation(value="Effettua l'upload di un singolo file in una determinata cartella", response=DettaglioFileDTO.class, code=200)
	@ApiResponses(value = { 
		@ApiResponse(code = 500, message = "Internal server error"),
		@ApiResponse(code = 404, message = "Not found") 
	})
	@PostMapping(value="/{importType}/{folder}")
	public ResponseEntity<DettaglioFileDTO> uploadSingolo(
			
			@ApiParam(value="Tipo di import", required=true, example="ATTUALITA", examples=@Example({
				@ExampleProperty("ATTUALITA"),
				@ExampleProperty("AGGIORNAMENTO")})
			)
			@PathVariable(value="importType", required=true) final ImportType importType,
			
			@ApiParam(value="Cartella di upload", required=true, example="CARTOGRAFIA", examples=@Example({
				@ExampleProperty("CARTOGRAFIA"),
				@ExampleProperty("FABBRICATI"),
				@ExampleProperty("PLANIMETRIE"),
				@ExampleProperty("TERRENI")})
			)
			@PathVariable(value="folder", required=true) final String folder, 
			
			@RequestParam(FILE) MultipartFile file) 
	
					throws CatastoServiceException {
		
		LOG.debug("POST REQUEST per import dati di un file nella cartella {}.", folder);

		importDatiService.importFile(file, this.getFolder(importType, folder));
		
		LOG.debug("POST REQUEST eseguita con successo. Salvati correttamente .");
		
		return ResponseEntity.ok( null );
		
	}
	
	/**
	 * 
	 * @param folder
	 * @return
	 * @throws CatastoServiceException
	 */
	@ApiOperation(value="Recupera l'elenco dei file in una determinata cartella", responseContainer="List", 
			response=DettaglioFileDTO.class, code=200)
	@ApiResponses(value = { 
		@ApiResponse(code = 500, message = "Internal server error"),
		@ApiResponse(code = 404, message = "Not found") 
	})
	@GetMapping(value="/{importType}/{folder}")
	public ResponseEntity<List<DettaglioFileDTO>> elencoFileInCartella(
			
			@ApiParam(value="Tipo di import", required=true, example="ATTUALITA", examples=@Example({
				@ExampleProperty("ATTUALITA"),
				@ExampleProperty("AGGIORNAMENTO")})
			)
			@PathVariable(value="importType", required=true) final ImportType importType,
			
			@ApiParam(value="Cartella di upload", required=true, example="CARTOGRAFIA", examples=@Example({
				@ExampleProperty("CARTOGRAFIA"),
				@ExampleProperty("FABBRICATI"),
				@ExampleProperty("PLANIMETRIE"),
				@ExampleProperty("TERRENI")})
			)
			@PathVariable(value="folder", required=true) final String folder ) 
					throws CatastoServiceException {
		
		LOG.debug("GET REQUEST per il recupero dei file nella cartella {}.", folder);
		
		List<DettaglioFileDTO> listaFile = importDatiService.elencoFileInCartella(getFolder(importType, folder));
		
		LOG.debug("GET REQUEST eseguita con successo. Recuperati i dettagli di {} nella cartella {}.", listaFile.size(), folder);
		
		return ResponseEntity.ok(listaFile);
		
	}
	
	/**
	 * 
	 * @param importType
	 * @param folder
	 * @param nomeFile
	 * @return
	 * @throws CatastoServiceException
	 */
	@ApiOperation(value="Elimina un file contenuto in una determinata cartella", response=DettaglioEliminazioneFileDTO.class, code=200)
	@ApiResponses(value = { 
		@ApiResponse(code = 500, message = "Internal server error"),
		@ApiResponse(code = 404, message = "Not found") 
	})
	@DeleteMapping(value="/{importType}/{folder}/{file}")
	public ResponseEntity<DettaglioEliminazioneFileDTO> rimuoviFileInCartella(
			
			@ApiParam(value="Tipo di import", required=true, example="ATTUALITA", examples=@Example({
				@ExampleProperty("ATTUALITA"),
				@ExampleProperty("AGGIORNAMENTO")})
			)
			@PathVariable(value="importType", required=true) final ImportType importType,
			
			@ApiParam(value="Cartella di upload", required=true, example="CARTOGRAFIA", examples=@Example({
				@ExampleProperty("CARTOGRAFIA"),
				@ExampleProperty("FABBRICATI"),
				@ExampleProperty("PLANIMETRIE"),
				@ExampleProperty("TERRENI")})
			)
			@PathVariable(value="folder", required=true) final String folder,
			
			@ApiParam(value="Nome del file da cancellare", required=true, example="C352_FileDaCancellare.TIT")
			@PathVariable(value="file", required=true) final String file) 
					throws CatastoServiceException {
		
		LOG.debug("DELETE REQUEST per la cancellazione dei file {} nella cartella {}.", file, folder);
		DettaglioEliminazioneFileDTO dettaglioEliminazione = importDatiService.rimuoviFileInCartella(getFolder(importType, folder), file);
		LOG.debug("DELETE REQUEST eseguita con successo.");
		
		return ResponseEntity.ok(dettaglioEliminazione);
		
	}

	/**
	 * Metodo privato di utilità
	 * @param multipartFile è il multipart file
	 * @return {@link DettaglioFileDTO}
	 */
	private DettaglioFileDTO get(@NotNull MultipartFile multipartFile) {
		DettaglioFileDTO dettaglio = new DettaglioFileDTO();
		dettaglio.setNome(multipartFile.getName());
		dettaglio.setDimensione(multipartFile.getSize());
		dettaglio.setTipo(multipartFile.getContentType());
		return dettaglio;
	}

	/**
	 * Metodo privato che tramite il service {@link ImportCatastoPathService} recupera una delle cartelle di upload.
	 * 
	 * @param importType è il tipo di import (ATTUALITA/AGGIORNAMENTO)
	 * @param folderAsString è la folder di upload 
	 * 
	 * @return il path dove effettuare l'upload e/o recuperare le info sulle cartelle
	 * 
	 * @throws CatastoServiceException
	 */
	private String getFolder(ImportType importType, String folderAsString) throws CatastoServiceException {
		StorageFolder storageFolder = StorageFolder.uploadFolderFromString(folderAsString).orElseThrow(CatastoServiceException::new); //too lazy to do it better! ç_ç
		String folder = null;
		switch(storageFolder) {
		case CARTOGRAFIA:
			folder = this.pathService.datiCartografiaDirectory(importType);
			break;
		case FABBRICATI:
			folder = this.pathService.datiFabbricatiDirectory(importType);
			break;
		case PLANIMETRIE:
			folder = this.pathService.datiPlanimetrieDirectory(importType);
			break;
		case TERRENI:
			folder = this.pathService.datiTerreniDirectory(importType);
			break;
		default:
			//do nothing
			break;
		}
		Assert.notNull(folder, "Folder MUST not be null!");
		return folder;
	}
	
	public ImportCatastoPathService getPathService() {
		return pathService;
	}
	
	//
	//	>>>		VERY TRASH CODE -- TO USE AT OWN RISK		<<<
	//
//	@PostMapping(value="/{folder}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//	public ResponseEntity<DettaglioBatchDTO> avviaImportDatiCatastali(
//			@PathVariable(value="folder") final String folder,
//			@RequestParam(value="files", required=true) final MultipartFile[] files,
//			@RequestParam(value="metadata", required=true) final String metadataJSON) 
//					throws CatastoServiceException {
//		LOG.debug("POST REQUEST per import dati catastali.");
//		//Al termine del processo di import dei file forniti dall'agenzia del territorio con i dati catastali 
//		//ci aspettiamo che sia partito un batch job che gestisce l'esecuzione dello script kitchen.sh di GeoKettle.
//		DettaglioBatchDTO dettaglioBatchDTO = service.avviaImportDatiCatastali( files, convertiDaJSON(metadataJSON) );
//		LOG.debug("POST REQUEST eseguita con successo. Dettaglio batch lanciato {}", dettaglioBatchDTO);
//		return ResponseEntity.ok( dettaglioBatchDTO );
//	}
	
//	@PostMapping(value="uploadSingolo/{folder}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//	public ResponseEntity<DettaglioBatchDTO> uploadSingolo(
//			@PathVariable(value="folder", required=true) final String folder,
//			@RequestParam(value="file", required=true) final MultipartFile file) throws CatastoServiceException {
//		LOG.debug("POST REQUEST per import dati di {} file nella cartella {}.", file.getSize(), folder);
//		service.importFile(file, folder);
//		LOG.debug("POST REQUEST eseguita con successo. Salvati correttamente {} file nella cartella {}.",
//				file.getSize(), folder);
//		return ResponseEntity.ok(null);
//	}
	

}
