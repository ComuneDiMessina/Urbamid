package it.eng.tz.urbamid.catasto.web.controller;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.eng.tz.urbamid.catasto.exception.CatastoServiceException;
import it.eng.tz.urbamid.catasto.shapefile.service.ShapefileService;
import it.eng.tz.urbamid.catasto.web.dto.ParticellaCustomDTO;
import it.eng.tz.urbamid.catasto.web.response.ResponseData;

@RestController
@RequestMapping("/catasto/rest/api/exportShapefile")
@Api(value = "Export Shape File", tags= {"Shapefile"})
public class ExportShapefileController {
	
	/**
	 * Logger
	 */
	private final Logger LOG = LoggerFactory.getLogger(this.getClass().getName());
	
	/**
	 * Service
	 */
	private final ShapefileService service;
	
	
	public static final String SUCCESS = "Success";
	public static final String ERROR = "Error";
	
	/**
	 * Costruttore.
	 * 
	 * @param service è il service.
	 */
	public ExportShapefileController( ShapefileService service) {
		Assert.notNull(service, "ShapefileService MUST not be null but don't panic!");
		this.service = service;
	}
	
	/**
	 * 
	 * @param importType
	 * @return
	 * @throws CatastoServiceException
	 */
	@ApiOperation(value="Export piano particellare in shapeFile", response=String.class, code=200)
	@ApiResponses(value = { 
		@ApiResponse(code = 500, message = "Internal server error"),
		@ApiResponse(code = 404, message = "Not found") 
	})
	@PostMapping("/pianoParticellare")
	public @ResponseBody ResponseData exportPianoParticellareShape(
			HttpServletRequest request, 
			@RequestBody(required = true) List<ParticellaCustomDTO> lsParticelle,
			@RequestParam String titolo) {
		
		LOG.debug("POST exportPianoParticellareShape");
		ResponseData response = null;
		try {
			
			LocalTime currentTime = LocalTime.now();
			titolo += ""+(currentTime.getHour()<10 ? "0"+currentTime.getHour() : currentTime.getHour()) + 
						(currentTime.getMinute()<10?"0"+currentTime.getMinute():currentTime.getMinute());
			
			File output = service.exportShapefile(lsParticelle, titolo);
			
			if (output!=null) {
				
				byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray( output ));
				String base64String = new String(encoded, StandardCharsets.US_ASCII);
				response = new ResponseData(true, base64String, 0, 0, null);
				response.setMessage(SUCCESS);
			} else {
				
				response = new ResponseData(true, null, 0, 0, null);
				response.setMessage(ERROR);
			}
			
		} catch (Exception e) {
			LOG.error("Error in exportPianoParticellareShape", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

}
