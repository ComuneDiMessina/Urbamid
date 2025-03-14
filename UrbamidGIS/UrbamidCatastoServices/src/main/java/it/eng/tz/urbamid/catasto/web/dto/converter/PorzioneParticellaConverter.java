package it.eng.tz.urbamid.catasto.web.dto.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.catasto.exception.ConverterException;
import it.eng.tz.urbamid.catasto.persistence.model.PorzioneParticella;
import it.eng.tz.urbamid.catasto.util.AbstractConverter;
import it.eng.tz.urbamid.catasto.web.dto.ParticellaPorzioneDTO;

@Component
public class PorzioneParticellaConverter extends AbstractConverter<PorzioneParticella, ParticellaPorzioneDTO> {

	@Override
	public PorzioneParticella toModel(ParticellaPorzioneDTO dto, Map<String, Object> parameters)
			throws ConverterException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParticellaPorzioneDTO toDTO(PorzioneParticella model, Map<String, Object> parameters)
			throws ConverterException {
		ParticellaPorzioneDTO dto = new ParticellaPorzioneDTO();
		dto.setClasse(model.getClasse());
		dto.setEttari(model.getEttari());
		dto.setAre(model.getAre());
		dto.setCentiare(model.getCentiare());
		dto.setQualita(model.getQualita());
		return dto;
	}

	public List<ParticellaPorzioneDTO> toDTO(List<PorzioneParticella> models) throws ConverterException {
		List<ParticellaPorzioneDTO> result = new ArrayList<>();
		try 
		{
			for (PorzioneParticella model : models) {
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
