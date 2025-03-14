package it.eng.tz.urbamid.catasto.geokettle.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

import javax.validation.constraints.NotNull;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.PumpStreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import it.eng.tz.urbamid.catasto.exception.CatastoServiceException;
import it.eng.tz.urbamid.catasto.exception.GeoKettleDBParametersParserException;
import it.eng.tz.urbamid.catasto.exception.KettleJobExecutionException;
import it.eng.tz.urbamid.catasto.geokettle.service.GeoKettleService;
import it.eng.tz.urbamid.catasto.geokettle.util.GeoKettleJobImportParameter;
import it.eng.tz.urbamid.catasto.geokettle.util.GeoKettleJobType;
import it.eng.tz.urbamid.catasto.geokettle.util.KitchenLogLevel;
import it.eng.tz.urbamid.catasto.geokettle.util.KitchenSHCommandLineBuilder;
import it.eng.tz.urbamid.catasto.geokettle.util.KitchenScriptStatusCode;
import it.eng.tz.urbamid.catasto.geokettle.util.PrintingLogOutputStream;
import it.eng.tz.urbamid.catasto.storage.service.ImportCatastoPathService;
import it.eng.tz.urbamid.catasto.util.IConstants;
import it.eng.tz.urbamid.catasto.util.ImportType;
import it.eng.tz.urbamid.catasto.util.JDBCUrl;
import it.eng.tz.urbamid.catasto.util.JDBCUrlParser;
import it.eng.tz.urbamid.catasto.web.dto.ParametriAvvioJobDTO;

/**
 * <pre>
    __  ______ _       __     __________     _       ______  ____  __ _______
   / / / / __ \ |     / /    /  _/_  __/    | |     / / __ \/ __ \/ //_/ ___/
  / /_/ / / / / | /| / /     / /  / /       | | /| / / / / / /_/ / ,<  \__ \ 
 / __  / /_/ /| |/ |/ /    _/ /  / /        | |/ |/ / /_/ / _, _/ /| |___/ / 
/_/ /_/\____/ |__/|__/    /___/ /_/         |__/|__/\____/_/ |_/_/ |_/____/  
                                                                             
</pre>
 * NEL SEGUITO LE VARIABILI RELATIVE AI PARAMETRI DA DARE CON APACHE COMMON EXEC.
 * LO SCRIPT KITCHEN.SH VIENE ESEGUITO TRAMITE APACHE COMMON EXEC ALLA QUALE DIAMO
 * IN PASTO UN COMANDO DI QUESTO TIPO:
<br/><br/>
~/GeoKettle/kitchen.sh 
	-norep 
	-param:DATABASE_NAME=${dbName} 
	-param:DATABASE_PORT=${port} 
	-param:DATABASE_USERNAME=${username} 
	-param:DATABASE_PASSWORD=${password} 
	-param:DATABASE_HOST=${host}
	-file=${file}
<br/><br/>
 * i vari parametri da dare allo script a riga di comando (cfr. -norep, -param ecc...)
 * sono astratti tramite l'enumeration KitchenScriptOption tramite la quale è possibile anche
 * costruire tramite il metodo buildOptionWithValue un parametro del tipo:
 * <br/><br/><b>
 * -file=${STRINGA_SOSTITUZIONE_PARAMETRO}
 * <br/><br/></b>
 * e tramite il metodo buildParameterOption (da usare esclusivamente per fornire parametri a
 * GeoKettle) un parametro del tipo
 * <br/><br/><b>
 * -param:NOME_PARAMETRO=${STRINGA_SOSTITUZIONE_PARAMETRO}
 * <br/><br/></b>
 * Tutti i valori in ${NOME_PARAMETRO} vengono poi sostituiti dalla libreria Apache tramite
 * un mapping di sostituzione nome parametro/valore reale.
 * <br/><br/><b>
 * Remember: 42 is the answer.
 * <br/><br/><b>
 */
@Service
public class GeoKettleServiceImpl implements GeoKettleService {

	private static final long WATCHDOG_TIMEOUT_MILLISEC = 42_0__0___0____0_____0;
	
	/**
	 * Logger
	 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Environment di Spring per il recupero dei parametri
	 */
	private final Environment environment;
	
	/**
	 * Path service
	 */
	private final ImportCatastoPathService pathService;
	
	/**
	 * Costruttore.
	 * 
	 * @param environment è l'environment di Spring.
	 * @param pathService è il path service.
	 */
	public GeoKettleServiceImpl( Environment environment, ImportCatastoPathService pathService ) {
		Assert.notNull(environment, "Environment must not be null but don't panic!");
		Assert.notNull(pathService, "ImportCatastoPathService must not be null but don't panic!");
		this.environment = environment;
		this.pathService = pathService;
	}
	
