package oe.security3a.seucore.accountser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import oe.frame.orm.OrmerEntry;

import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

public class UserDaoImplReference2 {

	public static List<Clerk> buildClerkList(List list) {
		List<Clerk> clerklist = new ArrayList<Clerk>();
		if (list != null && list.size() > 0) {
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				Clerk user = (Clerk) iter.next();
				try {
					UmsProtectedobject up = (UmsProtectedobject) OrmerEntry.fetchOrmer().fetchQuerister().loadObject(
							UmsProtectedobject.class, user.getDeptment());
					user.setFaxNO(up.getName());
				} catch (Exception e) {
					e.printStackTrace();
					System.err.println("部门不存在:" + user.getDeptment());
					user.setFaxNO(user.getDeptment());
				}
				clerklist.add(user);
			}
		}
		return clerklist;
	}

}
