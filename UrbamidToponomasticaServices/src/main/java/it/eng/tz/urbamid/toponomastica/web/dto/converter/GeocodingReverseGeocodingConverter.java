package it.eng.tz.urbamid.toponomastica.web.dto.converter;

import java.util.Map;

import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.toponomastica.exception.ConverterException;
import it.eng.tz.urbamid.toponomastica.persistence.model.Accesso;
import it.eng.tz.urbamid.toponomastica.persistence.model.Comuni;
import it.eng.tz.urbamid.toponomastica.persistence.model.Dug;
import it.eng.tz.urbamid.toponomastica.persistence.model.GeocodingReverseGeocoding;
import it.eng.tz.urbamid.toponomastica.persistence.model.Localita;
import it.eng.tz.urbamid.toponomastica.persistence.model.TipoAccesso;
import it.eng.tz.urbamid.toponomastica.persistence.model.TipoLocalita;
import it.eng.tz.urbamid.toponomastica.persistence.model.TipoToponimo;
import it.eng.tz.urbamid.toponomastica.persistence.model.ToponimoStradale;
import it.eng.tz.urbamid.toponomastica.util.AbstractConverter;
import it.eng.tz.urbamid.toponomastica.web.dto.AccessoDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.ComuniDto;
import it.eng.tz.urbamid.toponomastica.web.dto.DugDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.GeocodingReverseGeocodingDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.LocalitaDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.TipoAccessoDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.TipoLocalitaDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.TipoToponimoDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.ToponimoStradaleDTO;

@Component
public class GeocodingReverseGeocodingConverter extends AbstractConverter<GeocodingReverseGeocoding, GeocodingReverseGeocodingDTO> {

