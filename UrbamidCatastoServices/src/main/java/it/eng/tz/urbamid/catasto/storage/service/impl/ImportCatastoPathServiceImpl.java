package it.eng.tz.urbamid.catasto.storage.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import it.eng.tz.urbamid.catasto.exception.CatastoServiceException;
import it.eng.tz.urbamid.catasto.persistence.repositories.ParametroGeokettleRepository;
import it.eng.tz.urbamid.catasto.storage.service.ImportCatastoPathService;
import it.eng.tz.urbamid.catasto.storage.util.StorageFolder;
import it.eng.tz.urbamid.catasto.util.ImportType;

/**
 * Service che aiuta la navigazione della struttura delle directory sulla quale lavora la procedura di import.
 * Nello specifico la struttura delle directory è illustrata nel seguito:
 * 
<pre>
                                                                                                                         
                                       +----------------+                                              -                 
                 +---------------------|   ROOT PATH    |---------------------+                                          
                 |                     +----------------+                     |                                 -        
                 |                              |                             |                                          
                 |                              |                             |                                          
   +--------------------------+   +--------------------------+  +--------------------------+                             
   |           DATI           |   |    GEOKETTLE_JOB_FILE    |  |    PYTHON_WORKSPACE      |                             
   |                          |   |                          |  |                          |                             
   |     File elaborazione    |   | Kettle job/trasformate   |  |workspace progetto python |                             
   +--------------------------+   +--------------------------+  +--------------------------+                             
                 |                                                                                                       
                 |                                                                                                       
                 |-----------------------------+                                                                         
                 |                             |                                                 -                       
                 |                             |                                                                         
                 |                             |                                                                         
   +--------------------------+  +--------------------------+                                                            
   |       AGGIORNAMENTO      |  |         ATTUALITA        |                                                            
   |                          |  |                          |                                                            
   |  Dati per aggiornamento  |  |    Dati per attualita    |                                                            
   +--------------------------+  +--------------------------+                                                            
                                                                                                                         
</pre>
 * A partire da una root path ci sono le cartelle:<br>
 * <ol>
 *  <li><b>DATI</b>: è la direcotory dove sono presenti i dati sulla quale agisce la procedura di import.</li>
 *  <li><b>GEOKETTLE_JOB_FILE</b>: è la directory dove sono presenti tutti i file dei job (.kjb) e delle trasformate per GeoKettle</li>
 *  <li><b>PYTHON_WORKSPACE</b>: è la directory dove risiede il workspace del progetto python.</li>
 *  <li><b>SCRIPT</b>: è la directory dove risiedono script sh di utilita' varia (non disegnata sopra).</li>
 *  <li><b>LOG</b>: è la directory dove risiedono i file di log della procedura di import (non disegnata sopra).</li>
 * </ol>
 * La cartella DATI, a sua volta è divisa in due sotto-cartelle che racchiudono i dati per gli import di AGGIORNAMENTO
 * ed ATTUALITA. Ognuna di queste due cartelle, a sua volta, avrà una serie di sotto cartelle:</br>
 * <ol>
 * 	<li><b>CARTOGRAFIA</b>: directory dove viene fatto l'upload dei file zip con all'interno i CXF.</li>
 *  <li><b>FABBRICATI</b>: directory dove viene fatto l'upload dei file zip con i dati dei fabbricati.</li>
 *  <li><b>MANAGE</b>: directory contenente temp-folder di gestione.</li>
 *  <li><b>PLANIMETRIE</b>: directory dove risiederanno le planimetrie.</li>
 *  <li><b>SHAPEFILE</b>: directory dove verranno salvati gli shapefile prodotti.</li>
 *  <li><b>SQL_SCRIPT</b>: directory dove verranno salvati gli script SQL prodotti da shp2pgsql.</li>
 *  <li><b>TERRENI</b>: directory dove viene fatto l'upload dei file zip con i dati dei terreni.</li>
 * </ol>
 */
