package oe.netone.bi;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

public class DataBI {
	public String AddCatalog(String naturalname,String cname,String extendattribute,String nameid){
	ResourceRmi rsrmi = null;
	Serializable idcreated=null;
	  
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
	      
			UmsProtectedobject upo = new UmsProtectedobject();
			upo.setNaturalname(naturalname);
            upo.setName(cname);
			upo.setActionurl("");
			upo.setExtendattribute(extendattribute);
			upo.setObjecttype("etl");
			upo.setDescription("");
	try {
		  idcreated = rsrmi.addResource(upo,nameid);
		
		 
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return idcreated.toString();
	}
	public String AddCatalog1(String naturalname,String cname,String extendattribute,String nameID){
		ResourceRmi rsrmi = null;
		Serializable idcreated=null;
		  
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
		      
				UmsProtectedobject upo = new UmsProtectedobject();
				upo.setNaturalname(naturalname);
	            upo.setName(cname);
				upo.setActionurl("");
				upo.setExtendattribute(extendattribute);
				upo.setObjecttype("trans");
				upo.setDescription("");
		try {
			  idcreated = rsrmi.addResource(upo,nameID);
			
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return idcreated.toString();
		}
}
