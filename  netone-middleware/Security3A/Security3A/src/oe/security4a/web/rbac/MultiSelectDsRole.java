package oe.security4a.web.rbac;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import oe.frame.web.form.RequestParamMap;
import oe.frame.web.util.IAjaxService;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.seucore.obj.db.UmsRole;


public class MultiSelectDsRole implements IAjaxService {

	public String execute(HttpServletRequest request, RequestParamMap map) throws Exception {
		ResourceRmi rsrmi = null;
		StringBuffer sb = new StringBuffer();
		try {
			// 读取名为resource的rmi服务
			rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			String id = map.getParameter("belongingness");
			UmsProtectedobject upo = rsrmi.loadResourceById(id);
			UmsRole ur = new UmsRole();
			ur.setBelongingness(upo.getNaturalname());
			List urlist = rsrmi.fetchRole(ur, null, null);
			if (urlist != null) {
				for (Iterator iter = urlist.iterator(); iter.hasNext();) {
					UmsRole tmpur = (UmsRole) iter.next();
					sb.append("name=" + tmpur.getName() + ",");
					sb.append("id=" + tmpur.getId() + ";");
				}
			}
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

}
