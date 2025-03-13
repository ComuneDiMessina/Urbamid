package it.eng.tz.urbamid.wrappergeo.storage.service;

import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.urbamid.wrappergeo.exception.WrapperGeoServiceException;
import it.eng.tz.urbamid.wrappergeo.exception.WrappergeoStorageException;

/**
 * Interfaccia che definisce i metodi del rispettivo service di storage.
 */
public interface FileStorageService {
	
	/**
	 * Metodo per il salvataggio di un file in una cartella.
	 * 
	 * @param file è il file
	 * @param folder è la directory di destinazione
	 * 
	 * @throws WrappergeoStorageException
	 */
	void store(MultipartFile file) throws WrapperGeoServiceException;
	
	/**
	 * Metodo per il salvataggio di una lista di file in una cartella.
	 * 
	 * @param files è la lista di file.
	 * @param folder è la directory di destinazione.
	 * 
	 * @throws WrappergeoStorageException
	 */
	void store(MultipartFile[] files) throws WrapperGeoServiceException;

	boolean checkDuplicate(MultipartFile file) throws WrapperGeoServiceException;
	boolean checkDuplicate(MultipartFile[] files) throws WrapperGeoServiceException;
	

}
