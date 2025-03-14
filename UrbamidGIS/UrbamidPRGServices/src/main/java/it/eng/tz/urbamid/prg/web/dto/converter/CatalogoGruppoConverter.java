package it.eng.tz.urbamid.prg.web.dto.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.prg.persistence.model.CatalogoGruppo;
import it.eng.tz.urbamid.prg.persistence.model.CatalogoLayer;
import it.eng.tz.urbamid.prg.web.dto.CatalogoGruppoDTO;
import it.eng.tz.urbamid.prg.web.dto.CatalogoLayerDTO;

@Component
public class CatalogoGruppoConverter implements IConverter<CatalogoGruppo, CatalogoGruppoDTO> {

	@Override
	public CatalogoGruppo toModel(CatalogoGruppoDTO dto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CatalogoGruppoDTO toDto(CatalogoGruppo model) throws Exception {
		CatalogoGruppoDTO dto = new CatalogoGruppoDTO();
		dto.setId(model.getId());
		dto.setNomeGruppo(model.getNomeGruppo());
		List<CatalogoLayerDTO> layers = new ArrayList<>();
		for (CatalogoLayer item : model.getCatalogoLayer()) {
			CatalogoLayerDTO layer = new CatalogoLayerDTO();
			layer.setId(item.getId());
			layer.setIdLayer(item.getIdLayer());
			layer.setNomeLayer(item.getNomeLayer());
			layer.setSorgente(item.getSorgente());
			layer.setHrefDetail(item.getHrefDetail());
			layer.setNomeColonna(item.getNomeColonna());
			layers.add(layer);
		}
		
		Collections.sort(layers, new Comparator<CatalogoLayerDTO>() {

			@Override
			public int compare(CatalogoLayerDTO o1, CatalogoLayerDTO o2) {
				return o1.getNomeLayer().compareToIgnoreCase(o2.getNomeLayer());
			}
			
		});
		
		dto.setListCatalogoLayer(layers);
		return dto;
	}

	@Override
	public List<CatalogoGruppoDTO> toDto(List<CatalogoGruppo> models) throws Exception {
		List<CatalogoGruppoDTO> result = new ArrayList<CatalogoGruppoDTO>();
		try 
		{
			for (CatalogoGruppo model : models) {
				result.add(this.toDto(model));
			}
		}
		catch (Exception e) 
		{
			throw (e);
		}

		return result;
	}

}