	@Override
	public String eseguiScriptKitchenSH(
			KitchenLogLevel kitchenLogLevel, GeoKettleJobType tipo, String databaseName,
			String databaseHost, String databasePassword, String databasePort, String databaseUser) 
					throws CatastoServiceException {

		if(logger.isDebugEnabled()) {
			logger.debug("Eseguo lo script kitchen.sh per il job di tipo {} e con livello di log {}.", 
					tipo.name(), kitchenLogLevel.level());
		}
		logger.info("pathService.pathInstallazioneGeoKettle(): "+pathService.pathInstallazioneGeoKettle());
		CommandLine commandLine = new KitchenSHCommandLineBuilder(pathService.pathInstallazioneGeoKettle())
				.noRepositories()
				.loggingLevel(kitchenLogLevel)
				.parameter(GeoKettleJobImportParameter.DATABASE_NAME, databaseName)
				.parameter(GeoKettleJobImportParameter.DATABASE_HOST, databaseHost)
				.parameter(GeoKettleJobImportParameter.DATABASE_PASSWORD, databasePassword)
				.parameter(GeoKettleJobImportParameter.DATABASE_PORT, databasePort)
				.parameter(GeoKettleJobImportParameter.DATABASE_USER, databaseUser)
				.parameter(GeoKettleJobImportParameter.BASE_JOB_DIRECTORY, pathService.pathJobGeoKettle())
				.parameter(GeoKettleJobImportParameter.BASE_JOB_DATA_DIRECTORY, getBaseJobDataDirectory(tipo))
				.parameter(GeoKettleJobImportParameter.JOB_IMPORT_TYPE, tipo.jobImportType())
				.file(this.getPath(tipo))
					.build();

		logger.info(":::::::::::::::::########	"+commandLine.toString());
		
		ExecuteWatchdog watchdog = new ExecuteWatchdog(WATCHDOG_TIMEOUT_MILLISEC);
		Executor executor = new DefaultExecutor();
		PrintingLogOutputStream plos = new PrintingLogOutputStream();
		executor.setStreamHandler(new PumpStreamHandler(plos));
		executor.setExitValues(KitchenScriptStatusCode.statiOK());
		executor.setWatchdog(watchdog);
		
		try {
			if(logger.isDebugEnabled()) {
				logger.debug("Eseguo il comando {} tramite apache common exec.", commandLine.getExecutable());
			}
			executor.execute(commandLine);
			return plos.getOutput();
		} catch (ExecuteException ee) {
			logger.error("Si è verificato un errore nell'esecuzione dello script.");
			throw new KettleJobExecutionException(ee);
		} catch (IOException ioe) {
			logger.error("Si è verificato un errore di I/O.");
			throw new KettleJobExecutionException(ioe);
		} 
		
	}
	
	@Override
	public String eseguiScriptKitchenSH(KitchenLogLevel kitchenLogLevel, GeoKettleJobType tipo) throws CatastoServiceException {
		//recupero dall'environment i valori per la connessione al database
		String databaseUsername = environment.getProperty(IConstants.ENVIRONMENT_CATASTO_DB_USERNAME);
		String databasePassword = environment.getProperty(IConstants.ENVIRONMENT_CATASTO_DB_PASSWORD);
		String jdbcUrl = environment.getProperty(IConstants.ENVIRONMENT_CATASTO_DB_JDBC_URL);
		JDBCUrl jdbcURL = JDBCUrlParser.parseConnectionUrl(jdbcUrl).orElseThrow(GeoKettleDBParametersParserException::new);
		return this.eseguiScriptKitchenSH(kitchenLogLevel, tipo, jdbcURL.getDatabase(), jdbcURL.getHost(), 
				databasePassword, jdbcURL.getPort(), databaseUsername);
	}

