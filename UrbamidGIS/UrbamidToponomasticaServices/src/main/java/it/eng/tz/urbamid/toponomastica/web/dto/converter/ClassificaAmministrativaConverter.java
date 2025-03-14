package it.eng.tz.urbamid.toponomastica.web.dto.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.toponomastica.persistence.model.ClassificaAmministrativa;
import it.eng.tz.urbamid.toponomastica.web.dto.ClassificaAmministrativaDTO;

@Component
public class ClassificaAmministrativaConverter implements IConverter<ClassificaAmministrativa, ClassificaAmministrativaDTO>{

	private static final Logger logger = LoggerFactory.getLogger(ClassificaAmministrativaConverter.class.getName());
	
	@Override
	public ClassificaAmministrativa toModel(ClassificaAmministrativaDTO dto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClassificaAmministrativaDTO toDto(ClassificaAmministrativa model) throws Exception {
		
		ClassificaAmministrativaDTO dto = new ClassificaAmministrativaDTO();
		
		dto.setId(model.getId());
		dto.setDescrizione(model.getDescrizione());
		
		return dto;
	}

	@Override
	public List<ClassificaAmministrativaDTO> toDto(List<ClassificaAmministrativa> models) throws Exception {
		
		List<ClassificaAmministrativaDTO> result = new ArrayList<>();
		
		try {
			
			for(ClassificaAmministrativa model : models) {
				
				result.add(this.toDto(model));
				
			}
			
		} catch (Exception e) {

			logger.error("Errore nella conversione ClassificaAmministrativa --> ClassificaAmministrativaDTO" + e, e);
		
		}
		
		return result;

	}

}
