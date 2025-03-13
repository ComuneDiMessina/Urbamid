package it.eng.tz.urbamid.prg.service;

import java.io.File;

import org.springframework.stereotype.Service;

import it.eng.tz.urbamid.prg.filter.CduFilter;
import it.eng.tz.urbamid.prg.web.dto.CduDTO;
import it.eng.tz.urbamid.prg.web.dto.PagedResult;

@Service
public interface CduService {

	public PagedResult<CduDTO> cercaCdu(CduFilter filter) throws Exception;

	public File downloadCduAnagrafica(String protocollo) throws Exception;

}
