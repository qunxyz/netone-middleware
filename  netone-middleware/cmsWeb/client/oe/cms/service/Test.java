package oe.cms.service;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import oe.rmi.client.RmiEntry;

public class Test {

	public static void main(String[] args) {
		try {
			CmsService serv = (CmsService) RmiEntry.iv("cmshandle");
			serv.addpage("PORTALPG.PORTALPG.#DYFORM.DY_761205591318855.CFA45854F2A211DCAFD611322478B28D ", "abc", "abc", "mike",
					"100", "200");
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

}
