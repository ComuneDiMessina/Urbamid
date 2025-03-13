package it.eng.tz.urbamid.web.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.urbamid.exception.UrbamidServiceException;
import it.eng.tz.urbamid.web.dto.DettaglioFileDTO;

/**
 * Interfaccia che definisce i metodi del proxy service di storage  catasto.
 */
public interface CatastoStorageService {
	
	/**
	 * Recupera la lista dei file presenti in una cartella.
	 * 
	 * @param importType tipo di import (ATTUALITA/AGGIORNAMENTO)
	 * @param folder nome della cartella di upload
	 * 
	 * @return la lista dei file
	 * 
	 * @throws UrbamidServiceException
	 */
	List<DettaglioFileDTO> elencoFileInCartella(final String importType, final String folder) throws UrbamidServiceException;
	
	/**
	 * Effettua l'upload di un file in una cartella di upload.
	 * 
	 * @param importType tipo di import (ATTUALITA/AGGIORNAMENTO)
	 * @param folder nome della cartella di upload
	 * @param file è il {@link MultipartFile} che incapsula il file da caricare
	 * 
	 * @throws UrbamidServiceException
	 */
	void upload(final String importType, final String folder, final MultipartFile file) throws UrbamidServiceException;
		
	/**
	 * Rimuove un file da una determinata cartella di upload.
	 * 
	 * @param importType tipo di import (ATTUALITA/AGGIORNAMENTO)
	 * @param folder nome della cartella di upload
	 * @param nomeFile è il nome del file da rimuovere
	 * 
	 * @throws UrbamidServiceException
	 */
	void rimuovi(String importType, String folder, String nomeFile) throws UrbamidServiceException;

}
