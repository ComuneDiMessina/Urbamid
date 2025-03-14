package it.eng.tz.urbamid.profilemanager.service;


import java.util.List;

import it.eng.tz.urbamid.profilemanager.exception.ProfileManagerDbException;
import it.eng.tz.urbamid.profilemanager.web.dto.RuoloDto;
import it.eng.tz.urbamid.profilemanager.web.dto.RuoloUtenteDto;

public interface RuoloSvc {
	
	RuoloDto salvaRuolo( RuoloDto ruoloDto ) throws ProfileManagerDbException; 
	
	List<RuoloUtenteDto> getRuoli() throws ProfileManagerDbException;
	
	RuoloDto findRuolo(String codice) throws ProfileManagerDbException;
	
	void deleteRuolo(RuoloDto ruoloDto) throws ProfileManagerDbException;
	
}
