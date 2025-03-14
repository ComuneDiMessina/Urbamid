package it.eng.tz.urbamid.toponomastica.web.dto.converter;

import java.util.Map;

import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.toponomastica.exception.ConverterException;
import it.eng.tz.urbamid.toponomastica.persistence.model.Comuni;
import it.eng.tz.urbamid.toponomastica.persistence.model.Dug;
import it.eng.tz.urbamid.toponomastica.persistence.model.TipoToponimo;
import it.eng.tz.urbamid.toponomastica.persistence.model.ToponimoStradale;
import it.eng.tz.urbamid.toponomastica.util.AbstractConverter;
import it.eng.tz.urbamid.toponomastica.web.dto.ComuniDto;
import it.eng.tz.urbamid.toponomastica.web.dto.DugDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.TipoToponimoDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.ToponimoStradaleDTO;

@Component
public class ToponimoStradaleConverter extends AbstractConverter<ToponimoStradale, ToponimoStradaleDTO> {

	@Override
	public ToponimoStradale toModel(ToponimoStradaleDTO dto, Map<String, Object> parameters) throws ConverterException {

		Dug classe = new Dug();

		if(dto.getClasse() != null) {
			
			classe.setId(dto.getClasse().getId());
			classe.setDug(dto.getClasse().getDug());
			
		} 

		TipoToponimo tipo = new TipoToponimo();

		if(dto.getTipo() != null) {
			
			tipo.setId(dto.getTipo().getId());
			tipo.setDescrizione(dto.getTipo().getDescrizione());
			
		} else {
			
			tipo = null;
			
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

		ToponimoStradale model = new ToponimoStradale();
		
		model.setId(dto.getId());
		model.setComuneLabel(dto.getComuneLabel());
		model.setDenominazione(dto.getDenominazione());
		model.setDenominazioneUfficiale(dto.getDenominazioneUfficiale());
		model.setClasseLabel(dto.getClasseLabel());
		model.setShapeLeng(dto.getShapeLeng());
		model.setCap(dto.getCap());
		model.setCompendi(dto.getCompendi());
		model.setPrecdenomi(dto.getPrecdenomi());
		model.setQuartiere(dto.getQuartiere());
		model.setGeom(dto.getGeom());
		model.setNumeroDelibera(dto.getNumeroDelibera());
		model.setDataDelibera( dto.getDataDelibera()!=null ? new java.sql.Date(dto.getDataDelibera().getTime()):null );
		model.setCodiceAutorizzazione(dto.getCodiceAutorizzazione());
		model.setDataAutorizzazione(dto.getDataAutorizzazione()!=null ? new java.sql.Date(dto.getDataAutorizzazione().getTime()) : null);
		model.setComune(comuni);
		model.setClasse(classe);
		model.setNote(dto.getNote());
		model.setTipo(tipo);
		model.setCodice(dto.getCodice());
		model.setStato(dto.getStato());
		model.setIdPadre(dto.getIdPadre()!=null?dto.getIdPadre():new Long(0));
		model.setIsCircle(dto.getIsCircle());
		
		return model;
	}

	@Override
	public ToponimoStradaleDTO toDTO(ToponimoStradale model, Map<String, Object> parameters) throws ConverterException {

		DugDTO classe = new DugDTO();
		
		if(model.getClasse() != null) {
			
			classe.setId(model.getClasse().getId());
			classe.setDug(model.getClasse().getDug());
			
		}

		TipoToponimoDTO tipo = new TipoToponimoDTO();

		if(model.getTipo() != null) {
			
			tipo.setId(model.getTipo().getId());
			tipo.setDescrizione(model.getTipo().getDescrizione());
			
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

		ToponimoStradaleDTO dto = new ToponimoStradaleDTO();
		
		dto.setId(model.getId());
		dto.setComuneLabel(comuni.getDenominazione());
		dto.setDenominazione(model.getDenominazione());
		dto.setDenominazioneUfficiale(model.getDenominazioneUfficiale());
		dto.setClasseLabel(classe.getDug());
		dto.setShapeLeng(model.getShapeLeng());
		dto.setCap(model.getCap());
		dto.setCompendi(model.getCompendi());
		dto.setPrecdenomi(model.getPrecdenomi());
		dto.setQuartiere(model.getQuartiere());
		dto.setGeom(model.getGeom());
		dto.setNumeroDelibera(model.getNumeroDelibera());
		dto.setDataDelibera(model.getDataDelibera());
		dto.setCodiceAutorizzazione(model.getCodiceAutorizzazione());
		dto.setDataAutorizzazione(model.getDataAutorizzazione());
		dto.setComune(comuni);
		dto.setClasse(classe);
		dto.setNote(model.getNote());
		dto.setTipo(tipo);
		dto.setCodice(model.getCodice());
		dto.setStato(model.getStato());
		dto.setIdPadre(model.getIdPadre());
		dto.setIsCircle(model.getIsCircle());
		
		return dto;
		
	}

}
