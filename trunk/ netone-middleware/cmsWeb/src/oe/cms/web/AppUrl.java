package oe.cms.web;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import oe.cms.web.common.CookiesOpe;
import oe.env.client.EnvService;
import oe.rmi.client.RmiEntry;

public class AppUrl {

	public static String url(String name) {


		EnvService env = null;

		try {

			env = (EnvService) RmiEntry.iv("envinfo");
			return env.fetchEnvValue(name);

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
		return "";
	}
}
