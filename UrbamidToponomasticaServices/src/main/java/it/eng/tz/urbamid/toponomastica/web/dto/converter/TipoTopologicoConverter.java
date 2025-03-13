package it.eng.tz.urbamid.toponomastica.web.dto.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.toponomastica.persistence.model.TipoTopologico;
import it.eng.tz.urbamid.toponomastica.web.dto.TipoTopologicoDTO;

@Component
public class TipoTopologicoConverter implements IConverter<TipoTopologico, TipoTopologicoDTO> {

	private static final Logger logger = LoggerFactory.getLogger(TipoTopologicoConverter.class.getName());
	
	@Override
	public TipoTopologico toModel(TipoTopologicoDTO dto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TipoTopologicoDTO toDto(TipoTopologico model) throws Exception {
		
		TipoTopologicoDTO dto = new TipoTopologicoDTO();
		
		dto.setId(model.getId());
		dto.setDescrizione(model.getDescrizione());
		
		return dto;
	}

	@Override
	public List<TipoTopologicoDTO> toDto(List<TipoTopologico> models) throws Exception {
		
		List<TipoTopologicoDTO> result = new ArrayList<>();
		
		try {
			
			for (TipoTopologico model : models) {
				result.add(this.toDto(model));
			}
			
		} catch (Exception e) {
			
			logger.error("Errore nella conversione TipoLocalita --> TipoLocalitaDTO: {}" + e.getMessage());
			throw new Exception("Errore nella conversione TipoLocalita --> TipoLocalitaDTO.");
		}
		
		return result;
	}

	

}
