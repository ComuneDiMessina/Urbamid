package it.eng.tz.urbamid.toponomastica.service;

import java.util.List;

import org.springframework.stereotype.Service;

import it.eng.tz.urbamid.toponomastica.exception.ToponomasticaServiceException;
import it.eng.tz.urbamid.toponomastica.filter.LocalitaFilter;
import it.eng.tz.urbamid.toponomastica.web.dto.LocalitaDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.PagedResult;

@Service
public interface LocalitaService {
	
	public PagedResult<LocalitaDTO> getLocalita(LocalitaFilter filter) throws ToponomasticaServiceException;

	public LocalitaDTO insertOrUpdate(LocalitaDTO localita) throws ToponomasticaServiceException;
	
	public void deleteById(Long id) throws ToponomasticaServiceException;

	public List<LocalitaDTO> findAll() throws ToponomasticaServiceException;
	
}
