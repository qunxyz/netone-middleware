package oe.netone.phone;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

public class SavePhoneConfig{
	 public void SaveData(String naturalname, String name,
					String extendattribute,String parentdir,String type) {
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
				String cString="<?xml version='1.0' encoding='UTF-8'?>";
				extendattribute=extendattribute.replace("&lt;", "<");
				extendattribute=cString+extendattribute.replace("&gt;", ">");

				UmsProtectedobject upo = new UmsProtectedobject();
				upo.setNaturalname(naturalname);
				upo.setName(name);
				upo.setActionurl("");
				upo.setExtendattribute(extendattribute);
				upo.setObjecttype(type);
				upo.setDescription("");

				try {
					idcreated = rsrmi.addResource(upo,parentdir);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
			}
}