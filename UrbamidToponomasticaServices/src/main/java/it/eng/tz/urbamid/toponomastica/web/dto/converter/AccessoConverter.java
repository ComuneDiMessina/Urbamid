package it.eng.tz.urbamid.toponomastica.web.dto.converter;

import java.util.Map;

import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.toponomastica.exception.ConverterException;
import it.eng.tz.urbamid.toponomastica.persistence.model.Accesso;
import it.eng.tz.urbamid.toponomastica.persistence.model.Comuni;
import it.eng.tz.urbamid.toponomastica.persistence.model.Dug;
import it.eng.tz.urbamid.toponomastica.persistence.model.Localita;
import it.eng.tz.urbamid.toponomastica.persistence.model.TipoAccesso;
import it.eng.tz.urbamid.toponomastica.persistence.model.TipoLocalita;
import it.eng.tz.urbamid.toponomastica.persistence.model.TipoToponimo;
import it.eng.tz.urbamid.toponomastica.persistence.model.ToponimoStradale;
import it.eng.tz.urbamid.toponomastica.util.AbstractConverter;
import it.eng.tz.urbamid.toponomastica.web.dto.AccessoDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.ComuniDto;
import it.eng.tz.urbamid.toponomastica.web.dto.DugDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.LocalitaDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.TipoAccessoDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.TipoLocalitaDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.TipoToponimoDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.ToponimoStradaleDTO;

@Component
public class AccessoConverter extends AbstractConverter<Accesso, AccessoDTO> {

