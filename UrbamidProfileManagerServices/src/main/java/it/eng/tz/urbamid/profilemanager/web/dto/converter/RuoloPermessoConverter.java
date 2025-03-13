package it.eng.tz.urbamid.profilemanager.web.dto.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.profilemanager.exception.ConverterException;
import it.eng.tz.urbamid.profilemanager.persistence.model.RuoloPermesso;
import it.eng.tz.urbamid.profilemanager.web.dto.RuoloPermessoDto;

@Component
public class RuoloPermessoConverter implements IConverter<RuoloPermesso, RuoloPermessoDto>{

	private static final Logger logger = LoggerFactory.getLogger(RuoloPermessoConverter.class.getName());
	
	@Override
	public RuoloPermesso toModel(RuoloPermessoDto dto) throws ConverterException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RuoloPermessoDto toDto(RuoloPermesso model) throws ConverterException {
		RuoloPermessoDto dto = new RuoloPermessoDto();
		dto.setId( model.getId() );
		dto.setIdRuolo( model.getIdRuolo() );
		dto.setIdPermesso( model.getIdPermesso() );
		return dto;
	}

	@Override
	public List<RuoloPermessoDto> toDto(List<RuoloPermesso> models) throws ConverterException {
		List<RuoloPermessoDto> result = new ArrayList<>();
		try 
		{
			for (RuoloPermesso model : models) {
				result.add(this.toDto(model));
			}
		}
		catch (Exception e) 
		{
			logger.error("Errore nella conversione RuoloPermesso --> RuoloPermessoDto" + e, e);
		}

		return result;
	}
}
