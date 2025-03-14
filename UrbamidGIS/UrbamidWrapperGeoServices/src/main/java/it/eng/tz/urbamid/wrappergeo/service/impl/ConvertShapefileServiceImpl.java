package it.eng.tz.urbamid.wrappergeo.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.opengis.referencing.FactoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import it.eng.tz.urbamid.wrappergeo.exception.WrapperGeoServiceException;
import it.eng.tz.urbamid.wrappergeo.exception.WrappergeoStorageException;
import it.eng.tz.urbamid.wrappergeo.psql.service.PsqlService;
import it.eng.tz.urbamid.wrappergeo.service.AbstractShapefileService;
import it.eng.tz.urbamid.wrappergeo.service.ConvertShapefileService;
import it.eng.tz.urbamid.wrappergeo.shp2pgsql.service.Shp2PgsqlService;
import it.eng.tz.urbamid.wrappergeo.storage.service.WrapperPathService;
import it.eng.tz.urbamid.wrappergeo.storage.util.StorageFolder;
import it.eng.tz.urbamid.wrappergeo.util.UnZipUtils;

@Service
public class ConvertShapefileServiceImpl extends AbstractShapefileService implements ConvertShapefileService {
	
	/**
	 * Logger
	 */
	private final Logger LOG = LoggerFactory.getLogger(this.getClass().getName());
	
	/**
	 * Service per la conversione di uno shapefile in script SQL
	 */
	@Autowired
	public Shp2PgsqlService shp2PgsqlService;

	/**
	 * Service per l'esecuzione/import nel database di uno script SQL
	 */
	@Autowired
	public PsqlService psqlService;

	/**
	 * Service per il recupero dei path necessari all'esecuzione degli script shp2pgsql e psql
	 */
	public WrapperPathService pathService;
	
	/**
	 * Costruttore.
	 * 
	 * @param psqlService {@link PsqlService}
	 * @param shp2PgsqlService {@link Shp2PgsqlService}
	 * @param pathService {@link ImportWrappergeoPathService}
	 */
	public ConvertShapefileServiceImpl(
			Shp2PgsqlService shp2PgsqlService, PsqlService psqlService, WrapperPathService pathService) {
		Assert.notNull(psqlService, "Shp2PgsqlService MUST not be null but don't panic!");
		Assert.notNull(shp2PgsqlService, "PsqlService MUST not be null but don't panic!");
		Assert.notNull(pathService, "WrapperPathService MUST not be null but don't panic!");
		this.psqlService = psqlService;
		this.shp2PgsqlService = shp2PgsqlService;
		this.pathService = pathService;
	}
	
	@Override
	public String importaShapefile(String nameShape) throws WrapperGeoServiceException {
		
		String logUnzipShape = unzipShapeFiles(nameShape);
		String logShp2Pgsql = creaScriptSQLDagliShapefile(nameShape);
		String logPsql = importaScriptSQLNelDatabase(nameShape);
		String logClearFolder = clearFolder(nameShape); 
		return new StringBuilder(logUnzipShape).append(logShp2Pgsql).append(logPsql).append(logClearFolder).toString();
	}
	
