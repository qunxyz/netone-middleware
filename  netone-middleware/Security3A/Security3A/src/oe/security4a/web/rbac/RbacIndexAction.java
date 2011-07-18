package oe.security4a.web.rbac;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.form.RequestParamMap;
import oe.frame.web.form.RequestUtil;
import oe.frame.web.page.PageInfo;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.seucore.obj.db.UmsRole;
import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.onlineuser.OnlineUserMgr;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class RbacIndexAction extends Action {

	static Log log = LogFactory.getLog(RbacIndexAction.class);

	// 1使用jssq.jsp，2使用jssq2.jsp
	public static String PurviewType = "2";

	static {
		CupmRmi cupmRmi = null;
		try {
			cupmRmi = (CupmRmi) RmiEntry.iv("cupm");
			PurviewType = cupmRmi.fetchConfig("permissionMode");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private int _PRE_PAGE = 10;// 默认分页

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RequestParamMap reqmap = RequestUtil.setParamMapToRequest(request);
		OnlineUserMgr olmgr = new DefaultOnlineUserMgr();
		OnlineUser oluser = olmgr.getOnlineUser(request);
		String loginName = oluser.getLoginname();
		String code = oluser.getBelongto();
		UmsRole ur = new UmsRole();
		Map<String, String> newmap = new HashMap<String, String>();
		String condition = null;
		if (!"adminx".equals(loginName)) {
			try {
				ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");
				Clerk user = rmi.loadClerk(code, loginName);
				String extend = user.getExtendattribute();
				newmap.put("belongingness", "like");
				ur.setBelongingness(extend + "%");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			PageInfo pageinfo = null;// 分页信息
			if (StringUtils.isNotEmpty(reqmap.getParameter("sclass2"))) {
				condition = " and belongingness='"
						+ reqmap.getParameter("sclass2") + "'";
			}
			if (StringUtils.isNotEmpty(reqmap.getParameter("sname"))) {
				newmap.put("name", "like");
				ur.setName("%"
						+ reqmap.getParameter("sname").trim().toUpperCase()
						+ "%");
			}
			// newmap.put("belongingness", "like");
			// ur.setBelongingness("%");
			//List<UmsRole> listinfo = new ArrayList<UmsRole>();
			List listinfo = rsrmi.fetchRole(ur, newmap, condition);
//			if (urlist != null) {
//				for (Iterator iteror = urlist.iterator(); iteror.hasNext();) {
//					UmsRole tmpur = (UmsRole) iteror.next();
//					UmsProtectedobject up = null;
//					try {
//
//						up = rsrmi.loadResourceByNatural(tmpur
//								.getBelongingness());
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//					if(up!=null){
//						tmpur.setBelongingness(up.getName());
//					}
//					
//					if (!listinfo.contains(tmpur)) {
//						listinfo.add(tmpur);
//					}
//				}
//			}
			if (PageInfo.isPageEvent(request)) {
				pageinfo = new PageInfo(request, "rolesearch");
			} else {
				long num = listinfo.size();
				pageinfo = new PageInfo((int) num, _PRE_PAGE, request,
						"rolesearch");// 分页信息
			}
			if (listinfo.size() > 0) {
				listinfo = listinfo.subList(pageinfo.getPageStartIndex(),
						pageinfo.getPageEndIndex() + 1);
			}
			request.setAttribute("listinfo", listinfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.getInputForward();
	}
}
