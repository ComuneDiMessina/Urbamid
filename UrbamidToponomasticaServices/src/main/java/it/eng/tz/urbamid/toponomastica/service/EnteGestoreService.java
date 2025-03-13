package it.eng.tz.urbamid.toponomastica.service;

import java.util.List;

import it.eng.tz.urbamid.toponomastica.exception.ToponomasticaServiceException;
import it.eng.tz.urbamid.toponomastica.web.dto.EnteGestoreDTO;

public interface EnteGestoreService {
	
	public List<EnteGestoreDTO> findAll() throws ToponomasticaServiceException;

}
