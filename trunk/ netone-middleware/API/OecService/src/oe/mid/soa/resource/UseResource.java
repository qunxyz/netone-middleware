package oe.mid.soa.resource;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.seucore.obj.db.UmsRole;


public class UseResource {
	static String APPNAME = "DEPT.DEPT";

	public static void main(String[] args) throws MalformedURLException,
			RemoteException, NotBoundException {
		// 资源的访问控制句柄
		ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
		UmsProtectedobject upo=rsrmi.loadResourceByNatural("BUSSFORM.BUSSFORM");
		List list=rsrmi.subResource(upo.getId());
		
		
		upo.setName("fdsfsd");
		upo.setNaturalname("");
		rsrmi.addResource(upo, "");
		
		
		//	
		// UmsProtectedobject ums = new UmsProtectedobject();
		// String querycondition = " and naturalname like '" + APPNAME + "%'";
		// List list = resourceRmi.fetchResource(ums, null, querycondition);


	}

	public static String todoProvice(String belongUp)
			throws MalformedURLException, RemoteException, NotBoundException {
		ResourceRmi resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
		UmsRole role = new UmsRole();
		role.setBelongingness(belongUp);
		List list = resourceRmi.fetchRole(role, null, null);
		System.out.println(((UmsRole) list.get(0)).getName());

		List listx = resourceRmi.fetchUser("0000", ((UmsRole) list.get(0))
				.getId().toString());
		Clerk clerkx = (Clerk) listx.get(0);

		return clerkx.getName()+"_"+clerkx.getDescription();
	}
	
	

}
