package it.eng.tz.urbamid.toponomastica.web.dto.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.tz.urbamid.toponomastica.persistence.model.Province;
import it.eng.tz.urbamid.toponomastica.web.dto.ProvinciaDTO;

public class ProvinciaConverter implements IConverter<Province, ProvinciaDTO>{

	private static final Logger logger = LoggerFactory.getLogger(ProvinciaConverter.class.getName());
	
	@Override
	public Province toModel(ProvinciaDTO dto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProvinciaDTO toDto(Province model) throws Exception {
		
		ProvinciaDTO dto = new ProvinciaDTO();
		
		dto.setIdProvincia(model.getIdProvincia());
		dto.setCodiceIstat(model.getCodiceIstat());
		dto.setSigla(model.getSigla());
		dto.setDenominazione(model.getDenominazione());
		dto.setVlInizio(model.getVlInizio());
		dto.setVlFine(model.getVlFine());
		dto.setDuCatAgg(model.getDuCatAgg());
		dto.setIdRegione(model.getIdRegione());
		
		return dto;
	}

	@Override
	public List<ProvinciaDTO> toDto(List<Province> models) throws Exception {
		
		List<ProvinciaDTO> result = new ArrayList<>();
		
		try {
			
			for (Province model : models) {
				
				result.add(this.toDto(model));
				
			}
			
		} catch (Exception e) {
			
			logger.error("Errore nella conversione Provincia --> ProvinciaDTO" + e, e);
			
		}
		
		return result;
	}

}
