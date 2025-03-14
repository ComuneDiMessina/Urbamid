package it.eng.tz.urbamid.wrappergeo.web.dto.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.wrappergeo.exception.ConverterException;
import it.eng.tz.urbamid.wrappergeo.persistence.model.LayerFeatureGeom;
import it.eng.tz.urbamid.wrappergeo.web.entities.FeatureGeomDTO;

@Component
public class FeatureGeomConverter implements IConverter<LayerFeatureGeom, FeatureGeomDTO>{
	
	private static final Logger logger = LoggerFactory.getLogger(FeatureGeomConverter.class.getName());
	
	@Override
	public LayerFeatureGeom toModel(FeatureGeomDTO dto) throws ConverterException{
		LayerFeatureGeom model = new LayerFeatureGeom();
		model.setGeometry(dto.getGeometry());

		return model;
	}

	@Override
	public FeatureGeomDTO toDto(LayerFeatureGeom model) throws ConverterException{
		FeatureGeomDTO dto = new FeatureGeomDTO();
		dto.setId( model.getGid() );
		dto.setGeometry(model.getGeometry());
		return dto;
	}
	
	@Override
	public List<FeatureGeomDTO> toDto(List<LayerFeatureGeom> models) throws ConverterException {
		List<FeatureGeomDTO> result = new ArrayList<FeatureGeomDTO>();
		try 
		{
			for (LayerFeatureGeom model : models) {
				result.add(this.toDto(model));
			}
		}
		catch (Exception e) 
		{
			logger.error("Errore nella conversione ParticellaGeom --> ParticellaGeomDTO" + e, e);
		}

		return result;
	}
	

}
