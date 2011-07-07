package oe.cav.bean.logic.tools;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class FormColumnCache {

	static Map formColumn = new Hashtable();

	public static void addCache(List cache, String formcode) {
		formColumn.put(formcode, cache);
		FormCache.initCache(formcode);
	}

	public static List getCache(String formcode) {
		if(!formColumn.containsKey(formcode)){
			
		}
		return (List) formColumn.get(formcode);
	}

	public static boolean hasCache(String formcode) {
		return formColumn.containsKey(formcode);
	}

	public static void removeCache(String formcode) {
		formColumn.remove(formcode);
		FormCache.initCache(formcode);
	}

}
