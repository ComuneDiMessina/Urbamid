package it.eng.tz.urbamid.toponomastica.web.dto.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.toponomastica.persistence.model.TipoFunzionale;
import it.eng.tz.urbamid.toponomastica.web.dto.TipoFunzionaleDTO;

@Component
public class TipoFunzionaleConverter implements IConverter<TipoFunzionale, TipoFunzionaleDTO> {

	private static final Logger logger = LoggerFactory.getLogger(TipoFunzionaleConverter.class.getName());
	
	@Override
	public TipoFunzionale toModel(TipoFunzionaleDTO dto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TipoFunzionaleDTO toDto(TipoFunzionale model) throws Exception {
		
		TipoFunzionaleDTO dto = new TipoFunzionaleDTO();
		
		dto.setId(model.getId());
		dto.setDescrizione(model.getDescrizione());
		
		return dto;
	}

	@Override
	public List<TipoFunzionaleDTO> toDto(List<TipoFunzionale> models) throws Exception {
		
		List<TipoFunzionaleDTO> result = new ArrayList<>();
		
		try {
			
			for (TipoFunzionale model : models) {
				result.add(this.toDto(model));
			}
			
		} catch (Exception e) {
			
			logger.error("Errore nella conversione TipoLocalita --> TipoLocalitaDTO: {}" + e.getMessage());
			throw new Exception("Errore nella conversione TipoLocalita --> TipoLocalitaDTO.");
			
		}
		
		return result;
	}

}
