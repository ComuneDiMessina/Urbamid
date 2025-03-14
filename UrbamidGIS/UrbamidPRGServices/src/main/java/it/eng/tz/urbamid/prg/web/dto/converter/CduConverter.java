package it.eng.tz.urbamid.prg.web.dto.converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.prg.exception.ConverterException;
import it.eng.tz.urbamid.prg.persistence.model.Cdu;
import it.eng.tz.urbamid.prg.util.AbstractConverter;
import it.eng.tz.urbamid.prg.web.dto.CduDTO;

@Component
public class CduConverter extends AbstractConverter<Cdu, CduDTO>{

	DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public Cdu toModel(CduDTO dto, Map<String, Object> parameters) throws ConverterException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CduDTO toDTO(Cdu model, Map<String, Object> parameters) throws ConverterException {
		CduDTO dto = new CduDTO();
		dto.setId(model.getId());
		dto.setProtocollo(model.getProtocollo());
		dto.setDataCreazione(model.getDataCreazione() == null ? null : formatter.format(model.getDataCreazione()));
		return dto;
	}

}
