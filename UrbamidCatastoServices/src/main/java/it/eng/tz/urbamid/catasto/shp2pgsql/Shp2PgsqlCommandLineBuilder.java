package it.eng.tz.urbamid.catasto.shp2pgsql;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.exec.CommandLine;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Classe Builder per la generazione di un {@link CommandLine} per l'esecuzione di un comando con
 * shp2psql. Shp2psql è un tool per la conversione di uno shapefile in una lista di statement sql per l'import
 * all'interno del database. L'utilizzo di shp2psql è riportato nel seguito ed i metodi di questa
 * classe non fanno altro che ricalcare i parametri per facilitare la costruzione del comando.
<pre>
USAGE: shp2pgsql [<options>] <shapefile> [[<schema>.]<table>]
OPTIONS:
  -s [<from>:]<srid> Set the SRID field. Defaults to 0.
      Optionally reprojects from given SRID (cannot be used with -D).
 (-d|a|c|p) These are mutually exclusive options:
     -d  Drops the table, then recreates it and populates
         it with current shape file data.
     -a  Appends shape file into current table, must be
         exactly the same table schema.
     -c  Creates a new table and populates it, this is the
         default if you do not specify any options.
     -p  Prepare mode, only creates the table.
  -g <geocolumn> Specify the name of the geometry/geography column
      (mostly useful in append mode).
  -D  Use postgresql dump format (defaults to SQL insert statements).
  -e  Execute each statement individually, do not use a transaction.
      Not compatible with -D.
  -G  Use geography type (requires lon/lat data or -s to reproject).
  -k  Keep postgresql identifiers case.
  -i  Use int4 type for all integer dbf fields.
  -I  Create a spatial index on the geocolumn.
  -m <filename>  Specify a file containing a set of mappings of (long) column
     names to 10 character DBF column names. The content of the file is one or
     more lines of two names separated by white space and no trailing or
     leading space. For example:
         COLUMNNAME DBFFIELD1
         AVERYLONGCOLUMNNAME DBFFIELD2
  -S  Generate simple geometries instead of MULTI geometries.
  -t <dimensionality> Force geometry to be one of '2D', '3DZ', '3DM', or '4D'
  -w  Output WKT instead of WKB.  Note that this can result in
      coordinate drift.
  -W <encoding> Specify the character encoding of Shape's
      attribute column. (default: "UTF-8")
  -N <policy> NULL geometries handling policy (insert*,skip,abort).
  -n  Only import DBF file.
  -T <tablespace> Specify the tablespace for the new table.
      Note that indexes will still use the default tablespace unless the
      -X flag is also used.
  -X <tablespace> Specify the tablespace for the table's indexes.
      This applies to the primary key, and the spatial index if
      the -I flag is used.
  -?  Display this help screen.
</pre>
 */
public class Shp2PgsqlCommandLineBuilder {
	
	private static final String SHP_2_PSQL_EXECUTABLE = "shp2pgsql ${STATEMENT_MODE} ${SHAPEFILE} ${SCHEMA}.${TABLE}";
	private static final String SHAPEFILE_ARGOMENT = "${SHAPEFILE}";
	private static final String SHAPEFILE_SUBSTITUTION_STRING = "SHAPEFILE";
	private static final String SCHEMA_SUBSTITUTION_STRING = "SCHEMA";
	private static final String TABLE_SUBSTITUTION_STRING = "TABLE";
	private static final String STATEMENT_MODE_SUBSTITUTION_STRING = "STATEMENT_MODE";
	public static final String DEFAULT_SRID = "4326";
	public static final String DEFAULT_ENCODING = "UTF-8";
	
	protected String shapefileAsString;
	protected File shapefile;
	protected String schema;
	protected String table;
	protected List<String> argomenti;
	protected Map<String, Object> substitutionMap;
	
	/**
	 * Costruttore della classe. Accetta in ingresso i parametri obbligatori per costruire un comando shp2pgsql.
	 * 
	 * @param shapefile è il path dello shapefile
	 * @param schema è lo schema del database in cui importare i dati
	 * @param table è la tabella del database in cui importare i dati
	 */
	public Shp2PgsqlCommandLineBuilder(String shapefile, String schema, String table) {
		super();
		Assert.hasLength(shapefile, "Shapefile MUST not be empty!");
		Assert.isTrue(StringUtils.hasText(schema), "Schema MUST not be empty!");
		Assert.isTrue(StringUtils.hasText(table), "Table MUST not be empty!");
		this.shapefileAsString = shapefile;
		this.schema = schema;
		this.table = table;
		this.argomenti = new ArrayList<>();
		this.substitutionMap = new HashMap<>();
		this.substitutionMap.put(Shp2PgsqlCommandLineBuilder.SHAPEFILE_SUBSTITUTION_STRING, shapefile);
		this.substitutionMap.put(Shp2PgsqlCommandLineBuilder.SCHEMA_SUBSTITUTION_STRING, schema);
		this.substitutionMap.put(Shp2PgsqlCommandLineBuilder.TABLE_SUBSTITUTION_STRING, table);
	}
	
