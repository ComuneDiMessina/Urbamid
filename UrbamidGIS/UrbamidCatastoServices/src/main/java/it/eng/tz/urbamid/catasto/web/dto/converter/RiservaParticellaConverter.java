package it.eng.tz.urbamid.catasto.web.dto.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.catasto.exception.ConverterException;
import it.eng.tz.urbamid.catasto.persistence.model.RiservaParticella;
import it.eng.tz.urbamid.catasto.util.AbstractConverter;
import it.eng.tz.urbamid.catasto.web.dto.ParticellaRiservaDTO;

@Component
public class RiservaParticellaConverter extends AbstractConverter<RiservaParticella, ParticellaRiservaDTO> {

	@Override
	public RiservaParticella toModel(ParticellaRiservaDTO dto, Map<String, Object> parameters)
			throws ConverterException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParticellaRiservaDTO toDTO(RiservaParticella model, Map<String, Object> parameters)
			throws ConverterException {
		ParticellaRiservaDTO dto = new ParticellaRiservaDTO();
		dto.setRiserva(model.getCodiceRiserva());
		dto.setPartitaIscrizione(model.getPartitaIscrizioneRiserva());
		return dto;
	}

	public List<ParticellaRiservaDTO> toDTO(List<RiservaParticella> models) throws ConverterException {
		List<ParticellaRiservaDTO> result = new ArrayList<>();
		try 
		{
			for (RiservaParticella model : models) {
				result.add(this.toDTO(model));
			}
		}
		catch (Exception e) 
		{
			//logger.error("Errore nella conversione ParticellaGeom --> ParticellaGeomDTO" + e, e);
		}

		return result;
	}

}
