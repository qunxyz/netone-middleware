package oe.security4a.web.human;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import oe.frame.web.form.RequestParamMap;
import oe.frame.web.util.IAjaxService;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.onlineuser.OnlineUserMgr;


public class AjaxTreeCheckItem implements IAjaxService {

	public String execute(HttpServletRequest request, RequestParamMap reqmap) throws Exception {
		OnlineUserMgr olmgr = new DefaultOnlineUserMgr();
		OnlineUser oluser = olmgr.getOnlineUser(request);
		String code = oluser.getBelongto();
		
		ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");
		String appid = reqmap.getParameter("appid");
		String userid = reqmap.getParameter("userid");
		if (userid != null && !"".equals(userid)) {
			UmsProtectedobject upo = new UmsProtectedobject();
			upo.setAppid(new Long(appid));
			List list = rmi.fetchResource(upo, null);
			StringBuffer sb = new StringBuffer();
			CupmRmi cupmRmi = (CupmRmi) RmiEntry.iv("cupm");
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				UmsProtectedobject upotmp = (UmsProtectedobject) iter.next();
				if (cupmRmi.checkUserPermissionCore(code,userid, upotmp.getOu(), "7")) {
					sb.append(upotmp.getOu() + "=7,");
				} else if (cupmRmi.checkUserPermissionCore(code, userid, upotmp.getOu(), "3")) {
					sb.append(upotmp.getOu() + "=3,");
				} else if (cupmRmi.checkUserPermissionCore(code, userid, upotmp.getOu(), "1")) {
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
