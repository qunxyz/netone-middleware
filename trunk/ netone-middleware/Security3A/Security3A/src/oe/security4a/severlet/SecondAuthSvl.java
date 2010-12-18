package oe.security4a.severlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.security3a.sso.SecurityConfig;

/**
 * 单点登录服务 用于二次认证
 * 
 * @author chenjx <br>
 *         mail:15860836998@139.com
 * 
 */
public class SecondAuthSvl extends HttpServlet {
	private static final String CONTENT_TYPE = "text/html; charset=GBK";

	// Initialize global variables
	public void init() throws ServletException {
	}

	// Process the HTTP Get request
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		String gotourl = request.getParameter("gotourl");
		Object secondauth = request.getSession().getAttribute("secondauth");
		if (secondauth != null) {
			response.sendRedirect(gotourl);
		}

		String ssourl = SecurityConfig.getInstance().getSsoServerUrl();

		request.getSession().invalidate();

		response
				.sendRedirect(ssourl + "/sso/secondAuth.jsp?gotourl=" + gotourl);

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
