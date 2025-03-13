package it.eng.tz.urbamid.catasto.web.dto.converter;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.catasto.exception.ConverterException;
import it.eng.tz.urbamid.catasto.persistence.model.Soggetto;
import it.eng.tz.urbamid.catasto.persistence.model.SoggettoIntestatario;
import it.eng.tz.urbamid.catasto.web.dto.ListaIntestatariDTO;
import it.eng.tz.urbamid.catasto.web.dto.ListaIntestatariDirittoDTO;
import it.eng.tz.urbamid.catasto.web.dto.ListaIntestatariVisuraDTO;
import it.eng.tz.urbamid.catasto.web.dto.RicercaSoggettiDTO;

@Component
public class IntestatariConverter implements IConverter<Soggetto, ListaIntestatariDTO> {

	private static final Logger logger = LoggerFactory.getLogger(IntestatariConverter.class.getName());

	Format formatter = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public Soggetto toModel(ListaIntestatariDTO dto) throws ConverterException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListaIntestatariDTO toDto(Soggetto model) throws ConverterException {
		ListaIntestatariDTO dto = new ListaIntestatariDTO();
		dto.setComune("Messina");
		dto.setProvincia("ME");
		dto.setNome(model.getNome());
		dto.setCognome(model.getCognome());
		dto.setCodiceFiscale(model.getCodiceFiscale());
		dto.setTipoSoggetto(model.getTipoSoggetto());
		if (model.getTipoSoggetto().equalsIgnoreCase("P")) {
			dto.setSesso(model.getSesso().equalsIgnoreCase("1") ? "M" : "F");
		} else {
			dto.setSesso(null);
		}
		dto.setDataNascita(model.getDataNascita() == null ? null : formatter.format(model.getDataNascita()));
		dto.setNote(model.getIndicazioniSupplementari());
		dto.setDenominazione(model.getDenominazione());
		dto.setSede(model.getSede());
		return dto;
	}

	@Override
	public List<ListaIntestatariDTO> toDto(List<Soggetto> models) throws ConverterException {
		List<ListaIntestatariDTO> result = new ArrayList<>();
		try 
		{
			for (Soggetto model : models) {
				result.add(this.toDto(model));
			}
		}
		catch (Exception e) 
		{
			logger.error("Errore nella conversione ParticellaGeom --> ParticellaGeomDTO" + e, e);
		}

		return result;
	}

	public ListaIntestatariDTO toDtoCustom(SoggettoIntestatario model) throws ConverterException {
		ListaIntestatariDTO dto = new ListaIntestatariDTO();
		dto.setComune("Messina");
		dto.setProvincia("ME");
		dto.setTipoSoggetto(model.getTipo_soggetto());
		if (model.getTipo_soggetto().equalsIgnoreCase("P")) {
			dto.setNome(model.getCognome() + " " + model.getNome());
		} else {
			dto.setNome(model.getDenominazione());
		}
		dto.setCodiceFiscale(model.getCodice_fiscale());
		dto.setDataNascita(model.getData_nascita() == null ? null : formatter.format(model.getData_nascita()));
		dto.setNote(model.getNote());
		dto.setDenominazione(model.getDenominazione());
		return dto;
	}

	public List<ListaIntestatariDTO> toDtoCustom(List<SoggettoIntestatario> models) throws ConverterException {
		List<ListaIntestatariDTO> result = new ArrayList<>();
		try 
		{
			for (SoggettoIntestatario model : models) {
				result.add(this.toDtoCustom(model));
			}
		}
		catch (Exception e) 
		{
			logger.error("Errore nella conversione ParticellaGeom --> ParticellaGeomDTO" + e, e);
		}

		return result;
	}

