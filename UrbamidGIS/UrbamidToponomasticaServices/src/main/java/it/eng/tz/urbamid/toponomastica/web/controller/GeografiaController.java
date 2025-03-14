package it.eng.tz.urbamid.toponomastica.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.eng.tz.urbamid.toponomastica.service.GeografiaService;
import it.eng.tz.urbamid.toponomastica.web.dto.TipologicaDTO;
import it.eng.tz.urbamid.toponomastica.web.response.ResponseData;

@RestController
@RequestMapping("/toponomastica/geografia")
@Api(value = "urbamid toponomastica", tags= {"Geografia"})
public class GeografiaController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(GeografiaController.class.getName());

	public static final String SUCCESS = "Success";
	public static final String ERROR = "Error";

	@Autowired
	private GeografiaService geografiaService;

	@ApiOperation("Recupera la lista dei comuni")
	@GetMapping("/getComuni")
	public @ResponseBody ResponseData getComuni(HttpServletRequest request ) {
		logger.debug("GET getComuni");
		ResponseData response = null;
		try {
			List<TipologicaDTO> listaComuni;
			listaComuni = geografiaService.getComuni();
			response = new ResponseData(true, listaComuni, listaComuni.size(), listaComuni.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in getComuni", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Recupera la lista dei comuni")
	@GetMapping("/getProvince")
	public @ResponseBody ResponseData getProvince(HttpServletRequest request ) {
		logger.debug("GET getProvince");
		ResponseData response = null;
		try {
			List<TipologicaDTO> listaProvince;
			listaProvince = geografiaService.getProvince();
			response = new ResponseData(true, listaProvince, listaProvince.size(), listaProvince.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in getProvince", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Recupera la lista dei comuni in base all'id della provincia")
	@GetMapping("/getComuniByProvincia")
	public @ResponseBody ResponseData getComuniByProvincia(HttpServletRequest request,
			@RequestParam(name="idProvincia") Long idProvincia) {
		logger.debug("GET getComuniByProvincia");
		ResponseData response = null;
		try {
			List<TipologicaDTO> listaComuni;
			listaComuni = geografiaService.getComuniByProvincia(idProvincia);
			response = new ResponseData(true, listaComuni, listaComuni.size(), listaComuni.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in getComuniByProvincia", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@ApiOperation("Recupera la lista dei comuni in base all'id della provincia di Messina")
	@GetMapping("/getComuniByMessina")
	public @ResponseBody ResponseData getComuniByMessina(HttpServletRequest request) {
		logger.debug("GET getComuniByMessina");
		ResponseData response = null;
		try {
			List<TipologicaDTO> listaComuni;
			listaComuni = geografiaService.getComuniByMessina();
			response = new ResponseData(true, listaComuni, listaComuni.size(), listaComuni.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in getComuniByMessina", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

}
