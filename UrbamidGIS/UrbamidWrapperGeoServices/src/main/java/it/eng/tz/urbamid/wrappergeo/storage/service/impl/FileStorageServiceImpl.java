package it.eng.tz.urbamid.wrappergeo.storage.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.urbamid.wrappergeo.exception.WrapperGeoServiceException;
import it.eng.tz.urbamid.wrappergeo.exception.WrappergeoStorageException;
import it.eng.tz.urbamid.wrappergeo.storage.service.FileStorageService;
import it.eng.tz.urbamid.wrappergeo.storage.service.WrapperPathService;

/**
 * Service per lo storage dei file.
 */
@Service
public class FileStorageServiceImpl implements FileStorageService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

//	private static final String PATTERN_PUNTO_ZIP = ".*\\.zip";
	
	/**
	 * Service per il recupero dei path necessari all'esecuzione degli script shp2pgsql e psql
	 */
	public WrapperPathService pathService;
	
	/**
	 * Costruttore.
	 * 
	 * @param pathService {@link ImportWrappergeoPathService}
	 */
	public FileStorageServiceImpl(
			WrapperPathService pathService) {
		Assert.notNull(pathService, "WrapperPathService MUST not be null but don't panic!");
		this.pathService = pathService;
	}
	
	@Override
	public void store(MultipartFile file) throws WrapperGeoServiceException {
		
		Path path = Paths.get(this.pathService.jobDataDirectory());
		try {
			if(logger.isDebugEnabled()) {
				logger.debug("Effettuo l'upload del file {} nella cartella {}.", file.getOriginalFilename(),path.getRoot());
			}
			Files.copy( 
					file.getInputStream(), 
					path.resolve(file.getOriginalFilename()), 
					StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ioe) {
			logger.error("Errore di I/O nella copia del file {} all'interno della cartella {}",
					file.getOriginalFilename());
			throw new WrappergeoStorageException(ioe);
		}
	}

	@Override
	public void store(MultipartFile[] files) throws WrapperGeoServiceException {
		if( files != null && files.length > 0 ) {
			for (MultipartFile file : files) {
				this.store(file);
			}
		}
	}
	
	
	
	@Override
	public boolean checkDuplicate(MultipartFile file) throws WrapperGeoServiceException {
		
		Path path = Paths.get(this.pathService.jobDataDirectory());
		try {
			if(logger.isDebugEnabled()) {
				logger.debug("Effettuo l'upload del file {} nella cartella {}.", file.getOriginalFilename(),path.getRoot());
			}
			
			File fileC = new File(this.pathService.jobDataDirectory()+File.separator+(file.getOriginalFilename()) );
			if( fileC.isFile() ) 
				return true;
			
			return false;
		} catch (WrapperGeoServiceException e) {
			logger.error("Errore di I/O nella copia del file {} all'interno della cartella {}",
					file.getOriginalFilename());
			throw new WrappergeoStorageException(e);
		}
	}
	
	@Override
	public boolean checkDuplicate(MultipartFile[] files) throws WrapperGeoServiceException {
		if( files != null && files.length > 0 ) {
			for (MultipartFile file : files) {
				if ( this.checkDuplicate(file) )
					return true;
			}
		}
		return false;
	}
	
	private boolean rimuoviFile(File file) {
		boolean esito = false;
		if(logger.isDebugEnabled()) {
			logger.debug("Rimuovo il file {}.", file.getName());
		}
		try {
			Files.delete(file.toPath());
			if(logger.isDebugEnabled()) {
				logger.debug("Il file {} è stato rimosso con successo.", file.getName());
			}
			esito = true;
		} catch( IOException ioe ){
			logger.error("Impossibile rimuovere il file {}.", file.getName(), ioe);
		}
		return esito;
	}

	private Path getPathFromPathString(String pathString, String... more) throws WrappergeoStorageException {
		Path path = null;
		try {
			path = Paths.get(pathString, more);
		} catch( InvalidPathException ipe ) {
			if( logger.isErrorEnabled() )
				logger.error("Il path {} non risulta essere valido.", pathString);
			throw new WrappergeoStorageException(ipe);
		}
		return path;
	}
	
	
	
}
