package it.eng.tz.urbamid.catasto.web.controller;

import java.util.HashMap;
import java.util.Map;

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
import it.eng.tz.urbamid.catasto.persistence.util.TabellaStorico;
import it.eng.tz.urbamid.catasto.storico.service.StoricoService;
import it.eng.tz.urbamid.catasto.web.dto.ParametriStoricizzazioneDTO;

@RestController
@RequestMapping("/catasto/rest/api/storico")
@Api(value = "urbamid storico", tags= {"StoricoTabelle"})
public class StoricoRESTController {
	
	/**
	 * Logger
	 */
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * Service
	 */
	private final StoricoService service;
	
	/**
	 * Costruttore.
	 * 
	 * @param service è il service per la storicizzazione.
	 */
	public StoricoRESTController(StoricoService service) {
		Assert.notNull(service, "StoricoService must not be null but don't panic!");
		this.service = service;
	}
	
	@ApiOperation(value="Avvia il processo di storicizzazione", response=Map.class, code=200)
	@ApiResponses(value = { 
		@ApiResponse(code = 500, message = "Internal server error"),
		@ApiResponse(code = 404, message = "Not found") 
	})
	@PostMapping
	public ResponseEntity<Map<String,Long>> avviaProcessoBatchAttualita(
			
			@ApiParam(name="parametri", value="I parametri di storicizzazione", required=true)
			@RequestBody(required=true) @Valid ParametriStoricizzazioneDTO parametri
			
			) throws CatastoServiceException {
		
	
		LOG.debug("POST REQUEST per avvio processo storicizzazione dati catastali.");
		Map<TabellaStorico, Long> map = this.service.storicizza(parametri.getDataInizioValidita(), parametri.getDataFineValidita());
		LOG.debug("POST REQUEST il processo di storicizzazione terminato con successo.");
		
		Map<String,Long> body = new HashMap<>();
		map.forEach( (tabella, numeroRigheInserite) -> body.put(tabella.table(), numeroRigheInserite));
		
		return ResponseEntity.ok(body);
		
	}
	
}
