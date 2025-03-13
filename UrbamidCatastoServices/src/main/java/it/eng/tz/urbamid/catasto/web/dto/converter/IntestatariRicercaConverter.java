package it.eng.tz.urbamid.catasto.web.dto.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.catasto.exception.ConverterException;
import it.eng.tz.urbamid.catasto.persistence.model.RicercaIntestazioni;
import it.eng.tz.urbamid.catasto.util.AbstractConverter;
import it.eng.tz.urbamid.catasto.web.dto.RicercaIntestazioniDTO;

@Component
public class IntestatariRicercaConverter extends AbstractConverter<RicercaIntestazioni, RicercaIntestazioniDTO> {

	@Override
	public RicercaIntestazioni toModel(RicercaIntestazioniDTO dto, Map<String, Object> parameters)
			throws ConverterException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RicercaIntestazioniDTO toDTO(RicercaIntestazioni model, Map<String, Object> parameters)
			throws ConverterException {
		RicercaIntestazioniDTO dto = new RicercaIntestazioniDTO();
		dto.setCodiceFiscale(model.getCodiceFiscale());
		dto.setCognome(model.getCognome());
		dto.setNome(model.getNome());
		dto.setIdSoggetto(model.getIdSoggetto());
		dto.setTipo(model.getTipo());
		dto.setSezione(model.getSezione());
		dto.setFoglio(model.getFoglio());
		dto.setNumero(model.getNumero());
		dto.setSubalterno(model.getSubalterno());
		dto.setCodComune(model.getCodComune());
		dto.setIdImmobile(model.getIdImmobile());

		dto.setDenominazione(model.getDenominazione());
		dto.setUte("");
		return dto;
	}

	public List<RicercaIntestazioniDTO> toDTO(List<RicercaIntestazioni> models) throws ConverterException {
		List<RicercaIntestazioniDTO> result = new ArrayList<>();
		try 
		{
			for (RicercaIntestazioni model : models) {
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
