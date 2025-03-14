package it.eng.tz.urbamid.catasto.web.dto.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.catasto.exception.ConverterException;
import it.eng.tz.urbamid.catasto.persistence.model.FabbricatiGeom;
import it.eng.tz.urbamid.catasto.persistence.model.IdentificativoUnitaImmobiliari;
import it.eng.tz.urbamid.catasto.persistence.model.Particella;
import it.eng.tz.urbamid.catasto.persistence.model.ParticellaCatastaleJoin;
import it.eng.tz.urbamid.catasto.web.dto.DettaglioSoggettiPTDTO;
import it.eng.tz.urbamid.catasto.web.dto.IdentificativiUiuDTO;
import it.eng.tz.urbamid.catasto.web.dto.ParticelleTerreniDTO;

@Component
public class RicerchePTConverter implements IConverter<Particella, ParticelleTerreniDTO>{

	private static final Logger logger = LoggerFactory.getLogger(RicerchePTConverter.class.getName());

	@Override
	public Particella toModel(ParticelleTerreniDTO dto) throws ConverterException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParticelleTerreniDTO toDto(Particella model) throws ConverterException {
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
		}		
		dto.setRedditoDominicaleLire(model.getRedditoDominicaleLire());
		dto.setRedditoDominicaleEuro(model.getRedditoAgrarioEuro());
		dto.setRedditoAgrarioLire(model.getRedditoAgrarioLire());
		dto.setRedditoAgrarioEuro(model.getRedditoAgrarioEuro());
		return dto;
	}

	@Override
	public List<ParticelleTerreniDTO> toDto(List<Particella> models) throws ConverterException {
		List<ParticelleTerreniDTO> result = new ArrayList<>();
		try 
		{
			for (Particella model : models) {
				result.add(this.toDto(model));
			}
		}
		catch (Exception e) 
		{
			logger.error("Errore nella conversione ParticellaGeom --> ParticellaGeomDTO" + e, e);
		}

		return result;
	}

	public DettaglioSoggettiPTDTO toDtoDettaglioSoggetti(ParticellaCatastaleJoin model/*, List<ParticellaGeom> list*/) throws ConverterException {
		
		List<String> diritti = new ArrayList<>();
		diritti.add("Proprieta 1/1");
		diritti.add("Proprieta 10/10");
		diritti.add("Proprieta 100/100");
		diritti.add("Proprieta 1000/1000");
		
		DettaglioSoggettiPTDTO dto = new DettaglioSoggettiPTDTO();
		dto.setId(model.getId());
		dto.setIdImmobile(model.getIdImmobile());
		dto.setComune(model.getComune());
		//dto.setProvincia("ME");
		dto.setFoglio(model.getFoglio());
		dto.setNumero(model.getNumero());
		dto.setSubalterno(model.getSubalterno());
		dto.setPartita(model.getPartita());
		dto.setDiritto(model.getDiritto());
		if (diritti.contains(dto.getDiritto())) {
			dto.setSingoloProprietario(true);
		} else {
			dto.setSingoloProprietario(false);
		}
		/*if (list != null && !list.isEmpty()) {
			dto.setGeometry(list.get(0).getGeom());
		}*/
		return dto;
	}

	public IdentificativiUiuDTO toDtoDettaglio(IdentificativoUnitaImmobiliari model, List<FabbricatiGeom> list) {
		IdentificativiUiuDTO dto = new IdentificativiUiuDTO();
		dto.setId(model.getId());
		dto.setSezione(model.getSezione());
		dto.setNumero(model.getNumero());
		dto.setFoglio(model.getFoglio());
		dto.setSubalterno(model.getSubalterno());
		if (list != null && !list.isEmpty()) {
			dto.setGeometry(list.get(0).getGeometry());
		}
		return dto;
	}

	public List<DettaglioSoggettiPTDTO> toDtoDettaglioSoggetti(List<ParticellaCatastaleJoin> models) throws ConverterException {
		List<DettaglioSoggettiPTDTO> result = new ArrayList<>();
		try 
		{
			for (ParticellaCatastaleJoin model : models) {
				result.add(this.toDtoDettaglioSoggetti(model));
			}
		}
		catch (Exception e) 
		{
			logger.error("Errore nella conversione ParticellaGeom --> ParticellaGeomDTO" + e, e);
		}

		return result;
	}

}
