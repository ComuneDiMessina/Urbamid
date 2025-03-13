package it.eng.tz.urbamid.toponomastica.web.dto.converter;

import java.util.Map;

import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.toponomastica.exception.ConverterException;
import it.eng.tz.urbamid.toponomastica.persistence.model.ClassificaAmministrativa;
import it.eng.tz.urbamid.toponomastica.persistence.model.ClassificaFunzionale;
import it.eng.tz.urbamid.toponomastica.persistence.model.Comuni;
import it.eng.tz.urbamid.toponomastica.persistence.model.Dug;
import it.eng.tz.urbamid.toponomastica.persistence.model.EnteGestore;
import it.eng.tz.urbamid.toponomastica.persistence.model.EstesaAmministrativa;
import it.eng.tz.urbamid.toponomastica.persistence.model.Patrimonialita;
import it.eng.tz.urbamid.toponomastica.persistence.model.Percorso;
import it.eng.tz.urbamid.toponomastica.persistence.model.Province;
import it.eng.tz.urbamid.toponomastica.persistence.model.TipoToponimo;
import it.eng.tz.urbamid.toponomastica.persistence.model.ToponimoStradale;
import it.eng.tz.urbamid.toponomastica.util.AbstractConverter;
import it.eng.tz.urbamid.toponomastica.web.dto.ClassificaAmministrativaDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.ClassificaFunzionaleDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.ComuniDto;
import it.eng.tz.urbamid.toponomastica.web.dto.DugDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.EnteGestoreDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.EstesaAmministrativaDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.PatrimonialitaDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.PercorsoDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.ProvinciaDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.TipoToponimoDTO;
import it.eng.tz.urbamid.toponomastica.web.dto.ToponimoStradaleDTO;

@Component
public class PercorsoConverter extends AbstractConverter<Percorso, PercorsoDTO>{

	@Override
	public Percorso toModel(PercorsoDTO dto, Map<String, Object> parameters) throws ConverterException {

		ToponimoStradale toponimo = new ToponimoStradale();

		EstesaAmministrativa estesaAmministrativa = new EstesaAmministrativa();
		
		if(dto.getToponimo() != null) {
			
//			estesaAmministrativa = null;
			
			Dug dug = new Dug();

			if(dto.getToponimo().getClasse() != null) {

				dug.setId(dto.getToponimo().getClasse().getId());
				dug.setDug(dto.getToponimo().getClasse().getDug());

			}

			Comuni comune = new Comuni();

			if(dto.getToponimo().getComune() != null) {

				comune.setIdComune(dto.getToponimo().getComune().getIdComune());
				comune.setSiglaProvincia(dto.getToponimo().getComune().getSiglaProvincia());
				comune.setCodiceIstat(dto.getToponimo().getComune().getCodiceIstat());
				comune.setCodiceMf(dto.getToponimo().getComune().getCodiceMf());
				comune.setDenominazione(dto.getToponimo().getComune().getDenominazione());
				comune.setVlInizio(dto.getToponimo().getComune().getVlInizio());
				comune.setVlFine(dto.getToponimo().getComune().getVlFine());
				comune.setDuCatAgg(dto.getToponimo().getComune().getDuCatAgg());
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
			
		}

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

		Percorso model = new Percorso();

		model.setId(dto.getId());
		model.setSigla(dto.getSigla());
		model.setDescrizione(dto.getDescrizione());
		model.setToponimo(toponimo);
		model.setEstesaAmministrativa(estesaAmministrativa);
		model.setNote(dto.getNote());
		model.setGeom(dto.getGeom());
		model.setStato(dto.getStato());

		return model;
	}

	@Override
	public PercorsoDTO toDTO(Percorso model, Map<String, Object> parameters) throws ConverterException {

		ToponimoStradaleDTO toponimo = new ToponimoStradaleDTO();

		EstesaAmministrativaDTO estesaAmministrativa = new EstesaAmministrativaDTO();
		
		if(model.getToponimo() != null) {
			
			DugDTO dug = new DugDTO();

			if(model.getToponimo().getClasse() != null) {

				dug.setId(model.getToponimo().getClasse().getId());
				dug.setDug(model.getToponimo().getClasse().getDug());

			}

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
			
		}

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

			estesaAmministrativa.setId(model.getEstesaAmministrativa().getId());
			estesaAmministrativa.setSigla(model.getEstesaAmministrativa().getDescrizione());
			estesaAmministrativa.setDescrizione(model.getEstesaAmministrativa().getDescrizione());
			estesaAmministrativa.setClassificaAmministrativa(classificaAmministrativa);
			estesaAmministrativa.setCodice(model.getEstesaAmministrativa().getCodice());
			estesaAmministrativa.setEstensione(model.getEstesaAmministrativa().getEstensione());
			estesaAmministrativa.setTronco(model.getEstesaAmministrativa().getTronco());
			estesaAmministrativa.setPatrimonialita(patrimonialita);
			estesaAmministrativa.setEnteGestore(enteGestore);
			estesaAmministrativa.setClassificaFunzionale(classificaFunzionale);
			estesaAmministrativa.setProvincia(provincia);
			estesaAmministrativa.setComune(comuni);
			estesaAmministrativa.setNote(model.getEstesaAmministrativa().getNote());
			estesaAmministrativa.setGeom(model.getEstesaAmministrativa().getGeom());
			estesaAmministrativa.setStato(model.getEstesaAmministrativa().getStato());
			
		}

		PercorsoDTO dto = new PercorsoDTO();

		dto.setId(model.getId());
		dto.setSigla(model.getSigla());
		dto.setDescrizione(model.getDescrizione());
		dto.setToponimo(toponimo);
		dto.setEstesaAmministrativa(estesaAmministrativa);
		dto.setNote(model.getNote());
		dto.setGeom(model.getGeom());
		dto.setStato(model.getStato());

		return dto;
	}


}
