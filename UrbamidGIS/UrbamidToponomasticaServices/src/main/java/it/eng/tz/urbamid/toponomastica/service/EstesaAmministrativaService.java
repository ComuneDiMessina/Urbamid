package it.eng.tz.urbamid.toponomastica.service;

import java.util.List;

import it.eng.tz.urbamid.toponomastica.exception.ToponomasticaServiceException;
import it.eng.tz.urbamid.toponomastica.filter.EstesaAmministrativaFilter;
import it.eng.tz.urbamid.toponomastica.web.dto.EstesaAmministrativaDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.PagedResult;

public interface EstesaAmministrativaService {

	public PagedResult<EstesaAmministrativaDTO> getEstesaAmministrativa(EstesaAmministrativaFilter filter) throws ToponomasticaServiceException;

	public List<EstesaAmministrativaDTO> findAll(String descrizione) throws ToponomasticaServiceException;
	
	public EstesaAmministrativaDTO insertOrUpdate(EstesaAmministrativaDTO estesa) throws ToponomasticaServiceException;
	
	public void deleteById(Long id) throws ToponomasticaServiceException;

	public List<EstesaAmministrativaDTO> findSigla(Long id) throws ToponomasticaServiceException;
}
