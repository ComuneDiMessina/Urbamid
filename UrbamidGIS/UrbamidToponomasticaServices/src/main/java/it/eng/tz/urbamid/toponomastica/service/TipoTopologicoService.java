package it.eng.tz.urbamid.toponomastica.service;

import java.util.List;

import it.eng.tz.urbamid.toponomastica.exception.ToponomasticaServiceException;
import it.eng.tz.urbamid.toponomastica.web.dto.TipoTopologicoDTO;

public interface TipoTopologicoService {

	public List<TipoTopologicoDTO> findAll() throws ToponomasticaServiceException;
	
}