	@Override
	public GeocodingReverseGeocoding toModel(GeocodingReverseGeocodingDTO dto, Map<String, Object> parameters)
			throws ConverterException {

		ToponimoStradale toponimoStradale = new ToponimoStradale();

		if(dto.getToponimo() != null) {

			Dug dug = new Dug();

			if(dto.getToponimo().getClasse() != null) {

				dug.setId(dto.getToponimo().getClasse().getId());
				dug.setDug(dto.getToponimo().getClasse().getDug());

			}

			Comuni comuneToponimo = new Comuni();

			if(dto.getToponimo().getComune() != null) {

				comuneToponimo.setIdComune(dto.getToponimo().getComune().getIdComune());
				comuneToponimo.setSiglaProvincia(dto.getToponimo().getComune().getSiglaProvincia());
				comuneToponimo.setCodiceIstat(dto.getToponimo().getComune().getCodiceIstat());
				comuneToponimo.setCodiceMf(dto.getToponimo().getComune().getCodiceMf());
				comuneToponimo.setDenominazione(dto.getToponimo().getComune().getDenominazione());
				comuneToponimo.setVlInizio(dto.getToponimo().getComune().getVlInizio());
				comuneToponimo.setVlFine(dto.getToponimo().getComune().getVlFine());
				comuneToponimo.setDuCatAgg(dto.getToponimo().getComune().getDuCatAgg());
				comuneToponimo.setIdProvincia(dto.getToponimo().getComune().getIdProvincia());

			}

			TipoToponimo tipoToponimo = new TipoToponimo();

			if(dto.getToponimo().getTipo() != null) {

				tipoToponimo.setId(dto.getToponimo().getTipo().getId());
				tipoToponimo.setDescrizione(dto.getToponimo().getTipo().getDescrizione());

			}

			toponimoStradale.setId(dto.getToponimo().getId());
			toponimoStradale.setComuneLabel(dto.getToponimo().getComuneLabel());
			toponimoStradale.setDenominazione(dto.getToponimo().getDenominazione());
			toponimoStradale.setDenominazioneUfficiale(dto.getToponimo().getDenominazioneUfficiale());
			toponimoStradale.setClasseLabel(dto.getToponimo().getClasseLabel());
			toponimoStradale.setShapeLeng(dto.getToponimo().getShapeLeng());
			toponimoStradale.setCap(dto.getToponimo().getCap());
			toponimoStradale.setCompendi(dto.getToponimo().getCompendi());
			toponimoStradale.setPrecdenomi(dto.getToponimo().getPrecdenomi());
			toponimoStradale.setQuartiere(dto.getToponimo().getQuartiere());
			toponimoStradale.setGeom(dto.getToponimo().getGeom());
			toponimoStradale.setNumeroDelibera(dto.getToponimo().getNumeroDelibera());
			toponimoStradale.setDataDelibera(dto.getToponimo().getDataDelibera());
			toponimoStradale.setCodiceAutorizzazione(dto.getToponimo().getCodiceAutorizzazione());
			toponimoStradale.setDataAutorizzazione(dto.getToponimo().getDataAutorizzazione());
			toponimoStradale.setComune(comuneToponimo);
			toponimoStradale.setClasse(dug);
			toponimoStradale.setNote(dto.getToponimo().getNote());
			toponimoStradale.setTipo(tipoToponimo);
			toponimoStradale.setCodice(dto.getToponimo().getCodice());
			toponimoStradale.setStato(dto.getToponimo().getStato());
			toponimoStradale.setIdPadre(dto.getToponimo().getIdPadre());

		}
			
		Accesso accesso = new Accesso();
		
		if(dto.getAccesso() != null) {
		
			TipoAccesso tipoAccesso = new TipoAccesso();
			
			if(dto.getAccesso().getTipo() != null) {
				
				tipoAccesso.setId(dto.getAccesso().getTipo().getId());
				tipoAccesso.setDescrizione(dto.getAccesso().getTipo().getDescrizione());
				
			}
			
			Localita localita = new Localita();
			
			if(dto.getAccesso().getLocalita() != null) {
				
				TipoLocalita tipoLocalita = new TipoLocalita();
				
				if(dto.getAccesso().getLocalita().getTipo() != null) {
					
					tipoLocalita.setId(dto.getAccesso().getLocalita().getTipo().getId());
					tipoLocalita.setDescrizione(dto.getAccesso().getLocalita().getTipo().getDescrizione());
					
				}
				
			}
			
			accesso.setId(dto.getAccesso().getId());
			accesso.setToponimo(toponimoStradale);
			accesso.setLocalita(localita);
			accesso.setNumero(dto.getAccesso().getNumero());
			accesso.setEsponente(dto.getAccesso().getEsponente());
			accesso.setTipo(tipoAccesso);
			accesso.setPassoCarrabile(dto.getAccesso().isPassoCarrabile());
			accesso.setPrincipale(dto.getAccesso().isPrincipale());
			accesso.setMetodo(dto.getAccesso().getMetodo());
			accesso.setNote(dto.getAccesso().getNote());
			accesso.setGeom(dto.getAccesso().getGeom());
			accesso.setStato(dto.getAccesso().getStato());

			
		} else {
			
			accesso = null;
			
		}
		
		Comuni comuni = new Comuni();

		if(dto.getComune() != null) {

			comuni.setIdComune(dto.getComune().getIdComune());
			comuni.setSiglaProvincia(dto.getComune().getSiglaProvincia());
			comuni.setCodiceIstat(dto.getComune().getCodiceIstat());
			comuni.setCodiceMf(dto.getComune().getCodiceMf());
			comuni.setDenominazione(dto.getComune().getDenominazione());
			comuni.setVlInizio(dto.getComune().getVlInizio());
			comuni.setVlFine(dto.getComune().getVlFine());
			comuni.setDuCatAgg(dto.getComune().getDuCatAgg());
			comuni.setIdProvincia(dto.getComune().getIdProvincia());

		}

		
		GeocodingReverseGeocoding model = new GeocodingReverseGeocoding();
		
		model.setId(dto.getId());
		model.setComuneLabel(dto.getComuneLabel());
		model.setDenominazioneUfficiale(dto.getDenominazioneUfficiale());
		model.setShapeLeng(dto.getShapeLeng());
		model.setCap(dto.getCap());
		model.setCompendi(dto.getCompendi());
		model.setPrecdenomi(dto.getPrecdenomi());
		model.setQuartiere(dto.getQuartiere());
		model.setComune(comuni);
		model.setNote(dto.getNote());
		model.setCodice(dto.getCodice());
		model.setIdPadre(dto.getIdPadre());
		model.setStato(dto.getStato());
		model.setDataFineValidita(dto.getDataFineValidita());
		model.setGeom(dto.getGeom());
		model.setToponimo(toponimoStradale);
		model.setAccesso(accesso);

		return model;
	}

