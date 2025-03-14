package it.eng.tz.urbamid.wrappergeo.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.exec.CommandLine;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import it.eng.tz.urbamid.wrappergeo.psql.util.PsqlArgument;
import it.eng.tz.urbamid.wrappergeo.shp2pgsql.Shp2PgsqlCommandLineBuilder;



/**
 * 
 * Classe Builder per la generazione di un {@link CommandLine} per l'esecuzione di un comando con
 * psql. Psql è il terminale interattivo per PostgreSQL con la quale (nel nostro caso) vengono eseguiti 
 * script sql derivanti dagli shapefile tramite il tool shp2psql.
 * L'utilizzo di psql è riportato nel seguito ed i metodi di questa
 * classe non fanno altro che ricalcare (alcuni) dei parametri per facilitare la costruzione del comando.
<pre>
psql è il terminale interattivo per PostgreSQL.

Utilizzo:
  psql [OPZIONI]... [NOME DB [NOME UTENTE]]
Esempio:
  psql -h ${databaseHost} -U ${databaseUser} -d ${databaseName} -w -f ${scriptSQL}

Opzioni generali:
  -c, --command=COMANDO    esegue solamente un comando singolo (SQL o interno)
                           e termina
  -d, --dbname=NOMEDB      specifica il nome del database a cui connettersi
                           (default: "fesposit")
  -f, --file=NOME FILE     esegui i comandi da un file ed esci
  -l --list                elenca i database disponibili ed esci
  -v, --set=, --variable=NOME=VALORE
                           imposta la variabile psql NOME a VALORE
                           (es.: -v ON_ERROR_STOP=1)
  -V, --version            mostra informazioni sulla versione ed esci
  -X, --no-psqlrc          non leggere il file di avvio (~/.psqlrc)
  -1 ("uno"), --single-transaction
                           esegui in un'unica transazione (se non interattivo)
  -?, --help[=opzioni]     mostra quest'aiuto ed esci
      --help=commands      mostra la lista dei comandi backslash ed esci
      --help=variables     mostra la lista delle variabili speciali ed esci

Opzioni di input e output:
  -a, --echo-all           mostra tutti gli input dallo script
  -b, --echo-errors        mostra i comandi falliti
  -e, --echo-queries       mostra i comandi inviati al server
  -E, --echo-hidden        mostra le query generate dai comandi interni
  -L, --log-file=NOME_FILE invia log di sessione al file
  -n, --no-readline        disabilita la modifica avanzata della riga
                           di comando (readline)
  -o, --output=NOME_FILE   reindirizza i risultati al file specificato
                           (oppure |pipe)
  -q, --quiet              esegui in modo silenzioso (nessun messaggio, solo
                           risultati query)
  -s, --single-step        modalità passo singolo (conferma ogni query)
  -S, --single-line        modalità riga singola (la fine riga termina
                           il comando SQL)

Opzioni formato output:
  -A, --no-align           modo output tabelle disallineato
  -F, --field-separator=STRINGA
                           separatore di campo per output non allineato
                           (default: "|")
  -H, --html               modo output tabelle in HTML
  -P, --pset=VAR[=ARG]     imposta l'opzione di stampa VAR ad ARG (vedi anche
                           il comando \pset)
  -R, --record-separator=STRINGa
                           separatore di record per output non allineato
                           (default: "a capo")
  -t, --tuples-only        mostra solo le righe
  -T, --table-attr=TESTO   imposta gli attributi delle tabelle HTML
                           (es: larghezza, bordo)
  -x, --expanded           attiva output tabelle espanso
  -z, --field-separator-zero
                           usa il byte zero come separatore di campo per l'output
                           non allineato
  -0, --record-separator-zero
                           usa il byte zero come separatore di record per l'output
                           non allineato

Opzioni di connessione:
  -h, --host=HOSTNAME      host server del database o directory socket
                           (default: "sockect locale")
  -p, --port=PORTA         porta di ascolto del database (default: "5432")
  -U, --username=UTENTE    nome utente del database (default: "fesposit")
  -w, --no-password        non chiedere mai le password
  -W, --password           forza la richiesta di una password (dovrebbe essere
                           automatico)
</pre>
 *
 */
public class PsqlCommandLineBuilder {
	
	private static final String PSQL_EXECUTABLE = "psql ";
	//private static final String PSQL_EXECUTABLE_NO_TRUST = "PGPASSWORD=${PGPASSWORD} psql ";
	private static final String FILE_ARGOMENT = "${FILE}";
	private static final String FILE_SUBSTITUTION_STRING = "FILE";
	private static final String PASSWORD_SUBSTITUTION_STRING = "PGPASSWORD";
	
	protected File file;
	protected String fileAsString;
	private String password;
	protected List<String> argomenti;
	protected Map<String, Object> substitutionMap;
	protected boolean trustMode;
	
	/**
	 * Costruttore della classe. Accetta in ingresso i parametri obbligatori per costruire un comando psql.
	 * 
	 * @param file è il nome del file rappresentante lo script SQL
	 * @param trustMode specifica se il database è in trust (non c'è bisogno di specificare la password) oppure no.
	 */
	public PsqlCommandLineBuilder(String file, boolean trustMode) {
		super();
		Assert.hasLength(file, "File MUST not be empty!");
		this.fileAsString = file;
		this.trustMode = trustMode;
		this.argomenti = new ArrayList<>();
		this.argomenti.add(PsqlArgument.FILE.argument());
		this.argomenti.add(PsqlCommandLineBuilder.FILE_ARGOMENT);
		this.substitutionMap = new HashMap<>();
		this.substitutionMap.put(PsqlCommandLineBuilder.FILE_SUBSTITUTION_STRING, file);
	}
	
