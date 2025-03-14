package it.eng.tz.urbamid.profilemanager.web.dto.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.profilemanager.exception.ConverterException;
import it.eng.tz.urbamid.profilemanager.persistence.model.Ruolo;
import it.eng.tz.urbamid.profilemanager.web.dto.RuoloUtenteDto;

@Component
public class RuoloUtenteConverter implements IConverter<Ruolo, RuoloUtenteDto>{

	private static final Logger logger = LoggerFactory.getLogger(RuoloUtenteConverter.class.getName());
	
	@Override
	public Ruolo toModel(RuoloUtenteDto dto) throws ConverterException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RuoloUtenteDto toDto(Ruolo model) throws ConverterException {
		RuoloUtenteDto dto = new RuoloUtenteDto();
		dto.setId(model.getId());
		dto.setCodice(model.getCodice());
		dto.setDescrizione(model.getDescrizione());
		dto.setDenominazione(model.getDenominazione());
		dto.setRuoloDefault(model.isRuoloDefault());
		return dto;
	}

	@Override
	public List<RuoloUtenteDto> toDto(List<Ruolo> models) throws ConverterException {
		List<RuoloUtenteDto> result = new ArrayList<>();
		try 
		{
			for (Ruolo model : models) {
				result.add(this.toDto(model));
			}
		}
		catch (Exception e) 
		{
			logger.error("Errore nella conversione Ruolo --> RuoloUtenteDto" + e, e);
		}

		return result;
	}
}
