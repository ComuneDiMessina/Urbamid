package it.eng.tz.urbamid.administration.util;

/**
 * Interfaccia che espone il metodo per popolare i valori di un bean in maniera
 * custom
 */
public interface IBeanPopulate<S, D>
{// S source D Destination

	/**
	 * converte bean di hibernate in quello per la pagina
	 * 
	 * @param src
	 */
	public D convertdbToPage(S src) throws Exception;

	/**
	 * converte bean di pagina in quello per hibernate
	 * 
	 * @param src
	 */
	public S convertpageToDb(D src) throws Exception;

}
