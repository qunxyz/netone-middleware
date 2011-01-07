package oe.mid.soa.resource;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.seucore.obj.db.UmsRole;

public class DEPTCODE {
	static String APPNAME = "DEPT.DEPT";

	public static void main(String[] args) throws MalformedURLException,
			RemoteException, NotBoundException {

		// 资源的访问控制句柄
		ResourceRmi resourceRmi = (ResourceRmi) RmiEntry.iv("resource");

		UmsProtectedobject upoDept = new UmsProtectedobject();
		upoDept.setActionurl("01");

		List list = resourceRmi.fetchResource(upoDept, null);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			UmsProtectedobject object = (UmsProtectedobject) iterator.next();
			System.out.println(object.getName());
			break;
		}

		//	
		// UmsProtectedobject ums = new UmsProtectedobject();
		// String querycondition = " and naturalname like '" + APPNAME + "%'";
		// List list = resourceRmi.fetchResource(ums, null, querycondition);


		// String name1=todoProvice(belongUp);
		// System.out.println(name1);
		//		
		// // 上一级
		// String belongUp1 = StringUtils.substringBeforeLast(belongUp, ".");
		// String name2=todoProvice(belongUp1);
		// System.out.println(name2);
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
