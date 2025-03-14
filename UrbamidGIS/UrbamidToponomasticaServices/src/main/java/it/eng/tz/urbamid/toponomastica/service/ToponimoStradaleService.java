package it.eng.tz.urbamid.toponomastica.service;

import java.util.List;

import org.springframework.stereotype.Service;

import it.eng.tz.urbamid.toponomastica.exception.ToponomasticaServiceException;
import it.eng.tz.urbamid.toponomastica.filter.ToponimoFilter;
import it.eng.tz.urbamid.toponomastica.web.dto.PagedResult;
import it.eng.tz.urbamid.toponomastica.web.dto.ToponimoDugDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.ToponimoStradaleDTO;

@Service
public interface ToponimoStradaleService {
	
	public PagedResult<ToponimoStradaleDTO> getToponimoByFilter(ToponimoFilter filter) throws ToponomasticaServiceException;

	public ToponimoStradaleDTO insertOrUpdate(ToponimoStradaleDTO toponimoDto) throws ToponomasticaServiceException;
	
	public int pubblicaToponimoStradale(Long id) throws ToponomasticaServiceException;
	
	public void deleteToponimoById(Long id) throws ToponomasticaServiceException;
	
	public void archiviaToponimoById(Long id) throws ToponomasticaServiceException;
		
	public List<ToponimoDugDTO> ricercaToponimoByDug(String toponimo) throws ToponomasticaServiceException;

	public List<ToponimoStradaleDTO> ricercaToponimoByIdPadre(Long idPadre)  throws Exception;

	public boolean isFigliPubblicati(Long idPadre) throws ToponomasticaServiceException;
	
}
