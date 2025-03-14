package it.eng.tz.urbamid.toponomastica.web.dto.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.toponomastica.persistence.model.Toponimo;
import it.eng.tz.urbamid.toponomastica.web.dto.ToponimoDto;



@Component
public class ToponimoConverter implements IConverter<Toponimo, ToponimoDto>{

	
	private static final Logger logger = LoggerFactory.getLogger(FunzionalitaConverter.class.getName());

	@Override
	public Toponimo toModel(ToponimoDto dto) throws Exception{
		Toponimo model = new Toponimo();
		model.setId( dto.getId() );	
		model.setCodStrada(Integer.valueOf(dto.getCodStrada()));
		model.setDenominazione(dto.getDenom());
		model.setSysref(dto.getsRef());
		model.setLatitudine(dto.getLat());
		model.setLongitudine(dto.getLon());
		return model;
	}

	@Override
	public ToponimoDto toDto(Toponimo model) throws Exception{
		ToponimoDto dto = new ToponimoDto();
		dto.setId( model.getId() );
		dto.setCodStrada(model.getCodStrada().toString());
		dto.setDenom(model.getDenominazione());
		dto.setsRef(model.getSysref());
		dto.setLon(model.getLongitudine());
		dto.setLat(model.getLatitudine());

		return dto;
	}



	@Override
	public List<ToponimoDto> toDto(List<Toponimo> models) throws Exception {
		List<ToponimoDto> result = new ArrayList<ToponimoDto>();
		try 
		{
			for (Toponimo model : models) {
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
