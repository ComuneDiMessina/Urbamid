package it.eng.tz.urbamid.profilemanager.web.dto.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.profilemanager.exception.ConverterException;
import it.eng.tz.urbamid.profilemanager.persistence.model.Permesso;
import it.eng.tz.urbamid.profilemanager.web.dto.PermessoUtenteDto;

@Component
public class PermessoUtenteConverter implements IConverter<Permesso, PermessoUtenteDto>{

	private static final Logger logger = LoggerFactory.getLogger(PermessoUtenteConverter.class.getName());
	
	@Override
	public Permesso toModel(PermessoUtenteDto dto) throws ConverterException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PermessoUtenteDto toDto(Permesso model) throws ConverterException {
		PermessoUtenteDto dto = new PermessoUtenteDto();
		dto.setId(model.getId());
		dto.setCodice(model.getCodice());
		dto.setDescrizione(model.getDescrizione());
		dto.setDenominazione(model.getDenominazione());
		dto.setPadre(model.getPadre());
		dto.setBlocked(model.isBlocked());
		dto.setChecked(model.isChecked());
		return dto;
	}

	@Override
	public List<PermessoUtenteDto> toDto(List<Permesso> models) throws ConverterException {
		List<PermessoUtenteDto> result = new ArrayList<>();
		try 
		{
			for (Permesso model : models) {
				result.add(this.toDto(model));
			}
		}
		catch (Exception e) 
		{
			logger.error("Errore nella conversione Permesso --> PermessoUtenteDto" + e, e);
		}

		return result;
	}
}
