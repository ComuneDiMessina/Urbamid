package it.eng.tz.urbamid.prg.service;

import java.util.List;

import org.springframework.stereotype.Service;

import it.eng.tz.urbamid.prg.web.dto.FunzionalitaDto;

/**
 * @author Alessandro Paolillo
 */
@Service
public interface PRGService {
	
	public List<FunzionalitaDto> getFunzionalita(List<String> authorities) throws Exception;
}