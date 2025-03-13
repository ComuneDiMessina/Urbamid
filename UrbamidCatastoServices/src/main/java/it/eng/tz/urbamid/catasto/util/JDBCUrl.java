package it.eng.tz.urbamid.catasto.util;

public class JDBCUrl {
	
	private String host;
	private String database;
	private String port;
	
	public JDBCUrl() {
		super();
	}
	
	public JDBCUrl(String host, String database, String port) {
		super();
		this.host = host;
		this.database = database;
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
	
}