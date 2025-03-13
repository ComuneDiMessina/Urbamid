package it.eng.tz.urbamid.toponomastica.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.eng.tz.urbamid.toponomastica.exception.ToponomasticaServiceException;
import it.eng.tz.urbamid.toponomastica.service.ViarioService;
import it.eng.tz.urbamid.toponomastica.web.dto.GeocodingReverseGeocodingDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.NumCivicoDto;
import it.eng.tz.urbamid.toponomastica.web.dto.ToponimoDto;
import it.eng.tz.urbamid.toponomastica.web.dto.ToponimoStradaleDTO;
import it.eng.tz.urbamid.toponomastica.web.response.BaseResponse;

@RestController
@RequestMapping("/toponomastica")
@Api(value = "urbamid toponomastica", tags= {"Toponomastica"})
public class ToponomasticaController extends AbstractController{

	
	@Autowired
	private ViarioService viarioService;
	
	private static final Logger logger = LoggerFactory.getLogger(ToponomasticaController.class.getName());
	
	@ApiOperation("Recupera l'elenco di strade corrispondenti ai criteri di ricerca")
	@GetMapping("/viario/strade")
	public ResponseEntity<BaseResponse<List<ToponimoDto>>> findStreetByName(HttpServletRequest request, 
			@ApiParam(value = "Nome della strada", required = true)
			@RequestParam(name="search", required=true) String denominazione) {
		
		
		try {
			List<ToponimoDto> response = new ArrayList<ToponimoDto>();
			logger.debug("GET findStreetByName");
			if (null!=denominazione && denominazione.length()>3)
				response = viarioService.findStreetByName(denominazione);
			else 
				return koResponseError(new ToponomasticaServiceException("Non è stato possibile recuperare il toponimo stradale"));
			
			return okResponse(response);

		} catch (Exception e) {
			logger.error("Error in findStreetByName", e);
			return koResponseError(e);
		}		 
	}
	
	@ApiOperation("Il metodo si occupa di cercare i civici in base al codice della strada")	
	@GetMapping("/viario/strade/{codStrada}/numeriCivici")
	public ResponseEntity<BaseResponse<List<NumCivicoDto>>> findNumberByStreet(HttpServletRequest request, 
			@ApiParam(value = "Codice della strada", required = true)
			@PathVariable(name="codStrada") Long codStrada) {
		
		try {
			
			logger.debug("GET findStreetByName");
			List<NumCivicoDto> numCivici = viarioService.findNumberByStreet(codStrada);
			
			return okResponse(numCivici);
		} catch (Exception e) {
			
			logger.error("Error in findStreetByName", e);
			
			return koResponseError(e);
		}
	}
	
	@ApiOperation("Il metodo si occupa di cercare i toponimi stradali in base alla denominazione ufficiale")
	@RequestMapping(value = "/viario/toponimo", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public ResponseEntity<BaseResponse<List<GeocodingReverseGeocodingDTO>>> geocodingToponimo(HttpServletRequest request, 
			@ApiParam(value = "Denominazione ufficiale", required = true)
			@RequestParam(name="search", required = true) String denominazione) {
	
		try {	
			logger.debug("geocodingToponimo");
		
			List<GeocodingReverseGeocodingDTO> listaToponimi = viarioService.geocodingToponimo(denominazione.toUpperCase());
			
			return okResponse(listaToponimi);
		} catch (Exception e) {
			
			logger.error("Error in findStreetByName", e);
			return koResponseError(e);
		}
		
	}
	
	@ApiOperation("Il metodo si occupa di cercare i toponimi stradali in base al WKT della strada")
	@RequestMapping(value = "/viario/reverseToponimo", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public ResponseEntity<BaseResponse<List<ToponimoStradaleDTO>>> reverseGeocodingToponimo(HttpServletRequest request, 
			@ApiParam(value = "WKT della strada", required = true)
			@RequestParam(name="WKT", required = true) String WKT) {
	
		try {	
			logger.debug("geocodingToponimo");
		
			List<ToponimoStradaleDTO> listaToponimi = null;
			
			return okResponse(listaToponimi);
		} catch (Exception e) {
			
			logger.error("Error in findStreetByName", e);
			return koResponseError(e);
		}
		
	}
	
}
