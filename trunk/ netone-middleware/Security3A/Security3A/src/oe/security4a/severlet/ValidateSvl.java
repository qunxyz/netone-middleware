package oe.security4a.severlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.net.URLEncoder;
import java.util.Locale;
import java.util.ResourceBundle;

import oe.security3a.seucore.accountser.UserService;
import oe.security3a.sso.SsoToken;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class ValidateSvl extends HttpServlet {

	ResourceBundle messages = ResourceBundle.getBundle("ssoserver",
			Locale.CHINESE);

	static Log log = LogFactory.getLog(ValidateSvl.class);

	private static final String CONTENT_TYPE = "text/html; charset=GBK";

	private long limittime;

	// Initialize global variables
	public void init() throws ServletException {

		// 分钟
		String tokenTimeOut = messages.getString("tokenTimeout");
		int limit = Integer.parseInt(tokenTimeOut);
		limittime = limit * 24 * 60 * 60 * 1000;
	}

	// Process the HTTP Get request
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		String todo = request.getParameter("todo");
		if (todo.equals("checktoken")) {
			log.debug("处理checktoken！");
			PrintWriter out = response.getWriter();
			if (checkLogin(request)) {
				// 验证token的正确性， URLConnection连接
				String user = request.getParameter("user");
				user = StringUtil.iso8859toGBK(user);
				OnlineUser oluser = (OnlineUser) request.getSession()
						.getAttribute(UserService.Login_Key);
				if (user.equals(oluser.getLoginname())) {
					log.debug("处理checktoken：验证登录用户成功！");
					out.println("checktoken:true");
				} else {
					log.debug("处理checktoken：验证登录用户失败,该用户与ssoserver的用户不匹配！");
					out.println("checktoken:relogin");
				}
			} else {
				log.debug("处理checktoken：验证登录用户失败,该用户的session不存在！");
				out.println("checktoken:false");
			}
			out.flush();
			out.close();
		} else if (todo.equals("regettoken")) {
			log.debug("处理regettoken！");
			// 重新发放token，URLConnection连接
			SsoToken st = createToken(request);
			PrintWriter out = response.getWriter();
			if (st != null) {
				log.debug("处理regettoken: 成功！");
				st.setFirstToken(false);
				st.setTokenValidated(false);
				out.print("regettoken:" + st.tokenToString());
			} else {
				out.print("regettoken:false");
				log.debug("处理regettoken: 生成token失败！");
			}
			out.flush();
			out.close();
		} else if (todo.equals("addtoken")) {
			log.debug("处理addtoken！");
			// 添加token到请求， ie连接
			// 验证是否登录
			SsoToken st = createToken(request);
			if (st == null) {
				log.debug("处理addtoken : 用户没有登录，无法生成ssoserver的token！");
				String url = "sso/login.jsp";
				String gotourl = request.getParameter("gotourl");

				if (!gotourl.equals("")) {
					url = "sso/login.jsp?todo=addtoken&gotourl="
							+ URLEncoder.encode(gotourl, "GBK");
				}
				response.sendRedirect(url);
			} else {
				log.debug("处理addtoken : 用户已经登录，处理成功！");
				String gotourl = request.getParameter("gotourl");
				st.setFirstToken(true);
				st.setTokenValidated(false);

				PrintWriter out = response.getWriter();
				out.println("<html>");
				out.println("<head><title>跳转到目标页面...</title></head>");
				out
						.println("<body onload=\"javascript:document.forms[0].submit();\">");
				out.println("<form action='" + gotourl + "' method='post'>");
				out.println("<input type='hidden' name='addtoken' value='"
						+ st.tokenToString() + "'>");
				out.println("</form>");
				out.println("</body>");
				out.println("</html>");
				out.flush();
				out.close();
			}
		}

	}

	/**
	 * 验证用户session是否有存在！
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return boolean
	 */
	private boolean checkLogin(HttpServletRequest request) {
		Object obj = request.getSession().getAttribute(UserService.Login_Key);
		if (obj != null) {
			return true;
		}
		return false;
	}

	private SsoToken createToken(HttpServletRequest request) {
		Object obj = request.getSession().getAttribute(UserService.Login_Key);
		if (obj != null) {
			OnlineUser oluser = (OnlineUser) obj;
			log.debug("创建用户：" + oluser.getLoginname() + "的SsoToken！");
			SsoToken st = new SsoToken();
			st.setUserid(oluser.getUserid());
			st.setLoginName(oluser.getLoginname());
			st.setLoginseq(oluser.getLoginseq());
			st.setLoginip(oluser.getLoginip());
			st.setLoginhost(oluser.getLoginhost());
			st.setCode(oluser.getBelongto());
			st.setSessionId(request.getRequestedSessionId());
			long time = System.currentTimeMillis();
			st.setLimitTime(time + limittime);
			return st;
		}
		return null;
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
