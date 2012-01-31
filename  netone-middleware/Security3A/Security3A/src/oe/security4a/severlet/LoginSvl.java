package oe.security4a.severlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

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

/**
 * �����½����,���ڵ�½Ӧ��
 * 
 * @author chenjx <br>
 *         mail:15860836998@139.com
 * 
 */
public class LoginSvl extends HttpServlet {

	public static final ResourceBundle messages = ResourceBundle.getBundle(
			"ssoserver", Locale.CHINESE);

	private boolean imagecodeavail;

	private static final String CONTENT_TYPE = "text/html; charset=GBK";

	private int sessiontimeout;

	private String gotourlX = "";

	private String encryptionMode = "default";

	// Initialize global variables
	public void init() throws ServletException {

		try {
			String imagecode = messages.getString("ignoreimagecode");
			imagecodeavail = "yes".equals(imagecode);
			int t = Integer.parseInt(messages.getString("sessionTimeOut"));
			sessiontimeout = t * 60;
			gotourlX = messages.getString("gotourl");

			CupmRmi cupm = (CupmRmi) RmiEntry.iv("cupm");
			encryptionMode = cupm.fetchConfig("EncryptionMode");

		} catch (Exception e) {
			System.err.println("lose imagecode config");
		}

	}

	// Process the HTTP Get request
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType(CONTENT_TYPE);
		// ��֤��¼���û�������
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
		} else if (StringUtils.isEmpty(username)) {
			errormsg.append(LoginInfo._ERROR_2[0]);
			request.setAttribute("errormsg", errormsg.toString());
			request.getRequestDispatcher("sso/login.jsp").forward(request,
					response);
		} else if (StringUtils.isEmpty(password)) {
			errormsg.append(LoginInfo._ERROR_3[0]);
			request.setAttribute("errormsg", errormsg.toString());
			request.getRequestDispatcher("sso/login.jsp").forward(request,
					response);
		} else {
			OnlineUser oluser = new OnlineUser();
			String reqip = request.getRemoteAddr();
			String reqhost = request.getRemoteHost();
			if (code == null || "".endsWith(code.trim())) {
				code = "0000";
			}
			try {
				CupmRmi cupm = (CupmRmi) RmiEntry.iv("cupm");
				String secu = request.getParameter("secu");
				if ("yes".equals(secu)) {// ������ϵͳ�Ѿ���¼��
					loginInOtherSys(request, response, oluser, username);
					cupm.log("��½", reqip, username, "�ɹ� FROM OA", "");
					return;
				}
				Clerk user = this.passwordMode(password, username);
				if (user != null && user.getDescription() != null) {
					// �û������Ѿ����ڵ��жϡ�
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

					oluser.setUserid(user.getName()); // ���ƣ����ģ�
					oluser.setLoginname(user.getDescription());// ��¼��(Ӣ��)
					oluser.setLoginseq(loginseq);
					oluser.setLoginip(reqip);
					oluser.setLoginhost(reqhost);
					oluser.setBelongto(code);

					// �����û���½��־
					request.getSession().setAttribute(UserService.Login_Key,
							oluser);

					String usercode = user.getDescription();
					// ���书��- ��Ե�½�û��ĸĽ�,ֻ������һ���ʻ���½һ��
					// 30���Ӻ����,180000��ʾ30����

					// �������cookies��Ϣ
					String cookies[] = { user.getDescription(), user.getName(),
							user.getPassword(), reqhost };
					CookiesOpe.addCookiesx(cookies, response);

					CupmRmi cupmrmi = (CupmRmi) RmiEntry.iv("cupm");
					String config = null;
					try {
						config = cupmrmi.fetchConfig("onlyoneOnline");
					} catch (Exception e) {

					}
					if ("yes".equals(config)) {// ����Ҫ��ֻ����һ���ʻ�һ���û�����

						if (!"adminx".equals(usercode)) {
							Object info = WebCache.getCache(usercode);
							if (info == null) {
								long time = System.currentTimeMillis() + 1800000;
								Date dateinfo = new Date(time);
								WebCache.setCache(usercode, usercode, dateinfo);
							} else {// �Ѿ���½��,��ֹ��½

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
					if (gotourlX != null && !gotourlX.equals("")) {
						// ru
						gotourl = gotourlX;
					}

					if (gotourl == null || gotourl.equals("")) {
						// û��������ת��ҳ�棬��ת����ҳ��
						request.getRequestDispatcher("sso/index.jsp").forward(
								request, response);
					} else {
						response.sendRedirect(gotourl);
						// ������sso����Ķ�������ת��validatesvlҳ�档
						// String todo = request.getParameter("todo");
						// if (todo == null || todo.equals("")) {
						// response.sendRedirect(gotourl);
						// } else {
						// request.getRequestDispatcher("validatesvl")
						// .forward(request, response);
						// }
					}

					// ����session�Ĺ���ʱ��
					HttpSession session = request.getSession();
					session.setMaxInactiveInterval(sessiontimeout);

					cupm.log("��½", reqip, username, "�ɹ�", "");

				} else {
					String errmsg = null;
					if (user != null) {
						errmsg = user.getOperationinfo();
					}

					if (errmsg != null && errmsg.indexOf("gotourl:") != -1) {
						String url = errmsg.substring(errmsg
								.indexOf("gotourl:") + 8);
						url = url
								+ "&gotourl="
								+ URLEncoder.encode(request.getRequestURL()
										.toString(), "GBK");
						response.sendRedirect(url);
					} else {
						if (user != null) {
							
							if(user.getPassword().equals("9$9$")){
								cupm.log("login", reqip, username,
										"forbid account!", "");
								
								errormsg.append(user.getOperationinfo());
								request.setAttribute("errormsg",
										LoginInfo._ERROR_9[0]);
								request.getRequestDispatcher("sso/login.jsp")
										.forward(request, response);
							}else{
								cupm.log("login", reqip, username,
										"password error!", "");
								
								errormsg.append(user.getOperationinfo());
								request.setAttribute("errormsg",
										LoginInfo._ERROR_6[0]);
								request.getRequestDispatcher("sso/login.jsp")
										.forward(request, response);
							}



						} else {
							cupm
									.log("login", reqip, username,
											"lose user!", "");

							request.setAttribute("errormsg",
									LoginInfo._ERROR_5[0]);
							request.getRequestDispatcher("sso/login.jsp")
									.forward(request, response);
						}

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void loginInOtherSys(HttpServletRequest request,
			HttpServletResponse response, OnlineUser oluser, String username)
			throws Exception {

		String loginseq = IdServer.uuid();

		ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
		Clerk clerk = rsrmi.loadClerk("0000", username);

		String reqip = request.getRemoteAddr();
		String reqhost = request.getRemoteHost();
		oluser.setUserid(clerk.getName()); // ���ƣ����ģ�
		oluser.setLoginname(username);// ��¼��(Ӣ��)
		oluser.setLoginseq(loginseq);
		oluser.setLoginip(reqip);
		oluser.setLoginhost(reqhost);
		oluser.setBelongto("0000");

		// �����û���½��־
		request.getSession().setAttribute(UserService.Login_Key, oluser);

		// ���书��- ��Ե�½�û��ĸĽ�,ֻ������һ���ʻ���½һ��
		// 30���Ӻ����,180000��ʾ30����

		// �������cookies��Ϣ
		String cookies[] = { username, clerk.getName(), clerk.getPassword(),
				reqhost };
		CookiesOpe.addCookiesx(cookies, response);

		CupmRmi cupmrmi = (CupmRmi) RmiEntry.iv("cupm");
		String config = null;
		try {
			config = cupmrmi.fetchConfig("onlyoneOnline");
		} catch (Exception e) {

		}
		String gotourl = request.getParameter("gotourl");

		response.sendRedirect(gotourl);

		// ����session�Ĺ���ʱ��
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(sessiontimeout);
	}

	private Clerk passwordMode(String password, String username)
			throws Exception {

		ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
		if ("md5".equalsIgnoreCase(encryptionMode)) {

			String passwordx = MD5Util.MD5_UTF16LE(password);

			Clerk user = rsrmi.loadClerk("0000", username);
			if (user == null) {
				return null;
			}

			if (!passwordx.equals(user.getPassword())) {
				user.setDescription(null);
			}
			user.setPassword(passwordx);
			return user;
		} else {
			return rsrmi.validationUserOpe("0000", username, password);
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
