package oe.mid.soa.resource;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.db.UmsRole;

public class CopyOfUseResource {
	static String APPNAME = "DEPT.DEPT";

	public static void main(String[] args) throws MalformedURLException,
			RemoteException, NotBoundException {
		// 资源的访问控制句柄
		ResourceRmi resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
		//	
		// UmsProtectedobject ums = new UmsProtectedobject();
		// String querycondition = " and naturalname like '" + APPNAME + "%'";
		// List list = resourceRmi.fetchResource(ums, null, querycondition);

		Clerk cl = new Clerk();

		List clerk = resourceRmi.fetchClerk("0000", cl, null,
				"and extendinfo like 'DEPT.DEPT.%'");
		List listClerkCode = new ArrayList();
		for (Iterator iterator = clerk.iterator(); iterator.hasNext();) {
			Clerk object = (Clerk) iterator.next();
			listClerkCode.add(object.getNaturalname());
		}

	}

	public String[] fetchSubUserCode(String fatherusercode) throws Exception {

		// 资源的访问控制句柄
		ResourceRmi resourceRmi = (ResourceRmi) RmiEntry.iv("resource");

		Clerk clerkFather = resourceRmi.loadClerk("0000", fatherusercode);
		String fatherDeptNaturalname = clerkFather.getExtendattribute();

		String findSubDeptConditon = " and extendinfo like '"
				+ fatherDeptNaturalname + ".%'";

		Clerk clQ = new Clerk();

		List clerk = resourceRmi.fetchClerk("0000", clQ, null,
				findSubDeptConditon);
		List listClerkCode = new ArrayList();
		for (Iterator iterator = clerk.iterator(); iterator.hasNext();) {
			Clerk object = (Clerk) iterator.next();
			listClerkCode.add(object.getNaturalname());
		}
		return (String[]) listClerkCode.toArray(new String[0]);
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

		return clerkx.getName() + "_" + clerkx.getDescription();
	}

}
