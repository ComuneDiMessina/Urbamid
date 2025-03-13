package it.eng.tz.urbamid.toponomastica.service;

import it.eng.tz.urbamid.toponomastica.exception.ToponomasticaServiceException;
import it.eng.tz.urbamid.toponomastica.filter.DocumentoFilter;
import it.eng.tz.urbamid.toponomastica.web.dto.DocumentoDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.PagedResult;

public interface StorageService {

	public PagedResult<DocumentoDTO> getDocumenti(DocumentoFilter filter) throws ToponomasticaServiceException;
	
	public void upload(DocumentoDTO file) throws ToponomasticaServiceException;
	
	public DocumentoDTO download(Long idRisorsa, Long tipoRisorsa, String nomeDocumento) throws ToponomasticaServiceException;
	
	public void elimina(Long idRisorsa, Long tipoRisorsa, String nomeDocumento) throws ToponomasticaServiceException;
		
}
