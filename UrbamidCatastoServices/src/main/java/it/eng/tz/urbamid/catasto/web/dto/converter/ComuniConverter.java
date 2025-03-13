package it.eng.tz.urbamid.catasto.web.dto.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.catasto.exception.ConverterException;
import it.eng.tz.urbamid.catasto.persistence.model.Comuni;
import it.eng.tz.urbamid.catasto.web.dto.TipologicaDTO;

@Component
public class ComuniConverter implements IConverter<Comuni, TipologicaDTO>{

	private static final Logger logger = LoggerFactory.getLogger(ComuniConverter.class.getName());

	@Override
	public Comuni toModel(TipologicaDTO dto) throws ConverterException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TipologicaDTO toDto(Comuni model) throws ConverterException {
		TipologicaDTO dto = new TipologicaDTO();
		dto.setCodice(model.getCodiceMf());
		dto.setDescrizione(model.getDenominazione());
		dto.setInformazione(model.getIdProvincia().toString());
		return dto;
	}

	@Override
	public List<TipologicaDTO> toDto(List<Comuni> models) throws ConverterException {
		List<TipologicaDTO> result = new ArrayList<>();
		try 
		{
			for (Comuni model : models) {
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
