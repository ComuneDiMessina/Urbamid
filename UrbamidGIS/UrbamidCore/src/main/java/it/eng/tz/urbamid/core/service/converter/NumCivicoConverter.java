package it.eng.tz.urbamid.core.service.converter;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import it.eng.tz.urbamid.web.dto.NumCivicoDTO;
import it.eng.tz.urbamid.webgis.dao.model.NumCivico;


@Component
public class NumCivicoConverter extends AbstractConverter<NumCivico, NumCivicoDTO>{

	@Override
	public NumCivico toModel(NumCivicoDTO dto) {
		NumCivico model = new NumCivico();
		model.setId( dto.getId() );	
		model.setCodStrada(dto.getCs());
		
		String[] civico = dto.getNum().split(dto.getSeparatore());		
		model.setNumero(Integer.getInteger(civico[0]));
		model.setEsponente(civico[1]);
		model.setSysRef(dto.getsRef());
		model.setLatitudine(dto.getLat());
		model.setLongitudine(dto.getLon());
		return model;
	}

	@Override
	public NumCivicoDTO toDTO(NumCivico model) {
		NumCivicoDTO dto = new NumCivicoDTO();
		//dto.setId( model.getId() );
		//dto.setCs(model.getCodStrada());
		StringBuffer sb = new StringBuffer(model.getNumero().toString());
		if (StringUtils.hasText(model.getEsponente())) 
		{
			sb.append(dto.getSeparatore());
			sb.append(model.getEsponente());
		}
		
		dto.setNum(sb.toString());
		dto.setsRef(model.getSysRef());
		dto.setLon(model.getLongitudine());
		dto.setLat(model.getLatitudine());

		return dto;
	}

}
