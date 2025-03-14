package it.eng.tz.urbamid.toponomastica.service;

import it.eng.tz.urbamid.toponomastica.exception.ToponomasticaServiceException;
import it.eng.tz.urbamid.toponomastica.filter.CippoChilometricoFilter;
import it.eng.tz.urbamid.toponomastica.web.dto.CippoChilometricoDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.PagedResult;

public interface CippoChilometricoService {

	public PagedResult<CippoChilometricoDTO> getCippo(CippoChilometricoFilter filter) throws ToponomasticaServiceException;
	
	public CippoChilometricoDTO insertOrUpdate(CippoChilometricoDTO cippo) throws ToponomasticaServiceException;
	
	public void delete(Long id) throws ToponomasticaServiceException;
	
	public Long countCippiByEstesa(Long idEstesa) throws ToponomasticaServiceException;
	
}
