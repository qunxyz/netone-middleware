package oe.env;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import oe.frame.web.util.WebStr;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 功能入口
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class EnvEntry {
	private static Log _log = LogFactory.getLog(EnvEntry.class);

	private static ResourceBundle env = null;

	private static ResourceBundle jpp = null;

	private static ResourceBundle jppDemo = null;

	private static Map envMap = null;

	private static String[][] envSysteminfo = null;

	private static Map jppMap = null;

	private static Map jppDemoMap = null;

	private static String[][] jppIdName = null;

	static {
		initEnv();
	}

	public static void initEnv() {
		env = ResourceBundle.getBundle("envconfig");
		// jpp = ResourceBundle.getBundle("jpp");
		// jppDemo = ResourceBundle.getBundle("jppDemo");
		// envMap = null;
		// jppMap = null;
		// jppDemoMap = null;
		envMap = new HashMap();
		// jppMap = new HashMap();
		// jppDemoMap = new HashMap();
		// _log.info("------初始化全局环境信息----");
		// List listSysteminfo = new ArrayList();
		for (Enumeration unrm = env.getKeys(); unrm.hasMoreElements();) {
			String key = (String) unrm.nextElement();
			String value = env.getString(key);
			_log.info(key + ":" + value);
			// if (key.matches("\\%\\{.*\\}")) {
			// listSysteminfo.add(key);
			// }
			envMap.put(key, value);
		}
		//
		// envSysteminfo = new String[listSysteminfo.size()][2];
		// for (int i = 0; i < envSysteminfo.length; i++) {
		// envSysteminfo[i][0] = (String) listSysteminfo.get(i);
		// envSysteminfo[i][1] = (String) envMap.get(envSysteminfo[i][0]);
		// }
		//
		// _log.info("------初始化JPP脚本----");
		// for (Enumeration unrm = jpp.getKeys(); unrm.hasMoreElements();) {
		// String key = (String) unrm.nextElement();
		// String value = jpp.getString(key);
		// _log.debug(key + ":" + value);
		// jppMap.put(key, value);
		// }
		//
		// _log.info("------初始化JPP脚本信息DEMO----");
		// Map list = new HashMap();
		// List listNameOrder = new ArrayList();
		// for (Enumeration unrm = jppDemo.getKeys(); unrm.hasMoreElements();) {
		// String keyReal = (String) unrm.nextElement();
		// String key = WebStr.iso8859ToGBK(keyReal);
		// String[] keys = StringUtils.split(key, "$&");
		// String realKey = "";
		// String keyName = "";
		// if (keys != null) {
		// if (keys.length == 2) {
		// realKey = keys[1];
		// keyName = keys[0];
		// } else {
		// keyName = keys[0];
		// realKey = keys[0];
		// }
		// } else {
		// continue;
		// }
		// String value = jppDemo.getString(keyReal);
		// _log.debug(realKey + ":" + keyName + ":" + value);
		// jppDemoMap.put(realKey, value);
		// list.put(keyName, realKey);
		// listNameOrder.add(keyName);
		// }
		// // jppIdName = (String[][]) list.toArray(new String[0][2]);
		// String[] nameOrders = (String[]) listNameOrder.toArray(new
		// String[0]);
		// Arrays.sort(nameOrders);
		// jppIdName = new String[nameOrders.length][2];
		// for (int i = 0; i < nameOrders.length; i++) {
		// jppIdName[i][0] = (String) list.get(nameOrders[i]);
		// jppIdName[i][1] = nameOrders[i];
		// }

		// File file = new File("");
		// try {
		// String url = file.toURI().toString();
		// _log.debug(url);
		// String path = StringUtils.substringBetween(url, "file:/", "/bin/");
		// envMap.put("cpath", path);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	/**
	 * 获得JPP脚本设计模板
	 * 
	 * @param paramname
	 * @return
	 */
	public static String getJppScriptCore(String paramname) {
		if (jppMap.containsKey(paramname)) {
			return (String) jppMap.get(paramname);
		} else if (envMap.containsKey(paramname)) {
			return (String) envMap.get(paramname);
		} else {
			return null;
		}
	}

	/**
	 * 获得JPP脚本应用模板
	 * 
	 * @param paramname
	 * @return
	 */
	public static String getJppScriptDemo(String paramname) {
		String demo = (String) jppDemoMap.get(paramname);
		if (demo == null || demo.equals("")) {
			return getJppScriptCore(paramname);
		} else {
			return demo;
		}
	}

	/**
	 * 获得所有的脚本
	 * 
	 * @return
	 */
	public static String[][] getJppArr() {
		return jppIdName;
	}

	/**
	 * 获得环境参数
	 * 
	 * @param paramname
	 * @return
	 */
	public static String getEnvInfo(String paramname) {
		return (String) envMap.get(paramname);
	}

	/**
	 * 动态添加环境参数
	 * 
	 * @param paramname
	 * @param value
	 */
	public static void setEnvInfo(String paramname, String value) {
		envMap.put(paramname, value);
	}

	/**
	 * 获得所有系统信息在envconfig.properties中的所有 $nnnn*形式定义的内容
	 * 
	 * @return
	 */
	public static String[][] getEnvSystemInfo() {
		return envSysteminfo;
	}

}
