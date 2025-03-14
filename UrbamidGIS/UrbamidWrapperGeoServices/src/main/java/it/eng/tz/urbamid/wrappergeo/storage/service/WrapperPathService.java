package it.eng.tz.urbamid.wrappergeo.storage.service;

import it.eng.tz.urbamid.wrappergeo.exception.WrapperGeoServiceException;
import it.eng.tz.urbamid.wrappergeo.storage.service.impl.WrapperPathServiceImpl;

/**
 * Interfaccia che elenca i metodi implementati nel service {@link WrapperPathServiceImpl} utilizzati
 * per il recupero di tutti i path applicativi utili.
 */
public interface WrapperPathService {

	/**
	 * Metodo che restituisce la root path della struttura di file utilizzati nella procedura di
	 * import dati catastali.
	 * 
	 * @return la root path
	 * 
	 * @throws WrapperGeoServiceException
	 */
	String rootPath() throws WrapperGeoServiceException;

	/**
	 * 
	 * @param importType
	 * @return
	 * @throws WrapperGeoServiceException
	 */
	String jobDataDirectory() throws WrapperGeoServiceException;

	/**
	 * 
	 * @param importType
	 * @return
	 * @throws WrapperGeoServiceException
	 */
	String jobDataShapeDirectory(String nameShape) throws WrapperGeoServiceException;
	
	/**
	 * 
	 * @param importType
	 * @return
	 * @throws WrapperGeoServiceException
	 */
	String sqlScriptDirectory(String nameShapeFile) throws WrapperGeoServiceException;
	
	String jobDataStyleDirectory() throws WrapperGeoServiceException;
}
