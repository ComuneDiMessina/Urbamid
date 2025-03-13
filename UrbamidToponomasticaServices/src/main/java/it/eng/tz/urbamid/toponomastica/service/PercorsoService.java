package it.eng.tz.urbamid.toponomastica.service;

import it.eng.tz.urbamid.toponomastica.exception.ToponomasticaServiceException;
import it.eng.tz.urbamid.toponomastica.filter.PercorsoFilter;
import it.eng.tz.urbamid.toponomastica.web.dto.PagedResult;
import it.eng.tz.urbamid.toponomastica.web.dto.PercorsoDTO;

public interface PercorsoService  {

	public PagedResult<PercorsoDTO> getPercorso(PercorsoFilter filter) throws ToponomasticaServiceException;

	public PercorsoDTO insertOrUpdate(PercorsoDTO percorso) throws ToponomasticaServiceException;
	
	public void deleteById(Long id) throws ToponomasticaServiceException;
		
}
