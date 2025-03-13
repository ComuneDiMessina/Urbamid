package it.eng.tz.urbamid.catasto.web.dto.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.catasto.exception.ConverterException;
import it.eng.tz.urbamid.catasto.persistence.model.Particella;
import it.eng.tz.urbamid.catasto.util.AbstractConverter;
import it.eng.tz.urbamid.catasto.web.dto.ParticelleTerreniDTO;

@Component
public class ParticellaRicercaConverter extends AbstractConverter<Particella, ParticelleTerreniDTO> {

	@Override
	public Particella toModel(ParticelleTerreniDTO dto, Map<String, Object> parameters) throws ConverterException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParticelleTerreniDTO toDTO(Particella model, Map<String, Object> parameters) throws ConverterException {
		ParticelleTerreniDTO dto = new ParticelleTerreniDTO();
		dto.setId(model.getIdImmobile());
		dto.setComune(model.getCodComune());
		dto.setProvincia(model.getCodComune());
		dto.setSezione(model.getSezione());
		dto.setFoglio(model.getFoglio());
		dto.setNumero(model.getNumero());
		dto.setSub(model.getSubalterno());
		dto.setPartita(model.getPartita());
		dto.setClasse(model.getClasse());
		dto.setQualita(model.getQualita());
		dto.setEttari(model.getEttari());
		dto.setAre(model.getAre());
		dto.setCentiare(model.getCentiare());
		if (model.getEttari() != null && model.getAre() != null && model.getCentiare() != null) {
			String sup = String.valueOf(model.getEttari()*10000.0 + model.getAre()*100.0 + model.getCentiare());
			if (sup.length()>5) {
				sup = sup.substring(0, 5);
			}
			dto.setSuperficie(Double.valueOf(sup));
		} else {
			dto.setSuperficie(0.00);
		}
		dto.setRedditoDominicaleLire(model.getRedditoDominicaleLire());
		dto.setRedditoDominicaleEuro(model.getRedditoDominicaleEuro());
		dto.setRedditoAgrarioLire(model.getRedditoAgrarioLire());
		dto.setRedditoAgrarioEuro(model.getRedditoAgrarioEuro());
		dto.setDenominatore(model.getDenominatore());
		dto.setAnnotazione(model.getAnnotazione());
		return dto;
	}

	public List<ParticelleTerreniDTO> toDTO(List<Particella> models) {
		List<ParticelleTerreniDTO> result = new ArrayList<>();
		try 
		{
			for (Particella model : models) {
				result.add(this.toDTO(model));
			}
		}
		catch (Exception e) 
		{
			//logger.error("Errore nella conversione ParticellaGeom --> ParticellaGeomDTO" + e, e);
		}

		return result;
	}

	public ParticelleTerreniDTO toDTOinformazioni(Particella model) {
		ParticelleTerreniDTO dto = new ParticelleTerreniDTO();
		dto.setId(model.getIdImmobile());
		dto.setComune(model.getCodComune());
		dto.setProvincia(model.getCodComune());
		dto.setSezione(model.getSezione());
		dto.setFoglio(model.getFoglio());
		dto.setNumero(model.getNumero());
		dto.setSub(model.getSubalterno());
		dto.setPartita(model.getPartita());
		dto.setClasse(model.getClasse());
		dto.setQualita(model.getQualita());
		dto.setEttari(model.getEttari());
		dto.setAre(model.getAre());
		dto.setCentiare(model.getCentiare());
		String sup = String.valueOf(model.getEttari()*10000.0 + model.getAre()*100.0 + model.getCentiare());
		if (sup.length()>5) {
			sup = sup.substring(0, 5);
		}
		dto.setSuperficie(Double.valueOf(sup));
		dto.setRedditoDominicaleLire(model.getRedditoDominicaleLire());
		dto.setRedditoDominicaleEuro(model.getRedditoAgrarioEuro());
		dto.setRedditoAgrarioLire(model.getRedditoAgrarioLire());
		dto.setRedditoAgrarioEuro(model.getRedditoAgrarioEuro());
		dto.setDenominatore(model.getDenominatore());
		dto.setAnnotazione(model.getAnnotazione());
		return dto;
	}

}
