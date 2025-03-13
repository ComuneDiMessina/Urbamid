package it.eng.tz.urbamid.toponomastica.web.dto.converter;

import java.util.Map;

import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.toponomastica.exception.ConverterException;
import it.eng.tz.urbamid.toponomastica.persistence.model.GiunzioneStradale;
import it.eng.tz.urbamid.toponomastica.persistence.model.TipoFunzionale;
import it.eng.tz.urbamid.toponomastica.persistence.model.TipoTopologico;
import it.eng.tz.urbamid.toponomastica.util.AbstractConverter;
import it.eng.tz.urbamid.toponomastica.web.dto.GiunzioneStradaleDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.TipoFunzionaleDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.TipoTopologicoDTO;

@Component
public class GiunzioneStradaleConverter extends AbstractConverter<GiunzioneStradale, GiunzioneStradaleDTO>{

	@Override
	public GiunzioneStradale toModel(GiunzioneStradaleDTO dto, Map<String, Object> parameters) throws ConverterException {
		
		TipoTopologico tipoTopologico = new TipoTopologico();
		
		if(dto.getTipoTopologico() != null) {
			
			tipoTopologico.setId(dto.getTipoTopologico().getId());
			tipoTopologico.setDescrizione(dto.getTipoTopologico().getDescrizione());
			
		} 
		
		TipoFunzionale tipoFunzionale = new TipoFunzionale();
		
		if(dto.getTipoFunzionale() != null) {
			
			tipoFunzionale.setId(dto.getTipoFunzionale().getId());
			tipoFunzionale.setDescrizione(dto.getTipoFunzionale().getDescrizione());
			
		} 
		
		GiunzioneStradale model = new GiunzioneStradale();
		
		model.setId(dto.getId());
		model.setDescrizione(dto.getDescrizione());
		model.setTipoTopologico(tipoTopologico);
		model.setTipoFunzionale(tipoFunzionale);
		model.setLimiteAmministrativo(dto.isLimiteAmministrativo());
		model.setInizioFineStrada(dto.isInizioFineStrada());
		model.setGeom(dto.getGeom());
		model.setStato(dto.getStato());
		model.setNote(dto.getNote());
		model.setIsCircle(dto.getIsCircle());
		
		return model;
	}

	@Override
	public GiunzioneStradaleDTO toDTO(GiunzioneStradale model, Map<String, Object> parameters) throws ConverterException {
		
		TipoTopologicoDTO tipoTopologico = new TipoTopologicoDTO();
		
		if(model.getTipoTopologico() != null) {
			
			tipoTopologico.setId(model.getTipoTopologico().getId());
			tipoTopologico.setDescrizione(model.getTipoTopologico().getDescrizione());
			
		} 
		
		TipoFunzionaleDTO tipoFunzionale = new TipoFunzionaleDTO();
		
		if(model.getTipoFunzionale() != null) {
			
			tipoFunzionale.setId(model.getTipoFunzionale().getId());
			tipoFunzionale.setDescrizione(model.getTipoFunzionale().getDescrizione());
			
		} 
		
		GiunzioneStradaleDTO dto = new GiunzioneStradaleDTO();
		
		dto.setId(model.getId());
		dto.setDescrizione(model.getDescrizione());
		dto.setTipoTopologico(tipoTopologico);
		dto.setTipoFunzionale(tipoFunzionale);
		dto.setLimiteAmministrativo(model.isLimiteAmministrativo());
		dto.setInizioFineStrada(model.isInizioFineStrada());
		dto.setGeom(model.getGeom());
		dto.setStato(model.getStato());
		dto.setNote(model.getNote());
		dto.setIsCircle(model.getIsCircle());
		
		return dto;
	}

}
