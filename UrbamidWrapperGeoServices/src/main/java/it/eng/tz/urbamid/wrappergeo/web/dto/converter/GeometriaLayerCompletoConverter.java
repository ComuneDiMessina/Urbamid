package it.eng.tz.urbamid.wrappergeo.web.dto.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.wrappergeo.exception.ConverterException;
import it.eng.tz.urbamid.wrappergeo.persistence.model.GeometriaCompleteLayer;
import it.eng.tz.urbamid.wrappergeo.web.dto.GeometriaLayerCompleteDTO;

@Component
public class GeometriaLayerCompletoConverter implements IConverter<GeometriaCompleteLayer, GeometriaLayerCompleteDTO>{
	
	private static final Logger logger = LoggerFactory.getLogger(GeometriaLayerCompletoConverter.class.getName());
	
	@Override
	public GeometriaCompleteLayer toModel(GeometriaLayerCompleteDTO dto) throws ConverterException{
		GeometriaCompleteLayer model = new GeometriaCompleteLayer();
		model.setId(dto.getId());
		model.setDistance(dto.getDistance());
		model.setGeom(dto.getGeometry());
		return model;
	}

	@Override
	public List<GeometriaLayerCompleteDTO> toDto(List<GeometriaCompleteLayer> models) throws ConverterException {
		List<GeometriaLayerCompleteDTO> result = new ArrayList<GeometriaLayerCompleteDTO>();
		try 
		{
			for (GeometriaCompleteLayer model : models) {
				result.add(this.toDto(model));
			}
		}
		catch (Exception e) 
		{
			logger.error("Errore nella conversione GeometriaLayer --> GeometriaLayerDTO" + e, e);
		}

		return result;
	}

	@Override
	public GeometriaLayerCompleteDTO toDto(GeometriaCompleteLayer model) throws ConverterException {
		GeometriaLayerCompleteDTO dto = new GeometriaLayerCompleteDTO();
		dto.setId(model.getId());
		dto.setNameColId(model.getNameColId());
		dto.setDistance(model.getDistance());
		dto.setGeometry(model.getGeom() );
		return dto;
	}
}
