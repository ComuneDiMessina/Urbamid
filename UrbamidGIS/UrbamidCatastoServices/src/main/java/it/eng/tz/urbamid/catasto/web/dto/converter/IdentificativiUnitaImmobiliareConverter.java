package it.eng.tz.urbamid.catasto.web.dto.converter;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.catasto.exception.ConverterException;
import it.eng.tz.urbamid.catasto.persistence.model.FabbricatiGeom;
import it.eng.tz.urbamid.catasto.persistence.model.IdentificativoUnitaImmobiliari;
import it.eng.tz.urbamid.catasto.util.AbstractConverter;
import it.eng.tz.urbamid.catasto.web.dto.IdentificativiUiuDTO;

@Component
public class IdentificativiUnitaImmobiliareConverter extends AbstractConverter<IdentificativoUnitaImmobiliari, IdentificativiUiuDTO> {

	@Override
	public IdentificativoUnitaImmobiliari toModel(IdentificativiUiuDTO dto, Map<String, Object> parameters)
			throws ConverterException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdentificativiUiuDTO toDTO(IdentificativoUnitaImmobiliari model, Map<String, Object> parameters)
			throws ConverterException {
		// TODO Auto-generated method stub
		return null;
	}

	public IdentificativiUiuDTO toDtoDettaglio(IdentificativoUnitaImmobiliari model, List<FabbricatiGeom> list) {
		IdentificativiUiuDTO dto = new IdentificativiUiuDTO();
		dto.setId(model.getId());
		dto.setSezione(model.getSezione());
		dto.setNumero(model.getNumero());
		dto.setFoglio(model.getFoglio());
		dto.setSubalterno(model.getSubalterno());
		if (list != null && !list.isEmpty()) {
			dto.setGeometry(list.get(0).getGeometry());
		}
		return dto;
	}

}
