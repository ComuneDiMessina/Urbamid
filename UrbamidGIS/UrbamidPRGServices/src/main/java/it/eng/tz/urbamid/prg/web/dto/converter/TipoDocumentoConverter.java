package it.eng.tz.urbamid.prg.web.dto.converter;

import java.util.Map;

import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.prg.exception.ConverterException;
import it.eng.tz.urbamid.prg.persistence.model.TipoDocumento;
import it.eng.tz.urbamid.prg.util.AbstractConverter;
import it.eng.tz.urbamid.prg.web.dto.TipoDocumentoDTO;

@Component
public class TipoDocumentoConverter extends AbstractConverter<TipoDocumento, TipoDocumentoDTO>{

	@Override
	public TipoDocumento toModel(TipoDocumentoDTO dto, Map<String, Object> parameters) throws ConverterException {
		
		TipoDocumento documento = new TipoDocumento();
		
		documento.setId(dto.getId());
		documento.setCodice(dto.getCodice());
		documento.setDescrizione(dto.getDescrizione());
		documento.setDescrizioneCDU(dto.getDescrizioneCDU());
		documento.setNote(dto.getNote());
		
		return documento;
	}

	@Override
	public TipoDocumentoDTO toDTO(TipoDocumento model, Map<String, Object> parameters) throws ConverterException {
		
		TipoDocumentoDTO dto = new TipoDocumentoDTO();
		
		dto.setId(model.getId());
		dto.setCodice(model.getCodice());
		dto.setDescrizione(model.getDescrizione());
		dto.setDescrizioneCDU(model.getDescrizioneCDU());
		dto.setNote(model.getNote());
		
		return dto;
	}

}
