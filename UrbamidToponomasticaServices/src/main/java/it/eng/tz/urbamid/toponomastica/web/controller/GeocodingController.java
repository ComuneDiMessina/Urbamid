package it.eng.tz.urbamid.toponomastica.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.eng.tz.urbamid.toponomastica.service.GeocodingReverseGeocodingService;
import it.eng.tz.urbamid.toponomastica.web.dto.GeocodingReverseGeocodingDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.RequestReverseGeocodingDTO;
import it.eng.tz.urbamid.toponomastica.web.response.ResponseData;

@RestController
@RequestMapping("toponomastica/service")
@Api(value = "urbamid toponomastica", tags= {"Geocoding"})
public class GeocodingController {

	private static final Logger logger = LoggerFactory.getLogger(GeocodingController.class.getName());
	
	public static final String START = "START >>> {}";
	public static final String SUCCESS = "Success";
	public static final String ERROR = "Error";
	public static final String END = "END <<< {}";
	
	@Autowired
	private GeocodingReverseGeocodingService service;
	
	/**
	 * Il metodo si occupa del recupero dalla tabella u_topo_toponimo_geocoding del toponimo passando come input la denominazione ufficiale
	 * @param request
	 * @param denominazione
	 * @return
	 */
	@ApiOperation("Il metodo si occupa del geocoding, passandogli una denominazione ufficiale")
	@RequestMapping(value="/geocoding", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData geocoding(HttpServletRequest request,
			@ApiParam(value = "Denominazione ufficiale", required = true)
			@RequestParam(name = "search") String denominazione) {
		
		String idLog = "geocoding";
		logger.info(START, idLog);
		
		ResponseData response = null;
		try {
			
			if(StringUtils.hasText(denominazione)) {
				List<GeocodingReverseGeocodingDTO> listaToponimi = service.geocoding(denominazione);
				
				response = new ResponseData(true, listaToponimi, listaToponimi.size(), listaToponimi.size(), null);
				response.setMessage(SUCCESS);
			} else {
				
				response = new ResponseData(true, null, 0, 0, null);
				response.setMessage(ERROR);
			}
			
		} catch (Exception e) {
			logger.error("ERRORE {} >>> {}", idLog, e.getMessage());
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
			
		}
		return response;
		
	}
	
	/**
	 * Il metodo si occupa del recupero dalla tabella u_topo_toponimo_geocoding del toponimo passando come input la denominazione ufficiale
	 * @param request
	 * @param denominazione
	 * @return
	 */
	@ApiOperation("Il metodo si occupa del reverse geocoding, passandogli un la longitudine e la latitudine")
	@RequestMapping(value="/reverseGeocoding", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public @ResponseBody ResponseData reverseGeocoding(HttpServletRequest request,
			@ApiParam(value = "Filtro di ricerca", required = true)
			@RequestBody RequestReverseGeocodingDTO reverseGeocoding) {
		
		String idLog = "reverseGeocoding";
		logger.info(START, idLog);
		
		ResponseData response = null;
		try {
			
			if(reverseGeocoding != null) {
				List<GeocodingReverseGeocodingDTO> listaToponimi = service.reverseGeocoding(reverseGeocoding);
				
				response = new ResponseData(true, listaToponimi, listaToponimi.size(), listaToponimi.size(), null);
				response.setMessage(SUCCESS);
			} else {
				
				response = new ResponseData(true, null, 0, 0, null);
				response.setMessage(ERROR);
			}
			
		} catch (Exception e) {
			logger.error("ERRORE {} >>> {}", idLog, e.getMessage());
			response = new ResponseData(false, null,0, 0, null);
			response.setMessage(ERROR);
			
		}
		return response;
		
	}
	
}
