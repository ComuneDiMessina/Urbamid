package it.eng.tz.urbamid.catasto.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.urbamid.catasto.exception.CatastoServiceException;
import it.eng.tz.urbamid.catasto.web.dto.DettaglioEliminazioneFileDTO;
import it.eng.tz.urbamid.catasto.web.dto.DettaglioFileDTO;

public interface ImportDatiCatastaliService {

	void importFile( MultipartFile file, String folder) throws CatastoServiceException;
	
	void importFiles( MultipartFile[] file, String folder) throws CatastoServiceException;

	List<DettaglioFileDTO> elencoFileInCartella(String folder) throws CatastoServiceException;

	DettaglioEliminazioneFileDTO rimuoviFileInCartella(String folder, String nomeFile) throws CatastoServiceException;

}
