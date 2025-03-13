package it.eng.tz.urbamid.core.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Class with util method for string
 * @author Antonio La Gatta
 * @date 02 mag 2017
 * @version 1.0
 */
public abstract class StringUtil {
	/**
	 * @author Antonio La Gatta
	 * @date 02 mag 2017
	 * @version 1.0
	 * @param string
	 * @return true if string is null or empty
	 */
	public static boolean isEmpty(String string){
		return StringUtils.isEmpty(string);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 02 mag 2017
	 * @version 1.0
	 * @param string
	 * @return true if string is not null and not empty
	 */
	public static boolean isNotEmpty(String string){
		return !isEmpty(string);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 02 mag 2017
	 * @version 1.0
	 * @param string
	 * @return true if string is null or blank
	 */
	public static boolean isBlank(String string){
		return StringUtils.isBlank(string);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 02 mag 2017
	 * @version 1.0
	 * @param string
	 * @return true if string is not null and not blank
	 */
	public static boolean isNotBlank(String string){
		return !isBlank(string);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 02 mag 2017
	 * @version 1.0
	 * @param string
	 * @return size of string checking null
	 */
	public static int size(String string){
		return isEmpty(string) ? 0 : string.length();
	}
	/**
	 * @author Antonio La Gatta
	 * @date 08 mag 2017
	 * @version 1.0
	 * @param string
	 * @return empty string if input is null
	 */
	public static String cleanNull(String string){
		return isEmpty(string) ? "" : string;
	}
	/**
	 * @author Antonio La Gatta
	 * @version 1.0
	 * @date 28 nov 2017
	 * @param obejcts
	 * @return string result of concat of toString of parameter array
	 */
	public static String concateneString(Object... obejcts){
		if(obejcts == null 
		|| obejcts.length == 0
		)
			return null;
		final StringBuilder stringBuilder = new StringBuilder(String.valueOf(obejcts[0]));
		final int           size          = obejcts.length;
		for(int i = 1 ; i < size ; i++){
			stringBuilder.append(obejcts[i]);
		}
		return stringBuilder.toString();
	}

}
