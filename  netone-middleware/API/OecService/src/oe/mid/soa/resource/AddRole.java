package oe.mid.soa.resource;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.seucore.obj.db.UmsRole;

public class AddRole {
	static String APPNAME = "DEPT.DEPT";

	public static void main(String[] args) throws MalformedURLException,
			RemoteException, NotBoundException {
		// 资源的访问控制句柄
		ResourceRmi resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
		//	
		// UmsProtectedobject ums = new UmsProtectedobject();
		// String querycondition = " and naturalname like '" + APPNAME + "%'";
		// List list = resourceRmi.fetchResource(ums, null, querycondition);

		Clerk clerk = resourceRmi.loadClerk("0000", "0131");

		System.out.println(clerk.getName());

		UmsProtectedobject upo = resourceRmi.loadResourceById(clerk
				.getDeptment());
		String belong = upo.getNaturalname();
		System.out.println(belong);
		// 通过部门逻辑找到角色
		UmsRole role = new UmsRole();
		role.setBelongingness(belong);
		List list = resourceRmi.fetchRole(role, null, null);
		System.out.println(((UmsRole) list.get(0)).getName());
		
		
		//将某角色添加给某用户
		resourceRmi.roleRelationupdate("0000", "0131", list);

		// // 上一级
		// String belongUp = StringUtils.replace(belong, "ZYERPC", "ZYERP");
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
