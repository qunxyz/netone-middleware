package oe.security4a.severlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.WebCache;
import oe.security3a.seucore.accountser.UserService;
import oe.security3a.sso.SecurityConfig;
import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.onlineuser.OnlineUserMgr;

import org.apache.commons.lang.StringUtils;

/**
 * �����½����,����ע��Ӧ��
 * @author chenjx <br> mail:15860836998@139.com
 *
 */
public class LogoutSvl extends HttpServlet {
	private static final String CONTENT_TYPE = "text/html; charset=GBK";

	// Initialize global variables
	public void init() throws ServletException {
	}

	// Process the HTTP Get request
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		String gotourl = request.getParameter("gotourl");

		String ssourl = SecurityConfig.getInstance().getSsoServerUrl();
		// ���书��- ��Ե�½�û��ĸĽ�,ֻ������һ���ʻ���½һ��
		OnlineUser oluser = (OnlineUser)request.getSession().getAttribute(UserService.Login_Key);
		//��յ�½��Ϣ
		if(oluser!=null){
			String username=oluser.getLoginname();
			WebCache.removeCache(username);
		}
		///////////////////////////
		// ���Session
		request.getSession().invalidate();

		

		if (StringUtils.isEmpty(gotourl)) {
			response.sendRedirect(ssourl + "/sso/login.jsp");
		} else {
			response.sendRedirect(gotourl);
		}
	}

	// Process the HTTP Post request
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	// Clean up resources
	public void destroy() {
	}
}
