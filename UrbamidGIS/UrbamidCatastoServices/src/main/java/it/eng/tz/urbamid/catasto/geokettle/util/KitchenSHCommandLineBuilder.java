package it.eng.tz.urbamid.catasto.geokettle.util;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.exec.CommandLine;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import it.eng.tz.urbamid.catasto.exception.CatastoServiceException;

/**
 * Classe che, utilizzando il design pattern builder, aiuta nella creazione della CommandLine
 * da eseguire per avviare uno script kitchen.sh.
 * Per chiarezza, questo builder ci deve aiutare a costruire command line per eseguire comandi di questo tipo:
<pre>

~/GeoKettle/kitchen.sh 
	-norep 
	-param:DATABASE_NAME=URBAMID 
	-param:DATABASE_PORT=5432 
	-param:DATABASE_USERNAME=postgres 
	-param:DATABASE_PASSWORD=postgres 
	-param:DATABASE_HOST=localhost
	-param:BASE_JOB_DIRECTORY=/home/fesposit/Lavoro/geokettle/IMPORT_CATASTO
	-file=/home/fesposit/Lavoro/geokettle/IMPORT_CATASTO/JOB_IMPORT_CATASTO.kjb
	
</pre>
 */
public final class KitchenSHCommandLineBuilder {
	
	/**
	 * Nome dello script kitchen.sh
	 */
	private static final String KITCHEN_SCRIPT = "kitchen.sh";
	
	protected List<String> argomenti;
	protected Map<String, Object> substitutionMap;
	private String pathInstallazioneGeokettle;
	protected String fileAsString;
	protected File file;
	
	/**
	 * Costruttore.
	 * 
	 * @param pathInstallazioneGeokettle è il path di installazione di Geo Kettle.
	 * 
	 * @throws CatastoServiceException
	 */
	public KitchenSHCommandLineBuilder(String pathInstallazioneGeokettle) {
		super();
		Assert.hasLength(pathInstallazioneGeokettle, "Path installazione Geokettle MUST not be empty!");
		this.argomenti = new ArrayList<>();
		this.substitutionMap = new HashMap<>();
		this.pathInstallazioneGeokettle = pathInstallazioneGeokettle;
	}
	
	/**
	 * Aggiunge l'argomento da linea di comando per specificare il nome di un repository Enterprise o database, 
	 * nel caso in cui se ne voglia specificare uno.
	 * 
	 * @param name è il nome del repository enterprise o database
	 * @return
	 */
	public KitchenSHCommandLineBuilder repository(String name) {
		return this.setArgument(KitchenShArgument.REPOSITORY, name);
	}

	/**
	 * Aggiunge l'argomento da linea di comando per specificare lo username per l'accesso al repository.
	 * 
	 * @param username è lo username di accesso al repository.
	 * 
	 * @return {@link KitchenSHCommandLineBuilder}
	 */
	public KitchenSHCommandLineBuilder repositoryUsername(String username) {
		return this.setArgument(KitchenShArgument.REPOSITORY, username);
	}
	
	/**
	 * Aggiunge l'argomento da linea di comando per specificare la password per l'accesso al repository.
	 * 
	 * @param password è la password di accesso al repository.
	 * 
	 * @return {@link KitchenSHCommandLineBuilder}
	 */
	public KitchenSHCommandLineBuilder repositoryPassword(String password) {
		return this.setArgument(KitchenShArgument.REPOSITORY_PASSWORD, password);
	}
	
	/**
	 * Aggiunge l'argomento da linea di comando per specificare la directory del repository.
	 * 
	 * @param directory è il nome della directory del repository.
	 * 
	 * @return {@link KitchenSHCommandLineBuilder}
	 */
	public KitchenSHCommandLineBuilder repositoryDirectory(String directory) {
		return this.setArgument(KitchenShArgument.REPOSITORY_DIRECTORY, directory);
	}
	
	/**
	 * Aggiunge l'argomento da linea di comando per specificare il nome della trasformata di GeoKettle da eseguire.
	 * 
	 * @param trasformationName è il nome della trasformata di GeoKettle da eseguire.
	 * 
	 * @return {@link KitchenSHCommandLineBuilder}
	 */
	public KitchenSHCommandLineBuilder transformationName(String trasformationName) {
		return this.setArgument(KitchenShArgument.TRANSFORMATION_NAME, trasformationName);
	}
	
	/**
	 * 
	 * @param file
	 * @return
	 */
	public KitchenSHCommandLineBuilder file(String file) {
		Assert.isTrue(StringUtils.hasText(file), "File for KitchenShArgument MUST not be empty!");
		this.argomenti.add(KitchenShArgument.FILE.argument());
		Optional<String> optionalSubstitutionString = KitchenShArgument.FILE.substitutionString();
		if( optionalSubstitutionString.isPresent() )
			this.substitutionMap.put(optionalSubstitutionString.get(), file);
		else
			Assert.isTrue(false, "Substituion string MUST not be null!");
		return this;
	}
	
