package it.eng.tz.urbamid.security.service;

import java.util.List;

import it.eng.tz.urbamid.security.model.PermessoUtenteDto;
import it.eng.tz.urbamid.security.model.ProfiloUtenteDto;
import it.eng.tz.urbamid.web.dto.RuoloDto;
import it.eng.tz.urbamid.web.dto.RuoloPermessoDto;
import it.eng.tz.urbamid.web.dto.RuoloUtenteDto;
import it.eng.tz.urbamid.web.dto.RuoloUtenteShrDto;
import it.eng.tz.urbamid.web.dto.UtenteDto;

public interface ProfileManagerService {

	public ProfiloUtenteDto getUtenteByUsername(String username) throws Exception;
	
	public List<ProfiloUtenteDto> getUtenti() throws Exception;
	
	public UtenteDto salvaUtente (UtenteDto utente) throws Exception;
	
	public RuoloUtenteShrDto salvaUtenteRuolo (RuoloUtenteShrDto ruoloUtente) throws Exception;
	
	public UtenteDto eliminaUtente (String username) throws Exception;
	
	public UtenteDto eliminaUtenteById (String id) throws Exception;
	
	public RuoloUtenteShrDto eliminaUtenteRuolo (RuoloUtenteShrDto ruoloUtente) throws Exception;
	
	public List<PermessoUtenteDto> getPermessi() throws Exception;
	
	public List<RuoloUtenteDto> getRuoli() throws Exception;
	
	public RuoloPermessoDto salvaRuoloPermesso(String idRuolo, String idPermesso) throws Exception;
	
	public boolean deleteRuoloPermesso(String idRuolo, String idPermesso) throws Exception;
	
	public RuoloDto salvaRuolo(RuoloDto ruolo) throws Exception;
	
	public RuoloDto deleteRuolo(String codice) throws Exception;
}
