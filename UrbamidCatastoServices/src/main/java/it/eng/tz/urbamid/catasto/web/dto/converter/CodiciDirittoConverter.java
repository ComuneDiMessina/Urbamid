package it.eng.tz.urbamid.catasto.web.dto.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.catasto.exception.ConverterException;
import it.eng.tz.urbamid.catasto.persistence.model.CodiciDiritto;
import it.eng.tz.urbamid.catasto.web.dto.TipologicaDTO;

@Component
public class CodiciDirittoConverter implements IConverter<CodiciDiritto, TipologicaDTO>{

	private static final Logger logger = LoggerFactory.getLogger(CodiciDirittoConverter.class.getName());

	@Override
	public CodiciDiritto toModel(TipologicaDTO dto) throws ConverterException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TipologicaDTO toDto(CodiciDiritto model) throws ConverterException {
		TipologicaDTO dto = new TipologicaDTO();
		dto.setCodice(model.getCodice());
		dto.setDescrizione(model.getDescrizione());
		return dto;
	}

	@Override
	public List<TipologicaDTO> toDto(List<CodiciDiritto> models) throws ConverterException {
		List<TipologicaDTO> result = new ArrayList<>();
		try 
		{
			for (CodiciDiritto model : models) {
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
