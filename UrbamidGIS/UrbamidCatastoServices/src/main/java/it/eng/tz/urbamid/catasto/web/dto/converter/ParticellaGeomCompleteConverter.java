package it.eng.tz.urbamid.catasto.web.dto.converter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.catasto.exception.ConverterException;
import it.eng.tz.urbamid.catasto.persistence.model.ParticellaCompleteGeom;
import it.eng.tz.urbamid.catasto.web.dto.ParticellaGeomCompleteDTO;

@Component
public class ParticellaGeomCompleteConverter implements IConverter<ParticellaCompleteGeom, ParticellaGeomCompleteDTO>{
	
	private static final Logger logger = LoggerFactory.getLogger(ParticellaGeomCompleteConverter.class.getName());
	
	@Override
	public ParticellaCompleteGeom toModel(ParticellaGeomCompleteDTO dto) throws ConverterException{
		ParticellaCompleteGeom model = new ParticellaCompleteGeom();
		
		model.setCodiceCom(dto.getCodiceCom());
		model.setFoglio(dto.getFoglio());
		model.setMappale(dto.getMappale());
		model.setAllegato(dto.getAllegato());
		model.setSviluppo(dto.getSviluppo());
		model.setGeomText(dto.getGeomText());
		model.setArea(dto.getArea());
		model.setIntersectGeomText(dto.getIntersectGeomText());
		model.setIntersectArea(dto.getIntersectArea());
		return model;
	}
	
	@Override
	public ParticellaGeomCompleteDTO toDto(ParticellaCompleteGeom model) throws ConverterException{
		ParticellaGeomCompleteDTO dto = new ParticellaGeomCompleteDTO();
		dto.setId( model.getGid() );
		
		dto.setCodiceCom(model.getCodiceCom());
		dto.setFoglio(model.getFoglio());
		dto.setMappale(model.getMappale());
		dto.setAllegato(model.getAllegato());
		dto.setSviluppo(model.getSviluppo());
		dto.setGeomText(model.getGeomText());
		dto.setArea(model.getArea());
		dto.setIntersectGeomText(model.getIntersectGeomText());
		dto.setIntersectArea(model.getIntersectArea());
		return dto;
	}
	
	@Override
	public List<ParticellaGeomCompleteDTO> toDto(List<ParticellaCompleteGeom> models) throws ConverterException {
		List<ParticellaGeomCompleteDTO> result = new ArrayList<ParticellaGeomCompleteDTO>();
		try 
		{
			for (ParticellaCompleteGeom model : models) {
				result.add(this.toDto(model));
			}
		}
		catch (Exception e) 
		{
			logger.error("Errore nella conversione ParticellaCompleteGeom --> ParticellaGeomCompleteDTO" + e, e);
		}

		return result;
	}
	

}
