package it.eng.tz.urbamid.catasto.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.eng.tz.urbamid.catasto.exception.CatastoServiceException;
import it.eng.tz.urbamid.catasto.python.service.PythonService;
import it.eng.tz.urbamid.catasto.storage.service.ImportCatastoPathService;
import it.eng.tz.urbamid.catasto.util.ImportType;

/**
 * REST Controller per l'esecuzione di script sql via psql.
 */
@RestController
@RequestMapping("/catasto/rest/api/python")
@Api(value = "urbamid python", tags= {"Python"})
public class PythonRESTController {
	
	/**
	 * Logger
	 */
	private final Logger LOG = LoggerFactory.getLogger(this.getClass().getName());
	
	/**
	 * Service
	 */
	private final PythonService service;
	
	/**
	 * Path service
	 */
	private final ImportCatastoPathService pathService;
	
	/**
	 * Costruttore.
	 * 
	 * @param service è il service per l'esecuzione dello script che lancia la procedura python.
	 */
	public PythonRESTController(PythonService service, ImportCatastoPathService pathService) {
		Assert.notNull(service, "PsqlService MUST not be null but don't panic!");
		Assert.notNull(pathService, "ImportCatastoPathService MUST not be null but don't panic!");
		this.service = service;
		this.pathService = pathService;
	}
	
	@ApiOperation(value="Esegue uno script sql", response=String.class, code=200)
	@ApiResponses(value = { 
		@ApiResponse(code = 500, message = "Internal server error"),
		@ApiResponse(code = 404, message = "Not found") 
	})
	@GetMapping
	public ResponseEntity<String> eseguiScript(
			
			@ApiParam(name="importType", value="Tipo di import (ATTUALITA/AGGIORNAMENTO)", required=true, 
				example="ATTUALITA")
			@RequestParam(value="importType", required=false, defaultValue="u_cat_") ImportType importType,
			
			@ApiParam(name="shapefilePrefix", value="Prefisso per gli SHAPEFILE prodotti", required=false, 
				example="u_cat_", defaultValue="u_cat_")
			@RequestParam(value="shapefilePrefix", required=false, defaultValue="u_cat_") String shapefilePrefix)
			
					throws CatastoServiceException {
		
		LOG.debug("GET REQUEST per esecuzione script SQL.");
		String result = this.service.eseguiScriptPython(
				this.pathService.scriptShDirectory(), 
				this.pathService.cassiniSoldnerWorkDirectory(importType), 
				this.pathService.gaussBoagaWorkDirectory(importType), 
				this.pathService.pythonScriptCxfToShape(), 
				this.pathService.shapefileOutputDirectory(importType), 
				shapefilePrefix);
		LOG.debug("GET REQUEST eseguita per esecuzione script SQL eseguito con successo.");
		
		return ResponseEntity.ok(result);
		
	}

	public PythonService getService() {
		return service;
	}

	public ImportCatastoPathService getPathService() {
		return pathService;
	}
	
	
//	./pythonscript.sh  /opt/IMPORT_CATASTO/DATI/ATTUALITA/SHAPEFILE/ u_cat_
	
	// TODO dovremmo estendere per dare in download il file de report del risultato.

}