	/**
	 * Metodo privato che crea gli script SQL a partire dagli shapefile in una determinata cartella
	 * attraverso shp2pgsql.
	 * 
	 * @param importType è il tipo di import (AGGIORNAMENTO/ATTUALITA)
	 * 
	 * @return una stringa con le informazioni di log
	 * 
	 * @throws CatastoServiceException 
	 */
	private String unzipShapeFiles(String nameZipFile) throws WrapperGeoServiceException {
		
		Path rootDirectory = Paths.get(this.pathService.jobDataDirectory());
		StringBuilder informazioniDiLog = new StringBuilder();
		
		if(LOG.isDebugEnabled()) {
			LOG.debug("Prelevo lo zip file presente nella cartella {}.", rootDirectory.toFile().getAbsolutePath());
		}
		
		this.checkParams(nameZipFile);
		
		try(
				//sfrutto l'autoclosable degli stream 
				Stream<Path> stream = Files.walk(rootDirectory)
				) {
			
			//ricavo la lista degli shapefile da importare
			List<File> listaZipfile = stream.map( Path::toFile )
					.filter( File::isFile )
					.filter( f -> f.getName().matches(nameZipFile + PATTERN_PUNTO_ZIP))
						.collect( Collectors.toList());

			informazioniDiLog.append("Viene recuperato e lavorato il file " + nameZipFile + PATTERN_PUNTO_ZIP);
			if (!listaZipfile.isEmpty()) {
				/**Estraggo lo Zip file contenente lo Shape file**/
				for (File zipfile : listaZipfile) {
					File folder = new File(rootDirectory+File.separator+nameZipFile);
					if (Files.exists(folder.toPath())) {
						FileUtils.cleanDirectory(folder); 
					} else {
						folder.mkdir();
					}
				    Collection<File> unzippedFile = UnZipUtils.unzippedFiles( zipfile, this.pathService.jobDataShapeDirectory(nameZipFile));
				}
				File folder = new File(rootDirectory + File.separator + nameZipFile + File.separator + StorageFolder.SQL_SCRIPT);
			    boolean bool = folder.mkdirs();
			} else {
				LOG.error("CONVERTER WRAPPERGEO SERVICE --> Non e' presente il file {} per convertirlo in shape.", nameZipFile + PATTERN_PUNTO_ZIP);
				throw new WrappergeoStorageException("Non e' presente il file "+nameZipFile + PATTERN_PUNTO_ZIP+" per convertirlo in shape.");
			}
			
			informazioniDiLog.append("E' stato estratto il file " + nameZipFile + PATTERN_PUNTO_ZIP + " al path " + this.pathService.jobDataShapeDirectory(nameZipFile));
			return informazioniDiLog.toString();
		} catch (IOException ioe) {
			
			LOG.error("CONVERTER WRAPPERGEO SERVICE --> Non e' stato possibile estrarre il file {}.", nameZipFile + PATTERN_PUNTO_ZIP);
			throw new WrappergeoStorageException("Non e' stato possibile estrarre il file "+nameZipFile + PATTERN_PUNTO_ZIP+".");
		}
	}
	