	@Override
	public Accesso toModel(AccessoDTO dto, Map<String, Object> parameters) throws ConverterException {
		
		ToponimoStradale toponimo = new ToponimoStradale();
		
		if(dto.getToponimo() != null) {
									
			Dug dug = new Dug();
			
			if(dto.getToponimo().getClasse() != null) {
				
				dug.setId(dto.getToponimo().getClasse().getId());
				dug.setDug(dto.getToponimo().getClasse().getDug());
				
			}
						
			Comuni comune = new Comuni();
			
			if(dto.getToponimo().getComune() != null) {
				
				comune.setIdComune(dto.getToponimo().getComune().getIdComune());
				comune.setSiglaProvincia(dto.getLocalita().getComune().getSiglaProvincia());
				comune.setCodiceIstat(dto.getToponimo().getComune().getCodiceIstat());
				comune.setCodiceMf(dto.getToponimo().getComune().getCodiceMf());
				comune.setDenominazione(dto.getToponimo().getComune().getDenominazione());
				comune.setVlInizio(dto.getToponimo().getComune().getVlInizio());
				comune.setVlFine(dto.getToponimo().getComune().getVlFine());
				comune.setDuCatAgg(dto.getLocalita().getComune().getDuCatAgg());
				comune.setIdProvincia(dto.getToponimo().getComune().getIdProvincia());
				
			}
						
			TipoToponimo tipoToponimo = new TipoToponimo();
			
			if(dto.getToponimo().getTipo() != null) {
				
				tipoToponimo.setId(dto.getToponimo().getTipo().getId());
				tipoToponimo.setDescrizione(dto.getToponimo().getTipo().getDescrizione());
				
			}
			
			toponimo.setId(dto.getToponimo().getId());
			toponimo.setClasse(dug);
			toponimo.setComune(comune);
			toponimo.setTipo(tipoToponimo);
			toponimo.setDenominazioneUfficiale(dto.getToponimo().getDenominazioneUfficiale());
			toponimo.setCodice(dto.getToponimo().getCodice());
			toponimo.setNote(dto.getToponimo().getNote());
			toponimo.setGeom(dto.getToponimo().getGeom());
			toponimo.setDataDelibera(dto.getToponimo().getDataDelibera());
			toponimo.setDataAutorizzazione(dto.getToponimo().getDataAutorizzazione());
			toponimo.setNumeroDelibera(dto.getToponimo().getNumeroDelibera());
			toponimo.setCodiceAutorizzazione(dto.getToponimo().getCodiceAutorizzazione());
			toponimo.setStato(dto.getToponimo().getStato());
			
		} else {
			
			toponimo = null;
			
		}
		
		Localita localita = new Localita();
		
		if(dto.getLocalita() != null) {
			
			Comuni comune = new Comuni();
			
			if(dto.getLocalita().getComune() != null) {
				
				comune.setIdComune(dto.getToponimo().getComune().getIdComune());
				comune.setSiglaProvincia(dto.getLocalita().getComune().getSiglaProvincia());
				comune.setCodiceIstat(dto.getToponimo().getComune().getCodiceIstat());
				comune.setCodiceMf(dto.getToponimo().getComune().getCodiceMf());
				comune.setDenominazione(dto.getToponimo().getComune().getDenominazione());
				comune.setVlInizio(dto.getToponimo().getComune().getVlInizio());
				comune.setVlFine(dto.getToponimo().getComune().getVlFine());
				comune.setDuCatAgg(dto.getLocalita().getComune().getDuCatAgg());
				comune.setIdProvincia(dto.getToponimo().getComune().getIdProvincia());
				
			}
			
			TipoLocalita tipoLocalita = new TipoLocalita();
			
			if(dto.getLocalita().getTipo() != null) {
				
				tipoLocalita.setId(dto.getLocalita().getTipo().getId());
				tipoLocalita.setDescrizione(dto.getLocalita().getTipo().getDescrizione());
				
			}
			
			localita.setId(dto.getLocalita().getId());
			localita.setDescrizione(dto.getLocalita().getDescrizione());
			localita.setComune(comune);
			localita.setFrazione(dto.getLocalita().getFrazione());
			localita.setTipo(tipoLocalita);
			localita.setGeom(dto.getLocalita().getGeom());
			localita.setStato(dto.getLocalita().getStato());
			
		} else {
			
			localita = null;
			
		}
		
		Accesso model = new Accesso();
		
		model.setId(dto.getId());
		model.setToponimo(toponimo);
		model.setLocalita(localita);
		model.setNumero(dto.getNumero());
		model.setEsponente(dto.getEsponente());
		
		TipoAccesso tipoAccesso = new TipoAccesso();
		
		if(dto.getTipo() != null) {
			
			tipoAccesso.setId(dto.getTipo().getId());
			tipoAccesso.setDescrizione(dto.getTipo().getDescrizione());
			
		} else {
			
			tipoAccesso = null;
			
		}
		
		model.setTipo(tipoAccesso);
		model.setPassoCarrabile(dto.isPassoCarrabile());
		model.setPrincipale(dto.isPrincipale());
		model.setMetodo(dto.getMetodo());
		model.setNote(dto.getNote());
		model.setGeom(dto.getGeom());
		model.setStato(dto.getStato());
		
		return model;
	}

