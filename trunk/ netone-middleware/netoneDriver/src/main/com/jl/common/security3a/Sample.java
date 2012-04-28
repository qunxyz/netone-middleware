package com.jl.common.security3a;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

public class Sample {

	public static void main(String[] args) throws Exception {
		// String rootDirId = "35";
		// String bussDirId = "xxx";
		// String bussDirId2 = "xxx2";
		// String bussDirId3 = "xxx3";
		// SecurityEntry.iv().newOrganization(rootDirId, bussDirId3, "xxx",
		// null);
		// SecurityEntry.iv().newOrganization(rootDirId, bussDirId, "xxx",
		// null);
		// SecurityEntry.iv().newOrganization(bussDirId, bussDirId2, "xxx",
		// null);
		// SecurityEntry.iv().deleteOrganization("xxx");
		// SecurityEntry.iv().editOrganization(bussDirId3, bussDirId, "xxxz",
		// null);

		// SecurityEntry.iv().newAccount(bussDirId3, "mikeee", "mikeee", null);
		// // SecurityEntry.iv().deleteAccount("mikeee");
		// SecurityEntry.iv().editAccount(bussDirId, "mikeee", "mikeeexxx",
		// null);

		// String roleid = "6";// ½ÇÉ«id
		// String deptid = "SYSTEAM.SYSTEAM.BB";// ×éid
		// List list = SecurityEntry.iv().listUserByRoleId(roleid);
		// List listx = SecurityEntry.iv().listUserByTeamId(deptid);

		// long xx = SecurityEntry.iv().countRsInDir("DEPT.DEPT", "");
		// long xx1 = SecurityEntry.iv().countRsInDir("FCK.FCK", "");
		// List list = SecurityEntry.iv().listDirRs("DEPT.DEPT");
		// List list2 = SecurityEntry.iv().listRsInDir("DEPT.DEPT", 0, 100, "");
		// System.out.println();
		
	

//		ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
//		Clerk clerk = rs.loadClerk("0000", "wangjinhua");
//		String departmentid = clerk.getDeptment();
//		UmsProtectedobject upox = rs.loadResourceById(departmentid);
//		String ou = upox.getOu();
//		String[] xx = StringUtils.split(ou, ".");
//		if (xx.length > 1) {
//			for (int i = 2; i < xx.length; i++) {
//				UmsProtectedobject upoxx = rs.loadResourceById(xx[i]);
//				System.out.print(upoxx.getName()+"|");
//			}
//		}
//
//		System.out.println();
		
		List listx=SecurityEntry.iv().listDirRs("BUSSFORM.BUSSFORM");
		List listy=SecurityEntry.iv().listRsInDir("BUSSFORM.BUSSFORM","26", 0, 100, "");
		System.out.println();
	}

}
