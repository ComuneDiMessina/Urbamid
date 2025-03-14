package it.eng.tz.urbamid.prg.service;

import java.util.List;

import org.springframework.stereotype.Service;

import it.eng.tz.urbamid.prg.exception.ConverterException;
import it.eng.tz.urbamid.prg.filter.TipoDocumentoFilter;
import it.eng.tz.urbamid.prg.persistence.model.TipoDocumento;
import it.eng.tz.urbamid.prg.web.dto.PagedResult;
import it.eng.tz.urbamid.prg.web.dto.TipoDocumentoDTO;
import it.eng.tz.urbamid.prg.web.dto.TipoDocumentoVarianteDTO;

@Service
public interface TipoDocumentoService {

	public PagedResult<TipoDocumentoDTO> getDocumenti(TipoDocumentoFilter filter) throws ConverterException;
	
	public List<TipoDocumento> findTipoDocumenti() throws ConverterException;
	
	public void insertOrUpdateDocumento(TipoDocumentoDTO documento) throws ConverterException;
	
	public void deleteDocumento(Long id);

	int countCodiceDocumento(String codice);

	public List<TipoDocumento> cercaTipiDocumentoMancanti(Long idVariante) throws Exception;

	public List<TipoDocumentoVarianteDTO> varianteByTipoDocumento(String tipoDocumento) throws ConverterException;
	
}
