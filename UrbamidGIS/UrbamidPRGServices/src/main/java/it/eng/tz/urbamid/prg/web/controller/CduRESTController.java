package it.eng.tz.urbamid.prg.web.controller;

import java.io.File;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.eng.tz.urbamid.prg.filter.CduFilter;
import it.eng.tz.urbamid.prg.service.CduService;
import it.eng.tz.urbamid.prg.util.DateUtils;
import it.eng.tz.urbamid.prg.web.dto.CduDTO;
import it.eng.tz.urbamid.prg.web.dto.PagedResult;
import it.eng.tz.urbamid.prg.web.response.ResponseData;

@RestController
@RequestMapping("/prg/rest/api/cdu")
@Api(value = "urbamid codice", tags= {"Cdu"})
public class CduRESTController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(CduRESTController.class.getName());

	public static final String SUCCESS = "Success";
	public static final String ERROR = "Error";

	@Autowired
	private CduService cduService;

	@ApiOperation("Recupera il protocollo se esiste")
	@GetMapping("/cercaCdu")
	public @ResponseBody ResponseData cercaCdu(HttpServletRequest request,
			@RequestParam(value="protocollo", required=false) String protocollo,
			@RequestParam(value="dataCreazione", required=false) String dataCreazione,
			@RequestParam(value="page", required=false, defaultValue="0") int page,
			@RequestParam(value="size", required=false, defaultValue="1") int size,
			@RequestParam(value="draw", required=false, defaultValue="1") int draw) {
		logger.debug("GET cercaCdu");
		ResponseData response = null;
		try {
			CduFilter filter = new CduFilter();
			filter.setProtocollo(protocollo);
			filter.setDataCreazione(DateUtils.stringToDate(dataCreazione, DateUtils.PATTERN_YYYY_MM_DD_SLASH_REVERSED));
			filter.setPageIndex(page);
			filter.setPageSize(size);
			PagedResult<CduDTO> result = cduService.cercaCdu(filter);
			response = new ResponseData(true, result.getContent(), (int)result.getTotalElements(), (int)result.getTotalElements(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in cercaCdu", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Esporta il documento cdu in base al protocollo")
	@GetMapping("/downloadCduAnagrafica")
	public @ResponseBody ResponseData downloadCduAnagrafica(HttpServletRequest request, 
			@RequestParam String protocollo) {
		logger.debug("POST downloadCduAnagrafica");
		ResponseData response = null;
		try {
			File file = cduService.downloadCduAnagrafica(protocollo);
		    byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
		    String base64String = new String(encoded, StandardCharsets.US_ASCII);
		    response = new ResponseData(true, base64String, 0, 0, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in downloadCduAnagrafica", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Esporta il documento cdu in base al protocollo")
	@PostMapping("/downloadCduAnagraficaByProtocollo")
	public @ResponseBody ResponseData downloadCduAnagraficaByProtocollo(HttpServletRequest request, 
			@RequestParam String protocollo) {
		
		logger.debug("POST downloadCduAnagraficaByProtocollo");
		ResponseData response = null;
		try {
			
			File file = cduService.downloadCduAnagrafica(protocollo);
			logger.debug("path del file: "+file.getAbsolutePath());
		    byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
		    String base64String = new String(encoded, StandardCharsets.US_ASCII);
		    response = new ResponseData(true, base64String, 0, 0, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			
			logger.error("Error in downloadCduAnagraficaByProtocollo", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
}
