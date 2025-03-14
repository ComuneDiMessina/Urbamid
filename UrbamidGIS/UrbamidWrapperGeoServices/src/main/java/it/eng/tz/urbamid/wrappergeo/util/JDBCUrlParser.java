package it.eng.tz.urbamid.wrappergeo.util;

import java.util.Optional;

public class JDBCUrlParser {

	private static final String START_JDBC_URL_POSTGRESQL = "jdbc:postgresql://";
	private static final String SLASH = "/";
	private static final String COLON = ":";
	
	/**
	 * Costruttore privato che evita che la classe venga allocata.
	 * E' una classe 
	 */
	private JDBCUrlParser() {
		super();
	}
	
	/**
	 * Metodo che elabora una jdbc url per postgres nel formato:
	 * 
	 *  jdbc:postgresql://localhost:5432/dbUrbamid
	 * 
	 * E restituisce un oggetto che incapsula le informazioni riguardanti l'host, il nome del database e 
	 * il numero di porto.
	 * 
	 * @param url è la stringa della connessione jdbc.
	 * 
	 * @return un oggetto {@link JDBCUrl} che incapsula i dati riguardanti host, database e port.
	 */
    public static Optional<JDBCUrl> parseConnectionUrl( String url ) {
    	JDBCUrl oggettoDaRestituire = null;
    	if (url.startsWith(START_JDBC_URL_POSTGRESQL)) {
    		url = url.substring(START_JDBC_URL_POSTGRESQL.length());
    		String[] shoryukens = url.split(SLASH);
    		if(shoryukens.length == 2) {
    			String[] hadokens = shoryukens[0].split(COLON);
    			if( hadokens.length == 2) {
    				oggettoDaRestituire = new JDBCUrl(hadokens[0], shoryukens[1], hadokens[1]);
    			}
    		}
        }
    	return Optional.ofNullable(oggettoDaRestituire);
    }
	
}