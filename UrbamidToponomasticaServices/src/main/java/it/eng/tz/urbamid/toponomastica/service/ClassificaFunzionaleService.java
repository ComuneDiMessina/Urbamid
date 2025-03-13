package it.eng.tz.urbamid.toponomastica.service;

import java.util.List;

import it.eng.tz.urbamid.toponomastica.exception.ToponomasticaServiceException;
import it.eng.tz.urbamid.toponomastica.web.dto.ClassificaFunzionaleDTO;

public interface ClassificaFunzionaleService {

	public List<ClassificaFunzionaleDTO> findAll() throws ToponomasticaServiceException;
	
}
