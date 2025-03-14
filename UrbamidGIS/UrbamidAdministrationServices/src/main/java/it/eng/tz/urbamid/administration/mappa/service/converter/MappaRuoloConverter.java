package it.eng.tz.urbamid.administration.mappa.service.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.administration.mappa.dao.mapper.RuoloBean;
import it.eng.tz.urbamid.administration.mappa.dao.model.Ruolo;
import it.eng.tz.urbamid.administration.web.dto.converter.IConverter;

@Component
public class MappaRuoloConverter implements IConverter<Ruolo, RuoloBean> {

	private static final Logger logger = LoggerFactory.getLogger(MappaRuoloConverter.class.getName());
	
	@Override
	public Ruolo toModel(RuoloBean dto) throws Exception {
		
		Ruolo model = new Ruolo();
		
		model.setId(dto.getId());
		model.setCodice(dto.getCodice());
		model.setDenominazione(dto.getDenominazione());
		model.setDescrizione(model.getDescrizione());
		
		return model;
	}

	@Override
	public RuoloBean toDto(Ruolo model) throws Exception {
		
		RuoloBean dto = new RuoloBean();
		
		dto.setId(model.getId());
		dto.setCodice(model.getCodice());
		dto.setDenominazione(model.getDenominazione());
		dto.setDescrizione(model.getDescrizione());
		
		return dto;
	}

	@Override
	public List<RuoloBean> toDto(List<Ruolo> models) throws Exception {
		
		List<RuoloBean> result = new ArrayList<RuoloBean>();
		
		try {
			
			for(Ruolo model : models) {
				
				result.add(this.toDto(model));
				
			}
			
		} catch (Exception e) {
			
			logger.error("Errore nella conversione Ruolo --> RuoloBean" + e, e);
			
		}
		
		return result;
	}

}
