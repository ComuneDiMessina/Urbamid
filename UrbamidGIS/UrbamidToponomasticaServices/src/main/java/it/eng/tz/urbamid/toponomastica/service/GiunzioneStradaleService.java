package it.eng.tz.urbamid.toponomastica.service;

import java.util.List;

import it.eng.tz.urbamid.toponomastica.exception.ToponomasticaServiceException;
import it.eng.tz.urbamid.toponomastica.filter.GiunzioneFilter;
import it.eng.tz.urbamid.toponomastica.web.dto.GiunzioneStradaleDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.PagedResult;
import it.eng.tz.urbamid.toponomastica.web.dto.ToponimoStradaleDTO;

public interface GiunzioneStradaleService {

	public PagedResult<GiunzioneStradaleDTO> getGiunzioneByFilter(GiunzioneFilter filter) throws ToponomasticaServiceException;

	public GiunzioneStradaleDTO insertOrUpdate(GiunzioneStradaleDTO giunzioneDTO) throws ToponomasticaServiceException;

	public void deleteGiunzioneById(Long id) throws ToponomasticaServiceException;
	
	public List<ToponimoStradaleDTO> findIntersections(String geom) throws ToponomasticaServiceException;
	
}