	/**
	 * 
	 * @param file
	 * @return
	 */
	public KitchenSHCommandLineBuilder file(File file) {
		Assert.notNull(file, "File for KitchenShArgument MUST not be null!");
		this.argomenti.add(KitchenShArgument.FILE.argument());
		Optional<String> optionalSubstitutionString = KitchenShArgument.FILE.substitutionString();
		if( optionalSubstitutionString.isPresent() )
			this.substitutionMap.put(optionalSubstitutionString.get(), file);
		else
			Assert.isTrue(false, "Substituion string MUST not be null!");
		return this;
	}
	
	/**
	 * Aggiunge l'argomento da linea di comando per specificare il livello di logging di GeoKettle.
	 * 
	 * @param loggingLevel è il livello di logging.
	 * 
	 * @return {@link KitchenSHCommandLineBuilder}
	 */
	public KitchenSHCommandLineBuilder loggingLevel(KitchenLogLevel loggingLevel) {
		return this.setArgument(KitchenShArgument.LOGGING_LEVEL, loggingLevel.level());
	}
	
	/**
	 * Aggiunge l'argomento da linea di comando per specificare il file dove salvare il log di GeoKettle.
	 * 
	 * @param logFile è il file di log di GeoKettle
	 * 
	 * @return {@link KitchenSHCommandLineBuilder}
	 */
	public KitchenSHCommandLineBuilder logFile(String logFile) {
		return this.setArgument(KitchenShArgument.LOG_FILE, logFile);
	}
	
	/**
	 * Imposta l'argomento da linea di comando per la stampa della lista di directory del repository.
	 * 
	 * @return {@link KitchenSHCommandLineBuilder}
	 */
	public KitchenSHCommandLineBuilder repositoryDirectoryList() {
		return this.setArgument(KitchenShArgument.REPOSITORY_DIRECTORIES_LIST);
	}
	
	/**
	 * Imposta l'argomento da linea di comando per la stampa della lista delle trasformate nel repository.
	 * 
	 * @return {@link KitchenSHCommandLineBuilder}
	 */
	public KitchenSHCommandLineBuilder repositoryTransformationsList() {
		return this.setArgument(KitchenShArgument.REPOSITORY_TRANSFORMATIONS_LIST);
	}
	
	/**
	 * Imposta l'argomento da linea di comando per la stampa della lista dei repository.
	 * 
	 * @return {@link KitchenSHCommandLineBuilder}
	 */
	public KitchenSHCommandLineBuilder repositoriesList() {
		return this.setArgument(KitchenShArgument.REPOSITORIES_LIST);
	}
	
	/**
	 * Imposta l'argomento da linea di comando per l'export dei repository.
	 * 
	 * @return {@link KitchenSHCommandLineBuilder}
	 */
	public KitchenSHCommandLineBuilder exportRepositories() {
		return this.setArgument(KitchenShArgument.EXPORT_REPOSITORIES);
	}
	
	/**
	 * Imposta l'argomento da linea di comando per dire che non va usato il repository Pentaho.
	 * 
	 * @return {@link KitchenSHCommandLineBuilder}
	 */
	public KitchenSHCommandLineBuilder noRepositories() {
		return this.setArgument(KitchenShArgument.NO_REPOSITORY);
	}
	
	/**
	 * Imposta l'argomento da linea di comando per specificare di eseguire il job/trasformata in safe mode
	 * (abilita maggiori controlli).
	 * 
	 * @return {@link KitchenSHCommandLineBuilder}
	 */
	public KitchenSHCommandLineBuilder safeMode() {
		return this.setArgument(KitchenShArgument.SAFE_MODE);
	}
	
	/**
	 * Imposta l'argomento da linea di comando per la stampa della versione di GeoKettle.
	 * 
	 * @return {@link KitchenSHCommandLineBuilder}
	 */
	public KitchenSHCommandLineBuilder version() {
		return this.setArgument(KitchenShArgument.VERSION);
	}
	
	/**
	 * 
	 * @param parametro
	 * @param valore
	 * @return {@link KitchenSHCommandLineBuilder}
	 */
	public KitchenSHCommandLineBuilder parameter(GeoKettleJobImportParameter parametro, Object valore) {
		Assert.notNull(valore, "Parameter value MUST not be null.");
		if(valore instanceof String) {
			Assert.hasText((String) valore, "Parameter value MUST not be empty.");
			this.argomenti.add(KitchenShArgument.PARAMETER.parameter(parametro.name(), (String) valore));
		} else {
			this.argomenti.add(KitchenShArgument.PARAMETER.parameter(parametro.name(), (String) valore));
			this.substitutionMap.put(parametro.name(), valore);
		}
		return this;
	}
	
