package it.eng.tz.urbamid.toponomastica.web.dto.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.toponomastica.persistence.model.ClassificaFunzionale;
import it.eng.tz.urbamid.toponomastica.web.dto.ClassificaFunzionaleDTO;

@Component
public class ClassificaFunzionaleConverter implements IConverter<ClassificaFunzionale, ClassificaFunzionaleDTO>{

	private static final Logger logger = LoggerFactory.getLogger(ClassificaFunzionaleConverter.class.getName());
	
	@Override
	public ClassificaFunzionale toModel(ClassificaFunzionaleDTO dto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClassificaFunzionaleDTO toDto(ClassificaFunzionale model) throws Exception {
	
		ClassificaFunzionaleDTO dto = new ClassificaFunzionaleDTO();
		
		dto.setId(model.getId());
		dto.setDescrizione(model.getDescrizione());
		
		return dto;
	}

	@Override
	public List<ClassificaFunzionaleDTO> toDto(List<ClassificaFunzionale> models) throws Exception {
		
		List<ClassificaFunzionaleDTO> result = new ArrayList<>();
		
		try {
			
			for(ClassificaFunzionale model : models) {
				
				result.add(this.toDto(model));
				
			}
			
		} catch (Exception e) {

			logger.error("Errore nella conversione ClassificaFunzionale --> ClassificaFunzionaleDTO" + e, e);
		
		}
		
		return result;
	}

}
