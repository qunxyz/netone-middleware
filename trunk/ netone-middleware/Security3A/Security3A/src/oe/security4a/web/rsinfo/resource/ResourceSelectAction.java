package oe.security4a.web.rsinfo.resource;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.form.RequestParamMap;
import oe.frame.web.form.RequestUtil;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ApplicationRmi;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsApplication;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class ResourceSelectAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		RequestParamMap reqmap = RequestUtil.setParamMapToRequest(request);
		try {
			ApplicationRmi rmi = (ApplicationRmi) RmiEntry.iv("application");
			ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			String application = reqmap.getParameter("application");
			if (StringUtils.isNotEmpty(application)) {
				UmsApplication ua = rmi.loadObject(new Long(application));
				request.setAttribute("appnaturalname", ua.getNaturalname());
				request.setAttribute("appname", ua.getName());
			}
			String func = request.getParameter("func");
			request.setAttribute("func", func);
			UmsApplication ua = new UmsApplication();
			ua.setApptype("resource");
			List applist = rmi.queryObjects(ua, null);
			request.setAttribute("applist", applist);
			if (StringUtils.isEmpty(application)) {
				if (applist.size() > 0) {
					application = ((UmsApplication) applist.get(0)).getId().toString();
				}
			}
			if (StringUtils.isNotEmpty(application)) {
				UmsProtectedobject upo = new UmsProtectedobject();
				upo.setParentdir("0");
				upo.setAppid(new Long(application));
				List upolist = rsrmi.fetchResource(upo, null);
				if (upolist != null && upolist.size() > 0) {
					upo = (UmsProtectedobject) upolist.get(0);
				}
				request.setAttribute("root", upo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("resourceselect");
	}

	public void setApplications(HttpServletRequest request) {
		try {
			ApplicationRmi rmi = (ApplicationRmi) RmiEntry.iv("application");
			UmsApplication ua = new UmsApplication();
			List applist = rmi.queryObjects(ua, null);
			request.setAttribute("applist", applist);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}