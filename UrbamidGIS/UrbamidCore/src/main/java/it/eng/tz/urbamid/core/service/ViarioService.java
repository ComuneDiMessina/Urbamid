package it.eng.tz.urbamid.core.service;

import java.util.List;

import it.eng.tz.urbamid.web.dto.NumCivicoDTO;
import it.eng.tz.urbamid.web.dto.ToponimoDTO;

public interface ViarioService {
	
	public List<ToponimoDTO> findStreetByName(String denom) throws Exception;
	
	public List<NumCivicoDTO> findNumberByStreet(String codStrada) throws Exception;
}
