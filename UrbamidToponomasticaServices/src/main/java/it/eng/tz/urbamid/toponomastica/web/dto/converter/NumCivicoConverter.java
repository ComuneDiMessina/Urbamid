package it.eng.tz.urbamid.toponomastica.web.dto.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import it.eng.tz.urbamid.toponomastica.persistence.model.NumCivico;
import it.eng.tz.urbamid.toponomastica.web.dto.NumCivicoDto;


@Component
public class NumCivicoConverter implements IConverter<NumCivico, NumCivicoDto>{

	private static final Logger logger = LoggerFactory.getLogger(NumCivicoConverter.class.getName());
	
	@Override
	public NumCivico toModel(NumCivicoDto dto) throws Exception{
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
	public NumCivicoDto toDto(NumCivico model) throws Exception{
		NumCivicoDto dto = new NumCivicoDto();
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

	@Override
	public List<NumCivicoDto> toDto(List<NumCivico> models) throws Exception {
		List<NumCivicoDto> result = new ArrayList<NumCivicoDto>();
		try 
		{
			for (NumCivico model : models) {
				result.add(this.toDto(model));
			}
		}
		catch (Exception e) 
		{
			logger.error("Errore nella conversione Toponimo --> ToponimoDto" + e, e);
		}

		return result;
	}

}
