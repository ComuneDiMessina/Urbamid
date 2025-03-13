package it.eng.tz.urbamid.wrappergeo.shp2pgsql;

/**
 * Enumerazione che astrae le modalità con la quale vengono creati gli statement sql con
 * shp2psql. In particolare, come riporta la documentazione:
<pre>
-d  Drops the table, then recreates it and populates
it with current shape file data.
-a  Appends shape file into current table, must be
exactly the same table schema.
-c  Creates a new table and populates it, this is the
default if you do not specify any options.
-p  Prepare mode, only creates the table.
</pre>
 */
public enum Shp2PgsqlSQLStatementsMode {
	
	DROP_AND_RECREATE_TABLE("-d"),
	APPEND_INTO_CURRENT_TABLE(" -a "),
	CREATE_NEW_TABLE(" -c "),
	PREPARE_TABLE(" -p ");
	
	private String argument;
	
	private Shp2PgsqlSQLStatementsMode(String argument) {
		this.argument = argument;
	}
	
	public String argument() {
		return this.argument;
	}

}
