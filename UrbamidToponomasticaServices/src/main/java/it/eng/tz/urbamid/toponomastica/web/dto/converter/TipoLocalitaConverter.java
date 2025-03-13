package it.eng.tz.urbamid.toponomastica.web.dto.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.toponomastica.persistence.model.TipoLocalita;
import it.eng.tz.urbamid.toponomastica.web.dto.TipoLocalitaDTO;

@Component
public class TipoLocalitaConverter implements IConverter<TipoLocalita, TipoLocalitaDTO> {

	private static final Logger logger = LoggerFactory.getLogger(TipoLocalitaConverter.class.getName());
	
	@Override
	public TipoLocalita toModel(TipoLocalitaDTO dto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TipoLocalitaDTO toDto(TipoLocalita model) throws Exception {
		
		TipoLocalitaDTO dto = new TipoLocalitaDTO();
		
		dto.setId(model.getId());
		dto.setDescrizione(model.getDescrizione());
		
		return dto;
	}

	@Override
	public List<TipoLocalitaDTO> toDto(List<TipoLocalita> models) throws Exception {

		List<TipoLocalitaDTO> result = new ArrayList<>();
		
		try {
			
			for(TipoLocalita model : models) {
				
				result.add(this.toDto(model));
				
			}
			
		} catch (Exception e) {

			logger.error("Errore nella conversione TipoLocalita --> TipoLocalitaDTO" + e, e);
		
		}
		
		return result;
	}

}
