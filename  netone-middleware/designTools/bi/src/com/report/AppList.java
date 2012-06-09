package com.report;

import java.rmi.NotBoundException;
import java.util.Iterator;
import java.util.List;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ApplicationRmi;
import oe.security3a.seucore.obj.db.UmsApplication;

public class AppList {

	public static String listApp() throws Exception {

		ApplicationRmi rsrmi = null;
		try {
			// 读取名为resource的rmi服务
			rsrmi = (ApplicationRmi) RmiEntry.iv("application");
			UmsApplication app = new UmsApplication();
			List list = rsrmi.queryObjects(app, null);
			StringBuffer but = new StringBuffer();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				UmsApplication type = (UmsApplication) iterator.next();
				but.append("<item label='" + type.getName() + "' value='"
						+ type.getNaturalname() + "'/>\n");
			}
			return but.toString();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		return "";
	}
}