	@Override
	public GeocodingReverseGeocodingDTO toDTO(GeocodingReverseGeocoding model, Map<String, Object> parameters)
			throws ConverterException {
		
		ToponimoStradaleDTO toponimoStradale = new ToponimoStradaleDTO();

		if(model.getToponimo() != null) {

			DugDTO dug = new DugDTO();

			if(model.getToponimo().getClasse() != null) {

				dug.setId(model.getToponimo().getClasse().getId());
				dug.setDug(model.getToponimo().getClasse().getDug());

			}

			ComuniDto comuneToponimo = new ComuniDto();

			if(model.getToponimo().getComune() != null) {

				comuneToponimo.setIdComune(model.getToponimo().getComune().getIdComune());
				comuneToponimo.setSiglaProvincia(model.getToponimo().getComune().getSiglaProvincia());
				comuneToponimo.setCodiceIstat(model.getToponimo().getComune().getCodiceIstat());
				comuneToponimo.setCodiceMf(model.getToponimo().getComune().getCodiceMf());
				comuneToponimo.setDenominazione(model.getToponimo().getComune().getDenominazione());
				comuneToponimo.setVlInizio(model.getToponimo().getComune().getVlInizio());
				comuneToponimo.setVlFine(model.getToponimo().getComune().getVlFine());
				comuneToponimo.setDuCatAgg(model.getToponimo().getComune().getDuCatAgg());
				comuneToponimo.setIdProvincia(model.getToponimo().getComune().getIdProvincia());

			}

			TipoToponimoDTO tipoToponimo = new TipoToponimoDTO();

			if(model.getToponimo().getTipo() != null) {

				tipoToponimo.setId(model.getToponimo().getTipo().getId());
				tipoToponimo.setDescrizione(model.getToponimo().getTipo().getDescrizione());

			}

			toponimoStradale.setId(model.getToponimo().getId());
			toponimoStradale.setComuneLabel(model.getToponimo().getComuneLabel());
			toponimoStradale.setDenominazione(model.getToponimo().getDenominazione());
			toponimoStradale.setDenominazioneUfficiale(model.getToponimo().getDenominazioneUfficiale());
			toponimoStradale.setClasseLabel(model.getToponimo().getClasseLabel());
			toponimoStradale.setShapeLeng(model.getToponimo().getShapeLeng());
			toponimoStradale.setCap(model.getToponimo().getCap());
			toponimoStradale.setCompendi(model.getToponimo().getCompendi());
			toponimoStradale.setPrecdenomi(model.getToponimo().getPrecdenomi());
			toponimoStradale.setQuartiere(model.getToponimo().getQuartiere());
			toponimoStradale.setGeom(model.getToponimo().getGeom());
			toponimoStradale.setNumeroDelibera(model.getToponimo().getNumeroDelibera());
			toponimoStradale.setDataDelibera(model.getToponimo().getDataDelibera());
			toponimoStradale.setCodiceAutorizzazione(model.getToponimo().getCodiceAutorizzazione());
			toponimoStradale.setDataAutorizzazione(model.getToponimo().getDataAutorizzazione());
			toponimoStradale.setComune(comuneToponimo);
			toponimoStradale.setClasse(dug);
			toponimoStradale.setNote(model.getToponimo().getNote());
			toponimoStradale.setTipo(tipoToponimo);
			toponimoStradale.setCodice(model.getToponimo().getCodice());
			toponimoStradale.setStato(model.getToponimo().getStato());
			toponimoStradale.setIdPadre(model.getToponimo().getIdPadre());
		} 
		
		AccessoDTO accesso = new AccessoDTO();
		
		
		if(model.getAccesso() != null) {
		
			TipoAccessoDTO tipoAccesso = new TipoAccessoDTO();
			
			if(model.getAccesso().getTipo() != null) {
				
				tipoAccesso.setId(model.getAccesso().getTipo().getId());
				tipoAccesso.setDescrizione(model.getAccesso().getTipo().getDescrizione());
				
			}
			
			LocalitaDTO localita = new LocalitaDTO();
			
			if(model.getAccesso().getLocalita() != null) {
				
				TipoLocalitaDTO tipoLocalita = new TipoLocalitaDTO();
				
				if(model.getAccesso().getLocalita().getTipo() != null) {
					
					tipoLocalita.setId(model.getAccesso().getLocalita().getTipo().getId());
					tipoLocalita.setDescrizione(model.getAccesso().getLocalita().getTipo().getDescrizione());
					
				}
				
			}
			
			accesso.setId(model.getAccesso().getId());
			accesso.setToponimo(toponimoStradale);
			accesso.setLocalita(localita);
			accesso.setNumero(model.getAccesso().getNumero());
			accesso.setEsponente(model.getAccesso().getEsponente());
			accesso.setTipo(tipoAccesso);
			accesso.setPassoCarrabile(model.getAccesso().isPassoCarrabile());
			accesso.setPrincipale(model.getAccesso().isPrincipale());
			accesso.setMetodo(model.getAccesso().getMetodo());
			accesso.setNote(model.getAccesso().getNote());
			accesso.setGeom(model.getAccesso().getGeom());
			accesso.setStato(model.getAccesso().getStato());

			
		} else {
			
			accesso = null;
			
		}
		
		ComuniDto comuni = new ComuniDto();

		if(model.getComune() != null) {

			comuni.setIdComune(model.getComune().getIdComune());
			comuni.setSiglaProvincia(model.getComune().getSiglaProvincia());
			comuni.setCodiceIstat(model.getComune().getCodiceIstat());
			comuni.setCodiceMf(model.getComune().getCodiceMf());
			comuni.setDenominazione(model.getComune().getDenominazione());
			comuni.setVlInizio(model.getComune().getVlInizio());
			comuni.setVlFine(model.getComune().getVlFine());
			comuni.setDuCatAgg(model.getComune().getDuCatAgg());
			comuni.setIdProvincia(model.getComune().getIdProvincia());

		}
		
		GeocodingReverseGeocodingDTO dto = new GeocodingReverseGeocodingDTO();
		
		dto.setId(model.getId());
		dto.setComuneLabel(model.getComuneLabel());
		dto.setDenominazioneUfficiale(model.getDenominazioneUfficiale());
		dto.setShapeLeng(model.getShapeLeng());
		dto.setCap(model.getCap());
		dto.setCompendi(model.getCompendi());
		dto.setPrecdenomi(model.getPrecdenomi());
		dto.setQuartiere(model.getQuartiere());
		dto.setComune(comuni);
		dto.setNote(model.getNote());
		dto.setCodice(model.getCodice());
		dto.setIdPadre(model.getIdPadre());
		dto.setStato(model.getStato());
		dto.setDataFineValidita(model.getDataFineValidita());
		dto.setGeom(model.getGeom());
		dto.setToponimo(toponimoStradale);
		dto.setAccesso(accesso);
		
		return dto;
	}

}
