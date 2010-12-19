package oe.cms;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import oe.env.client.EnvService;
import oe.rmi.client.RmiEntry;

/**
 * 视图展示配置文件存储路径
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */

public class RootPath {
	public static EnvService env;

	static {
		try {
			env = (EnvService) RmiEntry.iv("envinfo");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getDatamodelpath() {
		try {
			return env.fetchEnvValue("jppScriptRoot");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public static String getHistroyCachePath() {
		try {
			return env.fetchEnvValue("historyRoot");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String getUrlRoot() {
		return "";
	}

}
