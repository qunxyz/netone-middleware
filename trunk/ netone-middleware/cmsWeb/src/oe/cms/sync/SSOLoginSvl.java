package oe.cms.sync;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.NotBoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.sun.mail.iap.Response;

import oe.env.client.EnvService;
import oe.frame.web.WebCache;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.onlineuser.OnlineUserMgr;
import oe.security3a.sso.util.CookiesOpe;
import oe.security3a.sso.util.Encryption;

public class SSOLoginSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public SSOLoginSvl() {
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
		String cookies[] = CookiesOpe.readCookiex(request);
		if (cookies == null || cookies.length < 4) {
			System.out.println("cookies==null||cookies.length<4");
			return;
		}
		String loginame = cookies[0];
		String password = cookies[2];
		password = java.net.URLDecoder.decode(password, "gbk");

		EnvService env = null;

		try {

			env = (EnvService) RmiEntry.iv("envinfo");
			String urlhead = env.fetchEnvValue("WEBSER_PHPCMS");
			response.sendRedirect(urlhead
					+ "index.php?m=member&c=index&a=login4netone&username="
					+ loginame + "&password=" + password);

		} catch (Exception e) {
			e.printStackTrace();
		}

		// response.getWriter().print(
		// "<script>window.location.href='"
		// + "http://127.0.0.1/cms/index.php?username="
		// + loginame + "&password=" + password + "'</script>");
		// request.getRequestDispatcher(
		// "/http://127.0.0.1/cms/index.php?m=member&c=index&a=login&username="
		// + loginame
		// + "&password=" + password).forward(request, response);

		// String xxx[]={"a","b","c","d"};
		// CookiesOpe.addCookiesx(xxx, response);

		// String [] xx= {"3501","xxxxx"};
		//		
		// String username=(String)WebCache.getCache("3501");
		// String password = "";
		//		
		// if(StringUtils.isNotEmpty(username))
		// {
		// try {
		// ResourceRmi resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
		// CupmRmi cupmRmi = (CupmRmi) RmiEntry.iv("cupm");
		//
		// Clerk clerk = resourceRmi.loadClerk("0000", username);
		// String pass = clerk.getPassword();
		// String key = cupmRmi.fetchConfig("EncrypKey");
		// password = Encryption.encry(pass, key, false);// 还原密码
		// if ("9$9$".equals(password)) {// 该账户已被禁止登录
		// //return true;
		// } else {
		// //return false;
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// response.setContentType("text/plain");
		// response.getWriter().print(loginame + "|;|" + password);
		// System.out.println("xxx:" + loginame + " | " + password);
		// response.getWriter().print(username);

		// }
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
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
