package it.eng.tz.urbamid.web.services;

import java.util.List;

import it.eng.tz.urbamid.web.dto.GeocodingReverseGeocodingDTO;
import it.eng.tz.urbamid.web.dto.NumCivicoDTO;
import it.eng.tz.urbamid.web.dto.RequestReverseGeocodingDTO;
import it.eng.tz.urbamid.web.dto.ToponimoDTO;

public interface ViarioService {

	public List<ToponimoDTO> findStreetByName(String denom) throws Exception;
	
	public List<NumCivicoDTO> findNumberByStreet(Long codStrada) throws Exception;
	
	public List<GeocodingReverseGeocodingDTO> geocodingToponimo(String denominazione) throws Exception;

	public List<GeocodingReverseGeocodingDTO> reverseGeocodingToponimo(RequestReverseGeocodingDTO reverseGeocoding) throws Exception;

	
	
}
