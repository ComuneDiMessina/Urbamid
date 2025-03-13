package it.eng.tz.urbamid.toponomastica.service;

import java.util.List;

import org.springframework.stereotype.Service;

import it.eng.tz.urbamid.toponomastica.exception.ToponomasticaServiceException;
import it.eng.tz.urbamid.toponomastica.web.dto.TipoToponimoDTO;

@Service
public interface TipoToponimoService {

	List<TipoToponimoDTO> findAll() throws ToponomasticaServiceException;
	
}
