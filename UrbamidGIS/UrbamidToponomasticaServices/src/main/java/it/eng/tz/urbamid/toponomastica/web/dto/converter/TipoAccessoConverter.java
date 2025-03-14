package it.eng.tz.urbamid.toponomastica.web.dto.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.toponomastica.persistence.model.TipoAccesso;
import it.eng.tz.urbamid.toponomastica.web.dto.TipoAccessoDTO;

@Component
public class TipoAccessoConverter implements IConverter<TipoAccesso, TipoAccessoDTO> {

	private static final Logger logger = LoggerFactory.getLogger(TipoAccessoConverter.class.getName());
	
	@Override
	public TipoAccesso toModel(TipoAccessoDTO dto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TipoAccessoDTO toDto(TipoAccesso model) throws Exception {
		
		TipoAccessoDTO dto = new TipoAccessoDTO();
		
		dto.setId(model.getId());
		dto.setDescrizione(model.getDescrizione());
		
		return dto;
	}

	@Override
	public List<TipoAccessoDTO> toDto(List<TipoAccesso> models) throws Exception {
		
		List<TipoAccessoDTO> result = new ArrayList<>();
		
		try {
			
			for (TipoAccesso model : models) {
				
				result.add(this.toDto(model));
				
			}
			
		} catch (Exception e) {
			
			logger.error("Errore nella conversione TipoAccesso --> TipoAccessoDTO" + e, e);
			
		}
		
		return result;
	}

	
	
}
