package it.eng.tz.urbamid.catasto.storage.service;

import java.io.File;
import java.util.Optional;

import it.eng.tz.urbamid.catasto.exception.CatastoServiceException;
import it.eng.tz.urbamid.catasto.storage.service.impl.ImportCatastoPathServiceImpl;
import it.eng.tz.urbamid.catasto.util.ImportType;

/**
 * Interfaccia che elenca i metodi implementati nel service {@link ImportCatastoPathServiceImpl} utilizzati
 * per il recupero di tutti i path applicativi utili.
 */
public interface ImportCatastoPathService {

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
	String pathInstallazioneGeoKettle() throws CatastoServiceException;
	
	/**
	 * 
	 * @return
	 * @throws CatastoServiceException
	 */
	String pathJobGeoKettle() throws CatastoServiceException;
	
	/**
	 * 
	 * @return
	 * @throws CatastoServiceException
	 */
	String scriptShDirectory( ) throws CatastoServiceException;

	/**
	 * 
	 * @param importType
	 * @return
	 * @throws CatastoServiceException
	 */
	public String importFolderDirectory(ImportType importType) throws CatastoServiceException;
	
	/**
	 * 
	 * @param importType
	 * @return
	 * @throws CatastoServiceException
	 */
	String sqlScriptDirectory( ImportType importType ) throws CatastoServiceException;
	
	/**
	 * 
	 * @param importType
	 * @return
	 * @throws CatastoServiceException
	 */
	String sqlScriptUpdateDirectory(ImportType importType) throws CatastoServiceException;
	
	/**
	 * 
	 * @param importType
	 * @return
	 * @throws CatastoServiceException
	 */
	String cassiniSoldnerWorkDirectory( ImportType importType ) throws CatastoServiceException;
	
	/**
	 * 
	 * @param importType
	 * @return
	 * @throws CatastoServiceException
	 */
	String gaussBoagaWorkDirectory( ImportType importType ) throws CatastoServiceException;
	
	/**
	 * 
	 * @param importType
	 * @return
	 * @throws CatastoServiceException
	 */
	String jobDataDirectory(ImportType importType) throws CatastoServiceException;

	/**
	 * 
	 * @param importType
	 * @return
	 * @throws CatastoServiceException
	 */
	String datiTerreniDirectory( ImportType importType ) throws CatastoServiceException;
	
	/**
	 * 
	 * @param importType
	 * @return
	 * @throws CatastoServiceException
	 */
	String datiFabbricatiDirectory( ImportType importType ) throws CatastoServiceException;
	
	/**
	 * 
	 * @param importType
	 * @return
	 * @throws CatastoServiceException
	 */
	String datiCartografiaDirectory( ImportType importType ) throws CatastoServiceException;

	/**
	 * 
	 * @param importType
	 * @return
	 * @throws CatastoServiceException
	 */
	String datiPlanimetrieDirectory(ImportType importType) throws CatastoServiceException;

	/**
	 * 
	 * @return
	 * @throws CatastoServiceException
	 */
	String pythonScriptCxfToShape() throws CatastoServiceException;

	/**
	 * 
	 * @param importType
	 * @return
	 */
	String shapefileOutputDirectory(ImportType importType) throws CatastoServiceException;
	
	/**
	 * 
	 * @param importType
	 * @return
	 */
	Optional<File> fileLogProceduraImport(ImportType importType, Long jobId);
	
	/**
	 * 
	 * @param importType
	 * @return
	 */
	String shapefileDirectory(ImportType importType) throws CatastoServiceException;
	
}
