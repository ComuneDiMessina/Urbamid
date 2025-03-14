package it.eng.tz.urbamid.administration.mappa.service.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.administration.mappa.dao.mapper.MappaPermessoBean;
import it.eng.tz.urbamid.administration.mappa.dao.model.MappaPermesso;
import it.eng.tz.urbamid.administration.web.dto.converter.IConverter;

@Component
public class MappaPermessiConverter implements IConverter<MappaPermesso, MappaPermessoBean> {

	private static final Logger logger = LoggerFactory.getLogger(MappaPermessiConverter.class.getName());
	
	@Override
	public MappaPermesso toModel(MappaPermessoBean dto) throws Exception {
		
		MappaPermesso model = new MappaPermesso();
		
		model.setId_rsrs_prms(dto.getId_rsrs_prms());
		model.setId_risorsa(dto.getId_risorsa());
		model.setId_tipo_risorsa(dto.getId_tipo_risorsa());
		model.setId_ruolo(dto.getId_ruolo());
		model.setAbilita_visualizzazione(dto.getAbilita_visualizzazione());
		model.setAbilita_modifica(dto.getAbilita_modifica());
		model.setAbilita_permesso1(dto.getAbilita_permesso1());
		model.setAbilita_permesso2(dto.getAbilita_permesso2());
		model.setAbilita_permesso3(dto.getAbilita_permesso3());
		
		return model;
	}

	@Override
	public MappaPermessoBean toDto(MappaPermesso model) throws Exception {
		
		MappaPermessoBean dto = new MappaPermessoBean();
		
		dto.setId_rsrs_prms(model.getId_rsrs_prms());
		dto.setId_risorsa(model.getId_risorsa());
		dto.setId_tipo_risorsa(model.getId_tipo_risorsa());
		dto.setId_ruolo(model.getId_ruolo());
		dto.setAbilita_visualizzazione(model.getAbilita_visualizzazione());
		dto.setAbilita_modifica(model.getAbilita_modifica());
		dto.setAbilita_permesso1(model.getAbilita_permesso1());
		dto.setAbilita_permesso2(model.getAbilita_permesso2());
		dto.setAbilita_permesso3(model.getAbilita_permesso3());		
		
		return dto;
	}

	@Override
	public List<MappaPermessoBean> toDto(List<MappaPermesso> models) throws Exception {
		
		List<MappaPermessoBean> result = new ArrayList<MappaPermessoBean>();
		
		try {
			
			for(MappaPermesso model: models) {
				
				result.add(this.toDto(model));
				
			}
			
		} catch (Exception e) {
			
			logger.error("Errore nella conversione MappaPermesso ---> MappaPermessoDTO", e, e);
			
		}
		
		return result;
	}

}
