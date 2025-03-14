package it.eng.tz.urbamid.profilemanager.service;


import it.eng.tz.urbamid.profilemanager.exception.ProfileManagerDbException;
import it.eng.tz.urbamid.profilemanager.web.dto.RuoloPermessoDto;

public interface RuoloPermessoSvc {
	
	RuoloPermessoDto salvaRuoloPermesso(RuoloPermessoDto ruoloPermessoDto) throws ProfileManagerDbException;
	
	void deleteRuoloPermessoById(Long id) throws ProfileManagerDbException;
	
	void deleteRuoloPermesso(RuoloPermessoDto ruoloPermesso) throws ProfileManagerDbException;
	
}
