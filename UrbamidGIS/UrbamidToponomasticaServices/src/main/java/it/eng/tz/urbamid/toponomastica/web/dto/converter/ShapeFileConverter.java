package it.eng.tz.urbamid.toponomastica.web.dto.converter;

import java.util.Map;

import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.toponomastica.exception.ConverterException;
import it.eng.tz.urbamid.toponomastica.persistence.model.ShapeFileImport;
import it.eng.tz.urbamid.toponomastica.util.AbstractConverter;
import it.eng.tz.urbamid.toponomastica.web.dto.ShapeResponseDTO;

@Component
public class ShapeFileConverter extends AbstractConverter<ShapeFileImport, ShapeResponseDTO>{

	@Override
	public ShapeFileImport toModel(ShapeResponseDTO dto, Map<String, Object> parameters) throws ConverterException {
		
		ShapeFileImport model = new ShapeFileImport();
		
		model.setId(dto.getId());
		model.setNameFile(dto.getNameFile());
		model.setSizeFile(dto.getSizeFile());
		model.setDataImport(dto.getDataImport());
		model.setProcessato(dto.isProcessato());
		
		return model;
	}

	@Override
	public ShapeResponseDTO toDTO(ShapeFileImport model, Map<String, Object> parameters) throws ConverterException {
		
		ShapeResponseDTO dto = new ShapeResponseDTO();
		
		dto.setId(model.getId());
		dto.setNameFile(model.getNameFile());
		dto.setSizeFile(model.getSizeFile());
		dto.setDataImport(model.getDataImport());
		dto.setProcessato(model.getProcessato());
		
		return dto;
	}

}
