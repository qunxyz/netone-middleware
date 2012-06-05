package  oe.netone.dy;

  
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import com.jl.common.netone.UmsProtecte;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

public class QueryExtended {
	public List<String> qudongxuanz(String name) throws MalformedURLException, RemoteException, NotBoundException{
		ResourceRmi resourceRmi = null;
		List<String> list=new ArrayList<String>();
		resourceRmi=(ResourceRmi) RmiEntry.iv("resource");

		UmsProtectedobject upof=resourceRmi.loadResourceByNatural(name);
		String obl=upof.getExtendattribute();
		list.add(obl.trim());
		String name1=upof.getName();
		list.add(name1.trim());
		String naturalname=upof.getNaturalname();
		list.add(naturalname.trim());
		return list;
	}
	
	public  static Serializable Update(String id,String name,String xmlinfo,String parentname)
			throws Exception {
		ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
		
		//String id=UUID.randomUUID().toString().replaceAll("-", "");
		
		String cString="<?xml version='1.0' encoding='UTF-8'?>";
		xmlinfo=cString+xmlinfo;
		xmlinfo=xmlinfo.replace("&lt;", "<");
		xmlinfo=xmlinfo.replace("&gt;", ">");
		
		UmsProtectedobject upo =rsrmi.loadResourceByNatural(parentname);
		upo.setNaturalname(parentname);
		upo.setName(name);
		upo.setActionurl("");
		
		upo.setDescription(id);
		upo.setExtendattribute(xmlinfo);
		
		try {
			return rsrmi.updateResource(upo);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		}
	
	 public String describe(String naturalname){
		 UmsProtecte up=new UmsProtecte();
		 UmsProtectedobject umsobj=up.loadUmsProtecteNaturalname(naturalname);
		 return umsobj.getDescription();
	 }
	
}
