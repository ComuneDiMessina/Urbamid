package it.eng.tz.urbamid.catasto.web.dto.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.catasto.exception.ConverterException;
import it.eng.tz.urbamid.catasto.persistence.model.ParticellaGeom;
import it.eng.tz.urbamid.catasto.web.dto.ParticellaGeomDTO;

@Component
public class ParticellaGeomConverter implements IConverter<ParticellaGeom, ParticellaGeomDTO>{
	
	private static final Logger logger = LoggerFactory.getLogger(ParticellaGeomConverter.class.getName());
	
	@Override
	public ParticellaGeom toModel(ParticellaGeomDTO dto) throws ConverterException{
		ParticellaGeom model = new ParticellaGeom();
		
		model.setCodiceCom(dto.getCodiceCom());
		model.setFoglio(dto.getFoglio());
		model.setMappale(dto.getMappale());
		model.setAllegato(dto.getAllegato());
		model.setSviluppo(dto.getSviluppo());
		model.setGeom(dto.getGeometry());

		return model;
	}

	@Override
	public ParticellaGeomDTO toDto(ParticellaGeom model) throws ConverterException{
		ParticellaGeomDTO dto = new ParticellaGeomDTO();
		dto.setId( model.getGid() );
		
		dto.setCodiceCom(model.getCodiceCom());
		dto.setFoglio(model.getFoglio());
		dto.setMappale(model.getMappale());
		dto.setAllegato(model.getAllegato());
		dto.setSviluppo(model.getSviluppo());
		dto.setGeometry(model.getGeom());
		return dto;
	}
	
	@Override
	public List<ParticellaGeomDTO> toDto(List<ParticellaGeom> models) throws ConverterException {
		List<ParticellaGeomDTO> result = new ArrayList<ParticellaGeomDTO>();
		try 
		{
			for (ParticellaGeom model : models) {
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
