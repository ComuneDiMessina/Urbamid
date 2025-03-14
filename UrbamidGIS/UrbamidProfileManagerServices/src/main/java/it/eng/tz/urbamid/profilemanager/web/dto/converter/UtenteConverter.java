package it.eng.tz.urbamid.profilemanager.web.dto.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.profilemanager.exception.ConverterException;
import it.eng.tz.urbamid.profilemanager.persistence.model.Utente;
import it.eng.tz.urbamid.profilemanager.web.dto.UtenteDto;

@Component
public class UtenteConverter implements IConverter<Utente, UtenteDto>{

	private static final Logger logger = LoggerFactory.getLogger(UtenteConverter.class.getName());
	
	@Override
	public Utente toModel(UtenteDto dto) throws ConverterException {
		Utente model = new Utente();
		model.setCodiceFiscale(dto.getCodiceFiscale());
		model.setCognome(dto.getCognome());
		model.setNome(dto.getNome());
		model.setUsername(dto.getUsername());
		model.setNote(dto.getNote());
		model.setEmail(dto.getEmail());
		model.setAbilitato(dto.getAbilitato());
		return model;
	}

	@Override
	public UtenteDto toDto(Utente model) throws ConverterException {
		UtenteDto dto = new UtenteDto();
		dto.setId(model.getId());
		dto.setCodiceFiscale(model.getCodiceFiscale());
		dto.setCognome(model.getCognome());
		dto.setNome(model.getNome());
		dto.setUsername(model.getUsername());
		dto.setNote(model.getNote());
		dto.setEmail(model.getEmail());
		dto.setAbilitato(model.isAbilitato());
		return dto;
	}

	@Override
	public List<UtenteDto> toDto(List<Utente> models) throws ConverterException {
		List<UtenteDto> result = new ArrayList<>();
		try 
		{
			for (Utente model : models) {
				result.add(this.toDto(model));
			}
		}
		catch (Exception e) 
		{
			logger.error("Errore nella conversione Utente --> UtenteDto" + e, e);
		}

		return result;
	}
}
