package it.eng.tz.urbamid.prg.service;

import org.springframework.stereotype.Service;

import it.eng.tz.urbamid.prg.exception.ConverterException;
import it.eng.tz.urbamid.prg.filter.TipoDocumentoFilter;
import it.eng.tz.urbamid.prg.persistence.model.TipoDocumento;
import it.eng.tz.urbamid.prg.web.dto.PagedResult;
import it.eng.tz.urbamid.prg.web.dto.TipoDocumentoDTO;

@Service
public interface DocumentoService {

	public PagedResult<TipoDocumentoDTO> getDocumenti(TipoDocumentoFilter filter) throws ConverterException;
	
	public Iterable<TipoDocumento> findTipoDocumenti() throws ConverterException;
	
	public void insertOrUpdateDocumento(TipoDocumentoDTO documento) throws ConverterException;
	
	public void deleteDocumento(Long id);

	int countCodiceDocumento(String codice);
	
}
