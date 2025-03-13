package it.eng.tz.urbamid.prg.web.dto.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.prg.persistence.model.Funzionalita;
import it.eng.tz.urbamid.prg.web.dto.FunzionalitaDto;


@Component
public class FunzionalitaConverter implements IConverter<Funzionalita, FunzionalitaDto>{

	
	private static final Logger logger = LoggerFactory.getLogger(FunzionalitaConverter.class.getName());
	
	
	@Override
	public FunzionalitaDto toDto(Funzionalita model) throws Exception {
		FunzionalitaDto dto = null;
		if(model!=null) {
			dto = new FunzionalitaDto();
			BeanUtils.copyProperties(model, dto);
		}
		
		return dto;
	}

	@Override
	public Funzionalita toModel(FunzionalitaDto dto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<FunzionalitaDto> toDto(List<Funzionalita> models) throws Exception {
		List<FunzionalitaDto> result = new ArrayList<FunzionalitaDto>();
		try 
		{
			for (Funzionalita model : models) {
				result.add(this.toDto(model));
			}
		}
		catch (Exception e) 
		{
			logger.error("Errore nella conversione Funzionalita --> FunzionalitaDto" + e, e);
		}

		return result;
	}
}
