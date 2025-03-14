package it.eng.tz.urbamid.prg.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.eng.tz.urbamid.prg.service.StratiInformativiService;
import it.eng.tz.urbamid.prg.web.dto.AggiuntaLayerDTO;
import it.eng.tz.urbamid.prg.web.dto.CartografiaVarianteLayerDTO;
import it.eng.tz.urbamid.prg.web.dto.CatalogoGruppoDTO;
import it.eng.tz.urbamid.prg.web.response.ResponseData;

@RestController
@RequestMapping("/prg/rest/api/stratiinformativi")
@Api(value = "urbamid codice", tags= {"Variante"})
public class StratiInformativiRESTController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(StratiInformativiRESTController.class.getName());

	public static final String SUCCESS = "Success";
	public static final String ERROR = "Error";

	@Autowired
	private StratiInformativiService stratiInformativiService;

	@ApiOperation("Salva un nuovo gruppo layer")
	@GetMapping("/salvaNuovoGruppo")
	public @ResponseBody ResponseData salvaNuovoGruppo(HttpServletRequest request, 
			@ApiParam( value = "Nome del gruppo layer", required = false, example="1" )
			@RequestParam(name="nomeGruppo") String nomeGruppo) {
		logger.debug("GET salvaNuovoGruppo");
		ResponseData response = null;
		try {
			String result = stratiInformativiService.salvaNuovoGruppo(nomeGruppo);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in salvaNuovoGruppo", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Recupera il catalogo completo")
	@GetMapping("/reperimentoCatalogoVariante")
	public @ResponseBody ResponseData reperimentoCatalogoVariante(HttpServletRequest request) {
		logger.debug("GET reperimentoCatalogoVariante");
		ResponseData response = null;
		try {
			List<CatalogoGruppoDTO> result = stratiInformativiService.reperimentoCatalogoVariante();
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in reperimentoCatalogoVariante", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Salva un nuovo layer")
	@PostMapping("/salvaLayer")
	public @ResponseBody ResponseData salvaLayer(HttpServletRequest request, 
			@RequestBody(required = true) AggiuntaLayerDTO layer) {
		logger.debug("POST salvaLayer");
		ResponseData response = null;
		try {
			AggiuntaLayerDTO result = stratiInformativiService.salvaLayer(layer);
			response = new ResponseData(true, null, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in salvaLayer", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Cancella il layer in base all'id")
	@GetMapping("/cancellaLayer")
	public @ResponseBody ResponseData cancellaLayer(HttpServletRequest request, 
			@ApiParam( value = "Id del layer", required = false, example="1" )
			@RequestParam(name="idLayer") Long idLayer) {
		logger.debug("GET cancellaLayer");
		ResponseData response = null;
		try {
			String result = stratiInformativiService.cancellaLayer(idLayer);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in cancellaLayer", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Cancella il gruppo in base all'id")
	@GetMapping("/cancellaGruppo")
	public @ResponseBody ResponseData cancellaGruppo(HttpServletRequest request, 
			@ApiParam( value = "Id del gruppo", required = false, example="1" )
			@RequestParam(name="idGruppo") Long idGruppo) {
		logger.debug("GET cancellaGruppo");
		ResponseData response = null;
		try {
			String result = stratiInformativiService.cancellaGruppo(idGruppo);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in cancellaGruppo", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}
	
	@ApiOperation("Trova il nome della variante in base al nome del layer")
	@GetMapping("/varianteByNomeLayer")
	public @ResponseBody ResponseData varianteByNomeLayer(HttpServletRequest request, 
			@ApiParam( value = "Nome del layer", required = true)
			@RequestParam(name="nomeLayer") String nomeLayer) {
		logger.debug("GET varianteByNomeLayer");
		ResponseData response = null;
		try {
			List<CartografiaVarianteLayerDTO> result = stratiInformativiService.varianteByNomeLayer(nomeLayer);
			response = new ResponseData(true, result, result.size(), result.size(), null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in varianteByNomeLayer", e);
			response = new ResponseData(false, null, 0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

	@ApiOperation("Recupera le colonne del layer")
	@GetMapping("/reperimentoColonneLayer")
	public @ResponseBody ResponseData reperimentoColonneLayer(HttpServletRequest request,
			@RequestParam(name="nomeLayer") String nomeLayer) {
		logger.debug("GET reperimentoColonneLayer");
		ResponseData response = null;
		try {
			List<String> result = stratiInformativiService.reperimentoColonneLayer(nomeLayer);
			response = new ResponseData(true, result, 1, 1, null);
			response.setMessage(SUCCESS);
		} catch (Exception e) {
			logger.error("Error in reperimentoColonneLayer", e);
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
		}
		return response;
	}

}
