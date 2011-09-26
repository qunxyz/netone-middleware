package oe.security4a.web.logout;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.security3a.seucore.auditingser.OperationLog;
import oe.security3a.sso.util.SsoUtil;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class LogoutAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SsoUtil su = new SsoUtil();
		String gotourl=request.getParameter("gotourl");
		gotourl=gotourl==null?"":gotourl;
		try {
			su.doLogout(request, response, gotourl);
		} catch (IOException e) {
			e.printStackTrace();
			OperationLog.info(request, "×¢ÏúÓÃ»§", e.getMessage(),true);
		}
		return null;
	}
}
