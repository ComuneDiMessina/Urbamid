package it.eng.tz.urbamid.wrappergeo.shp2pgsql;

import java.util.Optional;

/**
 * Enumerazione che astrae tutti gli argomenti che si possono impostare con shp2psql. 
 */
public enum Shp2PgsqlArgument {
	
	SRID(" -s ", "${SRID}"),
	GEOCOLUMN(" -g ", "${GEOCOLUMN}"),
	USE_POSTGRESQL_DUMP_FORMAT(" -D ", null),
	EXECUTE_STATEMENT_INDIVIDUALLY(" -e ", null),
	USE_GEOGRAPHY_TYPE(" -G ", null),
	KEEP_POSTGRESQL_IDENTIFIERS_CASE(" -k ", null),
	USE_INT4_TYPE_FOR_INTEGER_DBF_FIELDS(" -i ", null),
	CREATE_SPATIAL_INDEX_ON_GEOCOLUMN(" -I ", null),
	MAPPING_NAME_FILE(" -m ", "${MAPPING_FILE_NAME}"),
	SIMPLE_GEOMETRIES(" -S ", null),
	DIMENSIONALITY(" -t ", "${DIMENSIONALITY}"),
	WKT_INSTEAD_OF_WKB(" -w ", null),
	ENCODING(" -W ", "${ENCODING}"),
	NULL_GEOMETRIES(" -N ", "{POLICY}"),
	ONLY_DBF(" -n ", null),
	TABLESPACE(" -T ", "${TABLESPACE}"),
	TABLESPACE_FOR_INDEXES(" -X ", "${TABLESPACE_INDEX}"),
	HELP(" -? ", null);
	
	/**
	 * E' l'argomento di shp2pgsql
	 */
	private String argument;
	
	/**
	 * E' la string di sostituzione, ovvero la stringa che verrà usata per il replacing dei parametri
	 * coi i rispettivi valori.
	 */
	private String substitutionString;
	
	private Shp2PgsqlArgument(String argument, String substitutionString) {
		this.argument = argument;
		this.substitutionString = substitutionString;
	}
	
	public String argument() {
		if( this.substitutionString().isPresent() )
			return this.argument.concat(this.substitutionString);
		else
			return this.argument;
	}
	
	public Optional<String> substitutionString() {
		if(this.substitutionString == null) {
			return Optional.empty();
		}
		return Optional.of(this.substitutionString.substring(2, this.substitutionString.length()-1));	//TODO migliorare
	}
	
}