package it.eng.tz.urbamid.toponomastica.service;

import java.util.List;

import it.eng.tz.urbamid.toponomastica.exception.ToponomasticaServiceException;
import it.eng.tz.urbamid.toponomastica.web.dto.GeocodingReverseGeocodingDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.NumCivicoDto;
import it.eng.tz.urbamid.toponomastica.web.dto.ToponimoDto;


public interface ViarioService {

	public List<ToponimoDto> findStreetByName(String denom) throws ToponomasticaServiceException;

	public List<NumCivicoDto> findNumberByStreet(Long codStrada) throws ToponomasticaServiceException;
	
	public List<GeocodingReverseGeocodingDTO> geocodingToponimo(String denominazione) throws ToponomasticaServiceException;
	
}
