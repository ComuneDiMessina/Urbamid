package it.eng.tz.urbamid.wrappergeo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.urbamid.wrappergeo.exception.WrapperGeoServiceException;
import it.eng.tz.urbamid.wrappergeo.exception.WrappergeoStorageException;
import it.eng.tz.urbamid.wrappergeo.service.ImportDatiWrappergeoService;
import it.eng.tz.urbamid.wrappergeo.storage.service.FileStorageService;
import it.eng.tz.urbamid.wrappergeo.util.IConstants;

@Service
public class ImportDatiWrappergeoServiceImpl implements ImportDatiWrappergeoService {

	/**
	 * Logger
	 */
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private FileStorageService serviceStorage;

	@Override
	public void importFile(MultipartFile file, boolean overwrite) throws WrapperGeoServiceException {
		
		this.checkParams( file );
		
		if(LOG.isDebugEnabled()) {
			LOG.debug("STORAGE WRAPPERGEO SERVICE --> Verra' fatto il caricaricamento del file {} in overwrite {}.",file, overwrite);
		}
		
		boolean isFileDuplicated = serviceStorage.checkDuplicate(file);
		
		if (overwrite) {
			
			if(LOG.isDebugEnabled()) {
				LOG.debug("STORAGE WRAPPERGEO SERVICE --> Verra' fatto il caricaricamento del file {} in overwrite.",file);
			}
			serviceStorage.store(file);
		} else if (!overwrite && !isFileDuplicated) {
			
			if(LOG.isDebugEnabled()) {
				LOG.debug("STORAGE WRAPPERGEO SERVICE --> Verra' fatto il caricaricamento del file {}.",file);
			}
			serviceStorage.store(file);
		} else { 
			LOG.error("STORAGE WRAPPERGEO SERVICE --> Non e' stato possibile eseguire lo store del file {}.", file);
			throw new WrappergeoStorageException("Il file e' gia' presente e non posso sovrascrivere.");
		}
	}

	@Override
	public void importFiles(MultipartFile[] files, boolean overwrite) throws WrapperGeoServiceException {

		this.checkParams( files );
		
		if(LOG.isDebugEnabled()) {
			LOG.debug("STORAGE WRAPPERGEO SERVICE --> Verra' fatto il caricaricamento dei files {} in overwrite {}.",files, overwrite);
		}
		
		boolean isFileDuplicated = serviceStorage.checkDuplicate(files);
		
		if (overwrite) {
			
			if(LOG.isDebugEnabled()) {
				LOG.debug("STORAGE WRAPPERGEO SERVICE --> Verra' fatto il caricaricamento dei files {} in overwrite.",files);
			}
			serviceStorage.store(files);
		} else if (!overwrite && !isFileDuplicated) {
			
			if(LOG.isDebugEnabled()) {
				LOG.debug("STORAGE WRAPPERGEO SERVICE --> Verra' fatto il caricaricamento dei files {}.",files);
			}
			serviceStorage.store(files);
		} else { 
			LOG.error("STORAGE WRAPPERGEO SERVICE --> Non e' stato possibile eseguire lo store dei files {}.", files);
			throw new WrappergeoStorageException("Il file e' gia' presente e non posso sovrascrivere.");
		}
	}

	/**
	 * Metodo privato che valida i file caricati
	 * 
	 * @param importType
	 * @param folder
	 */
	private void checkParams(MultipartFile... files) throws WrapperGeoServiceException {
		if (files!=null) {
			
			List<MultipartFile> filesChecked = new ArrayList<MultipartFile>();
			for (MultipartFile file : files) {
				
				/**Controllo se i file caricati sono vuoti**/
				if (file.isEmpty()) {
					LOG.error("STORAGE WRAPPERGEO SERVICE -- file {} non e' un file valido.", file);
					throw new WrapperGeoServiceException("Il file non e' un file valido.");
				}
				
				/**Controllo se i file caricati sono tutti nel formato ZIP**/
				if(file != null && !file.getContentType().equalsIgnoreCase(IConstants.TIPO_APPLICATION_ZIP)) {
					LOG.error("STORAGE WRAPPERGEO SERVICE -- file {} non e' nel formato ZIP.", file);
					throw new WrapperGeoServiceException("Il file non e' nel formato ZIP.");
				}
				
				/**Controllo se sono stati caricati file doppioni**/
				if (filesChecked.contains(file)) {
					LOG.error("STORAGE WRAPPERGEO SERVICE -- file {} presente due volte.", file);
					throw new WrapperGeoServiceException("Nei files e' presente due volte lo stesso file.");
				} else {
					filesChecked.add(file);
				}
			}
		} else {
			LOG.error("STORAGE WRAPPERGEO SERVICE -- Valore errato per files: {}.", files);
			throw new WrapperGeoServiceException("Valore errato per files");
		}
	}

}