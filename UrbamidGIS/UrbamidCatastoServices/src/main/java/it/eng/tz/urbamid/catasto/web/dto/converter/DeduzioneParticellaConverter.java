package it.eng.tz.urbamid.catasto.web.dto.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.catasto.exception.ConverterException;
import it.eng.tz.urbamid.catasto.persistence.model.DeduzioneParticella;
import it.eng.tz.urbamid.catasto.util.AbstractConverter;
import it.eng.tz.urbamid.catasto.web.dto.ParticellaDeduzioneDTO;

@Component
public class DeduzioneParticellaConverter extends AbstractConverter<DeduzioneParticella, ParticellaDeduzioneDTO> {

	@Override
	public DeduzioneParticella toModel(ParticellaDeduzioneDTO dto, Map<String, Object> parameters)
			throws ConverterException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParticellaDeduzioneDTO toDTO(DeduzioneParticella model, Map<String, Object> parameters)
			throws ConverterException {
		ParticellaDeduzioneDTO dto = new ParticellaDeduzioneDTO();
		dto.setSimboloDeduzione(model.getSimboloDeduzioni());
		return dto;
	}

	public List<ParticellaDeduzioneDTO> toDTO(List<DeduzioneParticella> models) throws ConverterException {
		List<ParticellaDeduzioneDTO> result = new ArrayList<>();
		try 
		{
			for (DeduzioneParticella model : models) {
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
