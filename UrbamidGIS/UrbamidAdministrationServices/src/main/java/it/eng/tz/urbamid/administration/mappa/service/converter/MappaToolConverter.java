package it.eng.tz.urbamid.administration.mappa.service.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.administration.mappa.dao.mapper.MappaToolBean;
import it.eng.tz.urbamid.administration.mappa.dao.model.MappaTool;
import it.eng.tz.urbamid.administration.web.dto.converter.IConverter;

@Component
public class MappaToolConverter implements IConverter<MappaTool, MappaToolBean>{
	
	private static final Logger logger = LoggerFactory.getLogger(MappaToolConverter.class.getName());
	
	@Override
	public MappaTool toModel(MappaToolBean dto) throws Exception{
		MappaTool model = new MappaTool();
		
		model.setId(dto.getId());
		model.setName(dto.getName());
		model.setTitle(dto.getTitle());

		return model;
	}

	@Override
	public MappaToolBean toDto(MappaTool model) throws Exception{
		MappaToolBean dto = new MappaToolBean();
		
		dto.setId(model.getId());
		dto.setName(model.getName());
		dto.setTitle(model.getTitle());
		
		return dto;
	}
	
	@Override
	public List<MappaToolBean> toDto(List<MappaTool> models) throws Exception {
		List<MappaToolBean> result = new ArrayList<MappaToolBean>();
		try 
		{
			for (MappaTool model : models) {
				result.add(this.toDto(model));
			}
		}
		catch (Exception e) 
		{
			logger.error("Errore nella conversione MappaTool --> MappaToolBean" + e, e);
		}

		return result;
	}
	

}
