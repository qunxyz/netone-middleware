package oe.netone.bi.gd;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

public class GraphicsDisplaySelect {
	public ObjectGraph ConfigurationData(String name){
		ObjectGraph OGXML = null;
		ResourceRmi resourceRmi = null;
		try {
			resourceRmi=(ResourceRmi) RmiEntry.iv("resource");
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
		UmsProtectedobject upof=null;
		try {
			upof = resourceRmi.loadResourceByNatural(name);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String obl=upof.getExtendattribute();
	     if(obl!="" || obl!=null){
		GraphicXML GX=new GraphicXML();
		obl=obl.trim();
		OGXML=GX.readXML(obl);
	     }
		return OGXML;
	}
}
