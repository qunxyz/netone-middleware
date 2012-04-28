package com.jl.common.report.parse;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.UUID;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

/**
 * 保持报表的设计结果
 * @author yj  <br>
 */
public class AddRs {

	public  static Serializable add(String parentname,String xmlinfo,String id,String name)
			throws Exception {
		ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
		
		//String id=UUID.randomUUID().toString().replaceAll("-", "");

		UmsProtectedobject upo = new UmsProtectedobject();
		upo.setNaturalname(id);
		upo.setName(name);
		upo.setActionurl("");

		upo.setDescription(id);
		upo.setActive("1");
		upo.setInclusion("0");
		upo.setExtendattribute(xmlinfo);

		try {
			return rsrmi.addResource(upo,parentname);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
	
	public static String readxml(String id) throws Exception {
		ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
		UmsProtectedobject upo = rsrmi.loadResourceById(id);
		if (upo != null) {
			return upo.getExtendattribute();
		}
		return null;
	}
	
	public static String readxmlByName(String name) throws Exception {
		ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
		UmsProtectedobject upo = rsrmi.loadResourceByNatural(name);
		if (upo != null) {
			return upo.getExtendattribute();
		}
		return null;
	}

	public static void modify(String id, String xmlinfo) throws Exception {
		ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
		UmsProtectedobject upo = rsrmi.loadResourceById(id);
		upo.setExtendattribute(xmlinfo);
		rsrmi.updateResource(upo);
	}

	public static void main(String[] args) throws Exception {
		AddRs ad = new AddRs();
//		ad.add("repor1t", "报表", "<xml? xxxxxxwafawefwaefew>");
		System.out.println(ad.readxmlByName("DEPT.DEPT.REPORT333"));

	}

}
