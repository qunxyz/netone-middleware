package oe.security4a.web.department;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.form.RequestParamMap;
import oe.frame.web.form.RequestUtil;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ApplicationRmi;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.db.UmsApplication;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.onlineuser.OnlineUserMgr;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class DepartmentTreeAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		RequestParamMap reqmap = RequestUtil.setParamMapToRequest(request);
		try {
			ApplicationRmi rmi = (ApplicationRmi) RmiEntry.iv("application");
			ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			String application = reqmap.getParameter("application");
			Long appid = null;
			if (StringUtils.isNotEmpty(application)) {
				request.setAttribute("appnaturalname", application);
				UmsApplication ua = new UmsApplication();
				ua.setNaturalname(StringUtils.substringBefore(application, ","));
				List tmpapplist = rmi.queryObjects(ua, null);
				appid = ((UmsApplication) tmpapplist.get(0)).getId();
			}

			List<UmsApplication> applist = new ArrayList<UmsApplication>();
			String naturalname = request.getParameter("naturalname");
			
			if(StringUtils.isEmpty(naturalname)){
				OnlineUserMgr olmgr = new DefaultOnlineUserMgr();
				OnlineUser oluser = olmgr.getOnlineUser(request);
				String loginName = oluser.getLoginname();
				String code = oluser.getBelongto();
				if (!"adminx".equals(loginName)) {
					Clerk user = rsrmi.loadClerk(code,loginName);
					naturalname = user.getExtendattribute();//≤ø√≈
				}
			}
			
			if (StringUtils.isNotEmpty(naturalname)) {
				String[] nas = StringUtils.split(naturalname, ",");
				for (int i = 0; i < nas.length; i++) {
					UmsProtectedobject upo = rsrmi.loadResourceByNatural(nas[i]);
					UmsApplication ua = rmi.loadObject(upo.getAppid());
					ua.setNaturalname(ua.getNaturalname() + "," + nas[i]);
					applist.add(ua);
				}
			} else {
				UmsApplication ua = new UmsApplication();
				ua.setApptype("ORG");
				applist = rmi.queryObjects(ua, null);
			}

			if (StringUtils.isEmpty(application)) {
				if (applist.size() > 0) {
					application = ((UmsApplication) applist.get(0)).getNaturalname();
					appid = ((UmsApplication) applist.get(0)).getId();
				}
			}
			UmsProtectedobject upo = new UmsProtectedobject();
			if (StringUtils.isNotEmpty(naturalname)) {
				String[] nas = StringUtils.split(naturalname, ",");
				for (int i = 0; i < nas.length; i++) {
					UmsProtectedobject tmp = rsrmi.loadResourceByNatural(nas[i]);
					if (tmp.getNaturalname().equals(StringUtils.substringAfter(application, ","))) {
						upo = tmp;
					}
				}
			} else {
				upo.setParentdir("0");
				upo.setAppid(new Long(appid));
				List upolist = rsrmi.fetchResource(upo, null);
				if (upolist != null && upolist.size() > 0) {
					upo = (UmsProtectedobject) upolist.get(0);
				}
			}
			request.setAttribute("applist", applist);
			request.setAttribute("root", upo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("departmenttree");
	}
}