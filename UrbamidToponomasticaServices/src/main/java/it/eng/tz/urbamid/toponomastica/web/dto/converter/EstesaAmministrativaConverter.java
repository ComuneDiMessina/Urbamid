package it.eng.tz.urbamid.toponomastica.web.dto.converter;

import java.util.Map;

import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.toponomastica.exception.ConverterException;
import it.eng.tz.urbamid.toponomastica.persistence.model.ClassificaAmministrativa;
import it.eng.tz.urbamid.toponomastica.persistence.model.ClassificaFunzionale;
import it.eng.tz.urbamid.toponomastica.persistence.model.Comuni;
import it.eng.tz.urbamid.toponomastica.persistence.model.EnteGestore;
import it.eng.tz.urbamid.toponomastica.persistence.model.EstesaAmministrativa;
import it.eng.tz.urbamid.toponomastica.persistence.model.Patrimonialita;
import it.eng.tz.urbamid.toponomastica.persistence.model.Province;
import it.eng.tz.urbamid.toponomastica.util.AbstractConverter;
import it.eng.tz.urbamid.toponomastica.web.dto.ClassificaAmministrativaDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.ClassificaFunzionaleDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.ComuniDto;
import it.eng.tz.urbamid.toponomastica.web.dto.EnteGestoreDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.EstesaAmministrativaDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.PatrimonialitaDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.ProvinciaDTO;

@Component
public class EstesaAmministrativaConverter extends AbstractConverter<EstesaAmministrativa, EstesaAmministrativaDTO> {

	@Override
	public EstesaAmministrativa toModel(EstesaAmministrativaDTO dto, Map<String, Object> parameters) throws ConverterException {

		ClassificaAmministrativa classificaAmministrativa = new ClassificaAmministrativa();
		Patrimonialita patrimonialita = new Patrimonialita();
		EnteGestore enteGestore = new EnteGestore();
		ClassificaFunzionale classificaFunzionale = new ClassificaFunzionale();
		Province provincia = new Province();
		Comuni comuni = new Comuni();
		
		if(dto.getClassificaAmministrativa() != null) {
					
			classificaAmministrativa.setId(dto.getClassificaAmministrativa().getId());
			classificaAmministrativa.setDescrizione(dto.getClassificaAmministrativa().getDescrizione());
			
		}
		
		if(dto.getPatrimonialita() != null) {
			
			patrimonialita.setId(dto.getPatrimonialita().getId());
			patrimonialita.setDescrizione(dto.getPatrimonialita().getDescrizione());
			
		}
		
		if(dto.getEnteGestore() != null) {
			
			enteGestore.setId(dto.getEnteGestore().getId());
			enteGestore.setCodice(dto.getEnteGestore().getCodice());
			enteGestore.setDescrizione(dto.getEnteGestore().getDescrizione());
			
		}
		
		if(dto.getClassificaFunzionale() != null) {
			
			classificaFunzionale.setId(dto.getClassificaFunzionale().getId());
			classificaFunzionale.setDescrizione(dto.getClassificaFunzionale().getDescrizione());
			
		}
		
		if(dto.getProvincia() != null) {
			
			provincia.setIdProvincia(dto.getProvincia().getIdProvincia());
			provincia.setCodiceIstat(dto.getProvincia().getCodiceIstat());
			provincia.setSigla(dto.getProvincia().getSigla());
			provincia.setDenominazione(dto.getProvincia().getDenominazione());
			provincia.setVlInizio(dto.getProvincia().getVlInizio());
			provincia.setVlFine(dto.getProvincia().getVlFine());
			provincia.setDuCatAgg(dto.getProvincia().getDuCatAgg());
			provincia.setIdRegione(dto.getProvincia().getIdRegione());
			
		}
		
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
		
		EstesaAmministrativa model = new EstesaAmministrativa();
		
		model.setId(dto.getId());
		model.setSigla(dto.getSigla());
		model.setDescrizione(dto.getDescrizione());
		model.setClassificaAmministrativa(classificaAmministrativa);
		model.setCodice(dto.getCodice());
		model.setEstensione(dto.getEstensione());
		model.setTronco(dto.getTronco());
		model.setPatrimonialita(patrimonialita);
		model.setEnteGestore(enteGestore);
		model.setClassificaFunzionale(classificaFunzionale);
		model.setProvincia(provincia);
		model.setComune(comuni);
		model.setNote(dto.getNote());
		model.setGeom(dto.getGeom());
		model.setStato(dto.getStato());
		model.setIsCircle(dto.getIsCircle());
		
		return model;
	}

	@Override
	public EstesaAmministrativaDTO toDTO(EstesaAmministrativa model, Map<String, Object> parameters) throws ConverterException {
		
		ClassificaAmministrativaDTO classificaAmministrativa = new ClassificaAmministrativaDTO();
		PatrimonialitaDTO patrimonialita = new PatrimonialitaDTO();
		EnteGestoreDTO enteGestore = new EnteGestoreDTO();
		ClassificaFunzionaleDTO classificaFunzionale = new ClassificaFunzionaleDTO();
		ProvinciaDTO provincia = new ProvinciaDTO();
		ComuniDto comuni = new ComuniDto();
		
		if(model.getClassificaAmministrativa() != null) {
			
			classificaAmministrativa.setId(model.getClassificaAmministrativa().getId());
			classificaAmministrativa.setDescrizione(model.getClassificaAmministrativa().getDescrizione());
			
		} 
		
		if(model.getPatrimonialita() != null) {
			
			patrimonialita.setId(model.getPatrimonialita().getId());
			patrimonialita.setDescrizione(model.getPatrimonialita().getDescrizione());
			
		} 
		
		if(model.getEnteGestore() != null) {
			
			enteGestore.setId(model.getEnteGestore().getId());
			enteGestore.setCodice(model.getEnteGestore().getCodice());
			enteGestore.setDescrizione(model.getEnteGestore().getDescrizione());
			
		}
		
		if(model.getClassificaFunzionale() != null) {
			
			classificaFunzionale.setId(model.getClassificaFunzionale().getId());
			classificaFunzionale.setDescrizione(model.getClassificaFunzionale().getDescrizione());
			
		} 
		
		if(model.getProvincia() != null) {
			
			provincia.setIdProvincia(model.getProvincia().getIdProvincia());
			provincia.setCodiceIstat(model.getProvincia().getCodiceIstat());
			provincia.setSigla(model.getProvincia().getSigla());
			provincia.setDenominazione(model.getProvincia().getDenominazione());
			provincia.setVlInizio(model.getProvincia().getVlInizio());
			provincia.setVlFine(model.getProvincia().getVlFine());
			provincia.setDuCatAgg(model.getProvincia().getDuCatAgg());
			provincia.setIdRegione(model.getProvincia().getIdRegione());
			
		}
		
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
		
		EstesaAmministrativaDTO dto = new EstesaAmministrativaDTO();
		
		dto.setId(model.getId());
		dto.setSigla(model.getSigla());
		dto.setDescrizione(model.getDescrizione());
		dto.setClassificaAmministrativa(classificaAmministrativa);
		dto.setCodice(model.getCodice());
		dto.setEstensione(model.getEstensione());
		dto.setTronco(model.getTronco());
		dto.setPatrimonialita(patrimonialita);
		dto.setEnteGestore(enteGestore);
		dto.setClassificaFunzionale(classificaFunzionale);
		dto.setProvincia(provincia);
		dto.setComune(comuni);
		dto.setNote(model.getNote());
		dto.setGeom(model.getGeom());
		dto.setStato(model.getStato());
		dto.setIsCircle(model.getIsCircle());
		
		return dto;
	}

}
