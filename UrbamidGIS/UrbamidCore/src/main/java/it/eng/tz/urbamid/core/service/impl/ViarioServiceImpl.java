package it.eng.tz.urbamid.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.eng.tz.urbamid.core.dao.NumCivicoDao;
import it.eng.tz.urbamid.core.dao.ToponimoDao;
import it.eng.tz.urbamid.core.service.ViarioService;
import it.eng.tz.urbamid.core.service.converter.NumCivicoConverter;
import it.eng.tz.urbamid.core.service.converter.ToponimoConverter;
import it.eng.tz.urbamid.web.dto.NumCivicoDTO;
import it.eng.tz.urbamid.web.dto.ToponimoDTO;
import it.eng.tz.urbamid.webgis.dao.model.NumCivico;
import it.eng.tz.urbamid.webgis.dao.model.Toponimo;

@Service
public class ViarioServiceImpl implements ViarioService {

	@Autowired
	private NumCivicoDao numCivicoDao;
	
	@Autowired
	private ToponimoDao toponimoDao;
	
	
	@Autowired
	private NumCivicoConverter numCivicoConverter; 
	
	@Autowired
	private ToponimoConverter toponimoConverter; 
	
	

	@Override
	public List<ToponimoDTO> findStreetByName(String streetName) throws Exception {
		Iterable<Toponimo> toponimi = toponimoDao.getStreet(streetName);
		List<ToponimoDTO> dtos = this.toponimoConverter.toDTO(toponimi);
		return dtos;
	}



	@Override
	public List<NumCivicoDTO> findNumberByStreet(String codStrada) throws Exception {
		Iterable<NumCivico> numCivici = numCivicoDao.getNumberbyStreet(codStrada);
		List<NumCivicoDTO> dtos = this.numCivicoConverter.toDTO(numCivici);
		return dtos;
	}
	
}
