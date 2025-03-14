package it.eng.tz.urbamid.catasto.geokettle.util;

import java.util.Optional;

import org.springframework.util.Assert;

/**
 * In accordo con la documentazione Pentaho a <a href="https://help.pentaho.com/Documentation/7.1/0L0/0Y0/070">questo link</a>,
 * è stata costruita l'enumeration seguente che astrae i possibili parametri che è possibile passare da linea di comando
 * che vengono riportati nel seguito:
<pre>
Options:
  -rep        = Nome del repository
  -user       = Username del repository
  -pass       = Password del repository
  -job        = Il nome del job da lanciare
  -dir        = La cartella (non dimenticare lo slash /)
  -file       = Il nome file (job XML) da lanciare
  -level      = Livello di log (Basic, Detailed, Debug, Rowlevel, Error, Nothing)
  -logfile    = File di log su cui scrivere
  -listdir    = Lista delle cartelle nel repository
  -listjobs   = Lista dei job nella cartella specificata
  -listrep    = Lista dei repository disponibili
  -norep      = Non loggarsi nel repository
  -version    = Mostra la versione, la revisione e la data di build
  -param      = Imposta un parametro <NAME>=<VALUE>. Per esempio -param:FOO=bar
  -listparam  = Informazioni di lista riguardanti i parametri definiti nel job specificato.
  -export     = Esporta tutte le risorse collegate del job specificato. L'argomento è il nome di un file ZIP.
</pre>
 * N.B.
 * Windows systems use syntax with the forward slash (“/”) and colon (“:”). 
 * If spaces are present in the option values, use single quotes (“) and double quotes (“”) to keep spaces together, 
 * for example, "-param:MASTER_HOST=192.168.1.3" "-param:MASTER_PORT=8181"
 * 
 * IN QUESTA SEDE CONSIDERIAMO SOLO LA VERSIONE LINUX DELLE OPTIONS... Estendere in futuro se dovesse servire lanciare
 * lo script su sistemi Windows.
 */
public enum KitchenShArgument {

	REPOSITORY("-rep",	"${REPOSITORY}", "Enterprise or database repository name, if you are using one"),
	REPOSITORY_USERNAME("-user", "${REPOSITORY_USERNAME}", "Repository username"),
	REPOSITORY_PASSWORD("-pass", "${REPOSITORY_PASSWORD}", "Repository password"),
	TRANSFORMATION_NAME("-trans", "${TRANSFORMATION_NAME}", "The name of the transformation (as it appears in the repository) to launch"),
	REPOSITORY_DIRECTORY("-dir", "${REPOSITORY_DIRECTORY}", "The repository directory that contains the transformation, including the leading slash"),
	FILE("-file", "${FILE}", "If you are calling a local KTR file, this is the filename, including the path if it is not in the local directory"),
	LOGGING_LEVEL("-level", "${LOGGING_LEVEL}", "The logging level (Basic, Detailed, Debug, Rowlevel, Error, Nothing"),
	LOG_FILE("-logfile", "${LOG_FILE}", "A local filename to write log output to"),
	REPOSITORY_DIRECTORIES_LIST("-listdir", null, "Lists the directories in the specified repository"),
	REPOSITORY_TRANSFORMATIONS_LIST("-listtrans", null, "Lists the transformations in the specified repository directory"),
	REPOSITORIES_LIST("-listrep", null, "Lists the available repositories"),
	EXPORT_REPOSITORIES("-exprep", null, "Exports all repository objects to one XML file"),
	NO_REPOSITORY("-norep", null, "Prevents Pan from logging into a repository. If you have set the KETTLE_REPOSITORY, KETTLE_USER, and KETTLE_PASSWORD environment variables, then this option will enable you to prevent Pan from logging into the specified repository, assuming you would like to execute a local KTR file instead."),
	SAFE_MODE("-safemode", null, "Runs in safe mode, which enables extra checking"),
	VERSION("-version", null, "Shows the version, revision, and build date"),
	//la sintassi è: -param:NOME_PARAMETRO=VALORE_PARAMETRO
	PARAMETER("-param", null, "Set a named parameter in a name=value format. For example: -param:FOO=bar"),
	PARAMETERS_LIST("-listparam", null, "List information about the defined named parameters in the specified transformation."),
	MAX_LOG_LINES("-maxloglines", "${MAX_LOG_LINES}", "The maximum number of log lines that are kept internally by PDI. Set to 0 to keep all rows (default)"),
	MAX_LOG_TIMEOUT("-maxlogtimeout", "${MAX_LOG_TIMEOUT}", "The maximum age (in minutes) of a log line while being kept internally by PDI. Set to 0 to keep all rows indefinitely (default)");
	
	private static final String START_PARAMETER = "=${";
	private static final String END_PARAMETER = "}";
	private static final String COLON_CHARACTER = ":";
	private static final String EQUAL_CHARACTER = "=";
	
	/**
	 * E' l'argomento di kitchen.sh
	 */
	private String argument;
	
	/**
	 * E' la string di sostituzione, ovvero la stringa che verrà usata per il replacing dei parametri
	 * coi i rispettivi valori.
	 */
	private String substitutionString;
	
	/**
	 * E' la descrizione dell'argomento (presa dalla documentazione)
	 */
	private String description;
	
	private KitchenShArgument(String argument, String substitutionString, String description) {
		this.argument = argument;
		this.substitutionString = substitutionString;
		this.description = description;
	}

	/**
	 * 
	 * @return
	 */
	public String argument() {
		if( this.substitutionString().isPresent() )
			return this.argument.concat(KitchenShArgument.EQUAL_CHARACTER).concat(this.substitutionString);
		else
			return this.argument;
	}
	
	/**
	 * 
	 * @return
	 */
	public Optional<String> substitutionString() {
		if(this.substitutionString == null) {
			return Optional.empty();
		}
		return Optional.of(this.substitutionString.substring(2, this.substitutionString.length()-1));	//TODO migliorare
	}
	
	/**
	 * Metodo che serve per costruire una option per -param con la seguente sintassi:
	 * 	
	 * -param:NOME_PARAMETRO=${VALORE_PARAMETRO}
	 * 	
	 * @param parameterName	è il nome del parametro
	 * @param parameterValue è la stringa che identifica il parametro (per la sua successiva sostituzione col valore reale)
	 * 
	 * @return la stringa relativa alla option creata.
	 */
	public String parameter(String parameterName, String parameterValue) {
		Assert.isTrue(this.compareTo(KitchenShArgument.PARAMETER) == 0, "The method parameter(name,value) MUST be used only enum value PARAMETER of KitchenShArgument"); 
		return 
				new StringBuilder(this.argument)
					.append(COLON_CHARACTER)
						.append(parameterName)
							.append(EQUAL_CHARACTER)
								.append(parameterValue)
									.toString();
	}
	
	public String parameter(String parametro) {
		Assert.isTrue(this.compareTo(KitchenShArgument.PARAMETER) == 0, "The method parameter(name,value) MUST be used only enum value PARAMETER of KitchenShArgument");
		return 
				new StringBuilder(this.argument)
					.append(COLON_CHARACTER)
						.append(parametro)
							.append(START_PARAMETER)
								.append(parametro)
									.append(END_PARAMETER)
										.toString();
	}

	/**
	 * Metodo che restituisce la descrizione dell'argomento.
	 * 
	 * @return la descrizione dell'argomento
	 */
	public String description() {
		return description;
	}
	
}