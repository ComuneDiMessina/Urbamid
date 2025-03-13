package it.eng.tz.urbamid.core.util;

import java.util.Map;

/**
 * Class with util method for map
 * @author Antonio La Gatta
 * @date 02 mag 2017
 * @version 1.0
 */
public abstract class MapUtil {
	/**
	 * @author Antonio La Gatta
	 * @date 02 mag 2017
	 * @version 1.0
	 * @param map
	 * @return true if map is null or empty
	 */
	public static boolean isEmpty(Map<?,?> map){
		return map == null || map.isEmpty();
	}
	/**
	 * @author Antonio La Gatta
	 * @date 02 mag 2017
	 * @version 1.0
	 * @param map
	 * @return true if map is not null and not empty
	 */
	public static boolean isNotEmpty(Map<?,?> map){
		return !isEmpty(map);
	}

}