	/**
	 * Costruttore della classe. Accetta in ingresso i parametri obbligatori per costruire un comando shp2pgsql.
	 * 
	 * @param shapefile è il file dello shapefile
	 * @param schema è lo schema del database in cui importare i dati
	 * @param table è la tabella del database in cui importare i dati
	 */
	public Shp2PgsqlCommandLineBuilder(File shapefile, String schema, String table) {
		super();
		Assert.notNull(shapefile, "Shapefile MUST not be null!");
		Assert.isTrue(StringUtils.hasText(schema), "Schema MUST not be empty!");
		Assert.isTrue(StringUtils.hasText(table), "Table MUST not be empty!");
		this.shapefile = shapefile;
		this.schema = schema;
		this.table = table;
		this.argomenti = new ArrayList<>();
		this.argomenti.add(Shp2PgsqlCommandLineBuilder.SHAPEFILE_ARGOMENT);
		this.substitutionMap = new HashMap<>();
		this.substitutionMap.put(Shp2PgsqlCommandLineBuilder.SHAPEFILE_SUBSTITUTION_STRING, shapefile);
		this.substitutionMap.put(Shp2PgsqlCommandLineBuilder.SCHEMA_SUBSTITUTION_STRING, schema);
		this.substitutionMap.put(Shp2PgsqlCommandLineBuilder.TABLE_SUBSTITUTION_STRING, table);
	}
	
	/**
	 * Costruisce una nuova {@link CommandLine} con gli argomenti impostati.
	 * 
	 * @return {@link CommandLine}
	 */
	public CommandLine build() {
		CommandLine commandLine = CommandLine.parse(Shp2PgsqlCommandLineBuilder.SHP_2_PSQL_EXECUTABLE);
		if( this.argomenti != null && !this.argomenti.isEmpty() ) {
			String[] arrayArgomenti = new String[argomenti.size()];
			for(int i=0; i<this.argomenti.size(); i++) {
				arrayArgomenti[i] = this.argomenti.get(i);
			}
			commandLine.addArguments(arrayArgomenti);
		}
		if( this.substitutionMap != null && this.substitutionMap.size() > 0 ) {
			if(!this.substitutionMap.containsKey(Shp2PgsqlCommandLineBuilder.STATEMENT_MODE_SUBSTITUTION_STRING)) {
				this.substitutionMap.put(Shp2PgsqlCommandLineBuilder.STATEMENT_MODE_SUBSTITUTION_STRING, "-a");
			}
			commandLine.setSubstitutionMap(this.substitutionMap);
		}
		return commandLine;
	}
	
	/**
	 * Imposta il parametro per specificare il valore per l'SRID.
	 * 
	 * @param srid è il valore dell'SRID (default 4326).
	 * 
	 * @return {@link Shp2PgsqlCommandLineBuilder}
	 */
	public Shp2PgsqlCommandLineBuilder srid(String srid) {
		if(!StringUtils.hasText(srid)) {
			srid = DEFAULT_SRID;
		}
		return this.setArgument(Shp2PgsqlArgument.SRID, srid);
	}
	
	/**
	 * Imposta l'argomento per la modalità con cui verranno creati gli statement SQL (append, drop and create etc...).
	 * 
	 * @param mode {@link Shp2PgsqlSQLStatementsMode} è la modalità di creazione degli statement SQL.
	 * 
	 * @return {@link Shp2PgsqlCommandLineBuilder}
	 */
	public Shp2PgsqlCommandLineBuilder sqlStatementMode(Shp2PgsqlSQLStatementsMode mode) {
		if(mode == null) {
			mode = Shp2PgsqlSQLStatementsMode.CREATE_NEW_TABLE;
		}
		this.substitutionMap.put(Shp2PgsqlCommandLineBuilder.STATEMENT_MODE_SUBSTITUTION_STRING, mode.argument());
		return this;
	}
	
