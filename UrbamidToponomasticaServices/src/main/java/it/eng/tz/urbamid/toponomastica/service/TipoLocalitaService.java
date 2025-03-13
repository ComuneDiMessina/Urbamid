package it.eng.tz.urbamid.toponomastica.service;

import java.util.List;

import it.eng.tz.urbamid.toponomastica.exception.ToponomasticaServiceException;
import it.eng.tz.urbamid.toponomastica.web.dto.TipoLocalitaDTO;

public interface TipoLocalitaService {

	List<TipoLocalitaDTO> findAll() throws ToponomasticaServiceException;
	
}
