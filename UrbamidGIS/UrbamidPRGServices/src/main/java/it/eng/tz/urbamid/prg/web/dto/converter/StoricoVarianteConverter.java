package it.eng.tz.urbamid.prg.web.dto.converter;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.prg.persistence.model.StoricoVariante;
import it.eng.tz.urbamid.prg.web.dto.StoricoVarianteDTO;

@Component
public class StoricoVarianteConverter implements IConverter<StoricoVariante, StoricoVarianteDTO>{

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public StoricoVariante toModel(StoricoVarianteDTO dto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StoricoVarianteDTO toDto(StoricoVariante model) throws Exception {
		StoricoVarianteDTO dto = new StoricoVarianteDTO();
		dto.setIdStorico(model.getIdStorico());
		dto.setAzione(model.getAzione());
		dto.setDataAzione(model.getDataAzione() != null ? dateFormat.format(model.getDataAzione()) : null);
		dto.setDescrizioneAzione(model.getDescrizioneAzione());
		dto.setNoteAzione(model.getNoteAzione());
		return dto;
	}

	@Override
	public List<StoricoVarianteDTO> toDto(List<StoricoVariante> models) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