	/**
	 * Imposta l'argomento per la colonna in cui verranno salvate le informazioni geografiche.
	 * 
	 * @param geocolumn è il nome della colonna in cui verranno salvate le informazioni geografiche.
	 * 
	 * @return {@link Shp2PgsqlCommandLineBuilder}
	 */
	public Shp2PgsqlCommandLineBuilder geocolumn(String geocolumn) {
		Assert.isTrue(StringUtils.hasText(geocolumn), "Geocolumn name MUST not be empty!");
		return this.setArgument(Shp2PgsqlArgument.GEOCOLUMN, geocolumn);
	}
	
	/**
	 * Imposta il parametro per specificare di usare il formato di dump di PostgreSQL.
	 * 
	 * @return {@link Shp2PgsqlCommandLineBuilder}
	 */
	public Shp2PgsqlCommandLineBuilder usePostgresqlDumpFormat() {
		return this.setArgument(Shp2PgsqlArgument.USE_POSTGRESQL_DUMP_FORMAT);
	}
	
	/**
	 * Imposta il parametro per specificare di eseguire ogni statement creato in maniera individuale e non in un'unica transazione.
	 * 
	 * @return {@link Shp2PgsqlCommandLineBuilder}
	 */
	public Shp2PgsqlCommandLineBuilder executeStatementIndividually() {
		return this.setArgument(Shp2PgsqlArgument.EXECUTE_STATEMENT_INDIVIDUALLY);
	}
	
	/**
	 * Imposta il parametro per specificare di usare il tipo geography.
	 * 
	 * @return {@link Shp2PgsqlCommandLineBuilder}
	 */
	public Shp2PgsqlCommandLineBuilder useGeographyType() {
		return this.setArgument(Shp2PgsqlArgument.USE_GEOGRAPHY_TYPE);
	}
	
	/**
	 * Imposta il parametro -k
	 * 
	 * @return {@link Shp2PgsqlCommandLineBuilder}
	 */
	public Shp2PgsqlCommandLineBuilder keepPostgresqlIdentifiersCase() {
		return this.setArgument(Shp2PgsqlArgument.KEEP_POSTGRESQL_IDENTIFIERS_CASE);
	}
	
	/**
	 * Imposta il parametro -i
	 * 
	 * @return {@link Shp2PgsqlCommandLineBuilder}
	 */
	public Shp2PgsqlCommandLineBuilder useInt4TypeForIntegerDBFFields() {
		return this.setArgument(Shp2PgsqlArgument.USE_INT4_TYPE_FOR_INTEGER_DBF_FIELDS);
	}
	
	/**
	 * Imposta il parametro per specificare di creare un indice sulla colonna della geografia.
	 * 
	 * @return {@link Shp2PgsqlCommandLineBuilder}
	 */
	public Shp2PgsqlCommandLineBuilder createSpatialIndexOnGeocolumn() {
		return this.setArgument(Shp2PgsqlArgument.CREATE_SPATIAL_INDEX_ON_GEOCOLUMN);
	}
	
	/**
	 * Imposta il parametro per specificare il file con il mapping dei campi.
	 * 
	 * @param mappingNameFile è il file di mapping.
	 * 
	 * @return {@link Shp2PgsqlCommandLineBuilder}
	 */
	public Shp2PgsqlCommandLineBuilder mappingNameFile(String mappingNameFile) {
		Assert.isTrue(StringUtils.hasText(mappingNameFile), "Mapping file MUST not be empty!");
		return this.setArgument(Shp2PgsqlArgument.MAPPING_NAME_FILE, mappingNameFile);
	}
	
	/**
	 * Imposta il parametro per la generazione di geometrie semplici.
	 * 
	 * @return {@link Shp2PgsqlCommandLineBuilder}
	 */
	public Shp2PgsqlCommandLineBuilder simpleGeometries() {
		return this.setArgument(Shp2PgsqlArgument.SIMPLE_GEOMETRIES);
	}
	
	/**
	 * Imposta il parametro per la dimensionalità.
	 * 
	 * @param dimensionality è la dimensionalità.
	 * 
	 * @return {@link Shp2PgsqlCommandLineBuilder}
	 */
	public Shp2PgsqlCommandLineBuilder dimensionality(String dimensionality) {
		Assert.isTrue(StringUtils.hasText(dimensionality), "Dimensionality MUST not be empty!");
		return this.setArgument(Shp2PgsqlArgument.DIMENSIONALITY, dimensionality);
	}
	
