package oe.env.client;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import oe.rmi.client.RmiEntry;

public class Test {

	public static void main(String[] args) throws MalformedURLException,
			RemoteException, NotBoundException {
		EnvService evn = (EnvService) RmiEntry.iv("envinfo");
	
		// 获得dy相对地址
		System.out.println(evn.fetchEnvValue("$dyform*"));
		// 获得wf相对地址
		System.out.println(evn.fetchEnvValue("$workflow*"));
		// 获得css相对地址
		System.out.println(evn.fetchEnvValue("$css*"));
		// 获得rs相对地址
		System.out.println(evn.fetchEnvValue("$resource*"));

		String[][] systeminfo = evn.fetchAllEnvSystemInfo();
		for (int i = 0; i < systeminfo.length; i++) {
			System.out.println(systeminfo[i][0] + "  " + systeminfo[i][1]);
		}
		System.out.println("%{workflow".matches("\\%\\{.*\\}"));

	}

}
