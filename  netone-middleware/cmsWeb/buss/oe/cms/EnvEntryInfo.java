package oe.cms;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import oe.env.client.EnvService;
import oe.rmi.client.RmiEntry;

public class EnvEntryInfo {

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

	public static void main(String[] args) throws RemoteException {
		//String[][] xxx = env.fetchAllEnvSystemInfo();
		//System.out.println(env.fetchEnvValue("curl"));
	}

}