	/**
	 * Metodo privato che crea gli script SQL a partire dagli shapefile in una determinata cartella
	 * attraverso shp2pgsql.
	 * 
	 * @param importType è il tipo di import (AGGIORNAMENTO/ATTUALITA)
	 * 
	 * @return una stringa con le informazioni di log
	 * @throws FactoryException 
	 * 
	 * @throws CatastoServiceException 
	 */
	private String creaScriptSQLDagliShapefile(String nameShapeFile) throws WrapperGeoServiceException {
		
		
		Path rootShapeDirectory = Paths.get(this.pathService.jobDataShapeDirectory(nameShapeFile));
		StringBuilder informazioniDiLog = new StringBuilder();
		
		if(LOG.isDebugEnabled()) {
			LOG.debug("Prelevo gli shapefile presenti nella cartella {}.", rootShapeDirectory.toFile().getAbsolutePath());
		}
		
		try(
				//sfrutto l'autoclosable degli stream 
				Stream<Path> stream = Files.walk(rootShapeDirectory)
				) {
			
			//ricavo la lista degli shapefile da importare
			List<File> listaShapefile = stream.map( Path::toFile )
					.filter( File::isFile )
					.filter( f -> f.getName().matches(PATTERN_PUNTO_SHP))
						.collect( Collectors.toList());
			
			if(LOG.isDebugEnabled()) {
				LOG.debug("Sono presenti {} shapefile da importare tramite il comando shp2pgsql.", listaShapefile.size());
			}

			if (listaShapefile!=null) {
				
				/**WRITE SHAPE TO SCRIPT_SQL**/
				for (File shapefile : listaShapefile) {
					String tableName = "u_geo_"+(nameShapeFile.toLowerCase());
					String log = shp2PgsqlService.convertiShapefileInScriptSQL(
							nameShapeFile,
							shapefile, 
							this.DEFAULT_DATABASE_SCHEMA_IMPORT,
							tableName, 
							this.DEFAULT_GEOMETRY_COLUMN_NAME);
					informazioniDiLog.append(log);
				}
				informazioniDiLog.append("Sono stati lavorati gli SHAPE_FILE e generati gli SCRIPT SQL");
				
				/**UPDATE GEOM FOR EPSG:4326**/
				for (File shapefile : listaShapefile) {
					String tableName = "u_geo_"+(nameShapeFile.toLowerCase());
					String log = shp2PgsqlService.reproject( nameShapeFile,
																shapefile, 
																this.DEFAULT_DATABASE_SCHEMA_IMPORT,
																tableName, 
																this.DEFAULT_GEOMETRY_COLUMN_NAME);
					informazioniDiLog.append(log);
				}
				informazioniDiLog.append("La tabella creata dagli SHAPE_FILE è stata riproiettata in EPSG:4326.");
				
			} else {
				LOG.error("CONVERTER WRAPPERGEO SERVICE --> Nnon è presente il file {} per convertirlo in tabella.", nameShapeFile);
				throw new WrappergeoStorageException("Nnon è presente il file "+nameShapeFile+" per convertirlo in tabella");
			}
			
			informazioniDiLog.append("E' stato convertito il file " + nameShapeFile + PATTERN_PUNTO_SHP + " in script SQL UPDATE.");
			return informazioniDiLog.toString();
		} catch (IOException ioe) {
			LOG.error("CONVERTER WRAPPERGEO SERVICE --> Si è verificato una eccezione di I/O nel convertire lo SHAPE_FILE in SCRIPT SQL.");
			throw new WrappergeoStorageException("Si è verificato una eccezione di I/O nel convertire lo SHAPE_FILE in SCRIPT SQL.");
		}
		
	}
	
	
	/**
	 * Metodo privato che importa tutti gli script SQL nel database tramite psql.
	 * 
	 * @param importType è il tipo di import (AGGIORNAMENTO/ATTUALITA)
	 * 
	 * @return una stringa con le informazioni di log
	 * 
	 * @throws CatastoServiceException 
	 */
	private String importaScriptSQLNelDatabase(String nameShape) throws WrapperGeoServiceException {
		
		Path rootDirectorySQLScriptFile = Paths.get(this.pathService.sqlScriptDirectory(nameShape));
		StringBuilder informazioniDiLog = new StringBuilder();
		
		if(LOG.isDebugEnabled()) {
			LOG.debug("Prelevo gli script SQL presenti nella cartella {}.", rootDirectorySQLScriptFile.toFile().getAbsolutePath());
		}

		try(
				//sfrutto l'autoclosable degli stream 
				Stream<Path> stream = Files.walk(rootDirectorySQLScriptFile)
				) {
			
			//ricavo la lista degli shapefile da importare
			List<File> listaSQLScriptFile = stream.map( Path::toFile )
					.filter( File::isFile )
					.filter( f -> f.getName().matches(PATTERN_PUNTO_SQL))
						.collect( Collectors.toList());
			
			if (!listaSQLScriptFile.isEmpty()) {
				/**Elaboro gli script SQL**/
				for (File scriptSQL : listaSQLScriptFile) {
					String log = psqlService.eseguiScriptSQL(scriptSQL);
					informazioniDiLog.append(log);
				}
			} else {
				LOG.error("CONVERTER WRAPPERGEO SERVICE --> Non sono presenti file {}{} da eseguire.", nameShape, PATTERN_PUNTO_SQL);
				throw new WrappergeoStorageException("Non sono presenti file "+nameShape+PATTERN_PUNTO_SQL+" da eseguire.");
			}
			informazioniDiLog.append("Sono stati elaborati "+listaSQLScriptFile.size()+" script SQL.");
			return informazioniDiLog.toString();
		} catch (IOException ioe) {
			LOG.error("CONVERTER WRAPPERGEO SERVICE --> Si è verificato una eccezione di I/O nell'eseguire gli script SQL.");
			throw new WrappergeoStorageException("Si è verificato una eccezione di I/O nell'eseguire gli script SQL.");
		}
	}
	
