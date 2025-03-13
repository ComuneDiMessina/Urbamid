package it.eng.tz.urbamid.toponomastica.service;

import it.eng.tz.urbamid.toponomastica.exception.ToponomasticaServiceException;
import it.eng.tz.urbamid.toponomastica.filter.AccessoFilter;
import it.eng.tz.urbamid.toponomastica.web.dto.AccessoDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.PagedResult;

public interface AccessoService {

	public PagedResult<AccessoDTO> getAccesso(AccessoFilter filter) throws ToponomasticaServiceException;
	
	public AccessoDTO insertOrUpdate(AccessoDTO accesso) throws ToponomasticaServiceException;
	
	public void deleteAccessoById(Long id) throws ToponomasticaServiceException;
	
	public void deleteByToponimo(Long id, Long toponimo) throws ToponomasticaServiceException;
	
	public void deleteByLocalita(Long id, Long localita) throws ToponomasticaServiceException;

	public Long countAccessiByIdToponimo(Long toponimo) throws ToponomasticaServiceException;

	public Long countAccessiByIdLocalita(Long localita) throws ToponomasticaServiceException;


}
