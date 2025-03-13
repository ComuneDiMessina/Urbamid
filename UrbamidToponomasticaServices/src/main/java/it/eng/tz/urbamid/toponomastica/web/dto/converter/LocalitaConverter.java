package it.eng.tz.urbamid.toponomastica.web.dto.converter;

import java.util.Map;

import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.toponomastica.exception.ConverterException;
import it.eng.tz.urbamid.toponomastica.persistence.model.Comuni;
import it.eng.tz.urbamid.toponomastica.persistence.model.Localita;
import it.eng.tz.urbamid.toponomastica.persistence.model.TipoLocalita;
import it.eng.tz.urbamid.toponomastica.util.AbstractConverter;
import it.eng.tz.urbamid.toponomastica.web.dto.ComuniDto;
import it.eng.tz.urbamid.toponomastica.web.dto.LocalitaDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.TipoLocalitaDTO;

@Component
public class LocalitaConverter extends AbstractConverter<Localita, LocalitaDTO>{

	@Override
	public Localita toModel(LocalitaDTO dto, Map<String, Object> parameters) throws ConverterException {
		
		Localita model = new Localita();
		
		model.setId(dto.getId());
		model.setDescrizione(dto.getDescrizione());
				
		if(dto.getComune() != null) {
		
			Comuni comuni = new Comuni();
			
			comuni.setIdComune(dto.getComune().getIdComune());
			comuni.setSiglaProvincia(dto.getComune().getSiglaProvincia());
			comuni.setCodiceIstat(dto.getComune().getCodiceIstat());
			comuni.setCodiceMf(dto.getComune().getCodiceMf());
			comuni.setDenominazione(dto.getComune().getDenominazione());
			comuni.setVlInizio(dto.getComune().getVlInizio());
			comuni.setVlFine(dto.getComune().getVlFine());
			comuni.setDuCatAgg(dto.getComune().getDuCatAgg());
			comuni.setIdProvincia(dto.getComune().getIdProvincia());
			
			model.setComune(comuni);
			
		}
		
		model.setFrazione(dto.getFrazione());
		if (dto.getGeom()!=null && !dto.getGeom().isEmpty()) 
			model.setGeom(dto.getGeom());
		model.setStato(dto.getStato());
		model.setNote(dto.getNote());
		
		TipoLocalita tipoLocalita = new TipoLocalita();
		
		tipoLocalita.setId(dto.getTipo().getId());
		tipoLocalita.setDescrizione(dto.getTipo().getDescrizione());
		
		model.setTipo(tipoLocalita);
		
		return model;
	}

	@Override
	public LocalitaDTO toDTO(Localita model, Map<String, Object> parameters) throws ConverterException {
		
		LocalitaDTO dto = new LocalitaDTO();
		
		dto.setId(model.getId());
		dto.setDescrizione(model.getDescrizione());
		
		ComuniDto comuni = new ComuniDto();
		
		comuni.setIdComune(model.getComune().getIdComune());
		comuni.setSiglaProvincia(model.getComune().getSiglaProvincia());
		comuni.setCodiceIstat(model.getComune().getCodiceIstat());
		comuni.setCodiceMf(model.getComune().getCodiceMf());
		comuni.setDenominazione(model.getComune().getDenominazione());
		comuni.setVlInizio(model.getComune().getVlInizio());
		comuni.setVlFine(model.getComune().getVlFine());
		comuni.setDuCatAgg(model.getComune().getDuCatAgg());
		comuni.setIdProvincia(model.getComune().getIdProvincia());
		
		dto.setComune(comuni);
		dto.setFrazione(model.getFrazione());
		dto.setGeom(model.getGeom());
		dto.setStato(model.getStato());
		dto.setNote(model.getNote());
		
		if(model.getTipo() != null) {
			
			TipoLocalitaDTO tipoLocalita = new TipoLocalitaDTO();
			
			tipoLocalita.setId(model.getTipo().getId());
			tipoLocalita.setDescrizione(model.getTipo().getDescrizione());
			
			dto.setTipo(tipoLocalita);
		
		}
		
				
		return dto;
	}

	
	
}
