package it.eng.tz.urbamid.profilemanager.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CommonUtil {

	
	
	public static boolean listIsEmpty(List list) {
		return (list==null || (list!=null && list.size()==0));
	}
	
	public static boolean listIsNotEmpty(List list) {
		return !listIsEmpty(list);
	}
	
	public static boolean listIsNotEmpty(Set set) {
		return set!=null && !listIsEmpty(new ArrayList<Object>(set));
	}
	
}
