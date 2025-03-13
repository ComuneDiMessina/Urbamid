package it.eng.tz.urbamid.prg.web.dto.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.prg.persistence.model.CartografiaVariante;
import it.eng.tz.urbamid.prg.web.dto.CartografiaVarianteDTO;

@Component
public class CartografiaVarianteConverter implements IConverter<CartografiaVariante, CartografiaVarianteDTO> {

	@Override
	public CartografiaVariante toModel(CartografiaVarianteDTO dto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CartografiaVarianteDTO toDto(CartografiaVariante model) throws Exception {
		CartografiaVarianteDTO dto = new CartografiaVarianteDTO();
		dto.setIdCartografia(model.getIdCartografia());
		dto.setIdVariante(model.getVariante().getIdVariante());
		dto.setNomeLayer(model.getNomeLayer());
		dto.setDescrizioneLayer(model.getDescrizioneLayer());
		dto.setGruppoLayer(model.getGruppoLayer());
		return dto;
	}

	@Override
	public List<CartografiaVarianteDTO> toDto(List<CartografiaVariante> models) throws Exception {
		List<CartografiaVarianteDTO> result = new ArrayList<CartografiaVarianteDTO>();
		try 
		{
			for (CartografiaVariante model : models) {
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
