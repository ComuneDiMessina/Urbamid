package it.eng.tz.urbamid.web.services;

import it.eng.tz.urbamid.exception.UrbamidServiceException;
import it.eng.tz.urbamid.web.dto.DettaglioBatchDTO;

public interface CatastoBatchManagementService {
	
	DettaglioBatchDTO avviaProcessoBatchAttualita() throws UrbamidServiceException;
	
	DettaglioBatchDTO avviaProcessoBatchAggiornamento() throws UrbamidServiceException;
	
	DettaglioBatchDTO recuperaDettaglioBatchJob(Long jobId) throws UrbamidServiceException;
	
	DettaglioBatchDTO avviaRecuperoProcessoBatch() throws UrbamidServiceException;

}
