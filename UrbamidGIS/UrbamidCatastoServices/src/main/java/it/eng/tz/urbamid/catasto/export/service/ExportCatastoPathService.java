package it.eng.tz.urbamid.catasto.export.service;

import it.eng.tz.urbamid.catasto.exception.CatastoServiceException;
import it.eng.tz.urbamid.catasto.storage.service.impl.ImportCatastoPathServiceImpl;

/**
 * Interfaccia che elenca i metodi implementati nel service {@link ImportCatastoPathServiceImpl} utilizzati
 * per il recupero di tutti i path applicativi utili.
 */
public interface ExportCatastoPathService {

	/**
	 * Metodo che restituisce la root path della struttura di file utilizzati nella procedura di
	 * import dati catastali.
	 * 
	 * @return la root path
	 * 
	 * @throws CatastoServiceException
	 */
	String rootPath() throws CatastoServiceException;

	/**
	 * 
	 * @return
	 * @throws CatastoServiceException
	 */
	String exportShapefileDirectory( ) throws CatastoServiceException;
}
