package it.eng.tz.urbamid.toponomastica.web.dto.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.toponomastica.persistence.model.Dug;
import it.eng.tz.urbamid.toponomastica.web.dto.DugDTO;

@Component
public class DugConverter implements IConverter<Dug, DugDTO>{

	private static final Logger logger = LoggerFactory.getLogger(DugConverter.class.getName());
	
	@Override
	public Dug toModel(DugDTO dto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DugDTO toDto(Dug model) throws Exception {
		
		DugDTO dto = new DugDTO();
		
		dto.setId(model.getId());
		dto.setDug(model.getDug());
		
		return dto;
	}

	@Override
	public List<DugDTO> toDto(List<Dug> models) throws Exception {
		
		List<DugDTO> result = new ArrayList<>();
		
		try {
			
			for(Dug model : models) {
				
				result.add(this.toDto(model));
				
			}
			
		} catch (Exception e) {

			logger.error("Errore nella conversione Dug --> DugDTO" + e, e);
		
		}
		
		return result;
	}

}
