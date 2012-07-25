package oe.rmi.message.mail.user;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.onlineuser.OnlineUserMgr;

public class Synchronization {

	public List getClerkList() {
		try {
			ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			return rsrmi.fetchClerk(null,new Clerk(), null, null);//改用新安全包方法
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		} catch (NotBoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List getClerkList(HttpServletRequest request, String[] str) {
		String code = request.getParameter("code");
		try {
			ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			List list = new ArrayList();
			for (int i = 0; i < str.length; i++) {
				Clerk clerk = rsrmi.loadClerk(code, str[i]);
				list.add(clerk);
			}
			return list;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		} catch (NotBoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List getClerkList(String str) {
		try {
			ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			Clerk clerk = new Clerk();
			clerk.setDeptment(str);
			return rsrmi.fetchClerk(null,clerk, null, null);//改用新安全包方法
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		} catch (NotBoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean sync(List clerkList, String key) {
		if (clerkList != null && !clerkList.isEmpty()) {
			DataOperation data = new DataOperation();
			for (Iterator iter = clerkList.iterator(); iter.hasNext();) {
				Clerk user = (Clerk) iter.next();
				if ("".equals(data.searchUserInfo(user))) {
					data.addUserInfo(user, key);
				} else {
					data.updateUserInfo(user);
				}
			}
			return true;
		}
		return false;
	}
}