@Service
public class ImportCatastoPathServiceImpl implements ImportCatastoPathService {
	
	/**
	 * Logger
	 */
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	private static final String CASSINI_SOLDNER_FOLDER_NAME = "Cassini-Soldner";
	private static final String GAUSS_BOAGA_FOLDER_NAME = "Gauss-Boaga";
	private static final String FORMATO_NOME_FILE_DI_LOG = "LOG_PID_%s_%s";
	private static final String FORMATO_DATA_FILE_DI_LOG = "yyyy_MM_dd";
	private final SimpleDateFormat fileLogDateFormatter;
	
	/**
	 * Mappa dei parametri recuperati dal database
	 */
	private Map<String, String> parametri = new HashMap<>();
	
	/**
	 * Enumerazione che racchiude alcuni dei nome_parametro utili a costruire
	 * i path.
	 */
	private enum ChiaveParametro {
		ROOT_PATH,
		GEOKETTLE_INSTALLATION_PATH,
		PYTHON_SRC,
		DIRECTORY_MANAGE_PATH,
		SUB_DIRECTORY_WORK_FILE
	}
	
	/**
	 * Repository JPA per il recupero dei parametri geokettle.
	 */
	private final ParametroGeokettleRepository repositoryParametri;
	
	/**
	 * Costruttore.
	 * 
	 * @param repositoryParametri è il repository JPA per il recupero dei dati nella tabella parametri_geokettle
	 */
	public ImportCatastoPathServiceImpl(ParametroGeokettleRepository repositoryParametri) {
		Assert.notNull(repositoryParametri, "ParametroGeokettleRepository must not be null but don't panic!");
		this.repositoryParametri = repositoryParametri;
		fileLogDateFormatter = new SimpleDateFormat(FORMATO_DATA_FILE_DI_LOG);
	}
	
	/**
	 * Metodo di init che serve per popolare la mappa dei parametri chiave/valore tramite le
	 * informazioni conservate sul database nella tabella parametri_geokettle.
	 * In questa tabella sono conservati alcuni dei path e/o subpath sulla che utilizza GeoKettle
	 * per le sue elaborazioni. Vengono qui sfruttate gli stessi path per coerenza (senza dover
	 * ridefinire tutto di nuovo sulle application properties).
	 */
	@PostConstruct
	public void init() {
		//TODO implementare un meccanismo di scadenza valori? Oppure possiamo assumere che siano sempre uguali?
		//credo di più la seconda cosa... cambiare un parametro GeoKettle porterebbe ad un ulteriore cambiamento
		//anche dei JOB/TRASFORMATE correlati.
		this.repositoryParametri.findAll().forEach( 
				parametro -> this.parametri.put(parametro.getChiave(), parametro.getValore()));
		Assert.isTrue(parametri.size() > 0, "La mappa dei parametri recuperati dal database per ImportCatastoPathService e' vuota!");
	}
	
	@Override
	public String rootPath() throws CatastoServiceException {
		LOG.debug("Recupero della root path.");
		String rootPath = this.parametri.get(ChiaveParametro.ROOT_PATH.name());
		Assert.hasLength(rootPath, "Root path MUST not be empty!");
		return rootPath;
	}

	@Override
	public String pathInstallazioneGeoKettle() throws CatastoServiceException {
		LOG.debug("Recupero della directory di installazione di geokettle.");
		String rootPath = this.parametri.get(ChiaveParametro.GEOKETTLE_INSTALLATION_PATH.name());
		Assert.hasLength(rootPath, "Geokettle installation path MUST not be empty!");
		return rootPath;
	}

	@Override
	public String pathJobGeoKettle() throws CatastoServiceException {
		LOG.debug("Recupero della directory dove sono presenti tutti i job geokettle.");
		File file = Paths.get(
				this.rootPath(), 
				StorageFolder.GEOKETTLE_JOB_FILE.name())
					.toFile();
		Assert.isTrue(file.exists(), "Geokettle job directory MUST exists!");
		return file.getAbsolutePath();
	}
	
