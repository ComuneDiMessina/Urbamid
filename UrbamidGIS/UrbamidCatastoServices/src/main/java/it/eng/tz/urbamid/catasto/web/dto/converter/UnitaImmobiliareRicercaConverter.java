package it.eng.tz.urbamid.catasto.web.dto.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.catasto.exception.ConverterException;
import it.eng.tz.urbamid.catasto.persistence.model.UnitaImmobiliareJoinRicerca;
import it.eng.tz.urbamid.catasto.util.AbstractConverter;
import it.eng.tz.urbamid.catasto.web.dto.UnitaImmobiliareDTO;

@Component
public class UnitaImmobiliareRicercaConverter extends AbstractConverter<UnitaImmobiliareJoinRicerca, UnitaImmobiliareDTO> {


	@Override
	public UnitaImmobiliareJoinRicerca toModel(UnitaImmobiliareDTO dto, Map<String, Object> parameters)
			throws ConverterException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public UnitaImmobiliareDTO toDTO(UnitaImmobiliareJoinRicerca model, Map<String, Object> parameters)
			throws ConverterException {
		UnitaImmobiliareDTO dto = new UnitaImmobiliareDTO();
		dto.setId(model.getId());
		dto.setComune(model.getCodComune());
		dto.setIdImmobile(model.getIdImmobile());
		dto.setIndirizzo(model.getIndirizzo());
		dto.setCivico1(model.getCivico1());
		dto.setCivico2(model.getCivico2());
		dto.setNumero(model.getNumero());
		dto.setFoglio(model.getFoglio());
		dto.setSubalterno(model.getSubalterno());
		dto.setZona(model.getZona());
		dto.setCategoria(model.getCategoria().trim());
		dto.setClasse(model.getClasse());
		dto.setConsistenza(model.getConsistenza());
		dto.setSuperficie(model.getSuperficie());
		dto.setRenditaLire(model.getRenditaLire());
		dto.setRenditaEuro(model.getRenditaEuro());
		dto.setPartita(model.getPartita());
		
		dto.setLotto(model.getLotto());
		dto.setEdificio(model.getEdificio());
		dto.setScala(model.getScala());
		dto.setInterno1(model.getInterno());
		dto.setPiano1(model.getPiano());
		dto.setAnnotazione(model.getAnnotazione());
		dto.setCivico3(model.getCivico3());
		
		return dto;
	}

	public List<UnitaImmobiliareDTO> toDTO(List<UnitaImmobiliareJoinRicerca> models) throws ConverterException {
		List<UnitaImmobiliareDTO> result = new ArrayList<>();
		try 
		{
			for (UnitaImmobiliareJoinRicerca model : models) {
				result.add(this.toDTO(model));
			}
		}
		catch (Exception e) 
		{
			//logger.error("Errore nella conversione ParticellaGeom --> ParticellaGeomDTO" + e, e);
		}

		return result;
	}

	public UnitaImmobiliareDTO toDTOinformazioni(UnitaImmobiliareJoinRicerca model) {
		UnitaImmobiliareDTO dto = new UnitaImmobiliareDTO();
		dto.setId(model.getId());
		dto.setComune(model.getCodComune());
		dto.setIdImmobile(model.getIdImmobile());
		dto.setIndirizzo(model.getIndirizzo());
		dto.setCivico1(model.getCivico1());
		dto.setCivico2(model.getCivico2());
		dto.setNumero(model.getNumero());
		dto.setFoglio(model.getFoglio());
		dto.setSubalterno(model.getSubalterno());
		dto.setZona(model.getZona());
		dto.setCategoria(model.getCategoria().trim());
		dto.setClasse(model.getClasse());
		dto.setConsistenza(model.getConsistenza());
		dto.setSuperficie(model.getSuperficie());
		dto.setRenditaLire(model.getRenditaLire());
		dto.setRenditaEuro(model.getRenditaEuro());
		dto.setPartita(model.getPartita());
		
		dto.setLotto(model.getLotto());
		dto.setEdificio(model.getEdificio());
		dto.setScala(model.getScala());
		dto.setInterno1(model.getInterno());
		dto.setPiano1(model.getPiano());
		dto.setAnnotazione(model.getAnnotazione());
		dto.setCivico3(model.getCivico3());
		
		return dto;
	}

}
