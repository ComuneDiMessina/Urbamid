package it.eng.tz.urbamid.catasto.web.dto.converter;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.catasto.exception.ConverterException;
import it.eng.tz.urbamid.catasto.persistence.model.UnitaImmobiliareJoin;
import it.eng.tz.urbamid.catasto.util.AbstractConverter;
import it.eng.tz.urbamid.catasto.web.dto.UnitaImmobiliareDTO;

@Component
public class UnitaImmobiliareConverter extends AbstractConverter<UnitaImmobiliareJoin, UnitaImmobiliareDTO>{

	Format formatter = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public UnitaImmobiliareJoin toModel(UnitaImmobiliareDTO dto, Map<String, Object> parameters) throws ConverterException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UnitaImmobiliareDTO toDTO(UnitaImmobiliareJoin model, Map<String, Object> parameters)
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
		return dto;
	}

	public List<UnitaImmobiliareDTO> toDTO(List<UnitaImmobiliareJoin> models) throws ConverterException {
		List<UnitaImmobiliareDTO> result = new ArrayList<>();
		try 
		{
			for (UnitaImmobiliareJoin model : models) {
				result.add(this.toDTO(model));
			}
		}
		catch (Exception e) 
		{
			//logger.error("Errore nella conversione ParticellaGeom --> ParticellaGeomDTO" + e, e);
		}

		return result;
	}

}