	/**
	 * Imposta il parametro da linea di comando per la stampa della lista dei parametri.
	 * 
	 * @return {@link KitchenSHCommandLineBuilder}
	 */
	public KitchenSHCommandLineBuilder parametersList() {
		return this.setArgument(KitchenShArgument.PARAMETERS_LIST);
	}
	
	/**
	 * Imposta il parametro da linea di comando per impostare il numero massimo di linee di log.
	 * 
	 * @param maxLogLines è il numero massimo di linee di log.
	 * 
	 * @return {@link KitchenSHCommandLineBuilder}
	 */
	public KitchenSHCommandLineBuilder maxLogLines( long maxLogLines) {
		return this.setArgument(KitchenShArgument.MAX_LOG_LINES, String.valueOf(maxLogLines));
	}
	
	/**
	 * Imposta il parametro da linea di comando per impostare il massimo timeout di log.
	 * 
	 * @param maxLogTimeout è il timeout massimo di log in millisecondi.
	 * 
	 * @return {@link KitchenSHCommandLineBuilder}
	 */
	public KitchenSHCommandLineBuilder maxLogTimeout( long maxLogTimeout) {
		return this.setArgument(KitchenShArgument.MAX_LOG_TIMEOUT, String.valueOf(maxLogTimeout));
	}
	
	/**
	 * Metodo che costruisce la stringa col percorso allo script kitchen.sh alla quale, successivamente, verrano
	 * aggiunti i vari parametri da linea di comando.
	 * 
	 * @return la stringa col percorso allo script kitchen.sh
	 * @throws CatastoServiceException 
	 */
	private String costruisciComandoKitchenSh() throws CatastoServiceException {
		try {
			File kitchenSh = Paths.get(this.pathInstallazioneGeokettle, KitchenSHCommandLineBuilder.KITCHEN_SCRIPT).toFile();
			return kitchenSh.getAbsolutePath();
		} catch(InvalidPathException ipe) {
			throw new CatastoServiceException(ipe);
		} catch(UnsupportedOperationException uoe) {
			throw new CatastoServiceException(uoe);
		} catch(SecurityException se) {
			throw new CatastoServiceException(se);
		}
	}

	/**
	 * Costruisce e restituisce la CommandLine.
	 * 
	 * @return la CommandLine con tutte le option impostate tramite i metodi del builder.
	 * 
	 * @throws CatastoServiceException 
	 */
	public CommandLine build() throws CatastoServiceException {
		CommandLine commandLine = CommandLine.parse(costruisciComandoKitchenSh());
		if( this.argomenti != null && !this.argomenti.isEmpty() ) {
			String[] arrayArgomenti = new String[argomenti.size()];
			for(int i=0; i<this.argomenti.size(); i++) {
				arrayArgomenti[i] = this.argomenti.get(i);
			}
			commandLine.addArguments(arrayArgomenti);
		}
		if( this.substitutionMap != null && this.substitutionMap.size() > 0 ) {
			commandLine.setSubstitutionMap(this.substitutionMap);
		}
		return commandLine;
	}
	
	/**
	 * Metodo privato per impostare un argomento che non prevede valori.
	 * 
	 * @param argument è il tipo di argomento.
	 * 
	 * @return {@link KitchenSHCommandLineBuilder}
	 */
	private KitchenSHCommandLineBuilder setArgument(KitchenShArgument argument) {
		Assert.notNull(argument, "KitchenShArgument MUST not be null!");
		this.argomenti.add(argument.argument());
		return this;
	}
	
	/**
	 * Metodo privateo per impostare un argomento con un determinato valore.
	 * 
	 * @param argument è il tipo di argomento
	 * @param value è il valore dell'argomento
	 * 
	 * @return {@link KitchenSHCommandLineBuilder}
	 */
	private KitchenSHCommandLineBuilder setArgument(KitchenShArgument argument, String value) {
		Assert.notNull(argument, "KitchenShArgument MUST not be null!");
		Assert.isTrue(StringUtils.hasText(value), "Value for KitchenShArgument MUST not be empty!");
		this.argomenti.add(argument.argument());
		Optional<String> optionalSubstitutionString = argument.substitutionString();
		if( optionalSubstitutionString.isPresent() )
			this.substitutionMap.put(optionalSubstitutionString.get(), value);
		else
			Assert.isTrue(false, "Substituion string MUST not be null!");
		return this;
	}
	
}
