package it.eng.tz.urbamid.catasto.export.service.impl;

import java.io.File;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import it.eng.tz.urbamid.catasto.exception.CatastoServiceException;
import it.eng.tz.urbamid.catasto.export.service.ExportCatastoPathService;
import it.eng.tz.urbamid.catasto.persistence.repositories.ParametroGeokettleRepository;

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
public class ExportCatastoPathServiceImpl implements ExportCatastoPathService {
	
	/**
	 * Logger
	 */
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	private static final String FORMATO_DATA_FILE_DI_LOG = "yyyy_MM_dd";
	private final SimpleDateFormat fileLogDateFormatter;
	
	@Autowired
	private Environment env;
	
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
	public ExportCatastoPathServiceImpl(ParametroGeokettleRepository repositoryParametri) {
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
		String rootPath = env.getProperty("catasto.export.base.path");
		Assert.hasLength(rootPath, "Root path MUST not be empty!");
		return rootPath;
	}

	@Override
	public String exportShapefileDirectory( ) throws CatastoServiceException {
		LOG.debug("Recupero della directory dove saranno presenti gli shapefile.");
		File file = Paths.get(
							this.rootPath()
						).toFile();
		Assert.isTrue(file.exists(), "SHAPEFILE directory MUST exists!");
		return file.getAbsolutePath();
	}
}
