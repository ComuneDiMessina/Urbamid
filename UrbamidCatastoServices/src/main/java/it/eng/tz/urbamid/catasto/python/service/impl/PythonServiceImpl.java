package it.eng.tz.urbamid.catasto.python.service.impl;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.PumpStreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import it.eng.tz.urbamid.catasto.exception.CatastoServiceException;
import it.eng.tz.urbamid.catasto.exception.KettleJobExecutionException;
import it.eng.tz.urbamid.catasto.geokettle.util.KitchenScriptStatusCode;
import it.eng.tz.urbamid.catasto.geokettle.util.PrintingLogOutputStream;
import it.eng.tz.urbamid.catasto.python.service.PythonService;
import it.eng.tz.urbamid.catasto.python.util.PythonScriptCommandBuilder;
import it.eng.tz.urbamid.catasto.storage.service.ImportCatastoPathService;
import it.eng.tz.urbamid.catasto.util.ImportType;

@Service
public class PythonServiceImpl implements PythonService {

	/**
	 * Logger
	 */
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * Path service
	 */
	private final ImportCatastoPathService pathService;
	
	public PythonServiceImpl(ImportCatastoPathService pathService) {
		Assert.notNull(pathService, "ImportCatastoPathService MUST not be null but don't panic!");
		this.pathService = pathService;
	}
	

	@Override
	public String eseguiScriptPython(ImportType batchType) throws CatastoServiceException {
		return this.eseguiScriptPython(
				this.pathService.scriptShDirectory(),
				this.pathService.cassiniSoldnerWorkDirectory(batchType), 
				this.pathService.gaussBoagaWorkDirectory(batchType), 
				this.pathService.pythonScriptCxfToShape(), 
				this.pathService.shapefileOutputDirectory(batchType), 
				"u_cat_");
	}
	
	@Override
	public String eseguiScriptPython( String scriptShPath, String cassiniSoldnerDirectory, String gaussBoagaDirectory,
			String pythonFile, String shapefileOutputDirectory, String shapefilePrefix ) 
					throws CatastoServiceException {
		
		long inizio = new Date().getTime()/1000;
		if(LOG.isDebugEnabled()) {
			LOG.debug("Eseguo lo script SH che lancia la procedura python.");
		}
		CommandLine commandLine = new PythonScriptCommandBuilder(scriptShPath)
				.cassiniSoldnerCXFDirectory(cassiniSoldnerDirectory)
				.gaussBoagaCXFDirectory(gaussBoagaDirectory)
				.pythonFile(pythonFile)
				.shapefileOutputDirectory(shapefileOutputDirectory)
				.shapefilePrefix(shapefilePrefix)
					.build();
		
		LOG.info(":::::::::::::::::########	"+commandLine.toString());
		
		ExecuteWatchdog watchdog = new ExecuteWatchdog(420000);
		Executor executor = new DefaultExecutor();
		PrintingLogOutputStream plos = new PrintingLogOutputStream();
		executor.setStreamHandler(new PumpStreamHandler(plos));
		executor.setExitValues(KitchenScriptStatusCode.statiOK());
		executor.setWatchdog(watchdog);
		
		try {
			int exitCode = executor.execute(commandLine);
			if(LOG.isDebugEnabled()) {
				LOG.debug("Script SH per il lancio della procedura python eseguito con codice di uscita: {}.", exitCode);
				long fine = new Date().getTime() / 1000;
				LOG.debug("Eseguito con successo lo script SH in {} secondi.", fine-inizio);
			}
			return plos.getOutput();
		} catch (ExecuteException ee) {
			LOG.error("Si è verificato un errore nell'esecuzione dello script.");
			throw new KettleJobExecutionException(ee);
		} catch (IOException ioe) {
			LOG.error("Si è verificato un errore di I/O.");
			throw new KettleJobExecutionException(ioe);
		} 
			
	}

	public ImportCatastoPathService getPathService() {
		return pathService;
	}
	

}
