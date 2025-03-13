package it.eng.tz.urbamid.web.pageController;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import it.eng.tz.urbamid.web.dao.bean.search.ResponseData;
import it.eng.tz.urbamid.web.dto.InterrogazionePianoDTO;
import it.eng.tz.urbamid.web.dto.VarianteDTO;
import it.eng.tz.urbamid.web.services.PianoRegolatoreService;

@Controller(value = "Consultazione Piano Regolatore Controller")
@RequestMapping("/prgConsultazioneController")
public class ConsultazionePianoRegolatoreCtrl extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(ConsultazionePianoRegolatoreCtrl.class.getName());

	@Autowired
	private PianoRegolatoreService prgService;
	
	public static final String SUCCESS = "Success";
	public static final String ERROR = "Error";

	
	@PostMapping(value="/getAllVariantiByDate", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData getAllVariantiByDate(HttpServletRequest request) {
		logger.debug("POST getAllVariantiByDate");
		ResponseData response = null;
		try {
			
			List<VarianteDTO> result = prgService.getAllVariantiByDate( );
			response = new ResponseData(true, result, result.size(), result.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			
			logger.error("Error in getAllVariantiByDate", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@GetMapping(value="/cercaProtocollo", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseData cercaProtocollo(HttpServletRequest request, 
			@RequestParam(name="protocollo") String protocollo) {
		logger.debug("GET cercaProtocollo");
		ResponseData response = null;
		try {
			
			String result = prgService.cercaProtocollo(protocollo);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			
			logger.error("Error in cercaProtocollo", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@PostMapping(value="/downloadCdu", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody void downloadCdu(HttpServletRequest request, 
			@RequestBody(required = true) InterrogazionePianoDTO interrogazionePianoDTO, HttpServletResponse response) {
		logger.debug("POST downloadCdu");
		try {
			
			PrintWriter printWriter = response.getWriter();
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export.pdf");
			printWriter.write(prgService.downloadCdu(interrogazionePianoDTO));
		} catch (Exception e) {
			
			logger.error("Error in downloadCdu", e);
		}
	}
	
	@PostMapping(value="/downloadCduByProtocollo", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody void downloadCduByProtocollo(HttpServletRequest request, 
			@RequestParam(value = "protocollo", required = true) String protocollo, HttpServletResponse response) {
		logger.debug("POST downloadCduByProtocollo");
		try {
			
			PrintWriter printWriter = response.getWriter();
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export.pdf");
			printWriter.write(prgService.downloadCduByProtocollo(protocollo));
		} catch (Exception e) {
			
			logger.error("Error in downloadCduByProtocollo", e);
		}
	}
}