	/**
	 * Costruttore della classe. Accetta in ingresso i parametri obbligatori per costruire un comando psql.
	 * 
	 * @param file è il file dello script SQL
	 * @param trustMode specifica se il database è in trust (non c'è bisogno di specificare la password) oppure no.
	 */
	public PsqlCommandLineBuilder(File file, boolean trustMode) {
		super();
		Assert.notNull(file, "File MUST not be null!");
		this.file = file;
		this.trustMode = trustMode;
		this.argomenti = new ArrayList<>();
		this.argomenti.add(PsqlArgument.FILE.argument());
		this.argomenti.add(PsqlCommandLineBuilder.FILE_ARGOMENT);
		this.substitutionMap = new HashMap<>();
		this.substitutionMap.put(PsqlCommandLineBuilder.FILE_SUBSTITUTION_STRING, file);
	}
	
	/**
	 * Costruisce una nuova {@link CommandLine} con gli argomenti impostati.
	 * 
	 * @return {@link CommandLine}
	 */
	public CommandLine build() {
		if(!this.trustMode && StringUtils.hasText(this.password)) {
			try {
				System.setProperty(PASSWORD_SUBSTITUTION_STRING, this.password);
			} catch( SecurityException e ) {
				//do nothing... potremmo loggare della roba?
			}
		}
		CommandLine commandLine = CommandLine.parse(PsqlCommandLineBuilder.PSQL_EXECUTABLE);
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
	 * Imposta il parametro per specificare il nome del database.
	 * 
	 * @param databaseName è il nome del database.
	 * 
	 * @return {@link PsqlCommandLineBuilder}
	 */
	public PsqlCommandLineBuilder databaseName(String databaseName) {
		Assert.isTrue(StringUtils.hasText(databaseName), "Database name MUST not be empty!");
		return this.setArgument(PsqlArgument.DATABASE_NAME, databaseName);
	}
	
	/**
	 * Imposta il parametro per specificare l'hostname del database.
	 * 
	 * @param databaseHostname è l'hostname.
	 * 
	 * @return {@link PsqlCommandLineBuilder}
	 */
	public PsqlCommandLineBuilder databaseHostname(String databaseHostname) {
		Assert.isTrue(StringUtils.hasText(databaseHostname), "Database hostname MUST not be empty!");
		return this.setArgument(PsqlArgument.DATABASE_HOSTNAME, databaseHostname);
	}
	
	/**
	 * Imposta il parametro per specificare il numero di porto del database.
	 * 
	 * @param databasePort è il numero di porto.
	 * 
	 * @return {@link PsqlCommandLineBuilder}
	 */
	public PsqlCommandLineBuilder databasePort(String databasePort) {
		Assert.isTrue(StringUtils.hasText(databasePort), "Database port MUST not be empty!");
		return this.setArgument(PsqlArgument.DATABASE_PORT, databasePort);
	}
	
	/**
	 * Imposta il parametro per specificare lo username del database.
	 * 
	 * @param databaseUsername è lo username del database.
	 * 
	 * @return {@link PsqlCommandLineBuilder}
	 */
	public PsqlCommandLineBuilder databaseUsername(String databaseUsername) {
		Assert.isTrue(StringUtils.hasText(databaseUsername), "Database username MUST not be empty!");
		return this.setArgument(PsqlArgument.DATABASE_USERNAME, databaseUsername);
	}
	
	/**
	 * Imposta il parametro per specificare la password del database.
	 * 
	 * @param databasePassword è la password del database.
	 * 
	 * @return {@link PsqlCommandLineBuilder}
	 */
	public PsqlCommandLineBuilder databasePassword(String databasePassword) {
		Assert.isTrue(!this.trustMode, "Trust mode MUST be false in order to set password!");
		Assert.isTrue(StringUtils.hasText(databasePassword), "Database password MUST not be empty!");
		//dobbiamo impostare solo la stringa di sostituzione
		this.password = databasePassword;
		return this;
	}
	
	/**
	 * Imposta il parametro per specificare di non richiedere la password del database.
	 * 
	 * @return {@link PsqlCommandLineBuilder}
	 */
	public PsqlCommandLineBuilder noPassword() {
		return setArgument(PsqlArgument.NO_PASSWORD);
	}
	
	/**
	 * Metodo privato per impostare un argomento che non prevede valori.
	 * 
	 * @param argument è il tipo di argomento.
	 * 
	 * @return {@link Shp2PgsqlCommandLineBuilder}
	 */
	private PsqlCommandLineBuilder setArgument(PsqlArgument argument) {
		Assert.notNull(argument, "PsqlArgument MUST not be null!");
		this.argomenti.add(argument.argument());
		return this;
	}
	
	/**
	 * Metodo privato per impostare un argomento con un determinato valore.
	 * 
	 * @param argument è il tipo di argomento
	 * @param value è il valore dell'argomento
	 * 
	 * @return {@link Shp2PgsqlCommandLineBuilder}
	 */
	private PsqlCommandLineBuilder setArgument(PsqlArgument argument, String value) {
		Assert.notNull(argument, "PsqlArgument MUST not be null!");
		Assert.isTrue(StringUtils.hasText(value), "Value for PsqlArgument MUST not be empty!");
		this.argomenti.add(argument.argument());
		this.argomenti.add(value);
		return this;
	}
	
}
