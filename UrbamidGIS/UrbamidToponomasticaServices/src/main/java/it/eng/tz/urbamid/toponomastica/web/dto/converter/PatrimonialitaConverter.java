package it.eng.tz.urbamid.toponomastica.web.dto.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.toponomastica.persistence.model.Patrimonialita;
import it.eng.tz.urbamid.toponomastica.web.dto.PatrimonialitaDTO;

@Component
public class PatrimonialitaConverter implements IConverter<Patrimonialita, PatrimonialitaDTO>{

	private static final Logger logger = LoggerFactory.getLogger(PatrimonialitaConverter.class.getName());
	
	@Override
	public Patrimonialita toModel(PatrimonialitaDTO dto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PatrimonialitaDTO toDto(Patrimonialita model) throws Exception {
		
		PatrimonialitaDTO dto = new PatrimonialitaDTO();
		
		dto.setId(model.getId());
		dto.setDescrizione(model.getDescrizione());
		
		return dto;
	}

	@Override
	public List<PatrimonialitaDTO> toDto(List<Patrimonialita> models) throws Exception {
		
		List<PatrimonialitaDTO> result = new ArrayList<>();
		
		try {
			
			for(Patrimonialita model : models) {
				
				result.add(this.toDto(model));
				
			}
			
		} catch (Exception e) {

			logger.error("Errore nella conversione Patrimonilita --> PatrimonilitaDTO" + e, e);
		
		}
		
		return result;
	}

}
