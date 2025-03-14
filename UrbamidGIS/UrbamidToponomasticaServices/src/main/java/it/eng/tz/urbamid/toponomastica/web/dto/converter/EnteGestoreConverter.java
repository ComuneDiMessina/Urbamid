package it.eng.tz.urbamid.toponomastica.web.dto.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.toponomastica.persistence.model.EnteGestore;
import it.eng.tz.urbamid.toponomastica.web.dto.EnteGestoreDTO;

@Component
public class EnteGestoreConverter implements IConverter<EnteGestore, EnteGestoreDTO> {

	private static final Logger logger = LoggerFactory.getLogger(EnteGestoreConverter.class.getName());
	
	@Override
	public EnteGestore toModel(EnteGestoreDTO dto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EnteGestoreDTO toDto(EnteGestore model) throws Exception {

		EnteGestoreDTO dto = new EnteGestoreDTO();
		
		dto.setId(model.getId());
		dto.setCodice(model.getCodice());
		dto.setDescrizione(model.getDescrizione());
		
		return dto;
	}

	@Override
	public List<EnteGestoreDTO> toDto(List<EnteGestore> models) throws Exception {
		
		List<EnteGestoreDTO> result = new ArrayList<>();
		
		try {
			
			for (EnteGestore model : models) {
				
				result.add(this.toDto(model));
				
			}
			
		} catch (Exception e) {

			logger.error("Errore nella conversione EnteGestore --> EnteGestoreDTO" + e, e);
		
		}
		
		return result;
	}

	
	
}
