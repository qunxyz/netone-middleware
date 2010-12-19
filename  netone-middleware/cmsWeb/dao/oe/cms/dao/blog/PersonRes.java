package oe.cms.dao.blog;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import oe.env.client.EnvService;
import oe.rmi.client.RmiEntry;

public class PersonRes {

	/**
	 * @param args
	 */
	public static String fetchCondition(String paritcipant) {
		EnvService env;
		String boss = "";
		try {
			env = (EnvService) RmiEntry.iv("envinfo");
			boss = env.fetchEnvValue("boss");
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
		return " and (participant='" + boss + "' or participant='"
				+ paritcipant + "') ";
	}

}
