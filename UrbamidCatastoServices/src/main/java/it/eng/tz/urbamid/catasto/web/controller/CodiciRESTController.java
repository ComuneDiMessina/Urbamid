package it.eng.tz.urbamid.catasto.web.controller;

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
import it.eng.tz.urbamid.catasto.service.CodiciService;
import it.eng.tz.urbamid.catasto.web.dto.TipologicaDTO;
import it.eng.tz.urbamid.catasto.web.response.ResponseData;

@RestController
@RequestMapping("/catasto/rest/api/codici")
@Api(value = "urbamid codice", tags= {"TipologicheCatasto"})
public class CodiciRESTController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(CodiciRESTController.class.getName());

	public static final String SUCCESS = "Success";
	public static final String ERROR = "Error";

	@Autowired
	private CodiciService codiciService;

	@ApiOperation("Recupera la lista delle categorie catastali")
	@GetMapping("/categorieCatastali")
	public @ResponseBody ResponseData categorieCatastali(HttpServletRequest request ) {
		logger.debug("GET categorieCatastali");
		ResponseData response = null;
		try {
			List<TipologicaDTO> listaCategorieCatastali;
			listaCategorieCatastali = codiciService.categorieCatastali();
			response = new ResponseData(true, listaCategorieCatastali, listaCategorieCatastali.size(), listaCategorieCatastali.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in categorieCatastali", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Recupera la lista dei codici diritto")
	@GetMapping("/codiciDiritto")
	public @ResponseBody ResponseData codiciDiritto(HttpServletRequest request ) {
		logger.debug("GET codiciDiritto");
		ResponseData response = null;
		try {
			List<TipologicaDTO> listaCodiciDiritto;
			listaCodiciDiritto = codiciService.codiciDiritto();
			response = new ResponseData(true, listaCodiciDiritto, listaCodiciDiritto.size(), listaCodiciDiritto.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in codiciDiritto", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Recupera la lista dei codici qualita'")
	@GetMapping("/codiciQualita")
	public @ResponseBody ResponseData codiciQualita(HttpServletRequest request ) {
		logger.debug("GET codiciQualita");
		ResponseData response = null;
		try {
			List<TipologicaDTO> listaCodiciQualita;
			listaCodiciQualita = codiciService.codiciQualita();
			response = new ResponseData(true, listaCodiciQualita, listaCodiciQualita.size(), listaCodiciQualita.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in codiciQualita", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Recupera la lista dei comuni")
	@GetMapping("/getComuni")
	public @ResponseBody ResponseData getComuni(HttpServletRequest request ) {
		logger.debug("GET getComuni");
		ResponseData response = null;
		try {
			List<TipologicaDTO> listaComuni;
			listaComuni = codiciService.getComuni();
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
			listaProvince = codiciService.getProvince();
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
			listaComuni = codiciService.getComuniByProvincia(idProvincia);
			response = new ResponseData(true, listaComuni, listaComuni.size(), listaComuni.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in getComuniByProvincia", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

}
