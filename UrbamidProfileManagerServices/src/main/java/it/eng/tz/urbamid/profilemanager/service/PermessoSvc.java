package it.eng.tz.urbamid.profilemanager.service;


import java.util.List;

import it.eng.tz.urbamid.profilemanager.exception.ProfileManagerDbException;
import it.eng.tz.urbamid.profilemanager.web.dto.PermessoUtenteDto;

public interface PermessoSvc {
	
	void salvaPermesso(PermessoUtenteDto permesso) throws ProfileManagerDbException; 
	
	PermessoUtenteDto getPermessoUtente(String username) throws ProfileManagerDbException;
	
	List<PermessoUtenteDto> getPermessi() throws ProfileManagerDbException;
}
