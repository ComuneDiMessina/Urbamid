package it.eng.tz.urbamid.profilemanager.service;


import java.util.List;

import it.eng.tz.urbamid.profilemanager.exception.ProfileManagerDbException;
import it.eng.tz.urbamid.profilemanager.web.dto.ProfiloUtenteDto;
import it.eng.tz.urbamid.profilemanager.web.dto.UtenteDto;

public interface UtenteSvc {
	
	UtenteDto salvaUtente(UtenteDto utenteDto) throws ProfileManagerDbException;
	
	void deleteUtente(UtenteDto utenteDto) throws ProfileManagerDbException;
	
	void deleteUtenteById(UtenteDto utenteDto) throws ProfileManagerDbException;
	
	UtenteDto findUtente(String username) throws ProfileManagerDbException;
	
	UtenteDto findUtenteById(String id) throws ProfileManagerDbException;
	
	ProfiloUtenteDto getProfiloUtente(String username) throws ProfileManagerDbException;
	
	List<ProfiloUtenteDto> getProfiloUtenti() throws ProfileManagerDbException;
	
}
