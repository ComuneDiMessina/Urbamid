package it.eng.tz.urbamid.wrappergeo.psql.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import it.eng.tz.urbamid.wrappergeo.exception.PsqlException;
import it.eng.tz.urbamid.wrappergeo.psql.service.PsqlService;
import it.eng.tz.urbamid.wrappergeo.util.IConstants;
import it.eng.tz.urbamid.wrappergeo.util.JDBCUrl;
import it.eng.tz.urbamid.wrappergeo.util.JDBCUrlParser;
import it.eng.tz.urbamid.wrappergeo.util.PsqlCommandLineBuilder;

/**
 * Service per l'esecuzione di script SQL via PSQL.
 */
@Service
public class PsqlServiceImpl implements PsqlService {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass().getName());
	
	/**
	 * Environment Spring per il recupero eventuale dei parametri di connessione al database.
	 */
	private final Environment environment;
	
	/**
	 * Costruttore.
	 * 
	 * @param environment è l'environment di Spring.
	 */
	public PsqlServiceImpl(Environment environment) {
		Assert.notNull(environment, "Environment must not be null but don't panic!");
		this.environment = environment;
	}
	
	@Override
	public String eseguiScriptSQL(File file) throws PsqlException {
		String databaseUsername = environment.getProperty(IConstants.ENVIRONMENT_CATASTO_DB_USERNAME);
		String jdbcUrl = environment.getProperty(IConstants.ENVIRONMENT_CATASTO_DB_JDBC_URL);
		JDBCUrl jdbcURL = JDBCUrlParser.parseConnectionUrl(jdbcUrl).orElseThrow(PsqlException::new);
		return this.eseguiScriptSQL(file, jdbcURL.getHost(), jdbcURL.getDatabase(), jdbcURL.getPort(), databaseUsername);
	}

	@Override
	public String eseguiScriptSQL(String file) throws PsqlException {
		return this.eseguiScriptSQL(new File(file));
	}

	@Override
	public String eseguiScriptSQL(String file, String host, String database, String port, String user)
			throws PsqlException {
		return this.eseguiScriptSQL((Object) file, host, database, port, user);
	}

	@Override
	public String eseguiScriptSQL(File file, String host, String database, String port, String user)
			throws PsqlException {
		return this.eseguiScriptSQL((Object) file, host, database, port, user);
	}
	
	/**
	 * Metodo privato per l'esecuzione di uno script SQL.
	 * 
	 * @param file è il file
	 * @param host è l'hostname del database
	 * @param database è il nome del database
	 * @param port è il numero di porto del database
	 * @param user è lo username del database
	 * 
	 * @return una stringa con il risultato dell'esecuzione.
	 * 
	 * @throws PsqlException
	 */
	private String eseguiScriptSQL(Object file, String host, String database, String port, String user)
			throws PsqlException {
		
		Assert.isTrue( (file instanceof File || file instanceof String), "File MUST be an instance of File or String classes");
		
		long inizio = new Date().getTime()/1000;
		if(LOG.isDebugEnabled()) {
			LOG.debug("Eseguo lo script SQL per il file {}.", file);
		}
		
		try(
				//sfrutto gli autoclosable stream
				ByteArrayOutputStream baosError = new ByteArrayOutputStream();
				ByteArrayOutputStream baosSuccess = new ByteArrayOutputStream();
				
				) {

			CommandLine commandLine = this.buildCommandLine(file, host, database, port, user);
			this.logExecutableAndArguments(commandLine);
			DefaultExecutor executor = new DefaultExecutor();
			final PumpStreamHandler streamHandler = new PumpStreamHandler(baosSuccess, baosError);
			executor.setStreamHandler(streamHandler);
			ExecuteWatchdog watchdog = new ExecuteWatchdog(420000);
			executor.setWatchdog(watchdog);
			int exitCode = executor.execute(commandLine);
			if(LOG.isDebugEnabled()) {
				LOG.debug("Script SQL eseguito tramite PSQL. Codice di uscita: {}.", exitCode);
				long fine = new Date().getTime() / 1000;
				LOG.debug("Eseguito con successo lo script SQL per il file {} in {} secondi.", file, fine-inizio);
			}
			baosSuccess.flush();
			return baosSuccess.toString();
		} catch (ExecuteException ee) {
			LOG.error("Si è verificata una <<ExecuteException>> durante l'esecuzione dello script {}.", file);
			throw new PsqlException(ee);
		} catch (IOException ioe) {
			LOG.error("Si è verificata una <<IOException>> durante l'esecuzione dello script {}.", file);
			throw new PsqlException(ioe);
		} 
		
	}
	
	/**
	 * Metodo interno che logga l'executable e gli argomenti della command line.
	 * 
	 * @param commandLine è la command line
	 */
	private void logExecutableAndArguments( CommandLine commandLine ) {
		if( LOG.isDebugEnabled() ) {
			LOG.debug("PSQL executable: {}.", commandLine.getExecutable());
			for(String arg : commandLine.getArguments()) {
				LOG.debug("PSQL ARGUMENTS: {}.", arg);
			}
		}
	}
	
	/**
	 * Metodo privato che sfrutta la classe builder {@link PsqlCommandLineBuilder} per costruire una command line per 
	 * l'esecuzione di un comando psql.
	 * 
	 * @param file è il file
	 * @param host è l'hostname del database
	 * @param database è il nome del database
	 * @param port è il numero di porto del database
	 * @param user è lo username del database
	 * 
	 * @return {@link CommandLine}
	 */
	private CommandLine buildCommandLine(Object file, String host, String database, String port, String user) {
		PsqlCommandLineBuilder builder;
		if( file instanceof File ) {
			Assert.notNull(file, "File MUST not be null!");
			builder = new PsqlCommandLineBuilder( (File) file, false );
		} else {
			Assert.hasText( (String) file, "File MUST not be empty!");
			builder = new PsqlCommandLineBuilder( (String) file, false );
		}
		//N.B. ricorda: un giorno la bellezza salvera' il mondo
		return builder
				.databaseHostname(host)
					.databaseName(database)
						.databasePort(port)
							.databaseUsername(user)
								.noPassword()
									.build();
	}
	
}
