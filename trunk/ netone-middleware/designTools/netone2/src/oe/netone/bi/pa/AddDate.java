package oe.netone.bi.pa;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;


public class AddDate {
	public String Str;
	public List  AddLogNodeDBInit(String name,String naturalname,String xml) {
			ResourceRmi rsrmi = null;
			Serializable idcreated = null;
			String cString="<?xml version='1.0' encoding='UTF-8'?>";
			xml=cString+xml;
			try {
				// 读取名为resource的rmi服务
				try {
					rsrmi = (ResourceRmi) RmiEntry.iv("resource");
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (NotBoundException e) {
				e.printStackTrace();
			}
			
			UmsProtectedobject upo=null;
			try {
				System.out.println(name);
				upo = rsrmi.loadResourceByNatural(naturalname);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			upo.setExtendattribute(xml);
			try {
				idcreated = rsrmi.updateResource(upo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
	}
	public String  Updata(String naturalname,String xml) {
		ResourceRmi rsrmi = null;
		Serializable idcreated = null;
		String cString="<?xml version='1.0' encoding='UTF-8'?>";
		xml=cString+xml;
		try {
			// 读取名为resource的rmi服务
			try {
				rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
		UmsProtectedobject upo=null;
		try {
			upo = rsrmi.loadResourceByNatural(naturalname);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		upo.setActionurl(xml);
		try {
			idcreated = rsrmi.updateResource(upo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "succeed";
}
}