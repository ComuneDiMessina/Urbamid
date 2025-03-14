package it.eng.tz.urbamid.catasto.shapefile.util;


/**
 * In accordo con la documentazione Pentaho a <a href="https://help.pentaho.com/Documentation/7.1/0L0/0Y0/070">questo link</a>,
 * è stata costruita l'enumeration seguente che astrae i possibili parametri che è possibile passare da linea di comando.
 * 
 * N.B.
 * Windows systems use syntax with the forward slash (“/”) and colon (“:”). 
 * If spaces are present in the option values, use single quotes (“) and double quotes (“”) to keep spaces together, 
 * for example, "-param:MASTER_HOST=192.168.1.3" "-param:MASTER_PORT=8181"
 * 
 * IN QUESTA SEDE CONSIDERIAMO SOLO LA VERSIONE LINUX DELLE OPTIONS... Estendere in futuro se dovesse servire lanciare
 * lo script su sistemi Windows.
 */
public enum Shp2PgsqlOption {

	DROPS_TABLE_IF_ALREADY_EXISTS("-d",	"Drops the table, then recreates it and populates it with current shape file data"),
	CREATE_AND_POPULATE_NEW_TABLE("-c", "Creates a new table and populates it, this is the default if you do not specify any options."),
	GEOMETRY_COLUMN("-g", "Specify the name of the geometry/geography column (mostly useful in append mode)."),
	SPATIAL_INDEX("-I", "Create a spatial index on the geocolumn."),
	ENCODING("-W", "Specify the character encoding of Shape's attribute column. (default: UTF-8)"),
	SRID("-s", "[<from>:]<srid> Set the SRID field. Defaults to 0.");
	
	private static final String START_PARAMETER = "=${";
	private static final String END_PARAMETER = "}";
	
	private String option;
	private String description;
	
	private Shp2PgsqlOption(String option, String description) {
		this.option = option;
		this.description = description;
	}

	public String option() {
		return option;
	}

	public String description() {
		return description;
	}
	
	/**
	 * Metodo che serve per costruire una option con la seguente sintassi (ad esempio):
	 * 
	 * -file=${parameterValue}
	 * 
	 * @param parameterValue è la stringa che identifica il parametro (per la sua successiva sostituzione col valore reale)
	 * 
	 * @return la stringa relativa alla option creata.
	 */
	public String buildOptionWithValue(String parameterValue) {
		return 
				new StringBuilder(this.option)
					.append(START_PARAMETER)
						.append(parameterValue)
							.append(END_PARAMETER)
								.toString();
	}

}