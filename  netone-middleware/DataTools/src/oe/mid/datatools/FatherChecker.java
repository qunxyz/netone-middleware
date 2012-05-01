package oe.mid.datatools;

import java.util.HashMap;
import java.util.Map;

/**
 * ¸¸½Úµã¼ì²é
 * 
 * @author chenjx
 * 
 */
public class FatherChecker {

	static Map map = new HashMap();

	public static String todo(String refname, String refvalue) {
		if (map.containsKey(refname + "_" + refvalue)) {
			return (String) map.get(refname + "_" + refvalue);
		}
		return null;
	}

}
