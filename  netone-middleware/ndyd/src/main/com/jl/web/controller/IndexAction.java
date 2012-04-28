package com.jl.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jl.common.CookiesOpe;
import com.jl.common.security3a.Security3AIfc;
import com.jl.entity.User;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jl.service.BaseService;

public class IndexAction extends AbstractAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date = df.format(new Date());

		User user = BaseService.getOnlineUser(request);

		String info = "公司:" + user.getDepartmentName() + "["
				+ user.getDepartmentCode() + "]"
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "用户:"
				+ user.getUserName() + "&nbsp;&nbsp;" + "登录日期:" + date
				+ "&nbsp;&nbsp; IP:" + request.getRemoteHost() + "&nbsp;&nbsp;";

		request.setAttribute("info", info);

		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/";

		String style = CookiesOpe.readCookiex(request);

		String defaultPortal = basePath + "/workList.do?method=onMainView";
		String portalUrl = BaseService.getSecurityAPI(request).getTopEnv(
				"WEBSER_CMSWEB")
				+ "/portal.do?portalmode=3&style=" + style + "&name=";

		Security3AIfc security3AIfc = (Security3AIfc) WebApplicationContextUtils
				.getRequiredWebApplicationContext(
						request.getSession().getServletContext()).getBean(
						"securityIfc");

		String jxskey = "PORTALPG.PORTALPG.DRP.JSXPORTAL";
		String yxbkey = "PORTALPG.PORTALPG.DRP.YXBPORTAL";
		String sgskey = "PORTALPG.PORTALPG.DRP.SGSPORTAL";
		String topkey = "PORTALPG.PORTALPG.DRP.DDS";
		String storage = "PORTALPG.PORTALPG.DRP.STORAGE";

//		boolean isStorageManager = security3AIfc.permission(user.getUserCode(),
//				"BUSSENV.BUSSENV.SECURITY.ROLE.CB");
//
//		if (isStorageManager&&!"adminx".equals(user.getUserCode())) {
//			defaultPortal = portalUrl + storage;// 仓库(仓库没有业务级别，所以通过特有的权限来识别)
//		} else if ("4".equals(user.getLevel())) {
//			defaultPortal = portalUrl + jxskey;
//			request.setAttribute("hiddenBar",
//					"$('body').layout('collapse','west');");
//		} else if ("2".equals(user.getLevel())) {
//			defaultPortal = portalUrl + yxbkey;
//		} else if ("1".equals(user.getLevel())
//				|| "adminx".equals(user.getUserCode())) {
//			defaultPortal = portalUrl + sgskey;
//			request.setAttribute("dds", "YES");
//		}
//		System.out.println(defaultPortal);

		request.setAttribute("defaultPortal", defaultPortal);

		return mapping.findForward("onExtMainView");
	}
}
