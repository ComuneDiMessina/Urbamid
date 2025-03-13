package it.eng.tz.urbamid.catasto.web.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.eng.tz.urbamid.catasto.exception.CatastoServiceException;
import it.eng.tz.urbamid.catasto.geokettle.service.GeoKettleService;
import it.eng.tz.urbamid.catasto.geokettle.util.GeoKettleJobType;
import it.eng.tz.urbamid.catasto.web.dto.ParametriAvvioJobDTO;

/**
 * REST Controller per avviare singolarmente i 3 processi di elaborazione che costituiscono
 * la procedura di import dati catastali.
 */
@RestController
@RequestMapping("/catasto/rest/api/job-execution")
@Api(value = "urbamid job-management", tags= {"JobManagement"})
public class JobManagementRESTController {
	
	private static final Logger LOG = LoggerFactory.getLogger(JobManagementRESTController.class.getClass());
	
	private final GeoKettleService service;

	/**
	 * Costruttore.
	 * 
	 * @param service è il service
	 */
	public JobManagementRESTController(GeoKettleService service) {
		Assert.notNull(service, "GeoKettleService must not be null but don't panic!");
		this.service = service;
	}
	
	/**
	 * Avvia il processo di import dei dati catastali nel caso di attualita'.
	 * 
	 * @param parametri DTO che incapsula i parametri per lanciare il job.
	 * 
	 * @return {@link ResponseEntity}
	 * 
	 * @throws CatastoServiceException
	 */
	@ApiOperation(value="Avvia il processo di import dei dati catastali nel caso di attualita'.", response=String.class, code=200)
	@ApiResponses(value = { 
		@ApiResponse(code = 500, message = "Internal server error"),
		@ApiResponse(code = 404, message = "Not found") 
	})
	@PostMapping("attualita/JOB_IMPORT_CATASTO")
	public ResponseEntity<String> avviaJobAttualitaImportCatasto(
			
			@ApiParam(name="parametri", value="I parametri di avvio del job", required=true)
			@RequestBody(required=true) @Valid ParametriAvvioJobDTO parametri
			
			) throws CatastoServiceException {
		
		if(LOG.isDebugEnabled()) {
			LOG.debug("POST REQUEST per avvio JOB IMPORT CATASTO -- CASO ATTUALITA.");
			LOG.debug("Parametri di avvio: {}.", parametri);
		}
		String log = service.eseguiScriptKitchenSH(parametri, GeoKettleJobType.JOB_ATTUALITA_IMPORT_CATASTO);
		return ResponseEntity.ok(log);
		
	}
	
	/**
	 * Avvia il processo di import dei dati catastali nel caso di aggiornamento.
	 * 
	 * @param parametri DTO che incapsula i parametri per lanciare il job.
	 * 
	 * @return {@link ResponseEntity}
	 * 
	 * @throws CatastoServiceException
	 */
	@ApiOperation(value="Avvia il processo di import dei dati catastali nel caso di aggiornamento.", response=String.class, code=200)
	@ApiResponses(value = { 
		@ApiResponse(code = 500, message = "Internal server error"),
		@ApiResponse(code = 404, message = "Not found") 
	})
	@PostMapping("aggiornamento/JOB_IMPORT_CATASTO")
	public ResponseEntity<String> avviaJobAggiornamentoImportCatasto(
			
			@ApiParam(name="parametri", value="I parametri di avvio del job", required=true)
			@RequestBody(required=true) @Valid ParametriAvvioJobDTO parametri
			
			) throws CatastoServiceException {
		
		if(LOG.isDebugEnabled()) {
			LOG.debug("POST REQUEST per avvio JOB IMPORT CATASTO -- CASO AGGIORNAMENTO.");
			LOG.debug("Parametri di avvio: {}.", parametri);
		}
		String log = service.eseguiScriptKitchenSH(parametri, GeoKettleJobType.JOB_AGGIORNAMENTO_IMPORT_CATASTO);
		return ResponseEntity.ok(log);
		
	}
	
	/**
	 * Avvia il processo cxf to shape nel caso di attualita'.
	 * 
	 * @param parametri DTO che incapsula i parametri per lanciare il job.
	 * 
	 * @return {@link ResponseEntity}
	 * 
	 * @throws CatastoServiceException
	 */
	@ApiOperation(value="Avvia il processo cxf to shape nel caso di attualita'.", response=String.class, code=200)
	@ApiResponses(value = { 
		@ApiResponse(code = 500, message = "Internal server error"),
		@ApiResponse(code = 404, message = "Not found") 
	})
	@PostMapping("attualita/JOB_CXF_TO_SHAPE")
	public ResponseEntity<String> avviaAttualitaJobCxfToShape(
			
			@ApiParam(name="parametri", value="I parametri di avvio del job", required=true)
			@RequestBody(required=true) @Valid ParametriAvvioJobDTO parametri
			
			) throws CatastoServiceException {
		
		if(LOG.isDebugEnabled()) {
			LOG.debug("POST REQUEST per avvio JOB CXF TO SHAPE -- CASO ATTUALITA.");
			LOG.debug("Parametri di avvio: {}.", parametri);
		}
		String log = service.eseguiScriptKitchenSH(parametri, GeoKettleJobType.JOB_ATTUALITA_CXF_TO_SHAPE);
		return ResponseEntity.ok(log);
		
	}
	
	/**
	 * Avvia il processo cxf to shape nel caso di aggiornamento.
	 * 
	 * @param parametri DTO che incapsula i parametri per lanciare il job.
	 * 
	 * @return {@link ResponseEntity}
	 * 
	 * @throws CatastoServiceException
	 */
	@ApiOperation(value="Avvia il processo cxf to shape nel caso di aggiornamento.", response=String.class, code=200)
	@ApiResponses(value = { 
		@ApiResponse(code = 500, message = "Internal server error"),
		@ApiResponse(code = 404, message = "Not found") 
	})
	@PostMapping("aggiornamento/JOB_CXF_TO_SHAPE")
	public ResponseEntity<String> avviaAggiornamentoJobCxfToShape(
			
			@ApiParam(name="parametri", value="I parametri di avvio del job", required=true)
			@RequestBody(required=true) @Valid ParametriAvvioJobDTO parametri
			
			) throws CatastoServiceException {
		
		if(LOG.isDebugEnabled()) {
			LOG.debug("POST REQUEST per avvio JOB CXF TO SHAPE -- CASO AGGIORNAMENTO.");
			LOG.debug("Parametri di avvio: {}.", parametri);
		}
		String log = service.eseguiScriptKitchenSH(parametri, GeoKettleJobType.JOB_AGGIORNAMENTO_CXF_TO_SHAPE);
		return ResponseEntity.ok(log);
		
	}
	
}
