package oe.security4a.web.rbac;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import oe.frame.web.form.RequestParamMap;
import oe.frame.web.util.IAjaxService;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;


public class AjaxTreeCheckItem implements IAjaxService {

	public String execute(HttpServletRequest request, RequestParamMap reqmap) throws Exception {
		ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");
		String appid = reqmap.getParameter("appid");
		String roleid = reqmap.getParameter("roleid");
		if (roleid != null && !"".equals(roleid)) {
			UmsProtectedobject upo = new UmsProtectedobject();
			upo.setAppid(new Long(appid));
			List list = rmi.fetchResource(upo, null);
			StringBuffer sb = new StringBuffer();
			CupmRmi cupmRmi = (CupmRmi) RmiEntry.iv("cupm");
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				UmsProtectedobject upotmp = (UmsProtectedobject) iter.next();
				if (cupmRmi.checkRoleSelfPermissionCore(roleid, upotmp.getOu(), "7")) {
					sb.append(upotmp.getOu() + "=7,");
				} else if (cupmRmi.checkRoleSelfPermissionCore(roleid, upotmp.getOu(), "3")) {
					sb.append(upotmp.getOu() + "=3,");
				} else if (cupmRmi.checkRoleSelfPermissionCore(roleid, upotmp.getOu(), "1")) {
					sb.append(upotmp.getOu() + "=1,");
				} else {
					sb.append(upotmp.getOu() + "=0,");
				}
			}
			return sb.toString();
		} else {
			return "";
		}
	}
}
