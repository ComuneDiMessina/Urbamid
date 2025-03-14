package it.eng.tz.urbamid.toponomastica.service;

import java.util.List;

import it.eng.tz.urbamid.toponomastica.exception.ToponomasticaServiceException;
import it.eng.tz.urbamid.toponomastica.web.dto.TipoAccessoDTO;

public interface TipoAccessoService {

	public List<TipoAccessoDTO> findAll() throws ToponomasticaServiceException;
	
}
