package it.eng.tz.urbamid.catasto.storeimport.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.joda.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import it.eng.tz.urbamid.catasto.exception.CatastoServiceException;
import it.eng.tz.urbamid.catasto.exception.CatastoStorageException;
import it.eng.tz.urbamid.catasto.psql.service.PsqlService;
import it.eng.tz.urbamid.catasto.storage.service.ImportCatastoPathService;
import it.eng.tz.urbamid.catasto.storeimport.service.StoreImportService;
import it.eng.tz.urbamid.catasto.util.ImportType;
import it.eng.tz.urbamid.catasto.util.ZipUtility;

@Service
public class StoreImportServiceImpl extends AbstractStoreImportService 	implements StoreImportService {
	
	/**
	 * Logger
	 */
	private final Logger LOG = LoggerFactory.getLogger(this.getClass().getName());
	
	/**
	 * Service per il recupero dei path necessari all'esecuzione degli script shp2pgsql e psql
	 */
	private final ImportCatastoPathService pathService;

	/**
	 * Service per l'esecuzione/import nel database di uno script SQL
	 */
	private final PsqlService psqlService;
	
	/**
	 * Costruttore.
	 * 
	 * @param pathService {@link ImportCatastoPathService}
	 */
	public StoreImportServiceImpl( PsqlService psqlService, ImportCatastoPathService pathService ) {
		Assert.notNull(psqlService, "ImportCatastoPathService MUST not be null but don't panic!");
		Assert.notNull(pathService, "ImportCatastoPathService MUST not be null but don't panic!");
		this.psqlService = psqlService;
		this.pathService = pathService;
	}

	/**
	 * Metodo pubblico che setta lo SRID sulle geometrie delle tabelle catastali .
	 * TODO: rendere il metodo privato e invocarlo da un metodo pubblico
	 
	 * @param importType è il tipo di import (AGGIORNAMENTO/ATTUALITA)
	 * 
	 * @return una stringa con le informazioni di log
	 * 
	 * @throws CatastoServiceException 
	 */
	@Override
	public String updateGeometry(ImportType importType) throws CatastoServiceException {
		
		StringBuilder informazioniDiLog = new StringBuilder();
		
		Path rootDirectorySQLScriptUpdateFile = Paths.get(this.pathService.sqlScriptUpdateDirectory(importType));
	
		if(LOG.isDebugEnabled()) {
			LOG.debug("Prelevo gli script SQL presenti nella cartella {}.", rootDirectorySQLScriptUpdateFile.toFile().getAbsolutePath());
		}

		try(
			//sfrutto l'autoclosable degli stream 
			Stream<Path> stream = Files.walk(rootDirectorySQLScriptUpdateFile)
			) {
		
			//ricavo la lista degli shapefile da importare
			List<File> listaSQLScriptUpdateFile = stream.map( Path::toFile )
					.filter( File::isFile )
					.filter( f -> f.getName().matches(PATTERN_PUNTO_SQL))
						.collect( Collectors.toList());
			
			if(LOG.isDebugEnabled()) {
				LOG.debug("Sono presenti {} script SQL da importare tramite psql.", listaSQLScriptUpdateFile.size());
			}
			
			for (File scriptSQL : listaSQLScriptUpdateFile) {
				String log = this.psqlService.eseguiScriptSQL(scriptSQL);
				informazioniDiLog.append(log);
			}
			
			return informazioniDiLog.toString();
		} catch (IOException ioe) {
			if( LOG.isErrorEnabled() )
				LOG.error("Si è verificato una eccezione nell'eseguire le query di update della geomertia.");
			throw new CatastoStorageException(ioe);
		}
	}
	
	@Override
	public String storeImportFile(ImportType importType) throws CatastoServiceException {
		
		String logStoreImport = storeImportFolder(importType);
		return new StringBuilder(logStoreImport).toString();
	}
	
	/**
	 * Metodo privato che effettua un zip degli shape files.
	 * 
	 * @param importType è il tipo di import (AGGIORNAMENTO/ATTUALITA)
	 * 
	 * @return una stringa con le informazioni di log
	 * 
	 * @throws CatastoServiceException 
	 */
	private String storeImportFolder(ImportType importType) throws CatastoServiceException {
		
		Path rootDirectoryImport = Paths.get(this.pathService.shapefileDirectory(importType));
		Path rootDirectoryStoreImport = Paths.get(this.pathService.importFolderDirectory(importType));
		StringBuilder informazioniDiLog = new StringBuilder();
		
		if(LOG.isDebugEnabled()) {
			LOG.debug("Prelevo gli shapefile presenti nella cartella {}.", rootDirectoryImport.toFile().getAbsolutePath());
		}

		try(
				//sfrutto l'autoclosable degli stream 
				Stream<Path> stream = Files.walk(rootDirectoryImport)
				) {
			
			//ricavo la lista degli shapefile da importare
			List<File> listaShapefile = stream.map( Path::toFile )
					.filter( File::isFile )
//					.filter( f -> f.getName().matches(PATTERN_PUNTO_SHP))
						.collect( Collectors.toList());
			
			if(LOG.isDebugEnabled()) {
				LOG.debug("Sono presenti {} shapefile da zippare.", listaShapefile.size());
			}
			
			//Creo il nome del file
			LocalDate currentDate = LocalDate.now();
			LocalTime currentTime = LocalTime.now();
			String zipShapeFiles = rootDirectoryStoreImport + "_SHAPES_" +  
										currentDate.getYear() + currentDate.getMonthValue() + currentDate.getDayOfMonth() + "_" + 
										(currentTime.getHourOfDay()<10?"0"+currentTime.getHourOfDay():currentTime.getHourOfDay()) + 
										(currentTime.getMinuteOfHour()<10?"0"+currentTime.getMinuteOfHour():currentTime.getMinuteOfHour()) + ".zip";
					
			//Inizio a zippare i file
			ZipUtility zipUtil = new ZipUtility();
			try {
				LOG.debug("################# Il path è il seguente: "+ zipShapeFiles);
				String log = zipUtil.zipShapeFiles(listaShapefile, zipShapeFiles);
				informazioniDiLog.append(log);
			} catch (Exception ex) {
				// 	some errors occurred
				ex.printStackTrace();
			}
			
			return informazioniDiLog.toString();
		} catch (IOException ioe) {
			if( LOG.isErrorEnabled() )
				LOG.error("Si è verificato una eccezione di I/O nel recupero dei file zip al path.");
			throw new CatastoServiceException(ioe);
		}
		
	}
	
