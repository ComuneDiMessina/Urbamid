package it.eng.tz.urbamid.toponomastica.web.dto.converter;

import java.util.Map;

import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.toponomastica.exception.ConverterException;
import it.eng.tz.urbamid.toponomastica.persistence.model.CippoChilometrico;
import it.eng.tz.urbamid.toponomastica.persistence.model.ClassificaAmministrativa;
import it.eng.tz.urbamid.toponomastica.persistence.model.ClassificaFunzionale;
import it.eng.tz.urbamid.toponomastica.persistence.model.Comuni;
import it.eng.tz.urbamid.toponomastica.persistence.model.EnteGestore;
import it.eng.tz.urbamid.toponomastica.persistence.model.EstesaAmministrativa;
import it.eng.tz.urbamid.toponomastica.persistence.model.Patrimonialita;
import it.eng.tz.urbamid.toponomastica.persistence.model.Province;
import it.eng.tz.urbamid.toponomastica.util.AbstractConverter;
import it.eng.tz.urbamid.toponomastica.web.dto.CippoChilometricoDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.ClassificaAmministrativaDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.ClassificaFunzionaleDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.ComuniDto;
import it.eng.tz.urbamid.toponomastica.web.dto.EnteGestoreDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.EstesaAmministrativaDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.PatrimonialitaDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.ProvinciaDTO;

/**
 * @author Luca Tricarico
 */

@Component
public class CippoChilometricoConverter extends AbstractConverter<CippoChilometrico, CippoChilometricoDTO>{

	@Override
	public CippoChilometrico toModel(CippoChilometricoDTO dto, Map<String, Object> parameters) throws ConverterException {

		EstesaAmministrativa estesaAmministrativa = new EstesaAmministrativa();
		
		if(dto.getEstesaAmministrativa() != null) {
			
			ClassificaAmministrativa classificaAmministrativa = new ClassificaAmministrativa();
			
			if(dto.getEstesaAmministrativa().getClassificaAmministrativa() != null) {
				
				classificaAmministrativa.setId(dto.getEstesaAmministrativa().getClassificaAmministrativa().getId());
				classificaAmministrativa.setDescrizione(dto.getEstesaAmministrativa().getClassificaAmministrativa().getDescrizione());
				
			}			
			
			Patrimonialita patrimonialita = new Patrimonialita();
			
			if(dto.getEstesaAmministrativa().getPatrimonialita() != null) {
				
				patrimonialita.setId(dto.getEstesaAmministrativa().getPatrimonialita().getId());
				patrimonialita.setDescrizione(dto.getEstesaAmministrativa().getPatrimonialita().getDescrizione());
				
			}
			
			EnteGestore enteGestore = new EnteGestore();
			
			if(dto.getEstesaAmministrativa().getEnteGestore() != null) {
				
				enteGestore.setId(dto.getEstesaAmministrativa().getEnteGestore().getId());
				enteGestore.setCodice(dto.getEstesaAmministrativa().getEnteGestore().getCodice());
				enteGestore.setDescrizione(dto.getEstesaAmministrativa().getEnteGestore().getDescrizione());
				
			}
			
			ClassificaFunzionale classificaFunzionale = new ClassificaFunzionale();
			
			if(dto.getEstesaAmministrativa().getClassificaFunzionale() != null) {
				
				classificaFunzionale.setId(dto.getEstesaAmministrativa().getClassificaFunzionale().getId());
				classificaFunzionale.setDescrizione(dto.getEstesaAmministrativa().getClassificaFunzionale().getDescrizione());
				
			}
			
			Province provincia = new Province();
			
			if(dto.getEstesaAmministrativa().getProvincia() != null) {
				
				provincia.setIdProvincia(dto.getEstesaAmministrativa().getProvincia().getIdProvincia());
				provincia.setCodiceIstat(dto.getEstesaAmministrativa().getProvincia().getCodiceIstat());
				provincia.setSigla(dto.getEstesaAmministrativa().getProvincia().getSigla());
				provincia.setDenominazione(dto.getEstesaAmministrativa().getProvincia().getDenominazione());
				provincia.setVlInizio(dto.getEstesaAmministrativa().getProvincia().getVlInizio());
				provincia.setVlFine(dto.getEstesaAmministrativa().getProvincia().getVlFine());
				provincia.setDuCatAgg(dto.getEstesaAmministrativa().getProvincia().getDuCatAgg());
				provincia.setIdRegione(dto.getEstesaAmministrativa().getProvincia().getIdRegione());
				
			}
			
			Comuni comuni = new Comuni();
			
			if(dto.getEstesaAmministrativa().getComune() != null) {
				
				comuni.setIdComune(dto.getEstesaAmministrativa().getComune().getIdComune());
				comuni.setSiglaProvincia(dto.getEstesaAmministrativa().getComune().getSiglaProvincia());
				comuni.setCodiceIstat(dto.getEstesaAmministrativa().getComune().getCodiceIstat());
				comuni.setCodiceMf(dto.getEstesaAmministrativa().getComune().getCodiceMf());
				comuni.setDenominazione(dto.getEstesaAmministrativa().getComune().getDenominazione());
				comuni.setVlInizio(dto.getEstesaAmministrativa().getComune().getVlInizio());
				comuni.setVlFine(dto.getEstesaAmministrativa().getComune().getVlFine());
				comuni.setDuCatAgg(dto.getEstesaAmministrativa().getComune().getDuCatAgg());
				comuni.setIdProvincia(dto.getEstesaAmministrativa().getComune().getIdProvincia());
				
			}
			
			estesaAmministrativa.setId(dto.getEstesaAmministrativa().getId());
			estesaAmministrativa.setSigla(dto.getEstesaAmministrativa().getDescrizione());
			estesaAmministrativa.setDescrizione(dto.getEstesaAmministrativa().getDescrizione());
			estesaAmministrativa.setClassificaAmministrativa(classificaAmministrativa);
			estesaAmministrativa.setCodice(dto.getEstesaAmministrativa().getCodice());
			estesaAmministrativa.setEstensione(dto.getEstesaAmministrativa().getEstensione());
			estesaAmministrativa.setTronco(dto.getEstesaAmministrativa().getTronco());
			estesaAmministrativa.setPatrimonialita(patrimonialita);
			estesaAmministrativa.setEnteGestore(enteGestore);
			estesaAmministrativa.setClassificaFunzionale(classificaFunzionale);
			estesaAmministrativa.setProvincia(provincia);
			estesaAmministrativa.setComune(comuni);
			estesaAmministrativa.setNote(dto.getEstesaAmministrativa().getNote());
			estesaAmministrativa.setGeom(dto.getEstesaAmministrativa().getGeom());
			estesaAmministrativa.setStato(dto.getEstesaAmministrativa().getStato());
			
			
		}
		
		CippoChilometrico model = new CippoChilometrico();
		
		model.setId(dto.getId());
		model.setEstesaAmministrativa(estesaAmministrativa);
		model.setMisura(dto.getMisura() != null ? dto.getMisura() : 0);
		model.setNote(dto.getNote());
		model.setGeom(dto.getGeom());
		model.setStato(dto.getStato());
		model.setCodice(dto.getCodice());
		
		return model;
	}

