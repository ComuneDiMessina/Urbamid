package it.eng.tz.urbamid.administration.mappa.service.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.administration.mappa.dao.mapper.MappaBean;
import it.eng.tz.urbamid.administration.mappa.dao.model.Mappa;
import it.eng.tz.urbamid.administration.web.dto.converter.IConverter;

@Component
public class MappaConverter implements IConverter<Mappa, MappaBean>{
	
	private static final Logger logger = LoggerFactory.getLogger(MappaConverter.class.getName());
	
	@Override
	public Mappa toModel(MappaBean dto) throws Exception{
		Mappa model = new Mappa();
		
		model.setId(dto.getId());
		model.setCatalog(dto.getCatalog());
		model.setCode(dto.getCode());
		model.setDescription(dto.getDescription());
		model.setTitle(dto.getTitle());
		model.setShowCatalog(dto.getShowCatalog());
		model.setZoom( dto.getZoom() );
		
		return model;
	}

	@Override
	public MappaBean toDto(Mappa model) throws Exception{
		MappaBean dto = new MappaBean();
		
		dto.setId(model.getId());
		dto.setCatalog(model.getCatalog());
		dto.setCode(model.getCode());
		dto.setDescription(model.getDescription());
		dto.setTitle(model.getTitle());
		dto.setShowCatalog(model.getShowCatalog());
		dto.setZoom(model.getZoom());
		
		return dto;
	}
	
	@Override
	public List<MappaBean> toDto(List<Mappa> models) throws Exception {
		List<MappaBean> result = new ArrayList<MappaBean>();
		try 
		{
			for (Mappa model : models) {
				result.add(this.toDto(model));
			}
		}
		catch (Exception e) 
		{
			logger.error("Errore nella conversione Mappa --> MappaBean" + e, e);
		}

		return result;
	}
	

}