	/**
	 * Imposta il parametro -w
	 * 
	 * @return {@link Shp2PgsqlCommandLineBuilder}
	 */
	public Shp2PgsqlCommandLineBuilder wktInsteadOfWkb() {
		return this.setArgument(Shp2PgsqlArgument.WKT_INSTEAD_OF_WKB);
	}
	
	/**
	 * Imposta il parametro per specificare l'encoding degli script sql generati.
	 * 
	 * @param encoding è l'encoding (default UTF-8)
	 * 
	 * @return {@link Shp2PgsqlCommandLineBuilder}
	 */
	public Shp2PgsqlCommandLineBuilder encoding(String encoding) {
		if(!StringUtils.hasText(encoding)) {
			encoding = DEFAULT_ENCODING;
		}
		return this.setArgument(Shp2PgsqlArgument.ENCODING, encoding);
	}
	
	/**
	 * Imposta il parametro per la policy sulle geometrie nulle.
	 * 
	 * @param policy è la policy.
	 * 
	 * @return {@link Shp2PgsqlCommandLineBuilder}
	 */
	public Shp2PgsqlCommandLineBuilder nullGeometries(String policy) {
		Assert.isTrue(StringUtils.hasText(policy), "Policy for null geometries MUST not be empty!");
		return this.setArgument(Shp2PgsqlArgument.NULL_GEOMETRIES, policy);
	}
	
	/**
	 * Imposta il parametro -n
	 * 
	 * @return {@link Shp2PgsqlCommandLineBuilder}
	 */
	public Shp2PgsqlCommandLineBuilder onlyDbf() {
		return this.setArgument(Shp2PgsqlArgument.ONLY_DBF);
	}
	
	/**
	 * Imposta il parametro per specificare la tablespace.
	 * 
	 * @param tablespace è il nome della tablespace.
	 * 
	 * @return {@link Shp2PgsqlCommandLineBuilder}
	 */
	public Shp2PgsqlCommandLineBuilder tablespace(String tablespace) {
		Assert.isTrue(StringUtils.hasText(tablespace), "Tablespace MUST not be empty!");
		return this.setArgument(Shp2PgsqlArgument.TABLESPACE, tablespace);
	}
	
	/**
	 * Imposta il parametro per specificare la tablespace per gli indici.
	 * 
	 * @param tablespaceForIndexes è il nome della tablespace per gli indici.
	 * 
	 * @return {@link Shp2PgsqlCommandLineBuilder}
	 */
	public Shp2PgsqlCommandLineBuilder tablespaceForIndexes(String tablespaceForIndexes) {
		Assert.isTrue(StringUtils.hasText(tablespaceForIndexes), "Tablespace for indexes MUST not be empty!");
		return this.setArgument(Shp2PgsqlArgument.TABLESPACE_FOR_INDEXES, tablespaceForIndexes);
	}
	
	/**
	 * Imposta il parametro per mostrare l'help di shp2pgsql.
	 * 
	 * @return {@link Shp2PgsqlCommandLineBuilder}
	 */
	public Shp2PgsqlCommandLineBuilder help() {
		return this.setArgument(Shp2PgsqlArgument.HELP);
	}

	/**
	 * Metodo privato per impostare un argomento che non prevede valori.
	 * 
	 * @param argument è il tipo di argomento.
	 * 
	 * @return {@link Shp2PgsqlCommandLineBuilder}
	 */
	private Shp2PgsqlCommandLineBuilder setArgument(Shp2PgsqlArgument argument) {
		Assert.notNull(argument, "Shp2PgsqlArgument MUST not be null!");
		this.argomenti.add(argument.argument());
		return this;
	}
	
	/**
	 * Metodo privateo per impostare un argomento con un determinato valore.
	 * 
	 * @param argument è il tipo di argomento
	 * @param value è il valore dell'argomento
	 * 
	 * @return {@link Shp2PgsqlCommandLineBuilder}
	 */
	private Shp2PgsqlCommandLineBuilder setArgument(Shp2PgsqlArgument argument, String value) {
		Assert.notNull(argument, "Shp2PgsqlArgument MUST not be null!");
		Assert.isTrue(StringUtils.hasText(value), "Value for Shp2PgsqlArgument MUST not be empty!");
		this.argomenti.add(argument.argument());
		Optional<String> optionalSubstitutionString = argument.substitutionString();
		if( optionalSubstitutionString.isPresent() )
			this.substitutionMap.put(optionalSubstitutionString.get(), value);
		else
			Assert.isTrue(false, "Substituion string MUST not be null!");
		return this;
	}
	
}
