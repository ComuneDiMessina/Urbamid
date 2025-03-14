package it.eng.tz.urbamid.catasto.web.dto.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.catasto.exception.ConverterException;
import it.eng.tz.urbamid.catasto.persistence.model.GeometriaLayer;
import it.eng.tz.urbamid.catasto.web.dto.GeometriaLayerDTO;

@Component
public class GeometriaLayerConverter implements IConverter<GeometriaLayer, GeometriaLayerDTO>{
	
	private static final Logger logger = LoggerFactory.getLogger(GeometriaLayerConverter.class.getName());
	
	@Override
	public GeometriaLayer toModel(GeometriaLayerDTO dto) throws ConverterException{
		GeometriaLayer model = new GeometriaLayer();
		model.setGid(dto.getId());
		model.setGeom(dto.getGeometry());
		return model;
	}

	@Override
	public GeometriaLayerDTO toDto(GeometriaLayer model) throws ConverterException{
		GeometriaLayerDTO dto = new GeometriaLayerDTO();
		dto.setId( model.getGid() );
		dto.setGeometry(model.getGeom());
		return dto;
	}
	
	@Override
	public List<GeometriaLayerDTO> toDto(List<GeometriaLayer> models) throws ConverterException {
		List<GeometriaLayerDTO> result = new ArrayList<GeometriaLayerDTO>();
		try 
		{
			for (GeometriaLayer model : models) {
				result.add(this.toDto(model));
			}
		}
		catch (Exception e) 
		{
			logger.error("Errore nella conversione GeometriaLayer --> GeometriaLayerDTO" + e, e);
		}

		return result;
	}
	

}
