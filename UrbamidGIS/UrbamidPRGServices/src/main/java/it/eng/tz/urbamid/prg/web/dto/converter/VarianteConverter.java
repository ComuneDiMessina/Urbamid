package it.eng.tz.urbamid.prg.web.dto.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.prg.exception.ConverterException;
import it.eng.tz.urbamid.prg.persistence.model.StoricoVariante;
import it.eng.tz.urbamid.prg.persistence.model.Variante;
import it.eng.tz.urbamid.prg.util.AbstractConverter;
import it.eng.tz.urbamid.prg.web.dto.VarianteDTO;

@Component
public class VarianteConverter extends AbstractConverter<Variante, VarianteDTO>{

	@Autowired
	StoricoVarianteConverter storicoVarianteConverter;

	DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public Variante toModel(VarianteDTO dto, Map<String, Object> parameters) throws ConverterException {
		Variante variante = new Variante();
		try {
			variante.setNome(dto.getNome());
			variante.setDescrizione(dto.getDescrizione());
			variante.setCodComune(dto.getCodComune());
			variante.setDataDelAdoz(dto.getDataDelAdoz().isEmpty() ? null : formatter.parse(dto.getDataDelAdoz()));
			variante.setNumDelAdoz(dto.getNumDelAdoz());
			variante.setOrgDelAdoz(dto.getOrgDelAdoz());
			variante.setDataDelAppr(dto.getDataDelAppr().isEmpty() ? null : formatter.parse(dto.getDataDelAppr()));
			variante.setNumDelAppr(dto.getNumDelAppr());
			variante.setNoteDelAppr(dto.getNoteDelAppr());
			variante.setStato(dto.getStato());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return variante;
	}

	@Override
	public VarianteDTO toDTO(Variante model, Map<String, Object> parameters) throws ConverterException {
		VarianteDTO dto = new VarianteDTO();
		dto.setIdVariante(model.getIdVariante());
		dto.setNome(model.getNome());
		dto.setDescrizione(model.getDescrizione());
		dto.setCodComune(model.getCodComune());
		dto.setDataDelAdoz(model.getDataDelAdoz() == null ? null : formatter.format(model.getDataDelAdoz()));
		dto.setNumDelAdoz(model.getNumDelAdoz());
		dto.setOrgDelAdoz(model.getOrgDelAdoz());
		dto.setDataDelAppr(model.getDataDelAppr() == null ? null : formatter.format(model.getDataDelAppr()));
		dto.setNumDelAppr(model.getNumDelAppr());
		dto.setNoteDelAppr(model.getNoteDelAppr());
		dto.setStato(model.getStato());
		for (StoricoVariante variante : model.getStoricoVariante()) {
			try {
				dto.getListaStorico().add(storicoVarianteConverter.toDto(variante));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (model.getAmbitoVariante().size() > 0) {
			dto.setAmbitoInserito(true);
		}
		return dto;
	}

}
