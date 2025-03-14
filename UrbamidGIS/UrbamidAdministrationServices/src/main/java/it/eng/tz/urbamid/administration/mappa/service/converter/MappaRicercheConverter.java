package it.eng.tz.urbamid.administration.mappa.service.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.administration.mappa.dao.mapper.MappaRicercaBean;
import it.eng.tz.urbamid.administration.mappa.dao.model.MappaRicerca;
import it.eng.tz.urbamid.administration.web.dto.converter.IConverter;

@Component
public class MappaRicercheConverter implements IConverter<MappaRicerca, MappaRicercaBean>{

	private static final Logger logger = LoggerFactory.getLogger(MappaRicercheConverter.class.getName());
	
	@Override
	public MappaRicerca toModel(MappaRicercaBean dto) throws Exception{
		MappaRicerca model = new MappaRicerca();
		
		model.setId(dto.getId());
		model.setIdHandle(dto.getIdHandle());
		model.setName(dto.getName());
		model.setTitle(dto.getTitle());

		return model;
	}

	@Override
	public MappaRicercaBean toDto(MappaRicerca model) throws Exception{
		MappaRicercaBean dto = new MappaRicercaBean();
		
		dto.setId(model.getId());
		dto.setIdHandle(model.getIdHandle());
		dto.setName(model.getName());
		dto.setTitle(model.getTitle());
		
		return dto;
	}
	
	@Override
	public List<MappaRicercaBean> toDto(List<MappaRicerca> models) throws Exception {
		List<MappaRicercaBean> result = new ArrayList<MappaRicercaBean>();
		try 
		{
			for (MappaRicerca model : models) {
				result.add(this.toDto(model));
			}
		}
		catch (Exception e) 
		{
			logger.error("Errore nella conversione MappaRicerca --> MappaRicercaBean" + e, e);
		}

		return result;
	}
	

}
