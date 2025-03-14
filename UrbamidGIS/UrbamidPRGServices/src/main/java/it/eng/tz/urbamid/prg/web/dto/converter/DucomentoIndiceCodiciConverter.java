package it.eng.tz.urbamid.prg.web.dto.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.prg.persistence.model.DocumentoIndiceCodici;
import it.eng.tz.urbamid.prg.web.dto.DocumentoIndiceCodiciDTO;

@Component
public class DucomentoIndiceCodiciConverter implements IConverter<DocumentoIndiceCodici, DocumentoIndiceCodiciDTO> {

	@Override
	public DocumentoIndiceCodici toModel(DocumentoIndiceCodiciDTO dto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DocumentoIndiceCodiciDTO toDto(DocumentoIndiceCodici model) throws Exception {
		DocumentoIndiceCodiciDTO dto = new DocumentoIndiceCodiciDTO();
		dto.setIdCodice(model.getIdCodice());
		dto.setCodice(model.getCodice());
		dto.setDescrizione(model.getDescrizione());
		return dto;
	}

	@Override
	public List<DocumentoIndiceCodiciDTO> toDto(List<DocumentoIndiceCodici> models) throws Exception {
		List<DocumentoIndiceCodiciDTO> result = new ArrayList<DocumentoIndiceCodiciDTO>();
		try 
		{
			for (DocumentoIndiceCodici model : models) {
				result.add(this.toDto(model));
			}
		}
		catch (Exception e) 
		{
			throw (e);
		}

		return result;
	}

}