	@Override
	public String importFolderDirectory(ImportType importType) throws CatastoServiceException {
		LOG.debug("Recupero della directory utilizzata per l'import del catasto.");
		File file = Paths.get(
				this.rootPath(), 
				StorageFolder.DATI.name(), 
				importType.name())
					.toFile();
		Assert.isTrue(file.exists(), "IMPORT directory MUST exists!");
		return file.getAbsolutePath();
	}

	@Override
	public String sqlScriptDirectory(ImportType importType) throws CatastoServiceException {
		LOG.debug("Recupero della directory dove sono presenti gli script SQL.");
		File file = Paths.get(
				this.rootPath(), 
				StorageFolder.DATI.name(), 
				importType.name(), 
				StorageFolder.SQL_SCRIPT.name())
					.toFile();
		Assert.isTrue(file.exists(), "SQL_SCRIPT directory MUST exists!");
		return file.getAbsolutePath();
	}
	
	@Override
	public String sqlScriptUpdateDirectory(ImportType importType) throws CatastoServiceException {
		LOG.debug("Recupero della directory dove sono presenti gli script di fine aggiornamento dati catastali.");
		File file = Paths.get(
				this.rootPath(), 
				StorageFolder.DATI.name(), 
				importType.name(), 
				StorageFolder.SQL_UPDATE_SCRIPT.name())
					.toFile();
		Assert.isTrue(file.exists(), "SQL_UPDATE_SCRIPT directory MUST exists!");
		return file.getAbsolutePath();
	}

	@Override
	public String cassiniSoldnerWorkDirectory(ImportType importType) throws CatastoServiceException {
		LOG.debug("Recupero della directory dove sono presenti i file .CXF di tipo Cassini Soldner che verranno elaborati da python.");
		File file = Paths.get(
				this.rootPath(), 
				StorageFolder.DATI.name(), 
				importType.name(), 
				this.parametri.get(ChiaveParametro.DIRECTORY_MANAGE_PATH.name()),
				this.parametri.get(ChiaveParametro.SUB_DIRECTORY_WORK_FILE.name()),
				CASSINI_SOLDNER_FOLDER_NAME)
					.toFile();
		Assert.isTrue(file.exists(), "Cassini-Soldner directory MUST exists!");
		return file.getAbsolutePath();
	}

	@Override
	public String gaussBoagaWorkDirectory(ImportType importType) throws CatastoServiceException {
		LOG.debug("Recupero della directory dove sono presenti i file .CXF di tipo Gauss Boaga che verranno elaborati da python.");
		File file = Paths.get(
				this.rootPath(), 
				StorageFolder.DATI.name(), 
				importType.name(), 
				this.parametri.get(ChiaveParametro.DIRECTORY_MANAGE_PATH.name()),
				this.parametri.get(ChiaveParametro.SUB_DIRECTORY_WORK_FILE.name()),
				GAUSS_BOAGA_FOLDER_NAME)
					.toFile();
		Assert.isTrue(file.exists(), "Gauss-Boaga directory MUST exists!");
		return file.getAbsolutePath();
	}

	@Override
	public String scriptShDirectory() throws CatastoServiceException {
		File file = Paths.get(
				this.rootPath(), 
				StorageFolder.SCRIPT.name())
					.toFile();
		Assert.isTrue(file.exists(), "SCRIPT directory MUST exists!");
		return file.getAbsolutePath();
	}

	public String jobDataDirectory(ImportType importType) throws CatastoServiceException {
		File file = Paths.get(
				this.rootPath(), 
				StorageFolder.DATI.name(), 
				importType.name())
					.toFile();
		Assert.isTrue(file.exists(), "Job data directory MUST exists!");
		return file.getAbsolutePath();
	}
	
