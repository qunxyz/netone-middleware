package readxml.readandmodify;

import java.io.Serializable;

import com.jl.common.netone.UmsProtecte;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

public class AddRs {

	public  static Serializable add(String parentname,String xmlinfo,String id,String name,String type)
			throws Exception {
		ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
		
		//String id=UUID.randomUUID().toString().replaceAll("-", "");
		xmlinfo=xmlinfo.replace("&lt;", "<");
		xmlinfo=xmlinfo.replace("&gt;", ">");
		
		UmsProtectedobject upo = new UmsProtectedobject();
		upo.setNaturalname(id);
		upo.setName(name);
		upo.setActionurl("");
		upo.setObjecttype(type);
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
	public  static Serializable addcatalogue(String parentname,String xmlinfo,String id,String name)
	throws Exception {
		ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
		
		//String id=UUID.randomUUID().toString().replaceAll("-", "");
		xmlinfo=xmlinfo.replace("&lt;", "<");
		xmlinfo=xmlinfo.replace("&gt;", ">");
		
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
	public  static Serializable Update(String parentname,String xmlinfo,String id,String name)
			throws Exception {
		ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
		
		//String id=UUID.randomUUID().toString().replaceAll("-", "");
		xmlinfo=xmlinfo.replace("&lt;", "<");
		xmlinfo=xmlinfo.replace("&gt;", ">");
		
		UmsProtectedobject upo =rsrmi.loadResourceByNatural(parentname);
		upo.setNaturalname(parentname);
		upo.setName(name);
		upo.setActionurl("");
		upo.setInclusion("0");
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
	public   String addphone(String parentname,String xmlinfo,String id,String name)
	throws Exception {
 
		 
		xmlinfo=xmlinfo.replace("&lt;", "<");
		xmlinfo=xmlinfo.replace("&gt;", ">");
		
		UmsProtectedobject upo = new UmsProtectedobject();
		upo.setNaturalname(id);
		upo.setName(name);
		upo.setActionurl("");
		
		upo.setDescription(id);
		upo.setActive("1");
		upo.setInclusion("1");
		upo.setExtendattribute(xmlinfo);
		UmsProtecte up=new UmsProtecte();
	    String slbString=(String) up.addUmsProtecte(upo, parentname);
		
	    return slbString;
	 
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
}