	@Override
	public AccessoDTO toDTO(Accesso model, Map<String, Object> parameters) throws ConverterException {
		
		ToponimoStradaleDTO toponimo = new ToponimoStradaleDTO();
		
		if(model.getToponimo() != null) {
						
			toponimo.setId(model.getToponimo().getId());
			
			DugDTO dug = new DugDTO();
			
			if(model.getToponimo().getClasse() != null) {
				
				dug.setId(model.getToponimo().getClasse().getId());
				dug.setDug(model.getToponimo().getClasse().getDug());
				
			}
			
			toponimo.setClasse(dug);
			
			ComuniDto comune = new ComuniDto();
			
			if(model.getToponimo().getComune() != null) {
				
				comune.setIdComune(model.getToponimo().getComune().getIdComune());
				comune.setSiglaProvincia(model.getToponimo().getComune().getSiglaProvincia());
				comune.setCodiceIstat(model.getToponimo().getComune().getCodiceIstat());
				comune.setCodiceMf(model.getToponimo().getComune().getCodiceMf());
				comune.setDenominazione(model.getToponimo().getComune().getDenominazione());
				comune.setVlInizio(model.getToponimo().getComune().getVlInizio());
				comune.setVlFine(model.getToponimo().getComune().getVlFine());
				comune.setDuCatAgg(model.getToponimo().getComune().getDuCatAgg());
				comune.setIdProvincia(model.getToponimo().getComune().getIdProvincia());
				
			}
			
			toponimo.setComune(comune);
			
			TipoToponimoDTO tipoToponimo = new TipoToponimoDTO();
			
			if(model.getToponimo().getTipo() != null) {
				
				tipoToponimo.setId(model.getToponimo().getTipo().getId());
				tipoToponimo.setDescrizione(model.getToponimo().getTipo().getDescrizione());
				
			}
			
			toponimo.setId(model.getToponimo().getId());
			toponimo.setClasse(dug);
			toponimo.setComune(comune);
			toponimo.setTipo(tipoToponimo);
			toponimo.setDenominazioneUfficiale(model.getToponimo().getDenominazioneUfficiale());
			toponimo.setCodice(model.getToponimo().getCodice());
			toponimo.setNote(model.getToponimo().getNote());
			toponimo.setGeom(model.getToponimo().getGeom());
			toponimo.setDataDelibera(model.getToponimo().getDataDelibera());
			toponimo.setDataAutorizzazione(model.getToponimo().getDataAutorizzazione());
			toponimo.setNumeroDelibera(model.getToponimo().getNumeroDelibera());
			toponimo.setCodiceAutorizzazione(model.getToponimo().getCodiceAutorizzazione());
			toponimo.setStato(model.getToponimo().getStato());
			
		} else {
			
			toponimo = null;
			
		}
		
		LocalitaDTO localita = new LocalitaDTO();
		
		if(model.getLocalita() != null) {
			
			ComuniDto comune = new ComuniDto();
			
			if(model.getLocalita().getComune() != null) {
				
				comune.setIdComune(model.getLocalita().getComune().getIdComune());
				comune.setSiglaProvincia(model.getLocalita().getComune().getSiglaProvincia());
				comune.setCodiceIstat(model.getLocalita().getComune().getCodiceIstat());
				comune.setCodiceMf(model.getLocalita().getComune().getCodiceMf());
				comune.setDenominazione(model.getLocalita().getComune().getDenominazione());
				comune.setVlInizio(model.getLocalita().getComune().getVlInizio());
				comune.setVlFine(model.getLocalita().getComune().getVlFine());
				comune.setDuCatAgg(model.getLocalita().getComune().getDuCatAgg());
				comune.setIdProvincia(model.getLocalita().getComune().getIdProvincia());
				
			}
			
			TipoLocalitaDTO tipoLocalita = new TipoLocalitaDTO();
			
			if(model.getLocalita().getTipo() != null) {
				
				tipoLocalita.setId(model.getLocalita().getTipo().getId());
				tipoLocalita.setDescrizione(model.getLocalita().getTipo().getDescrizione());
				
			}
			
			localita.setId(model.getLocalita().getId());
			localita.setDescrizione(model.getLocalita().getDescrizione());
			localita.setComune(comune);
			localita.setFrazione(model.getLocalita().getFrazione());
			localita.setTipo(tipoLocalita);
			localita.setGeom(model.getLocalita().getGeom());
			localita.setStato(model.getLocalita().getStato());
			
		} else {
			
			localita = null;
			
		}
		
		AccessoDTO dto = new AccessoDTO();
		
		dto.setId(model.getId());
		dto.setToponimo(toponimo);
		dto.setLocalita(localita);
		dto.setNumero(model.getNumero());
		dto.setEsponente(model.getEsponente());
		
		TipoAccessoDTO tipoAccesso = new TipoAccessoDTO();
		
		if(model.getTipo() != null) {
			
			tipoAccesso.setId(model.getTipo().getId());
			tipoAccesso.setDescrizione(model.getTipo().getDescrizione());
			
		} else {
			
			tipoAccesso = null;
			
		}
		
		dto.setTipo(tipoAccesso);
		dto.setPassoCarrabile(model.isPassoCarrabile());
		dto.setPrincipale(model.isPrincipale());
		dto.setMetodo(model.getMetodo());
		dto.setNote(model.getNote());
		dto.setGeom(model.getGeom());
		dto.setStato(model.getStato());;
		
		return dto;
	}

}
