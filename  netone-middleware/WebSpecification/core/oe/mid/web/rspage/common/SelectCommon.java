package oe.mid.web.rspage.common;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import oe.frame.web.SysFrame;
import oe.frame.web.WebCache;
import oe.security3a.client.rmi.ApplicationRmi;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsApplication;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.onlineuser.OnlineUserMgr;

import org.apache.commons.lang.StringUtils;



public class SelectCommon {
	public static void common(HttpServletRequest request, ResourceRmi rsrmi,
			ApplicationRmi rmi) throws RemoteException {
		// 页面参数
		//这里支持两种方式访问，使用appname 和 appid 
		
		String appname = request.getParameter("appname");// 应用程序id
		if(appname!=null){
			try {
				OnlineUserMgr olmgr = new DefaultOnlineUserMgr();
				OnlineUser oluser = olmgr.getOnlineUser(request);
				if (oluser != null) {
					String cacheinfo = (String) WebCache
							.getCache(WebCache._CACHE_ITEM_SECURITY
									+ oluser.getLoginname());
					if (cacheinfo != null) {

						String appendname = null;
						if (StringUtils.indexOf(appname, "ETL") == 0) {
							appendname = cacheinfo + SysFrame._BUSS_BUSSANA;
						} else if (StringUtils.indexOf(appname, "BUSSFORM") == 0) {
							appendname = cacheinfo + SysFrame._BUSS_BUSSFORM;
						} else if (StringUtils.indexOf(appname, "WF") == 0) {
							appendname = cacheinfo + SysFrame._BUSS_FLOW;
						} else if (StringUtils.indexOf(appname, "CHART") == 0) {
							appendname = cacheinfo + SysFrame._BUSS_GRAPH;
						}
						if (appendname != null) {
							appname = appendname + "," + appname;
						}
					} else {
						if (StringUtils.indexOf(appname, ".") == -1) {
							appname = appname + "." + appname;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		

		String appid = request.getParameter("appid");
		String application = request.getParameter("application");// 页面选择app参数
		if (StringUtils.isNotEmpty(application)) {
			request.setAttribute("appnaturalname", application);
			appid = StringUtils.substringBefore(application, ",");
		}
		List<UmsApplication> applist = new ArrayList<UmsApplication>();
		if (StringUtils.isNotEmpty(appname)) {
			String[] nas = StringUtils.split(appname, ",");
			for (int i = 0; i < nas.length; i++) {
				String appnameReal = nas[i];
				if (!StringUtils.contains(nas[i], '.')) {
					appnameReal = appnameReal + "." + appnameReal;
				}
				UmsProtectedobject upo = rsrmi
						.loadResourceByNatural(appnameReal);
				UmsApplication ua = rmi.loadObject(upo.getAppid());
				ua.setNaturalname(ua.getId() + "," + appnameReal);
				applist.add(ua);
			}
		} else if (StringUtils.isNotEmpty(appid)) {
			UmsApplication appReal = rmi.loadObject(new Long(appid));
			applist.add(appReal);
		}

		if (StringUtils.isEmpty(application)) {
			if (applist.size() > 0) {
				application = ((UmsApplication) applist.get(0))
						.getNaturalname();
				appid = ((UmsApplication) applist.get(0)).getId().toString();
			}
		}
		UmsProtectedobject upo = new UmsProtectedobject();
		if (StringUtils.isNotEmpty(appname)) {
			upo = rsrmi.loadResourceByNatural(StringUtils.substringAfter(
					application, ","));

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
	}
}
