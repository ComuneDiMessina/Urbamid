package it.eng.tz.urbamid.catasto.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;
import it.eng.tz.urbamid.catasto.exception.CatastoServiceException;
import it.eng.tz.urbamid.catasto.shapefile.service.ShapefileService;
import it.eng.tz.urbamid.catasto.util.ImportType;

@RestController
@RequestMapping("/catasto/rest/api/import-shapefile")
@Api(value = "urbamid shapefile", tags= {"Shapefile"})
public class ShapefileRESTController {
	
	/**
	 * Logger
	 */
	private final Logger LOG = LoggerFactory.getLogger(this.getClass().getName());
	
	/**
	 * Service
	 */
	private final ShapefileService service;
	
	/**
	 * Costruttore.
	 * 
	 * @param service è il service.
	 */
	public ShapefileRESTController( ShapefileService service ) {
		Assert.notNull(service, "ShapefileService MUST not be null but don't panic!");
		this.service = service;
	}
	
	/**
	 * 
	 * @param importType
	 * @return
	 * @throws CatastoServiceException
	 */
	@ApiOperation(value="Importa tutti gli shapefile nel database", response=String.class, code=200)
	@ApiResponses(value = { 
		@ApiResponse(code = 500, message = "Internal server error"),
		@ApiResponse(code = 404, message = "Not found") 
	})
	@PostMapping("/{importType}")
	public ResponseEntity<String> importaTuttiGliShapefile(
			
			@ApiParam(value="Tipo di import", required=true, example="ATTUALITA", examples=@Example({
				@ExampleProperty("ATTUALITA"),
				@ExampleProperty("AGGIORNAMENTO")})
			)
			@PathVariable(value="importType", required=true) final ImportType importType )
					throws CatastoServiceException {
		LOG.debug("Importo tutti gli shapefile per il tipo di import {}.", importType.name());
		service.importaShapefile(importType);
		return ResponseEntity.ok("OK");
	}

}
