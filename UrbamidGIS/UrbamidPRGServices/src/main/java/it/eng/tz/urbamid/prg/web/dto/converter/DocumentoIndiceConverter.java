package it.eng.tz.urbamid.prg.web.dto.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.prg.persistence.model.DocumentoIndice;
import it.eng.tz.urbamid.prg.web.dto.DocumentoIndiceDTO;

@Component
public class DocumentoIndiceConverter implements IConverter<DocumentoIndice, DocumentoIndiceDTO> {

	@Override
	public DocumentoIndice toModel(DocumentoIndiceDTO dto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DocumentoIndiceDTO toDto(DocumentoIndice model) throws Exception {
		DocumentoIndiceDTO dto = new DocumentoIndiceDTO();
		dto.setIdIndice(model.getIdIndice());
		dto.setArticolo(model.getArticolo());
		dto.setElencoPagine(model.getElencoPagine());
		return dto;
	}

	@Override
	public List<DocumentoIndiceDTO> toDto(List<DocumentoIndice> models) throws Exception {
		List<DocumentoIndiceDTO> result = new ArrayList<DocumentoIndiceDTO>();
		try 
		{
			for (DocumentoIndice model : models) {
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