	@Override
	public String datiTerreniDirectory(ImportType importType) throws CatastoServiceException {
		File file = Paths.get(
				this.rootPath(), 
				StorageFolder.DATI.name(), 
				importType.name(),
				StorageFolder.TERRENI.name())
					.toFile();
		Assert.isTrue(file.exists(), "TERRENI directory MUST exists!");
		return file.getAbsolutePath();
	}

	@Override
	public String datiFabbricatiDirectory(ImportType importType) throws CatastoServiceException {
		File file = Paths.get(
				this.rootPath(), 
				StorageFolder.DATI.name(), 
				importType.name(),
				StorageFolder.FABBRICATI.name())
					.toFile();
		Assert.isTrue(file.exists(), "FABBRICATI directory MUST exists!");
		return file.getAbsolutePath();
	}

	@Override
	public String datiCartografiaDirectory(ImportType importType) throws CatastoServiceException {
		File file = Paths.get(
				this.rootPath(), 
				StorageFolder.DATI.name(), 
				importType.name(),
				StorageFolder.CARTOGRAFIA.name())
					.toFile();
		Assert.isTrue(file.exists(), "CARTOGRAFIA directory MUST exists!");
		return file.getAbsolutePath();
	}
	
	@Override
	public String datiPlanimetrieDirectory(ImportType importType) throws CatastoServiceException {
		File file = Paths.get(
				this.rootPath(), 
				StorageFolder.DATI.name(), 
				importType.name(),
				StorageFolder.PLANIMETRIE.name())
					.toFile();
		Assert.isTrue(file.exists(), "PLANIMETRIE directory MUST exists!");
		return file.getAbsolutePath();
	}

	@Override
	public String pythonScriptCxfToShape() throws CatastoServiceException {
		File file = Paths.get(
				this.parametri.get(ChiaveParametro.PYTHON_SRC.name()), 
				"cxfToShape.py")
				.toFile();
		Assert.isTrue(file.exists(), "cxfToShape.py MUST exist in directory " + this.parametri.get(ChiaveParametro.PYTHON_SRC.name()));
		return file.getAbsolutePath();
	}

	@Override
	public String shapefileOutputDirectory(ImportType importType) throws CatastoServiceException {
		File file = Paths.get(
				this.rootPath(), 
				StorageFolder.DATI.name(), 
				importType.name(),
				StorageFolder.SHAPEFILE.name())
					.toFile();
		Assert.isTrue(file.exists(), "SHAPEFILE OUTPUT DIRECTORY MUST exists!"); 
		return file.getAbsolutePath() + "/"; //It's a really bud piece hot! 
	}

	@Override
	public Optional<File> fileLogProceduraImport(ImportType importType, Long jobId) {
		try {
			LOG.debug("Recupero del file dove viene scritto il log della procedura di import avviata dal batch con id {}", jobId);
			String filename = String.format(FORMATO_NOME_FILE_DI_LOG, jobId, fileLogDateFormatter.format(new Date()));
			File file = Paths.get(
					this.rootPath(), 
					StorageFolder.LOG.name(), 
					filename)
					.toFile();
			if(!file.exists() && file.createNewFile()) {
				LOG.debug("Creato con successo un nuovo file di log con nome {}.", filename);
			}
			return Optional.of(file); 
		} catch (IOException | CatastoServiceException e) {
			LOG.error("Si è verificato una eccezione durante la creazione del file di log per la procedura!");
			return Optional.empty();
		}
	}
	
	@Override
	public String shapefileDirectory(ImportType importType) throws CatastoServiceException {
		LOG.debug("Recupero della directory dove sono presenti gli shapefile.");
		File file = Paths.get(
				this.rootPath(), 
				StorageFolder.DATI.name(), 
				importType.name(), 
				StorageFolder.SHAPEFILE.name())
					.toFile();
		Assert.isTrue(file.exists(), "SHAPEFILE directory MUST exists!");
		return file.getAbsolutePath();
	}

}
