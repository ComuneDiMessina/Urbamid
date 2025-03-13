package it.eng.tz.urbamid.web.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.eng.tz.urbamid.exception.UrbamidServiceException;
import it.eng.tz.urbamid.web.dao.bean.search.ResponseData;
import it.eng.tz.urbamid.web.dto.GeocodingReverseGeocodingDTO;
import it.eng.tz.urbamid.web.dto.NumCivicoDTO;
import it.eng.tz.urbamid.web.dto.RequestReverseGeocodingDTO;
import it.eng.tz.urbamid.web.dto.ToponimoDTO;
import it.eng.tz.urbamid.web.services.ViarioService;

@RestController
@RequestMapping(value = "services/viario", produces = MediaType.APPLICATION_JSON_VALUE)
public class ViarioRESTController {
	
	private static final String START = "START >>> {}";
	private static final String SUCCESS = "SUCCESS";
	private static final String ERROR = "ERROR";

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ViarioService service;
		
	@GetMapping("/strade")
	public @ResponseBody ResponseData findStreetByName(HttpServletRequest request, @RequestParam(name="search", required=true) String denominazione) throws Exception {
		
		LOG.info(START, "GET findStreetByName");
		ResponseData response = null;
		
		try {
			LOG.debug("GET findStreetByName");
			
			List<ToponimoDTO> toponimi = service.findStreetByName(denominazione);
			
			if (toponimi!=null && toponimi.size()>0) {
				response = new ResponseData(true, toponimi, toponimi.size(), toponimi.size(), null);
				response.setMessage(SUCCESS);
			
				LOG.debug("GET findStreetByName eseguita con successo. Sono stati ricercati i seguenti toponimi stradali con questa denominazione: {}", denominazione);
			} else {
				response = new ResponseData(false, null, 0, 0, null);
				response.setMessage(ERROR);
				
				LOG.debug("GET findNumberByStreet eseguita con successo. Non è stato possibile ricercare i civici con questa denominazione: {}", denominazione);
			}

			
		} catch (UrbamidServiceException ese) {
			LOG.error("Error in geocodingToponimo {}", ese.getMessage());
			
			response = new ResponseData(false, null, 0, 0, ese.getMessage());
			response.setMessage(ERROR);
		}
		
		return response;
		
	}
	
		
	@GetMapping("/strade/{codStrada}/numeriCivici")
	public @ResponseBody ResponseData findNumberByStreet(HttpServletRequest request, @PathVariable(name="codStrada") Long codStrada) throws Exception {
		
		LOG.info(START, "GET findNumberByStreet");
		ResponseData response = null;

		try {
			LOG.debug("GET findNumberByStreet");
			
			List<NumCivicoDTO> numCivici = service.findNumberByStreet(codStrada);
			
			if (numCivici!=null && numCivici.size()>0) {
				response = new ResponseData(true, numCivici, numCivici.size(), numCivici.size(), null);
				response.setMessage(SUCCESS);
				
				LOG.debug("GET findNumberByStreet eseguita con successo. Sono stati ricercati i seguenti civici con questo codice: {}", codStrada);
			} else {
				response = new ResponseData(false, null, 0, 0, null);
				response.setMessage(ERROR);
				
				LOG.debug("GET findNumberByStreet eseguita con successo. Non è stato possibile ricercare i civici con questo codice: {}", codStrada);
			}
				
		} catch (UrbamidServiceException ese) {
			LOG.error("Error in geocodingToponimo {}", ese.getMessage());
			
			response = new ResponseData(false, null, 0, 0, ese.getMessage());
			response.setMessage(ERROR);
		}
		
		return response;
		
	}
	
	@GetMapping("/toponimo")
	public @ResponseBody ResponseData geocodingToponimo(HttpServletRequest request, @RequestParam(name="search") String denominazione) throws Exception {
		
		LOG.info(START, "GET geocodingToponimo");
		ResponseData response = null;
		
		try {
			LOG.debug("GET geocodingToponimo");
			
			List<GeocodingReverseGeocodingDTO> listaToponimi = service.geocodingToponimo(denominazione);
			
			if(listaToponimi != null && listaToponimi.size() > 0) {
				response = new ResponseData(true, listaToponimi, listaToponimi.size(), listaToponimi.size(), null);
				response.setMessage(SUCCESS);
			
				LOG.debug("GET geocodingToponimo eseguita con successo. Sono stati ricercati i seguenti toponimi stradali: {} con questa denominazione: {}", listaToponimi, denominazione);
			} else {
				response = new ResponseData(false, null, 0, 0, null);
				response.setMessage(ERROR);
			
				LOG.error("GET geocodingToponimo non eseguita con successo. Non è stato possibile ricercare i toponimi stradali con questa denominazione: {}", denominazione);
			}
			
			
		} catch (UrbamidServiceException ese) {
			LOG.error("Error in geocodingToponimo {}", ese.getMessage());
			
			response = new ResponseData(false, null, 0, 0, ese.getMessage());
			response.setMessage(ERROR);
		}
		
		return response;
		
	}
	
	@GetMapping("/reverseToponimo")
	public @ResponseBody ResponseData reverseGeocodingToponimo(HttpServletRequest request, @RequestBody RequestReverseGeocodingDTO reverseGeocoding) throws Exception {
		
		LOG.info(START, "GET reverseToponimo");
		ResponseData response = null;
		
		try {
			LOG.debug("GET reverseToponimo");
			
			List<GeocodingReverseGeocodingDTO> listaToponimi = service.reverseGeocodingToponimo(reverseGeocoding);
			
			if(listaToponimi != null && listaToponimi.size() > 0) {
				response = new ResponseData(true, listaToponimi, listaToponimi.size(), listaToponimi.size(), null);
				response.setMessage(SUCCESS);
			
				LOG.debug("GET reverseToponimo eseguita con successo. Sono stati ricercati i seguenti toponimi stradali: {} ", listaToponimi);
			} else {
				response = new ResponseData(false, null, 0, 0, null);
				response.setMessage(ERROR);
			
				LOG.error("GET reverseToponimo non eseguita con successo. Non è stato possibile ricercare i toponimi stradali.");
			}
			
			
		} catch (UrbamidServiceException ese) {
			LOG.error("Error in reverseToponimo {}", ese.getMessage());
			
			response = new ResponseData(false, null, 0, 0, ese.getMessage());
			response.setMessage(ERROR);
		}
		
		return response;
		
	}
	
}
