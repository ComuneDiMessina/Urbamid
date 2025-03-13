package it.eng.tz.urbamid.profilemanager.service;


import it.eng.tz.urbamid.profilemanager.exception.ProfileManagerDbException;
import it.eng.tz.urbamid.profilemanager.persistence.model.RuoloUtente;
import it.eng.tz.urbamid.profilemanager.web.dto.RuoloUtenteShtDto;

public interface RuoloUtenteSvc {
	
	void updateRuoloUtente(RuoloUtenteShtDto ruoloUtente) throws ProfileManagerDbException;
	
	RuoloUtente salvaUtenteRuolo(RuoloUtenteShtDto utenteRuoloDto) throws ProfileManagerDbException;
	
	RuoloUtente salvaUtenteRuolo(Long id,Long idUtente, Long idRuolo, String codRuolo) throws ProfileManagerDbException;
	
	void deleteRuoloUtenteById(Long id) throws ProfileManagerDbException;
	
}