	@Override
	public String eseguiScriptKitchenSH( ParametriAvvioJobDTO parametri, GeoKettleJobType tipo) 
			throws CatastoServiceException {
		//TODO check null etc...
		return this.eseguiScriptKitchenSH(
				KitchenLogLevel.fromString(parametri.getLivelloLog()), 
				tipo, parametri.getConnessione().getNome(), parametri.getConnessione().getHost(), 
				parametri.getConnessione().getPassword(), parametri.getConnessione().getPort(), 
				parametri.getConnessione().getUser());
	}

	
//	@Override
//	public String eseguiScriptKitchenVersionSH(
//			KitchenLogLevel kitchenLogLevel, GeoKettleJobType tipo, String databaseName,
//			String databaseHost, String databasePassword, String databasePort, String databaseUser) 
//					throws CatastoServiceException {
//
//		if(logger.isDebugEnabled()) {
//			logger.debug("Eseguo lo script kitchen.sh per il job di tipo {} e con livello di log {}.", 
//					tipo.name(), kitchenLogLevel.level());
//		}
//		
//		CommandLine commandLine = new KitchenSHCommandLineBuilder(pathService.pathInstallazioneGeoKettle())
//				.noRepositories()
//				.loggingLevel(kitchenLogLevel)
//				.file(this.getPath(tipo))
//					.build();
//
//		logger.error(":::::::::::::::::########	"+commandLine.toString());
//		
//		ExecuteWatchdog watchdog = new ExecuteWatchdog(WATCHDOG_TIMEOUT_MILLISEC);
//		Executor executor = new DefaultExecutor();
//		PrintingLogOutputStream plos = new PrintingLogOutputStream();
//		executor.setStreamHandler(new PumpStreamHandler(plos));
//		executor.setExitValues(KitchenScriptStatusCode.statiOK());
//		executor.setWatchdog(watchdog);
//		
//		try {
//			if(logger.isDebugEnabled()) {
//				logger.debug("Eseguo il comando {} tramite apache common exec.", commandLine.getExecutable());
//			}
//			executor.execute(commandLine);
//			return plos.getOutput();
//		} catch (ExecuteException ee) {
//			logger.error("Si è verificato un errore nell'esecuzione dello script.");
//			throw new KettleJobExecutionException(ee);
//		} catch (IOException ioe) {
//			logger.error("Si è verificato un errore di I/O.");
//			throw new KettleJobExecutionException(ioe);
//		} 
//		
//	}
//	
//	@Override
//	public String eseguiScriptKitchenVersionSH(KitchenLogLevel kitchenLogLevel, GeoKettleJobType tipo) throws CatastoServiceException {
//		//recupero dall'environment i valori per la connessione al database
//		String databaseUsername = environment.getProperty(IConstants.ENVIRONMENT_CATASTO_DB_USERNAME);
//		String databasePassword = environment.getProperty(IConstants.ENVIRONMENT_CATASTO_DB_PASSWORD);
//		String jdbcUrl = environment.getProperty(IConstants.ENVIRONMENT_CATASTO_DB_JDBC_URL);
//		JDBCUrl jdbcURL = JDBCUrlParser.parseConnectionUrl(jdbcUrl).orElseThrow(GeoKettleDBParametersParserException::new);
//		return this.eseguiScriptKitchenVersionSH(kitchenLogLevel, tipo, jdbcURL.getDatabase(), jdbcURL.getHost(), 
//				databasePassword, jdbcURL.getPort(), databaseUsername);
//	}
//
//	@Override
//	public String eseguiScriptKitchenVersionSH( ParametriAvvioJobDTO parametri, GeoKettleJobType tipo) 
//			throws CatastoServiceException {
//		//TODO check null etc...
//		return this.eseguiScriptKitchenVersionSH(
//				KitchenLogLevel.fromString(parametri.getLivelloLog()), 
//				tipo, parametri.getConnessione().getNome(), parametri.getConnessione().getHost(), 
//				parametri.getConnessione().getPassword(), parametri.getConnessione().getPort(), 
//				parametri.getConnessione().getUser());
//	}
	
	/**
	 * Metodo privato per il recupero del path dei dati sulla quale agisce la procedura
	 * 
	 * @param tipo è il tipo di job da effettuare.
	 * 
	 * @return
	 * @throws CatastoServiceException 
	 */
	protected String getBaseJobDataDirectory(GeoKettleJobType tipo) throws CatastoServiceException {
		Assert.notNull(tipo, "GeoKettleJobType must non be null!");
		String baseJobDataDirectory = null;
		switch(tipo) {
		case JOB_AGGIORNAMENTO_CXF_TO_SHAPE:
			baseJobDataDirectory = this.pathService.jobDataDirectory(ImportType.AGGIORNAMENTO);
			break;
		case JOB_AGGIORNAMENTO_IMPORT_CATASTO:
			baseJobDataDirectory = this.pathService.jobDataDirectory(ImportType.AGGIORNAMENTO);
			break;
		case JOB_ATTUALITA_CXF_TO_SHAPE:
			baseJobDataDirectory = this.pathService.jobDataDirectory(ImportType.ATTUALITA);
			break;
		case JOB_ATTUALITA_IMPORT_CATASTO:
			baseJobDataDirectory = this.pathService.jobDataDirectory(ImportType.ATTUALITA);
			break;
		}
		return baseJobDataDirectory;
	}
	
	/**
	 * Metodo che restituisce il path del job geo kettle
	 * 
	 * @param tipo è il tipo di job
	 * 
	 * @return
	 * 
	 * @throws CatastoServiceException
	 */
	private String getPath(@NotNull GeoKettleJobType tipo) throws CatastoServiceException {
		try {
			File file = Paths.get(this.pathService.pathJobGeoKettle(), tipo.filename()).toFile();
			String path = file.getAbsolutePath();
			if(logger.isDebugEnabled()) {
				logger.debug("Recuperato correttamente il path per il job geokettle {}", tipo.name());
				logger.debug("Il path è il seguente: {}", path);
			}
			return path;
		} catch(InvalidPathException ipe) {
			logger.error("Non è stato possibile trovare alcun file per il job kettle {}.", tipo.filename());
			throw new CatastoServiceException(ipe);
		} catch(UnsupportedOperationException uoe) {
			logger.error("Si è verificata una eccezione <<UnsupportedOperationException>> nel tentativo di recuperare il file {}",
					tipo.filename());
			throw new CatastoServiceException(uoe);
		}
	}
	
	public Environment getEnvironment() {
		return environment;
	}

	public ImportCatastoPathService getPathService() {
		return pathService;
	}
	
}