	@Override
	public String deleteImportFile(ImportType importType) throws CatastoServiceException {
		
		String logDeleteImport = deleteImportFolder(importType);
		return new StringBuilder(logDeleteImport).toString();
	}
	
	/**
	 * Metodo privato che effettua la cancellazione dei files e folder utili per l'import del catasto.
	 * 
	 * @param importType è il tipo di import (AGGIORNAMENTO/ATTUALITA)
	 * 
	 * @return una stringa con le informazioni di log
	 * 
	 * @throws CatastoServiceException 
	 */
	private String deleteImportFolder(ImportType importType) throws CatastoServiceException {
		
		Path terreniDirectoryImport = Paths.get(this.pathService.datiTerreniDirectory(importType));
		Path fabbricatiDirectoryImport = Paths.get(this.pathService.datiFabbricatiDirectory(importType));
		Path cartografiaDirectoryImport = Paths.get(this.pathService.datiCartografiaDirectory(importType));
		
		StringBuilder informazioniDiLog = new StringBuilder();
		
		if(LOG.isDebugEnabled()) {
			LOG.debug("Eliminiamo file e folder presenti nella cartella {}.", terreniDirectoryImport.toFile().getAbsolutePath());
			LOG.debug("Eliminiamo file e folder presenti nella cartella {}.", fabbricatiDirectoryImport.toFile().getAbsolutePath());
			LOG.debug("Eliminiamo file e folder presenti nella cartella {}.", cartografiaDirectoryImport.toFile().getAbsolutePath());
		}

		/**CARTOGRAFIA**/
		try( Stream<Path> stream = Files.walk(cartografiaDirectoryImport) ) {
			//ricavo la lista degli shapefile da importare
			List<File> listFiles = stream.map( Path::toFile )
									.filter( File::isFile )
										.collect( Collectors.toList());
			for (File file : listFiles) {
				file.delete();
			}
			informazioniDiLog.append("Sono stati eliminati tutti i file della folder CARTOGRAFIA utilizzati per import catasto " + importType);
		} catch (IOException ioe) {
			if( LOG.isErrorEnabled() )
				LOG.error("Si è verificato una eccezione di I/O nel recupero dei file zip al path.");
			throw new CatastoServiceException(ioe);
		}
		
		/**TERRENI**/
		try( Stream<Path> stream = Files.walk(terreniDirectoryImport) ) {
			//ricavo la lista degli shapefile da importare
			List<File> listFiles = stream.map( Path::toFile )
									.filter( File::isFile )
										.collect( Collectors.toList());
			for (File file : listFiles) {
				file.delete();
			}
			informazioniDiLog.append("Sono stati eliminati tutti i file della folder TERRENI utilizzati per import catasto " + importType);
		} catch (IOException ioe) {
			if( LOG.isErrorEnabled() )
				LOG.error("Si è verificato una eccezione di I/O nel recupero dei file zip al path.");
			throw new CatastoServiceException(ioe);
		}
		
		/**FABBRICATI**/
		try( Stream<Path> stream = Files.walk(fabbricatiDirectoryImport) ) {
			//ricavo la lista degli shapefile da importare
			List<File> listFiles = stream.map( Path::toFile )
									.filter( File::isFile )
										.collect( Collectors.toList());
			for (File file : listFiles) {
				file.delete();
			}
			informazioniDiLog.append("Sono stati eliminati tutti i file della folder FABBRICATI utilizzati per import catasto " + importType);
		} catch (IOException ioe) {
			if( LOG.isErrorEnabled() )
				LOG.error("Si è verificato una eccezione di I/O nel recupero dei file zip al path.");
			throw new CatastoServiceException(ioe);
		}
		return informazioniDiLog.toString();
	}
	
	public ImportCatastoPathService getPathService() {
		return pathService;
	}
	
	public PsqlService getPsqlService() {
		return psqlService;
	}

}
