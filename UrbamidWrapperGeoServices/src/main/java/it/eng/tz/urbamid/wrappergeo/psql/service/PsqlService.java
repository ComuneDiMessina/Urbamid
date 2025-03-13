package it.eng.tz.urbamid.wrappergeo.psql.service;

import java.io.File;

import it.eng.tz.urbamid.wrappergeo.exception.PsqlException;

/**
 * Interfaccia per la definizione dei metodi esposti dal service per l'esecuzione di script SQL.
 */
public interface PsqlService {

	/**
	 * Metodo che esegue uno script SQL tramite PSQL.
	 * N.B. I parametri necessari di connessione vengono recuperati dall'environment di Spring.
	 * 
	 * @param file è il file relativo allo script da eseguire.
	 * 
	 * @return il risultato dell'esecuzione dello script
	 * 
	 * @throws CatastoServiceException
	 */
	String eseguiScriptSQL(String file) throws PsqlException;
	
	/**
	 * Metodo che esegue uno script SQL tramite PSQL.
	 * N.B. I parametri necessari di connessione vengono recuperati dall'environment di Spring.
	 * 
	 * @param file è il file relativo allo script da eseguire.
	 * 
	 * @throws CatastoServiceException
	 */
	String eseguiScriptSQL(File file) throws PsqlException;
	
	/**
	 * Metodo che esegue uno script SQL tramite PSQL.
	 * 
	 * @param file è lo script SQL
	 * @param host è l'hostname del database
	 * @param database è il nome del database
	 * @param port è il numero di porto del database
	 * @param user è lo username
	 * 
	 * @return il risultato dell'esecuzione dello script
	 * 
	 * @throws PsqlException
	 */
	String eseguiScriptSQL(String file, String host, String database, String port, String user) throws PsqlException;

	
	
	/**
	 * Metodo che esegue uno script SQL tramite PSQL.
	 * 
	 * @param file è lo script SQL
	 * @param host è l'hostname del database
	 * @param database è il nome del database
	 * @param port è il numero di porto del database
	 * @param user è lo username
	 * 
	 * @return il risultato dell'esecuzione dello script
	 * 
	 * @throws PsqlException
	 */
	String eseguiScriptSQL(File file, String host, String database, String port, String user) throws PsqlException;
	
}
