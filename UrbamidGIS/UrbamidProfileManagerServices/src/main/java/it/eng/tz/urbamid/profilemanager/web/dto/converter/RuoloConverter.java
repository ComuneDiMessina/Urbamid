package it.eng.tz.urbamid.profilemanager.web.dto.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.profilemanager.exception.ConverterException;
import it.eng.tz.urbamid.profilemanager.persistence.model.Ruolo;
import it.eng.tz.urbamid.profilemanager.web.dto.RuoloDto;

@Component
public class RuoloConverter implements IConverter<Ruolo, RuoloDto>{

	private static final Logger logger = LoggerFactory.getLogger(RuoloConverter.class.getName());
	
	@Override
	public Ruolo toModel(RuoloDto dto) throws ConverterException {
		Ruolo model = new Ruolo();
		model.setCodice(dto.getCodice());
		model.setDenominazione(dto.getDenominazione());
		model.setDescrizione(dto.getDescrizione());
		return model;
	}

	@Override
	public RuoloDto toDto(Ruolo model) throws ConverterException {
		RuoloDto dto = new RuoloDto();
		dto.setId(model.getId());
		dto.setCodice(model.getCodice());
		dto.setDescrizione(model.getDescrizione());
		dto.setDenominazione(model.getDenominazione());
		return dto;
	}

	@Override
	public List<RuoloDto> toDto(List<Ruolo> models) throws ConverterException {
		List<RuoloDto> result = new ArrayList<>();
		try 
		{
			for (Ruolo model : models) {
				result.add(this.toDto(model));
			}
		}
		catch (Exception e) 
		{
			logger.error("Errore nella conversione Ruolo --> RuoloDto" + e, e);
		}

		return result;
	}
}
