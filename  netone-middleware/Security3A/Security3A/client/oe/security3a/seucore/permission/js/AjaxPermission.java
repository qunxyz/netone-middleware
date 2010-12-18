package oe.security3a.seucore.permission.js;

import javax.servlet.http.HttpServletRequest;

import oe.frame.web.form.RequestParamMap;
import oe.frame.web.util.IAjaxService;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.sso.SecurityConfig;
import oe.security3a.sso.SsoToken;
import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.onlineuser.OnlineUserMgr;


public class AjaxPermission implements IAjaxService {

	public String execute(HttpServletRequest request, RequestParamMap map)
			throws Exception {
		String task = map.getParameter("task");
		String cookie = map.getParameter("cookie");
		SsoToken st = new SsoToken();
		OnlineUserMgr olusermgr = new DefaultOnlineUserMgr();
		StringBuffer sb = new StringBuffer();
		if ("checkLogin".equals(task)) {
			boolean result = false;
			if (cookie != null && !"".equals(cookie)) {
				st.parse(cookie);
				String sessionid = st.getSessionId();
				OnlineUser oluser = olusermgr.getOnlineUser(sessionid);
				if (oluser != null) {
					sb.append("result=true,loginName="+oluser.getLoginname());
					result = true;
				}
			}
			if (!result) {
				String url = SecurityConfig.getInstance().getSsoServerUrl()
						+ "/sso/login.jsp";
				sb.append("result=false,url=" + url);
			}
		}
		if ("checkPagePermission".equals(task)) {
			boolean result = false;
			if (cookie != null && !"".equals(cookie)) {
				st.parse(cookie);
				String sessionid = st.getSessionId();
				OnlineUser oluser = olusermgr.getOnlineUser(sessionid);
				String code = oluser.getBelongto();
				if (oluser != null) {
					String resource = map.getParameter("resource");
					String action = map.getParameter("action");
					try {

						CupmRmi ps = (CupmRmi) RmiEntry.iv("cupm");

						boolean b = ps.checkUserPermission(code, oluser
								.getLoginname(), resource, action);
						if (b) {
							sb.append("result=true");
							result = true;
						} else {
							String url = SecurityConfig.getInstance()
									.getSsoServerUrl()
									+ "sso/impl/nopermission.jsp";
							sb.append("result=false,url=" + url);
							result = true;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			if (!result) {
				String url = SecurityConfig.getInstance().getSsoServerUrl()
						+ "/sso/login.jsp";
				sb.append("result=false,url=" + url);
			}
		}
		if ("checkPermission".equals(task)) {
			boolean result = false;
			if (cookie != null && !"".equals(cookie)) {
				st.parse(cookie);
				String sessionid = st.getSessionId();
				OnlineUser oluser = olusermgr.getOnlineUser(sessionid);
				String code = oluser.getBelongto();
				if (oluser != null) {
					String resource = map.getParameter("resource");
					String action = map.getParameter("action");
					try {
						CupmRmi ps = (CupmRmi) RmiEntry.iv("cupm");

						boolean b = ps.checkUserPermission(code, oluser
								.getLoginname(), resource, action);
						if (b) {
							sb.append("result=true");
							result = true;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			if (!result) {
				sb.append("result=false");
			}
		}
		if("getOnlineUser".equals(task)){
			boolean result = false;
			if (cookie != null && !"".equals(cookie)) {
				st.parse(cookie);
				String sessionid = st.getSessionId();
				OnlineUser oluser = olusermgr.getOnlineUser(sessionid);
				if (oluser != null) {
					sb.append("result=true,loginName="+oluser.getLoginname());
					result = true;
				}
			}
			if (!result) {
				sb.append("result=false");
			}
		}
		
		
		return sb.toString();
	}

}
