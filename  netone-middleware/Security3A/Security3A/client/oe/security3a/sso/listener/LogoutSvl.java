package oe.security3a.sso.listener;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;

import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.onlineuser.OnlineUserMgr;
import oe.security3a.sso.util.CookiesOpe;
import oe.security3a.sso.util.SsoUtil;

public class LogoutSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public LogoutSvl() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		OnlineUserMgr olmgr = new DefaultOnlineUserMgr();
		OnlineUser oluser = olmgr.getOnlineUser(request);
		String userid = "";
		if (oluser != null) {
			userid = oluser.getLoginname();
		}

		request.getSession().setAttribute("userid", null);
		String gotourl = request.getParameter("gotourl");
		gotourl = gotourl == null ? "" : gotourl;
		SsoUtil su = new SsoUtil();

		try {
			CookiesOpe.delcookie(request, response);

			CupmRmi cupm = (CupmRmi) RmiEntry.iv("cupm");
			String reqip = request.getRemoteAddr();
			cupm.log("×¢Ïú", reqip, userid, "success!", "");
			
			su.doLogout(request, response, gotourl);
			request.getSession().setAttribute("init", false);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occure
	 */
	public void init() throws ServletException {

	}

}
