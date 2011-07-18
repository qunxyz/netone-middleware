package oe.security4a.severlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import oe.frame.orm.util.IdServer;
import oe.frame.web.WebCache;
import oe.rmi.client.RmiEntry;
import oe.security3a.SeumanEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.accountser.UserManager;
import oe.security3a.seucore.accountser.UserService;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.sso.LoginInfo;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.util.CookiesOpe;
import oe.security3a.sso.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 单点登陆服务,用于登陆应用
 * 
 * @author chenjx <br>
 *         mail:15860836998@139.com
 * 
 */
public class LoginSvl extends HttpServlet {

	public static final ResourceBundle messages = ResourceBundle.getBundle(
			"ssoserver", Locale.CHINESE);

	private boolean imagecodeavail;

	static Log log = LogFactory.getLog(LoginSvl.class);

	private static final String CONTENT_TYPE = "text/html; charset=GBK";

	private int sessiontimeout;

	// Initialize global variables
	public void init() throws ServletException {

		try {
			String imagecode = messages.getString("ignoreimagecode");
			imagecodeavail = "yes".equals(imagecode);
			int t = Integer.parseInt(messages.getString("sessionTimeOut"));
			sessiontimeout = t * 60;
		} catch (Exception e) {
			System.err.println("lose imagecode config");
		}

	}

	// Process the HTTP Get request
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		// 验证登录的用户名密码
		String username = StringUtil.iso8859toGBK(request
				.getParameter("username"));
		String password = request.getParameter("password");
		String code = request.getParameter("code");
		StringBuffer errormsg = new StringBuffer();

		if (!imagecodeavail && !ImageCodeCheck.check(request)) {
			errormsg.append(LoginInfo._ERROR_1[0]);
			request.setAttribute("errormsg", errormsg.toString());
			request.getRequestDispatcher("sso/login.jsp").forward(request,
					response);
		} else if ("".equals(username.trim())) {
			errormsg.append(LoginInfo._ERROR_2[0]);
			request.setAttribute("errormsg", errormsg.toString());
			request.getRequestDispatcher("sso/login.jsp").forward(request,
					response);
		} else if ("".equals(password.trim())) {
			errormsg.append(LoginInfo._ERROR_3[0]);
			request.setAttribute("errormsg", errormsg.toString());
			request.getRequestDispatcher("sso/login.jsp").forward(request,
					response);
		} else {
			OnlineUser oluser = new OnlineUser();

			if (code == null || "".endsWith(code.trim())) {
				code = "0000";
			}
			try {
				ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
				Clerk user = rsrmi.validationUserOpe(code, username, password);
				if (user.getDescription() != null) {
					// 用户密码已经过期的判断。
					if (errormsg.toString() != null) {
						if (LoginInfo._ERROR_4[0].equals(errormsg)) {
							request.setAttribute("errormsg", errormsg
									.toString());
							request.getRequestDispatcher("sso/login.jsp")
									.forward(request, response);
							return;
						}
					}

					String loginseq = IdServer.uuid();

					String reqip = request.getRemoteAddr();
					String reqhost = request.getRemoteHost();
					oluser.setUserid(user.getName()); // 名称（中文）
					oluser.setLoginname(user.getDescription());// 登录名(英文)
					oluser.setLoginseq(loginseq);
					oluser.setLoginip(reqip);
					oluser.setLoginhost(reqhost);
					oluser.setBelongto(code);

					// 设置用户登陆标志
					request.getSession().setAttribute(UserService.Login_Key,
							oluser);

					String usercode = user.getDescription();
					// 补充功能- 针对登陆用户的改进,只能允许一个帐户登陆一次
					// 30分钟后过期,180000表示30分钟
					
					//另外添加cookies信息
					String cookies[]={user.getDescription(),user.getName(),password,reqhost};
					CookiesOpe.addCookiesx(cookies, response);

					CupmRmi cupmrmi = (CupmRmi) RmiEntry.iv("cupm");
					String config = null;
					try {
						config = cupmrmi.fetchConfig("onlyoneOnline");
					} catch (Exception e) {

					}
					if ("yes".equals(config)) {// 表明要求只能有一个帐户一个用户在线

						if (!"adminx".equals(usercode)) {
							Object info = WebCache.getCache(usercode);
							if (info == null) {
								long time = System.currentTimeMillis() + 1800000;
								Date dateinfo = new Date(time);
								WebCache.setCache(usercode, usercode, dateinfo);
							} else {// 已经登陆过,禁止登陆

								errormsg.append(LoginInfo._ERROR_8[0]);
								request.setAttribute("errormsg", errormsg
										.toString());
								request.getRequestDispatcher("sso/login.jsp")
										.forward(request, response);
								return;
							}
						}
					}
					// /////////////////////////////

					String gotourl = request.getParameter("gotourl");
					if (gotourl == null || gotourl.equals("")) {
						// 没有设置跳转的页面，跳转到主页。
						request.getRequestDispatcher("sso/index.jsp").forward(
								request, response);
					} else {
						// 设置了sso定义的动作，跳转到validatesvl页面。
						String todo = request.getParameter("todo");
						if (todo == null || todo.equals("")) {
							response.sendRedirect(gotourl);
						} else {
							request.getRequestDispatcher("validatesvl")
									.forward(request, response);
						}
					}

					// 设置session的过期时间
					HttpSession session = request.getSession();
					session.setMaxInactiveInterval(sessiontimeout);
				} else {
					String errmsg = user.getOperationinfo();
					int i = errmsg.indexOf("gotourl:");
					if (i != -1) {
						String url = errmsg.substring(i + 8);
						url = url
								+ "&gotourl="
								+ URLEncoder.encode(request.getRequestURL()
										.toString(), "GBK");
						response.sendRedirect(url);
					} else {
						errormsg.append(user.getOperationinfo());
						request.setAttribute("errormsg", errormsg.toString());
						request.getRequestDispatcher("sso/login.jsp").forward(
								request, response);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
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

	public static void main(String[] args) {
		UserManager userManager = (UserManager) SeumanEntry.iv("userManager");
	}

}
