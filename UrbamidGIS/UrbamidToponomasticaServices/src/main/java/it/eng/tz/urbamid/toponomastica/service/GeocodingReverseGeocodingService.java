package it.eng.tz.urbamid.toponomastica.service;

import java.util.List;

import it.eng.tz.urbamid.toponomastica.exception.ToponomasticaServiceException;
import it.eng.tz.urbamid.toponomastica.web.dto.GeocodingReverseGeocodingDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.RequestReverseGeocodingDTO;

public interface GeocodingReverseGeocodingService {

	public List<GeocodingReverseGeocodingDTO> geocoding(String denominazione) throws ToponomasticaServiceException;
	public List<GeocodingReverseGeocodingDTO> reverseGeocoding(RequestReverseGeocodingDTO reverseGeocoding) throws ToponomasticaServiceException;
	
}
