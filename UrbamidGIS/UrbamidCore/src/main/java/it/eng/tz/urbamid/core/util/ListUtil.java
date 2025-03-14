package it.eng.tz.urbamid.core.util;

import java.util.Collection;

/**
 * Class with util method for list
 * @author Antonio La Gatta
 * @date 02 mag 2017
 * @version 1.0
 */
public abstract class ListUtil {
	/**
	 * @author Antonio La Gatta
	 * @date 02 mag 2017
	 * @version 1.0
	 * @param list
	 * @return true if list is null or empty
	 */
	public static boolean isEmpty(Collection<?> list){
		return list == null || list.isEmpty();
	}
	/**
	 * @author Antonio La Gatta
	 * @date 02 mag 2017
	 * @version 1.0
	 * @param list
	 * @return true if list is not null and not empty
	 */
	public static boolean isNotEmpty(Collection<?> list){
		return !isEmpty(list);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 02 mag 2017
	 * @version 1.0
	 * @param list
	 * @return size of list checking null
	 */
	public static int size(Collection<?> list){
		return isEmpty(list) ? 0 : list.size();
	}
	/**
	 *
	 * @author Antonio La Gatta
	 * @date 04 mag 2017
	 * @version 1.0
	 * @param list
	 * @return
	 */
	public static Object[] asArray(Collection<?> list){
		return list == null ? null : list.toArray();
	}

}