	@Override
	public CippoChilometricoDTO toDTO(CippoChilometrico model, Map<String, Object> parameters) throws ConverterException {

		EstesaAmministrativaDTO estesaAmministrativaDTO = new EstesaAmministrativaDTO();
		
		if(model.getEstesaAmministrativa() != null) {
			
			ClassificaAmministrativaDTO classificaAmministrativa = new ClassificaAmministrativaDTO();
			
			if(model.getEstesaAmministrativa().getClassificaAmministrativa() != null) {
				
				classificaAmministrativa.setId(model.getEstesaAmministrativa().getClassificaAmministrativa().getId());
				classificaAmministrativa.setDescrizione(model.getEstesaAmministrativa().getClassificaAmministrativa().getDescrizione());
				
			}
			
			PatrimonialitaDTO patrimonialita = new PatrimonialitaDTO();
			
			if(model.getEstesaAmministrativa().getPatrimonialita() != null) {
				
				patrimonialita.setId(model.getEstesaAmministrativa().getPatrimonialita().getId());
				patrimonialita.setDescrizione(model.getEstesaAmministrativa().getPatrimonialita().getDescrizione());
				
			}

			EnteGestoreDTO enteGestore = new EnteGestoreDTO();
			
			if(model.getEstesaAmministrativa().getEnteGestore() != null) {
				
				enteGestore.setId(model.getEstesaAmministrativa().getEnteGestore().getId());
				enteGestore.setCodice(model.getEstesaAmministrativa().getEnteGestore().getCodice());
				enteGestore.setDescrizione(model.getEstesaAmministrativa().getEnteGestore().getDescrizione());
				
			}
			
			ClassificaFunzionaleDTO classificaFunzionale = new ClassificaFunzionaleDTO();
			
			if(model.getEstesaAmministrativa().getClassificaFunzionale() != null) {
				
				classificaFunzionale.setId(model.getEstesaAmministrativa().getClassificaFunzionale().getId());
				classificaFunzionale.setDescrizione(model.getEstesaAmministrativa().getClassificaFunzionale().getDescrizione());
				
			}
			
			ProvinciaDTO provincia = new ProvinciaDTO();
			
			if(model.getEstesaAmministrativa().getProvincia() != null) {
				
				provincia.setIdProvincia(model.getEstesaAmministrativa().getProvincia().getIdProvincia());
				provincia.setCodiceIstat(model.getEstesaAmministrativa().getProvincia().getCodiceIstat());
				provincia.setSigla(model.getEstesaAmministrativa().getProvincia().getSigla());
				provincia.setDenominazione(model.getEstesaAmministrativa().getProvincia().getDenominazione());
				provincia.setVlInizio(model.getEstesaAmministrativa().getProvincia().getVlInizio());
				provincia.setVlFine(model.getEstesaAmministrativa().getProvincia().getVlFine());
				provincia.setDuCatAgg(model.getEstesaAmministrativa().getProvincia().getDuCatAgg());
				provincia.setIdRegione(model.getEstesaAmministrativa().getProvincia().getIdRegione());
				
			}
			
			ComuniDto comuni = new ComuniDto();
			
			if(model.getEstesaAmministrativa().getComune() != null) {
				
				comuni.setIdComune(model.getEstesaAmministrativa().getComune().getIdComune());
				comuni.setSiglaProvincia(model.getEstesaAmministrativa().getComune().getSiglaProvincia());
				comuni.setCodiceIstat(model.getEstesaAmministrativa().getComune().getCodiceIstat());
				comuni.setCodiceMf(model.getEstesaAmministrativa().getComune().getCodiceMf());
				comuni.setDenominazione(model.getEstesaAmministrativa().getComune().getDenominazione());
				comuni.setVlInizio(model.getEstesaAmministrativa().getComune().getVlInizio());
				comuni.setVlFine(model.getEstesaAmministrativa().getComune().getVlFine());
				comuni.setDuCatAgg(model.getEstesaAmministrativa().getComune().getDuCatAgg());
				comuni.setIdProvincia(model.getEstesaAmministrativa().getComune().getIdProvincia());
				
			}
			
			estesaAmministrativaDTO.setId(model.getEstesaAmministrativa().getId());
			estesaAmministrativaDTO.setSigla(model.getEstesaAmministrativa().getDescrizione());
			estesaAmministrativaDTO.setDescrizione(model.getEstesaAmministrativa().getDescrizione());
			estesaAmministrativaDTO.setClassificaAmministrativa(classificaAmministrativa);
			estesaAmministrativaDTO.setCodice(model.getEstesaAmministrativa().getCodice());
			estesaAmministrativaDTO.setEstensione(model.getEstesaAmministrativa().getEstensione());
			estesaAmministrativaDTO.setTronco(model.getEstesaAmministrativa().getTronco());
			estesaAmministrativaDTO.setPatrimonialita(patrimonialita);
			estesaAmministrativaDTO.setEnteGestore(enteGestore);
			estesaAmministrativaDTO.setClassificaFunzionale(classificaFunzionale);
			estesaAmministrativaDTO.setProvincia(provincia);
			estesaAmministrativaDTO.setComune(comuni);
			estesaAmministrativaDTO.setNote(model.getEstesaAmministrativa().getNote());
			estesaAmministrativaDTO.setGeom(model.getEstesaAmministrativa().getGeom());
			estesaAmministrativaDTO.setStato(model.getEstesaAmministrativa().getStato());
			
		}
		
		CippoChilometricoDTO dto = new CippoChilometricoDTO();
		
		dto.setId(model.getId());
		dto.setEstesaAmministrativa(estesaAmministrativaDTO);
		dto.setMisura(model.getMisura() != null ? model.getMisura() : 0);
		dto.setNote(model.getNote());
		dto.setGeom(model.getGeom());
		dto.setStato(model.getStato());
		dto.setCodice(model.getCodice());
		
		return dto;
	
	}

	
}
