package it.eng.tz.urbamid.catasto.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.urbamid.catasto.exception.CatastoServiceException;
import it.eng.tz.urbamid.catasto.exception.WrongImportContentType;
import it.eng.tz.urbamid.catasto.service.ImportDatiCatastaliService;
import it.eng.tz.urbamid.catasto.storage.service.FileStorageService;
import it.eng.tz.urbamid.catasto.util.IConstants;
import it.eng.tz.urbamid.catasto.web.dto.DettaglioEliminazioneFileDTO;
import it.eng.tz.urbamid.catasto.web.dto.DettaglioFileDTO;
import it.eng.tz.urbamid.catasto.web.dto.converter.DettaglioFileConverter;

@Service
public class ImportDatiCatastaliServiceImpl implements ImportDatiCatastaliService {

	/**
	 * Logger
	 */
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private FileStorageService serviceStorage;

	@Autowired
	private DettaglioFileConverter converterFile;
	
	@Override
	public void importFile(MultipartFile file, String folder) throws CatastoServiceException {
		valida(file);
		serviceStorage.store(file, folder);
	}

	@Override
	public void importFiles(MultipartFile[] files, String folder) throws CatastoServiceException {
		valida(files);
		serviceStorage.store(files, folder);
	}

	@Override
	public List<DettaglioFileDTO> elencoFileInCartella(String folder) throws CatastoServiceException {
		if(LOG.isDebugEnabled()) {
			LOG.debug("Eseguo l'elenco dei file nella cartella {}.", folder);
		}
		List<DettaglioFileDTO> elencoFileInCartella =
				converterFile.toDto(serviceStorage.elencoFileZipInPath(folder));
		elencoFileInCartella.sort((a, b) -> a.getNome().compareTo(b.getNome()));
		if(LOG.isDebugEnabled()) {
			LOG.debug("Sono stati trovati {} file nella cartella {}.", elencoFileInCartella.size(), folder);
			LOG.debug("Nomi dei file nella cartella {}: {}", folder, 
					elencoFileInCartella.stream().map(DettaglioFileDTO::getNome).collect(Collectors.toList()));
		}
		return elencoFileInCartella;
	}

	@Override
	public DettaglioEliminazioneFileDTO rimuoviFileInCartella(String folder, String nomeFile) throws CatastoServiceException {
		if(LOG.isDebugEnabled()) {
			LOG.debug("Eseguo la cancellazione di tutti i file nella cartella {}.", folder);
		}
		List<String> nomiFileRimossi = serviceStorage.rimuoviFileInCartella(folder, nomeFile);
		return new DettaglioEliminazioneFileDTO(folder, nomiFileRimossi);
	}

	private void valida(MultipartFile... files) throws CatastoServiceException {
		for (MultipartFile file : files) {
			checkTipoFile(file);
		}
	}
	
	private void checkTipoFile(MultipartFile file) throws WrongImportContentType {
		if(file != null && !file.getContentType().equalsIgnoreCase(IConstants.TIPO_APPLICATION_ZIP)) {
			throw new WrongImportContentType();
		}
	}

}