package it.eng.tz.urbamid.wrappergeo.service;

import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.urbamid.wrappergeo.exception.WrapperGeoServiceException;

public interface ImportDatiWrappergeoService {

	void importFile( MultipartFile file, boolean overwrite) throws WrapperGeoServiceException;
	
	void importFiles( MultipartFile[] file, boolean overwrite) throws WrapperGeoServiceException;

//	List<DettaglioFileDTO> elencoFileInCartella(String folder) throws WrapperGeoServiceException;
//
//	DettaglioEliminazioneFileDTO rimuoviFileInCartella(String folder, String nomeFile) throws WrapperGeoServiceException;

}
