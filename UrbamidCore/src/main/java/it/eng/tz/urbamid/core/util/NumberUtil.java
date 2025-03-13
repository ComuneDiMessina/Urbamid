package it.eng.tz.urbamid.core.util;

import java.text.DecimalFormat;

/**
 * Class with util method for Number
 * @author Antonio La Gatta
 * @date 16 ott 2017
 * @version 1.0
 */
public abstract class NumberUtil {
	
	private static final DecimalFormat INT_FORMAT = new DecimalFormat("#,##0");
	/**
	 * @author Antonio La Gatta
	 * @version 1.0
	 * @date 16 ott 2017
	 * @param number
	 * @return number formatted
	 */
	public static String formatIntNumber(int number){
		return INT_FORMAT.format(number);
	}
	/**
	 * @author Antonio La Gatta
	 * @version 1.0
	 * @date 16 ott 2017
	 * @param number
	 * @return number formatted or empty string if number is null 
	 */
	public static String formatIntNumber(Integer number){
		return number == null ? "" : formatIntNumber(number.intValue());
	}

}
