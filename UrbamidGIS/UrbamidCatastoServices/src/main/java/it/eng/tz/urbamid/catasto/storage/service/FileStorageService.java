package it.eng.tz.urbamid.catasto.storage.service;

import java.io.File;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.urbamid.catasto.exception.CatastoStorageException;

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
	 * @throws CatastoStorageException
	 */
	void store(MultipartFile file, String folder) throws CatastoStorageException;
	
	/**
	 * Metodo per il salvataggio di una lista di file in una cartella.
	 * 
	 * @param files è la lista di file.
	 * @param folder è la directory di destinazione.
	 * 
	 * @throws CatastoStorageException
	 */
	void store(MultipartFile[] files, String folder) throws CatastoStorageException;

	/**
	 * Metodo per il recupero dell'elenco di file in formato .zip presenti in un determinato path.
	 * 
	 * @param path è il path della directory dove cercare i file in formato .zip
	 * 
	 * @return la lista dei file
	 * 
	 * @throws CatastoStorageException
	 */
	List<File> elencoFileZipInPath(@NotEmpty String path) throws CatastoStorageException;

	/**
	 * Metodo per la rimozione di un file in un determinato path.
	 * 
	 * @param path è il nome della directory dove è locato il file.
	 * @param nomeFile è il nome del file da cancellare.
	 * 
	 * @return
	 * 
	 * @throws CatastoStorageException
	 */
	List<String> rimuoviFileInCartella(@NotEmpty String path, @NotEmpty String nomeFile) throws CatastoStorageException;

}
