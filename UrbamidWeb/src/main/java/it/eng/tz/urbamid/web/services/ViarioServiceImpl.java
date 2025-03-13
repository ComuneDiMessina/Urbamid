package it.eng.tz.urbamid.web.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.eng.tz.urbamid.exception.UrbamidServiceException;
import it.eng.tz.urbamid.security.model.BaseResponse;
import it.eng.tz.urbamid.security.model.ResponseData;
import it.eng.tz.urbamid.security.service.RestService;
import it.eng.tz.urbamid.web.dto.GeocodingReverseGeocodingDTO;
import it.eng.tz.urbamid.web.dto.NumCivicoDTO;
import it.eng.tz.urbamid.web.dto.RequestReverseGeocodingDTO;
import it.eng.tz.urbamid.web.dto.ToponimoDTO;
import it.eng.tz.urbamid.web.util.IConstants;

@Service(value = "viario")
public class ViarioServiceImpl implements ViarioService {

	@Autowired
	private Environment env;
	
	@Autowired
	protected RestService restService;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ToponimoDTO> findStreetByName(String denom) throws Exception {
		
		List<ToponimoDTO> result = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_VIARIO)+"?search=" + denom;
		
		BaseResponse<List<ToponimoDTO>> response = restService.restGet(url);
		
		if(response!=null && response.getDescrizioneEsito().equalsIgnoreCase("success")) {
			result = new ObjectMapper().convertValue(response.getPayload(), new TypeReference<List<ToponimoDTO>>() {});		
		}
		
		return result;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NumCivicoDTO> findNumberByStreet(Long codStrada) throws Exception {
		List<NumCivicoDTO> result = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_VIARIO) + "/" +codStrada + "/numeriCivici";
		
		BaseResponse<List<NumCivicoDTO>> response = restService.restGet(url);
		
		if(response!=null && response.getDescrizioneEsito().equalsIgnoreCase("success"))
			result = new ObjectMapper().convertValue(response.getPayload(), new TypeReference<List<NumCivicoDTO>>() {});		
		else
			throw new UrbamidServiceException("Errore nella ricerca dei civici");
		
		return result;
	}
	
	@Override
	public List<GeocodingReverseGeocodingDTO> geocodingToponimo(String denominazione) throws Exception {
		List<GeocodingReverseGeocodingDTO> result = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_GEOCODING_TOPONIMO) + "?search=" + denominazione; 
		
		ResponseData response = restService.restPostTable(url, null);
		
		if(response != null && response.isSuccess())
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<GeocodingReverseGeocodingDTO>>() {});
		else
			throw new UrbamidServiceException("Errore nella ricerca dei toponimi stradali");
		
		return result;
	}
	
	@Override
	public List<GeocodingReverseGeocodingDTO> reverseGeocodingToponimo(RequestReverseGeocodingDTO reverseGeocoding) throws Exception {
		List<GeocodingReverseGeocodingDTO> result = null;
		
		String url = env.getProperty(IConstants.WS_TOPONOMASTICA_ENDPOINT) + env.getProperty(IConstants.WS_TOPONOMASTICA_REVERSEGEOCODING_TOPONIMO); 
		
		ResponseData response = restService.restPostTable(url, reverseGeocoding);
		
		if(response != null && response.isSuccess())
			result = new ObjectMapper().convertValue(response.getAaData(), new TypeReference<List<GeocodingReverseGeocodingDTO>>() {});
		else
			throw new UrbamidServiceException("Errore nella ricerca dei toponimi stradali");
		
		return result;
	}

}
