package oe.security4a.severlet;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import oe.env.client.EnvService;

import oe.rmi.client.RmiEntry;

import oe.security3a.sso.util.CookiesOpe;

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
		EnvService env = null;


		try {

			env = (EnvService) RmiEntry.iv("envinfo");
			String urlhead = env.fetchEnvValue("WEBSER_PHPCMS");

			String path = request.getContextPath();
			String securityahead = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";

			String cookies[] = CookiesOpe.readCookiex(request);
			if (cookies == null || cookies.length < 4) {
				System.err.println("cookies==null||cookies.length<4");
				response.sendRedirect(securityahead + "/sso/impl/login.jsp");
				return;
			}
			String loginame = cookies[0];
			String password = cookies[2];
			password = java.net.URLDecoder.decode(password, "gbk");
			System.out.println(urlhead
					+ "index.php?m=member&c=index&a=login4netone&username="
					+ loginame + "&password=" + password);
			response.sendRedirect(urlhead
					+ "index.php?m=member&c=index&a=login4netone&username="
					+ loginame + "&password=" + password);

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
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
