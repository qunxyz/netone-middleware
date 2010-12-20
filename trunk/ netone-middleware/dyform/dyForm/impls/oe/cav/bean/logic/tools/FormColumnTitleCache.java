package oe.cav.bean.logic.tools;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class FormColumnTitleCache {
	static Map formColumn = new Hashtable();

	public static void addCache(Map cache, String formcode) {
		formColumn.put(formcode, cache);
	}

	public static Map getCache(String formcode) {
		return (Map) formColumn.get(formcode);
	}

	public static boolean hasCache(String formcode) {
		return false;
	}

	public static void removeCache(String formcode) {
		formColumn.remove(formcode);
	}
}
