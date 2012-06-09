package oe.netone.bi;

import java.awt.List;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;


public class ConfigurationBrick {

	 public static String Configuration(String name,String natural,String extendattribute,String naturalname,String resourcename){
		 ResourceRmi rsrmi = null;
			Serializable idcreated = null;

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
			upo.setNaturalname(natural);
			upo.setName(name);
            upo.setExtendattribute(extendattribute);
		    upo.setDescription(naturalname);
			try {
				idcreated = rsrmi.addResource(upo,resourcename);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return idcreated.toString();
		 
	 }
	 public static void main(String[] args) {
   
	//Configuration("hhh","fdfd","fdffd","ssf","ss");
	Query("4028f8a13343c5660133442ea124001d");
	}
	 public String UPdata(String id,String xml){
		 ResourceRmi rsrmi = null;
			Serializable idcreated = null;

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
				upo = rsrmi.loadResourceById(id);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			upo.setExtendattribute(xml);
			try {
				  rsrmi.updateResource(upo);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return idcreated.toString();
		 
	 }
	 public static ZqApplicatioe Query(String id){
		    ResourceRmi rsrmi = null;
			Serializable idcreated = null;
			ArrayList<ZqApplicatioe> list= new ArrayList<ZqApplicatioe>();
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
					upo = rsrmi.loadResourceById(id);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String extendattribute=upo.getExtendattribute();
				Zqxml zq=new Zqxml();
				ZqApplicatioe zqapplicatioe=zq.readXML(extendattribute);
			 return zqapplicatioe;
		 
	 }
	 public String Query1(String id){
		 ResourceRmi rsrmi = null;
			Serializable idcreated = null;
			ArrayList<ZqApplicatioe> list= new ArrayList<ZqApplicatioe>();
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
					upo = rsrmi.loadResourceById(id);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String naturename=upo.getNaturalname();

          return naturename;
		 
	 }
}
