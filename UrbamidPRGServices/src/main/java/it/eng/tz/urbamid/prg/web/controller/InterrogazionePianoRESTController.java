package it.eng.tz.urbamid.prg.web.controller;

import java.io.File;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.eng.tz.urbamid.prg.service.InterrogazionePianoService;
import it.eng.tz.urbamid.prg.web.dto.InterrogazionePianoDTO;
import it.eng.tz.urbamid.prg.web.response.ResponseData;

@RestController
@RequestMapping("/prg/rest/api/interrogazionepiano")
@Api(value = "urbamid codice", tags= {"Variante"})
public class InterrogazionePianoRESTController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(InterrogazionePianoRESTController.class.getName());

	public static final String SUCCESS = "Success";
	public static final String ERROR = "Error";
	
	@Autowired
	private InterrogazionePianoService interrogazionePianoService;

	@ApiOperation("Elabora i dati e restituisce il cdu")
	@PostMapping("/downloadCdu")
	public @ResponseBody ResponseData downloadCdu(HttpServletRequest request, 
			@RequestBody(required = true) InterrogazionePianoDTO dto) {
		logger.debug("POST downloadCdu");
		ResponseData response = null;
		try {
			File file = interrogazionePianoService.downloadCdu(dto);
		    byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
		    String base64String = new String(encoded, StandardCharsets.US_ASCII);
		    response = new ResponseData(true, base64String, 0, 0, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in downloadCdu", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}


}