	/**
	 * Metodo privato che importa tutti gli script SQL nel database tramite psql.
	 * 
	 * @param importType è il tipo di import (AGGIORNAMENTO/ATTUALITA)
	 * 
	 * @return una stringa con le informazioni di log
	 * 
	 * @throws CatastoServiceException 
	 */
	private String clearFolder(String nameZipFile) throws WrapperGeoServiceException {
		
		Path rootDirectory = Paths.get(this.pathService.jobDataDirectory());
		StringBuilder informazioniDiLog = new StringBuilder();
		
		if(LOG.isDebugEnabled()) {
			LOG.debug("Prelevo lo zip file presente nella cartella {}.", rootDirectory.toFile().getAbsolutePath());
		}

		try(
				//sfrutto l'autoclosable degli stream 
				Stream<Path> stream = Files.walk(rootDirectory)
				) {
			
			//ricavo la lista degli shapefile da importare
			List<File> listaZipfile = stream.map( Path::toFile )
					.filter( File::isFile )
					.filter( f -> f.getName().matches(nameZipFile + PATTERN_PUNTO_ZIP))
						.collect( Collectors.toList());
			
			informazioniDiLog.append("Viene recuperato ed eliminato il file " + nameZipFile + PATTERN_PUNTO_ZIP);
			if (!listaZipfile.isEmpty()) {
				/**Estraggo lo Zip file contenente lo Shape file**/
				for (File zipFile : listaZipfile) {
					if(zipFile.delete())  { 
						informazioniDiLog.append("Viene eliminato il file " + nameZipFile + PATTERN_PUNTO_ZIP);
			        } else { 
			        	informazioniDiLog.append("Non viene eliminato il file " + nameZipFile + PATTERN_PUNTO_ZIP);
			        } 
				}
			} else {
				LOG.error("CONVERTER WRAPPERGEO SERVICE --> Nessun file {} presente in {}.", PATTERN_PUNTO_ZIP, rootDirectory.toFile().getAbsolutePath());
				throw new WrappergeoStorageException("Nessun file "+ PATTERN_PUNTO_ZIP +" presente in '" + rootDirectory.toFile().getAbsolutePath() + "'.");
			}
			
			informazioniDiLog.append("E' stato eliminato il file " + nameZipFile + PATTERN_PUNTO_ZIP + " al path " + this.pathService.jobDataDirectory());
			return informazioniDiLog.toString();
		} catch (IOException ioe) {
			if( LOG.isErrorEnabled() )
				LOG.error("Si è verificato una eccezione di I/O nel recupero dei file zip al path.");
			throw new WrapperGeoServiceException(ioe);
		}
	}
	
	/**
	 * Metodo privato che valida i file caricati
	 * 
	 * @param importType
	 * @param folder
	 */
	private void checkParams(String nameShapeFile) throws WrapperGeoServiceException {
		if (nameShapeFile!=null) {
			/**Controllo se i file caricati sono vuoti**/
			if (nameShapeFile.isEmpty()) {
				LOG.error("CONVERTER WRAPPERGEO SERVICE -- nome dello shape file {} non e' un file valido.", nameShapeFile);
				throw new WrapperGeoServiceException("Nome dello shape file non e' un nome valido.");
			}
		} else {
			LOG.error("CONVERTER WRAPPERGEO SERVICE -- Valore errato per shapeFile: {}.", nameShapeFile);
			throw new WrapperGeoServiceException("Valore errato per files");
		}
	}
}
