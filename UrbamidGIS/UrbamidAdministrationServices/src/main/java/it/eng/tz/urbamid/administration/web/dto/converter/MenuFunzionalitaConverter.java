package it.eng.tz.urbamid.administration.web.dto.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.administration.persistence.model.MenuFunzionalita;
import it.eng.tz.urbamid.administration.web.dto.MenuFunzionalitaDto;


@Component
public class MenuFunzionalitaConverter implements IConverter<MenuFunzionalita, MenuFunzionalitaDto>{

	
	private static final Logger logger = LoggerFactory.getLogger(MenuFunzionalitaConverter.class.getName());
	
	
	@Override
	public MenuFunzionalitaDto toDto(MenuFunzionalita model) throws Exception {
		MenuFunzionalitaDto dto = null;
		if(model!=null) {
			dto = new MenuFunzionalitaDto();
			BeanUtils.copyProperties(model, dto);
		}
		
		return dto;
	}

	@Override
	public MenuFunzionalita toModel(MenuFunzionalitaDto dto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<MenuFunzionalitaDto> toDto(List<MenuFunzionalita> models) throws Exception {
		List<MenuFunzionalitaDto> result = new ArrayList<MenuFunzionalitaDto>();
		try 
		{
			for (MenuFunzionalita model : models) {
				result.add(this.toDto(model));
			}
		}
		catch (Exception e) 
		{
			logger.error("Errore nella conversione MenuFunzionalita --> MenuFunzionalitaDto" + e, e);
		}

		return result;
	}
}
