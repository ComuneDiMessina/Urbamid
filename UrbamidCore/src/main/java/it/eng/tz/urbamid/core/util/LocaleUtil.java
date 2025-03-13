package it.eng.tz.urbamid.core.util;

import java.util.Locale;

/**
 * Class with utility methods for locale
 * @author Antonio La Gatta
 * @date 09 mag 2017
 * @version 1.0
 */
public class LocaleUtil {
	/**
	 * @author Antonio La Gatta
	 * @date 09 mag 2017
	 * @version 1.0
	 * @param locale
	 * @return true if locale input is italy locale
	 */
	public static boolean isItalian(Locale locale){
		return locale.equals(Locale.ITALIAN)
			|| locale.equals(Locale.ITALY  )
			   ;
	}
	/**
	 * @author Antonio La Gatta
	 * @date 09 mag 2017
	 * @version 1.0
	 * @param locale
	 * @return true if locale input isn't italy locale
	 */
	public static boolean isNotItalian(Locale locale){
		return !isItalian(locale);
	}

}
