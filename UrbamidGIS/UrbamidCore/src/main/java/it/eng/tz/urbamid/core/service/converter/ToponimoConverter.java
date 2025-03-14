package it.eng.tz.urbamid.core.service.converter;

import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.web.dto.ToponimoDTO;
import it.eng.tz.urbamid.webgis.dao.model.Toponimo;


@Component
public class ToponimoConverter extends AbstractConverter<Toponimo, ToponimoDTO>{

	@Override
	public Toponimo toModel(ToponimoDTO dto) {
		Toponimo model = new Toponimo();
		model.setId( dto.getId() );	
		model.setCodStrada(dto.getCodStrada());
		model.setDenominazione(dto.getDenom());
		model.setSysRef(dto.getsRef());
		model.setLatitudine(dto.getLat());
		model.setLongitudine(dto.getLon());
		return model;
	}

	@Override
	public ToponimoDTO toDTO(Toponimo model) {
		ToponimoDTO dto = new ToponimoDTO();
		dto.setId( model.getId() );
		dto.setCodStrada(model.getCodStrada());
		dto.setDenom(model.getDenominazione());
		dto.setsRef(model.getSysRef());
		dto.setLon(model.getLongitudine());
		dto.setLat(model.getLatitudine());

		return dto;
	}

}
