package it.eng.tz.urbamid.catasto.shp2pgsql.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import it.eng.tz.urbamid.catasto.exception.CatastoServiceException;
import it.eng.tz.urbamid.catasto.exception.Shp2PostgisException;
import it.eng.tz.urbamid.catasto.geokettle.util.PrintingLogOutputStream;
import it.eng.tz.urbamid.catasto.shp2pgsql.Shp2PgsqlCommandLineBuilder;
import it.eng.tz.urbamid.catasto.shp2pgsql.Shp2PgsqlSQLStatementsMode;
import it.eng.tz.urbamid.catasto.shp2pgsql.service.Shp2PgsqlService;
import it.eng.tz.urbamid.catasto.storage.service.ImportCatastoPathService;
import it.eng.tz.urbamid.catasto.util.ImportType;

@Service
public class Shp2PgsqlServiceImpl implements Shp2PgsqlService {
	
	/**
	 * Logger
	 */
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	/**
	 * Service per il recupero dei path necessari all'esecuzione degli script shp2pgsql
	 */
	private final ImportCatastoPathService pathService;
	
	public Shp2PgsqlServiceImpl( ImportCatastoPathService pathService ) {
		Assert.notNull(pathService, "ImportCatastoPathService MUST not be null but don't panic!");
		this.pathService = pathService;
	}
	
	@Override
	public String convertiShapefileInScriptSQL(ImportType importType, String shapefile, String schema, String table, String geometryColumnName) 
			throws CatastoServiceException {
		return this.convertiShapefileInScriptSQL(importType, new File(shapefile), schema, table, geometryColumnName);
	}
	
	@Override
	public String convertiShapefileInScriptSQL(ImportType importType, File shapefile, String schema, String table, String geometryColumnName) 
			throws CatastoServiceException {
		
		long inizio = new Date().getTime()/1000;
		if(LOG.isDebugEnabled())
			LOG.debug("Eseguo lo script shp2pgsql per lo shapefile {}.", shapefile);
		try(FileOutputStream fileOutputStream = new FileOutputStream( this.getOutputSqlFile(importType, table))) {
			
			//lo statement mode deve essere di append per l'AGGIORNAMENTO ed il drop per l'ATTUALITA
			Shp2PgsqlSQLStatementsMode sqlStatementsMode = 
					ImportType.AGGIORNAMENTO.equals(importType) ? Shp2PgsqlSQLStatementsMode.DROP_AND_RECREATE_TABLE
							: Shp2PgsqlSQLStatementsMode.DROP_AND_RECREATE_TABLE;
			//ora si va sempre in drop and recreate
			CommandLine commandLine = new Shp2PgsqlCommandLineBuilder(shapefile, schema, table)
					.sqlStatementMode(sqlStatementsMode)
					.encoding(Shp2PgsqlCommandLineBuilder.DEFAULT_ENCODING)
					.createSpatialIndexOnGeocolumn()
					.geocolumn(geometryColumnName)
					.srid(Shp2PgsqlCommandLineBuilder.DEFAULT_SRID)
					.build();
			
			LOG.info(":::::::::::::::::########	"+commandLine.toString());
			
			PrintingLogOutputStream printingLogOutputStream = new PrintingLogOutputStream();
			PumpStreamHandler pumpStreamHandler = new PumpStreamHandler(fileOutputStream, printingLogOutputStream);
			DefaultExecutor executor = new DefaultExecutor();
			executor.setStreamHandler(pumpStreamHandler);
			//42 è la risposta alla vita, l'universo e tutto quanto
			ExecuteWatchdog watchdog = new ExecuteWatchdog(42_0__0___0____0_____0);
			executor.setWatchdog(watchdog);
			executor.execute(commandLine);
			fileOutputStream.flush();
			if(LOG.isDebugEnabled()) {
				long fine = new Date().getTime() / 1_0__0___0;
				LOG.debug("Eseguito con successo lo script shp2pgsql per il file {} in {} secondi.", 
						shapefile, fine-inizio);
			}
			return printingLogOutputStream.getOutput();
		} catch (FileNotFoundException fnf) {
			LOG.error("Si è verificato un <<FileNotFoundException>> nell'apertura del file.");
			throw new Shp2PostgisException(fnf);
		} catch (IOException ioe) {
			LOG.error("Si è verificato un <<IOException>> durante l'esecuzione di shp2pgsql.");
			throw new Shp2PostgisException(ioe);
		}
		
	}

	/**
	 * Metodo privato che crea un oggetto {@link File} che verrà usato per scrivere i dati di output del comando shp2pgsql e,
	 * quindi, creare lo script SQL.
	 * N.B. Casomai il file dovesse esistere, viene cancellato preventivamente.
	 * 
	 * @param importType è il tipo di import (AGGIORNAMENTO/ATTUALITA). Serve per risalire al path dove creare il file.
	 * @param nomeFile è il nome del file SQL
	 * 
	 * @return {@link File}
	 * 
	 * @throws CatastoServiceException
	 */
	private File getOutputSqlFile(ImportType importType, String nomeFile) throws CatastoServiceException {
		
		File outputSqlFile = Paths.get(
				this.pathService.sqlScriptDirectory(importType), 
				nomeFile+".sql")
					.toFile();
		if(outputSqlFile.exists()) {
			try {
				Files.delete(outputSqlFile.toPath());
			} catch(IOException ioe ) {
				LOG.error("Si è verificato un errore di I/O durante la cancellazione del file {}.", outputSqlFile.getName());
				throw new CatastoServiceException(ioe);
			}
		}
		return outputSqlFile;
	}

	public ImportCatastoPathService getPathService() {
		return pathService;
	}

}
