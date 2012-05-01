package oe.env.client;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import oe.rmi.client.RmiEntry;

public class Test {

	public static void main(String[] args) throws MalformedURLException,
			RemoteException, NotBoundException {
		EnvService evn = (EnvService) RmiEntry.iv("envinfo");
	
		// ���dy��Ե�ַ
		System.out.println(evn.fetchEnvValue("$dyform*"));
		// ���wf��Ե�ַ
		System.out.println(evn.fetchEnvValue("$workflow*"));
		// ���css��Ե�ַ
		System.out.println(evn.fetchEnvValue("$css*"));
		// ���rs��Ե�ַ
		System.out.println(evn.fetchEnvValue("$resource*"));

		String[][] systeminfo = evn.fetchAllEnvSystemInfo();
		for (int i = 0; i < systeminfo.length; i++) {
			System.out.println(systeminfo[i][0] + "  " + systeminfo[i][1]);
		}
		System.out.println("%{workflow".matches("\\%\\{.*\\}"));

	}

}
