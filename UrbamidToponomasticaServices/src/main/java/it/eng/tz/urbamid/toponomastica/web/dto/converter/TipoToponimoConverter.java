package it.eng.tz.urbamid.toponomastica.web.dto.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.toponomastica.persistence.model.TipoToponimo;
import it.eng.tz.urbamid.toponomastica.web.dto.TipoToponimoDTO;

@Component
public class TipoToponimoConverter implements IConverter<TipoToponimo, TipoToponimoDTO>{

	private static final Logger logger = LoggerFactory.getLogger(TipoToponimoConverter.class.getName());
	
	@Override
	public TipoToponimo toModel(TipoToponimoDTO dto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TipoToponimoDTO toDto(TipoToponimo model) throws Exception {
		
		TipoToponimoDTO dto = new TipoToponimoDTO();
		
		dto.setId(model.getId());
		dto.setDescrizione(model.getDescrizione());
		
		return dto;
	}

	@Override
	public List<TipoToponimoDTO> toDto(List<TipoToponimo> models) throws Exception {
		
		List<TipoToponimoDTO> result = new ArrayList<>();
		
		try {
			
			for(TipoToponimo model : models) {
				
				result.add(this.toDto(model));
				
			}
			
		} catch (Exception e) {

			logger.error("Errore nella conversione TipoToponimo --> TipoToponimoDTO" + e, e);
		
		}
		
		return result;
	}

	
	
}
