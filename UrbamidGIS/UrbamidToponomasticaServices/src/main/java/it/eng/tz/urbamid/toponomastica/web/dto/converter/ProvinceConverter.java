package it.eng.tz.urbamid.toponomastica.web.dto.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.toponomastica.persistence.model.Province;
import it.eng.tz.urbamid.toponomastica.web.dto.TipologicaDTO;

@Component
public class ProvinceConverter implements IConverter<Province, TipologicaDTO>{

	private static final Logger logger = LoggerFactory.getLogger(ProvinceConverter.class.getName());

	@Override
	public Province toModel(TipologicaDTO dto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TipologicaDTO toDto(Province model) throws Exception {
		TipologicaDTO dto = new TipologicaDTO();
		dto.setCodice(model.getIdProvincia().toString());
		dto.setDescrizione(model.getDenominazione() + " (" + model.getSigla() + ")");
		return dto;
	}

	@Override
	public List<TipologicaDTO> toDto(List<Province> models) throws Exception {
		List<TipologicaDTO> result = new ArrayList<>();
		try 
		{
			for (Province model : models) {
				result.add(this.toDto(model));
			}
		}
		catch (Exception e) 
		{
			logger.error("Errore nella conversione CodiciDirittoGeom --> CodiciDirittoGeomDTO" + e, e);
		}

		return result;
	}

}
