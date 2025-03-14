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
import it.eng.tz.urbamid.catasto.psql.service.PsqlService;

/**
 * REST Controller per l'esecuzione di script sql via psql.
 */
@RestController
@RequestMapping("/catasto/rest/api/psql")
@Api(value = "urbamid psql", tags= {"PSQL"})
public class PsqlRESTController {
	
	/**
	 * Logger
	 */
	private final Logger LOG = LoggerFactory.getLogger(this.getClass().getName());
	
	/**
	 * Service
	 */
	private final PsqlService service;
	
	/**
	 * Costruttore.
	 * 
	 * @param service è il service per l'esecuzione degli script.
	 */
	public PsqlRESTController(PsqlService service) {
		Assert.notNull(service, "PsqlService must not be null but don't panic!");
		this.service = service;
	}
	
	@ApiOperation(value="Esegue uno script sql", response=String.class, code=200)
	@ApiResponses(value = { 
		@ApiResponse(code = 500, message = "Internal server error"),
		@ApiResponse(code = 404, message = "Not found") 
	})
	@GetMapping
	public ResponseEntity<String> eseguiScript(
			
			@ApiParam(name="hostname", value="Hostname del database", required=false, example="localhost")
			@RequestParam(value="hostname", required=false, defaultValue="localhost") String hostname,
			
			@ApiParam(name="database", value="Nome del database", required=true, example="dbUrbamid")
			@RequestParam(value="database", required=true) String database,
			
			@ApiParam(name="port", value="Numero di porto del database", required=false, example="5432")
			@RequestParam(value="port", required=false, defaultValue="5432") String port,
			
			@ApiParam(name="username", value="Username di connessione al database", required=true, example="username")
			@RequestParam(value="username", required=true, defaultValue="postgres") String username,

			@ApiParam(name="file", value="path dello script SQL da eseguire", required=true, example="/home/user/script.sql")
			@RequestParam(value="file", required=true) String file)
			
					throws CatastoServiceException {
		
		LOG.debug("GET REQUEST per esecuzione script SQL.");
		String result = this.service.eseguiScriptSQL(file, hostname, database, port, username);
		LOG.debug("GET REQUEST eseguita per esecuzione script SQL eseguito con successo.");
		
		return ResponseEntity.ok(result);
		
	}
	
}
