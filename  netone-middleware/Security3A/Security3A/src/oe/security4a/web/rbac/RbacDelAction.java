package oe.security4a.web.rbac;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.form.RequestParamMap;
import oe.frame.web.form.RequestUtil;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.auditingser.OperationLog;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.onlineuser.OnlineUserMgr;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class RbacDelAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		RequestParamMap reqmap = RequestUtil.setParamMapToRequest(request);
		OnlineUserMgr olmgr = new DefaultOnlineUserMgr();
		OnlineUser oluser = olmgr.getOnlineUser(request);
		String code =oluser.getBelongto();
		
		boolean b = true;
		if ("del".equals(reqmap.getParameter("task"))) {
			try {
				ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
				if (reqmap.getParameter("chkid") != null) {
					Map<Long, String> checkedMap = new HashMap<Long, String>();
					String[] str = reqmap.getParameterValues("chkid");
					for (int i = 0; i < str.length; i++) {
						checkedMap.put(new Long(str[i]), "checked");
						if (b) {
							try {
								List<Clerk> roleuserlist = rsrmi.fetchUser(code,str[i]);
								if (roleuserlist != null && roleuserlist.size() > 0) {
									request.setAttribute("result", 'y');
									b = false;
								}
							} catch (Exception e) {
								e.printStackTrace();
								OperationLog.error(request, "É¾³ý½ÇÉ«", e.getMessage());
							}
						}
					}
					request.setAttribute("checkedMap", checkedMap);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (b) {
			if (reqmap.getParameter("chkid") != null) {
				String[] str = reqmap.getParameterValues("chkid");
				try {
					ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
					CupmRmi cupmRmi = (CupmRmi) RmiEntry.iv("cupm");
					for (int i = 0; i < str.length; i++) {
						List<Clerk> roleuserlist = rsrmi.fetchUser(code,str[i]);
						for (Clerk clerk : roleuserlist) {
							cupmRmi.initCacheUser(clerk.getName());
						}
						cupmRmi.initCacheRoleCore(str[i]);
						if (rsrmi.dropRole(str[i])) {
							OperationLog.info(request, "É¾³ý½ÇÉ«", "É¾³ý½ÇÉ«³É¹¦!");
						} else {
							OperationLog.error(request, "É¾³ý½ÇÉ«", "É¾³ý½ÇÉ«Ê§°Ü!");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					OperationLog.error(request, "É¾³ý½ÇÉ«", e.getMessage());
				}
			}
		}
		if ("dept".equals(request.getParameter("task2"))) {
			return mapping.findForward("dept");
		}
		return mapping.findForward("index");
	}
}
