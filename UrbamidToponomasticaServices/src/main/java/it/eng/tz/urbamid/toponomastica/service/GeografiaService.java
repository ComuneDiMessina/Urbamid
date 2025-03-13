package it.eng.tz.urbamid.toponomastica.service;

import java.util.List;

import it.eng.tz.urbamid.toponomastica.web.dto.TipologicaDTO;

public interface GeografiaService {

	List<TipologicaDTO> getComuni() throws Exception;

	List<TipologicaDTO> getProvince() throws Exception;

	List<TipologicaDTO> getComuniByProvincia(Long idProvincia) throws Exception;
	
	List<TipologicaDTO> getComuniByMessina() throws Exception;

}
