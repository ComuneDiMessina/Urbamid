package it.eng.tz.urbamid.catasto.web.dto.converter;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.catasto.exception.ConverterException;
import it.eng.tz.urbamid.catasto.persistence.model.SoggettoModel;
import it.eng.tz.urbamid.catasto.util.AbstractConverter;
import it.eng.tz.urbamid.catasto.web.dto.RicercaSoggettiDTO;

@Component
public class SoggettoConverter extends AbstractConverter<SoggettoModel, RicercaSoggettiDTO>{

	Format formatter = new SimpleDateFormat("dd/MM/yyyy");

	/*@Override
	public Soggetto toModel(RicercaSoggettiDTO dto, Map<String, Object> parameters) throws ConverterException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RicercaSoggettiDTO toDTO(Soggetto model, Map<String, Object> parameters) throws ConverterException {
		RicercaSoggettiDTO dto = new RicercaSoggettiDTO();
		dto.setIdSoggetto(Long.valueOf(model.getIdSoggetto()));
		dto.setCodComune(model.getCodComune());
		dto.setSezione(model.getSezione());
		if (model.getTipoSoggetto().equalsIgnoreCase("P")) {
			dto.setNome(model.getCognome() + " " + model.getNome());
			if (model.getSesso() != null) {
				dto.setSesso(model.getSesso().equalsIgnoreCase("1") ? "M" : "F");
			} else {
				dto.setSesso("N/D");
			}
		} else {
			dto.setNome(model.getDenominazione());
		}
		dto.setDataNascita(model.getDataNascita() == null ? null : formatter.format(model.getDataNascita()));
		dto.setLuogoNascita(model.getLuogoNascita());
		dto.setCodiceFiscale(model.getCodiceFiscale()!= null ? model.getCodiceFiscale() : "");
		dto.setDenominazione(model.getDenominazione());
		dto.setSede(model.getSede());
		dto.setIndicazioniSupplementari(model.getIndicazioniSupplementari());
		return dto;
	}*/

	@Override
	public RicercaSoggettiDTO toDTO(SoggettoModel model, Map<String, Object> parameters)
			throws ConverterException {
		RicercaSoggettiDTO dto = new RicercaSoggettiDTO();
		dto.setIdSoggetto(Long.valueOf(model.getIdSoggetto()));
		dto.setCodComune(model.getCodComune());
		dto.setSezione(model.getSezione());
		if (model.getTipoSoggetto().equalsIgnoreCase("P")) {
			dto.setNome(model.getCognome() + " " + model.getNome());
			if (model.getSesso() != null) {
				dto.setSesso(model.getSesso().equalsIgnoreCase("1") ? "M" : "F");
			} else {
				dto.setSesso("N/D");
			}
		} else {
			dto.setNome(model.getDenominazione());
		}
		dto.setDataNascita(model.getDataNascita() == null ? null : formatter.format(model.getDataNascita()));
		dto.setLuogoNascita(model.getLuogoNascita());
		dto.setCodiceFiscale(model.getCodiceFiscale()!= null ? model.getCodiceFiscale() : "");
		dto.setDenominazione(model.getDenominazione());
		dto.setSede(model.getSede());
		dto.setIndicazioniSupplementari(model.getIndicazioniSupplementari());
		
		return dto;
	}

	public List<RicercaSoggettiDTO> toDTO(List<SoggettoModel> models) throws ConverterException {
		List<RicercaSoggettiDTO> result = new ArrayList<>();
		try 
		{
			for (SoggettoModel model : models) {
				result.add(this.toDTO(model));
			}
		}
		catch (Exception e) 
		{
			//logger.error("Errore nella conversione ParticellaGeom --> ParticellaGeomDTO" + e, e);
		}

		return result;
	}

	@Override
	public SoggettoModel toModel(RicercaSoggettiDTO dto, Map<String, Object> parameters) throws ConverterException {
		// TODO Auto-generated method stub
		return null;
	}

}