	public RicercaSoggettiDTO toDtoSoggetti(Soggetto model) throws ConverterException {
		RicercaSoggettiDTO dto = new RicercaSoggettiDTO();
		dto.setIdSoggetto(Long.valueOf(model.getIdSoggetto()));
		dto.setCodComune(model.getCodComune());
		dto.setSezione(model.getSezione());
		if (model.getTipoSoggetto().equalsIgnoreCase("P")) {
			dto.setNome(model.getCognome() + " " + model.getNome());
			dto.setSesso(model.getSesso().equalsIgnoreCase("1") ? "M" : "F");
		} else {
			dto.setNome(model.getDenominazione());
		}
		dto.setDataNascita(model.getDataNascita() == null ? null : formatter.format(model.getDataNascita()));
		dto.setLuogoNascita(model.getLuogoNascita());
		dto.setCodiceFiscale(model.getCodiceFiscale());
		dto.setDenominazione(model.getDenominazione());
		dto.setSede(model.getSede());
		dto.setIndicazioniSupplementari(model.getIndicazioniSupplementari());
		return dto;
	}

	public List<RicercaSoggettiDTO> toDtoSoggetti(List<Soggetto> models) throws ConverterException {
		List<RicercaSoggettiDTO> result = new ArrayList<>();
		try 
		{
			for (Soggetto model : models) {
				result.add(this.toDtoSoggetti(model));
			}
		}
		catch (Exception e) 
		{
			logger.error("Errore nella conversione ParticellaGeom --> ParticellaGeomDTO" + e, e);
		}

		return result;
	}

	public ListaIntestatariDirittoDTO toDtoCustomDiritto(SoggettoIntestatario model) throws ConverterException {
		ListaIntestatariDirittoDTO dto = new ListaIntestatariDirittoDTO();
		dto.setComune("Messina");
		dto.setProvincia("ME");
		dto.setTipoSoggetto(model.getTipo_soggetto());
		if (model.getTipo_soggetto().equalsIgnoreCase("P")) {
			dto.setNome(model.getCognome() + " " + model.getNome());
		} else {
			dto.setNome(model.getDenominazione());
		}
		dto.setCodiceFiscale(model.getCodice_fiscale());
		dto.setDataNascita(model.getData_nascita() == null ? null : formatter.format(model.getData_nascita()));
		dto.setNote(model.getNote());
		dto.setDenominazione(model.getDenominazione());
		dto.setDiritto(model.getDiritto());
		return dto;
	}

	public List<ListaIntestatariDirittoDTO> toDtoCustomDiritto(List<SoggettoIntestatario> resultModel) {
		List<ListaIntestatariDirittoDTO> result = new ArrayList<>();
		try 
		{
			for (SoggettoIntestatario model : resultModel) {
				result.add(this.toDtoCustomDiritto(model));
			}
		}
		catch (Exception e) 
		{
			logger.error("Errore nella conversione ParticellaGeom --> ParticellaGeomDTO" + e, e);
		}

		return result;
	}

	public ListaIntestatariVisuraDTO toDtoVisura(SoggettoIntestatario model, String comune, Integer index) throws ConverterException {
		ListaIntestatariVisuraDTO dto = new ListaIntestatariVisuraDTO();
		dto.setIndiceIntestatario(index);
		if (model.getTipo_soggetto().equalsIgnoreCase("P")) {
			String anagrafica = model.getCognome() + " " + model.getNome() + " nato a " + comune;
			if (model.getData_nascita() != null) {
				anagrafica += " il " + model.getData_nascita();
			}
			dto.setDatiAnagrafici(anagrafica);
		} else {
			dto.setDatiAnagrafici(model.getDenominazione());
		}
		dto.setCodiceFiscale(model.getCodice_fiscale());
		dto.setDiritto(model.getDiritto());
		return dto;
	}

	public List<ListaIntestatariVisuraDTO> toDtoVisura(List<SoggettoIntestatario> listaIntestatari, String comune, Integer index) {
		List<ListaIntestatariVisuraDTO> result = new ArrayList<>();
		try 
		{
			for (SoggettoIntestatario model : listaIntestatari) {
				result.add(this.toDtoVisura(model, comune, index));
				index++;
			}
		}
		catch (Exception e) 
		{
			logger.error("Errore nella conversione ParticellaGeom --> ParticellaGeomDTO" + e, e);
		}

		return result;
	}

}
