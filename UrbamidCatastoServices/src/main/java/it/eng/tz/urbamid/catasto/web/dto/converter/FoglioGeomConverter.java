package it.eng.tz.urbamid.catasto.web.dto.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.catasto.exception.ConverterException;
import it.eng.tz.urbamid.catasto.persistence.model.FoglioGeom;
import it.eng.tz.urbamid.catasto.web.dto.FoglioGeomDTO;

@Component
public class FoglioGeomConverter implements IConverter<FoglioGeom, FoglioGeomDTO>{
	
	private static final Logger logger = LoggerFactory.getLogger(FoglioGeomConverter.class.getName());
	
	@Override
	public FoglioGeom toModel(FoglioGeomDTO dto) throws ConverterException{
		FoglioGeom model = new FoglioGeom();
		
		model.setCodiceCom(dto.getCodiceCom());
		model.setFoglio(dto.getFoglio());
		model.setMappale(dto.getMappale());
		model.setAllegato(dto.getAllegato());
		model.setSviluppo(dto.getSviluppo());
		model.setGeom(dto.getGeometry());

		return model;
	}

	@Override
	public FoglioGeomDTO toDto(FoglioGeom model) throws ConverterException{
		FoglioGeomDTO dto = new FoglioGeomDTO();
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
	public List<FoglioGeomDTO> toDto(List<FoglioGeom> models) throws ConverterException {
		List<FoglioGeomDTO> result = new ArrayList<FoglioGeomDTO>();
		try 
		{
			for (FoglioGeom model : models) {
				result.add(this.toDto(model));
			}
		}
		catch (Exception e) 
		{
			logger.error("Errore nella conversione FoglioGeom --> FoglioGeomDTO" + e, e);
		}

		return result;
	}
	

}
