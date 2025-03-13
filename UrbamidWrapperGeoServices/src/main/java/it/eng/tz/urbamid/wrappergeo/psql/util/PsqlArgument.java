package it.eng.tz.urbamid.wrappergeo.psql.util;

import it.eng.tz.urbamid.wrappergeo.util.PsqlCommandLineBuilder;

/**
 * Enumerazione che astrae parte degli argomenti che si possono impostare con shp2psql.
 * Nello specifico vengono elencati solo i comandi che servono al nostro caso specifico.
 * L'aggiunta di ulteriori argomenti e la gestione degli stessi nella classe {@link PsqlCommandLineBuilder}
 * viene lasciata allo scrupoloso programmatore che ne avrà necessità. 
 */
public enum PsqlArgument {
	
	//OPZIONE PER SPECIFICARE IL FILE SQL
	FILE("-f"),
	//OPZIONI DI CONNESSIONE AL DATABASE
	DATABASE_NAME("-d"),
	DATABASE_HOSTNAME("-h"),
	DATABASE_PORT("-p"),
	DATABASE_USERNAME("-U"), 
	//ALTRO
	NO_PASSWORD("-w");
	
	/**
	 * E' l'argomento di psql
	 */
	private String argument;
	
	/**
	 * Costruttore privato.
	 * 
	 * @param argument è l'argomento
	 */
	private PsqlArgument(String argument) {
		this.argument = argument;
	}
	
	/**
	 * Metodo per ottenere la stringa dell'argomento
	 * @param argument
	 */
	public String argument() {
		return this.argument;
	}

}