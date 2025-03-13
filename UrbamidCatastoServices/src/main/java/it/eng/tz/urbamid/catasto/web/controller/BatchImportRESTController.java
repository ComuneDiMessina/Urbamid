package it.eng.tz.urbamid.catasto.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.eng.tz.urbamid.catasto.batch.service.BatchService;
import it.eng.tz.urbamid.catasto.exception.CatastoServiceException;
import it.eng.tz.urbamid.catasto.geokettle.util.KitchenLogLevel;
import it.eng.tz.urbamid.catasto.util.ImportType;
import it.eng.tz.urbamid.catasto.web.dto.DettaglioBatchDTO;

@RestController
@RequestMapping("/catasto/rest/api/batch")
@Api(value = "urbamid import-dati", tags= {"BatchManagement"})
public class BatchImportRESTController {
	
	/**
	 * Logger
	 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * Service
	 */
	private final BatchService service;
	
	/**
	 * Costruttore.
	 * 
	 * @param service è il service per la gestione del batch.
	 */
	public BatchImportRESTController(BatchService service) {
		Assert.notNull(service, "BatchService must not be null but don't panic!");
		this.service = service;
	}
	
	/**
	 * Metodo POST per l'avvio del processo BATCH di import COMPLETO.
	 * 
	 * @param logLevel è il livello di log.
	 * 
	 * @return {@link ResponseEntity}
	 * 
	 * @throws CatastoServiceException
	 */
	@ApiOperation(value="Avvia il processo batch per l'import completo del catasto (ATTUALITA)", response=String.class, code=200)
	@ApiResponses(value = { 
		@ApiResponse(code = 500, message = "Internal server error"),
		@ApiResponse(code = 404, message = "Not found") 
	})
	@PostMapping("/attualita")
	public ResponseEntity<DettaglioBatchDTO> avviaProcessoBatchAttualita(
			
			@ApiParam(name="logLevel", value="Livello di log di GeoKettle", required=false, example="Basic")
			@RequestParam(value="logLevel", required=false, defaultValue="Basic") String logLevel
			
			) throws CatastoServiceException {
		
		//FIXME L'attualità andrà fatta solo dall'admin... (anche per questo i 2 metodi quasi uguali sono separati)
		
		if(logger.isDebugEnabled())
			logger.debug("POST REQUEST per avvio processo import dati catastali -- CASO ATTUALITA.");
		
		DettaglioBatchDTO dettaglioBatch = this.service.avviaBatchImportDatiCatastali(KitchenLogLevel.fromString(logLevel), ImportType.ATTUALITA);
		
		if(logger.isDebugEnabled())
			logger.debug("POST REQUEST il processo batch è stato schedulato con successo. Dettagli batch schedulato: {}.", dettaglioBatch);
		
		return ResponseEntity.ok(dettaglioBatch);
		
	}
	
	/**
	 * Metodo POST per l'avvio del processo BATCH di import COMPLETO.
	 * 
	 * @param logLevel è il livello di log.
	 * 
	 * @return {@link ResponseEntity}
	 * 
	 * @throws CatastoServiceException
	 */
	@ApiOperation(value="Avvia il processo batch per l'import completo del catasto (ATTUALITA)", response=String.class, code=200)
	@ApiResponses(value = { 
		@ApiResponse(code = 500, message = "Internal server error"),
		@ApiResponse(code = 404, message = "Not found") 
	})
	@PostMapping("/aggiornamento")
	public ResponseEntity<DettaglioBatchDTO> avviaProcessoBatchAggiornamento(
			
			@ApiParam(name="logLevel", value="Livello di log di GeoKettle", required=false, example="Basic")
			@RequestParam(value="logLevel", required=false, defaultValue="Basic") String logLevel
			
			) throws CatastoServiceException {
		
		if(logger.isDebugEnabled())
			logger.debug("POST REQUEST per avvio processo import dati catastali. -- CASO AGGIORNAMENTO.");
		
		DettaglioBatchDTO dettaglioBatch = this.service.avviaBatchImportDatiCatastali( KitchenLogLevel.fromString(logLevel), ImportType.AGGIORNAMENTO);
		
		if(logger.isDebugEnabled())
			logger.debug("POST REQUEST il processo batch è stato schedulato con successo. Dettagli batch schedulato: {}.", dettaglioBatch);
		
		return ResponseEntity.ok(dettaglioBatch);
		
	}
	
	/**
	 * Metodo GET per il recupero del dettaglio di un job dato il suo id.
	 * 
	 * @param jobExecutionId è l'identificativo del job
	 * 
	 * @return {@link ResponseEntity}
	 * 
	 * @throws CatastoServiceException 
	 */
	@ApiOperation(value="Recupera i dettagli di esecuzione di un processo batch dato il suo identificativo", response=String.class, code=200)
	@ApiResponses(value = { 
		@ApiResponse(code = 204, message = "No content (entity not found)"),
		@ApiResponse(code = 500, message = "Internal server error"),
		@ApiResponse(code = 404, message = "Not found") 
	})
	@GetMapping("/{jobExecutionId}")
	public ResponseEntity<DettaglioBatchDTO> getByJobExexutionId(
			
			@ApiParam(name="jobExecutionId", value="Identificativo del JobExecution", required=true, example="1")  
			@PathVariable(name="jobExecutionId", required=true) final Long jobExecutionId ) throws CatastoServiceException {
		
		logger.debug("GET dettaglio esecuzione batch con jobExecutionId {}.", jobExecutionId );
		DettaglioBatchDTO dettaglio = service.getByJobExexutionId(jobExecutionId);
		logger.debug("GET dettaglio esecuzione batch eseguito con successo.");
		
		return ResponseEntity.ok( dettaglio );
		
	}
	
	
	/**
	 * Metodo POST per il recupero del processo BATCH di import in corso.
	 * 
	 * @param logLevel è il livello di log.
	 * 
	 * @return {@link ResponseEntity}
	 * 
	 * @throws CatastoServiceException
	 */
	@ApiOperation(value="Recuper del processo batch in corso", response=String.class, code=200)
	@ApiResponses(value = { 
		@ApiResponse(code = 500, message = "Internal server error"),
		@ApiResponse(code = 404, message = "Not found") 
	})
	@PostMapping("/getExecutionJob")
	public ResponseEntity<DettaglioBatchDTO> getExecutionJob( ) throws CatastoServiceException {
		
		if(logger.isDebugEnabled())
			logger.debug("POST REQUEST per recupero processo in corso di import dati catastali");
		
		DettaglioBatchDTO dettaglioBatch = this.service.getExecutionJob( );
		
		if(logger.isDebugEnabled())
			logger.debug("POST REQUEST E' stato recuperato il processo in corso. Dettagli batch schedulato: {}.", dettaglioBatch);
		
		return ResponseEntity.ok(dettaglioBatch);
		
	}
}
