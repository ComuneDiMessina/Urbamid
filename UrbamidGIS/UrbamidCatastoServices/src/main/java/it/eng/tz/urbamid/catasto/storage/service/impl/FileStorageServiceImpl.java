package it.eng.tz.urbamid.catasto.storage.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.constraints.NotEmpty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.urbamid.catasto.exception.CatastoStorageException;
import it.eng.tz.urbamid.catasto.storage.service.FileStorageService;

/**
 * Service per lo storage dei file.
 */
@Service
public class FileStorageServiceImpl implements FileStorageService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	private static final String PATTERN_PUNTO_ZIP = ".*\\.zip";
	
	@Override
	public void store(MultipartFile file, String folder) throws CatastoStorageException {
		Path path = getPathFromPathString(folder);
		try {
			if(logger.isDebugEnabled()) {
				logger.debug("Effettuo l'upload del file {} nella cartella {}.", 
						file.getOriginalFilename(), folder);
			}
			Files.copy( 
					file.getInputStream(), 
					path.resolve(file.getOriginalFilename()), 
					StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ioe) {
			logger.error("Errore di I/O nella copia del file {} all'interno della cartella {}",
					file.getOriginalFilename(), folder);
			throw new CatastoStorageException(ioe);
		}
	}

	@Override
	public void store(MultipartFile[] files, String folder) throws CatastoStorageException {
		if( files != null && files.length > 0 ) {
			for (MultipartFile file : files) {
				//TODO magari vedere di restituire un oggetto che fornisca informazioni su quali file sono falliti e quali no?
				this.store(file, folder);
			}
		}
	}

	@Override
	public List<File> elencoFileZipInPath(@NotEmpty String pathString) throws CatastoStorageException {
		if(logger.isInfoEnabled())
			logger.info("Tentativo di recupero dei file in formato ZIP nella cartella {}.", pathString);
		try(Stream<Path> stream = Files.walk(this.getPathFromPathString(pathString))) {
			List<File> files = stream
				.map( Path::toFile )
					.filter( File::isFile )
					.filter( f -> f.getName().matches(PATTERN_PUNTO_ZIP))
						.collect(Collectors.toList());
			return
					( files != null && !files.isEmpty() ) ? files : Collections.emptyList();
		} catch (IOException ioe) {
			if( logger.isErrorEnabled() )
				logger.error("Si è verificato una eccezione di I/O nel recupero dei file zip al path {}.", pathString);
			throw new CatastoStorageException(ioe);
		}
	}

	@Override
	public List<String> rimuoviFileInCartella(@NotEmpty String folder, @NotEmpty String nomeFile) 
			throws CatastoStorageException {
		List<String> listaFileRimossi = null;
		if(logger.isInfoEnabled())
			logger.info("Tentativo di rimozione dei file in formato ZIP nella cartella {}.", folder);
		try(Stream<Path> stream = Files.walk(this.getPathFromPathString(folder, nomeFile))) {
			List<File> fileDaRimuovere = stream
				.map( Path::toFile )
					.filter( File::isFile )
						.collect(Collectors.toList());
			listaFileRimossi = new ArrayList<>();
			for (File file : fileDaRimuovere) {
				if( rimuoviFile(file) )
					listaFileRimossi.add(file.getName());
			}
		} catch (IOException ioe) {
			if( logger.isErrorEnabled() )
				logger.error("Si è verificato una eccezione di I/O nel recupero dei file zip al path {}.", folder);
			throw new CatastoStorageException(ioe);
		}
		return listaFileRimossi;
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

	private Path getPathFromPathString(String pathString, String... more) throws CatastoStorageException {
		Path path = null;
		try {
			path = Paths.get(pathString, more);
		} catch( InvalidPathException ipe ) {
			if( logger.isErrorEnabled() )
				logger.error("Il path {} non risulta essere valido.", pathString);
			throw new CatastoStorageException(ipe);
		}
		return path;
	}
	
	
	
}
