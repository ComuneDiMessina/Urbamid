package it.eng.tz.urbamid.administration.service;

import java.util.List;

import org.springframework.stereotype.Service;

import it.eng.tz.urbamid.administration.web.dto.FunzionalitaDto;
import it.eng.tz.urbamid.administration.web.dto.MenuFunzionalitaDto;

/**
 * @author Alessandro Paolillo
 */
@Service
public interface AmministrazioneService {
	
	public List<FunzionalitaDto> getFunzionalita(List<String> authorities) throws Exception;
	
	public MenuFunzionalitaDto getMenuFunzionalita(String authority) throws Exception;
}