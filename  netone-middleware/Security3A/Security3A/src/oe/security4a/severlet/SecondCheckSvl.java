package oe.security4a.severlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.NotBoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.orm.util.IdServer;
import oe.frame.web.WebCache;
import oe.rmi.client.RmiEntry;
import oe.rmi.message.MessageHandle;
import oe.rmi.message.SendMail;
import oe.rmi.message.UmsBussformworklist;
import oe.rmi.message.mail.ManageMail;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.sso.SecurityConfig;
import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.onlineuser.OnlineUserMgr;

import org.apache.commons.lang.StringUtils;
import org.springframework.mail.MailMessage;

public class SecondCheckSvl extends HttpServlet {

	public static final String _SECOND_CHECK_HEAD = "secondcheck";// ��Webcache�е�KEY��Ϣͷ
	public static final String _SECOND_CHECK_PASS = "pass";// ��Webcache�е�ֵ

	/**
	 * 
	 */
	private static final long serialVersionUID = -3725467123600015540L;

	/**
	 * Constructor of the object.
	 */
	public SecondCheckSvl() {
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
		String task = request.getParameter("task");
		OnlineUserMgr olmgr = new DefaultOnlineUserMgr();
		OnlineUser oluser = olmgr.getOnlineUser(request);
		String username = oluser.getLoginname();
		String code = oluser.getBelongto();
		// ��֤������֤��
		if ("check".equals(task)) {
			String gotourl = request.getParameter("gotourl");
			if (SecondCheck.check(request, username)) {
				String timeout = SecurityConfig.getInstance()
						.getSSOServerProperty("sessionTimeOut");
				int timeoutValue = Integer.parseInt(timeout);
				WebCache.setCache(_SECOND_CHECK_HEAD + username,
						_SECOND_CHECK_PASS, timeoutValue);
				response.sendRedirect(gotourl);

			} else {
				request.setAttribute("errormsg", "������֤�벻��ȷ!�����»�ö�����֤��!");
				request.getRequestDispatcher(
						"/sso/secondAuth.jsp?gotourl2=" + gotourl).forward(
						request, response);
			}
		} else {
			// ���ɶ�����֤��

			String id = IdServer.xnumID();
			String alert = "";
			String mail = "";
			Clerk adminx =null;
			try {
				ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
				Clerk clerk = rsrmi.loadClerk(code, username);
				mail = clerk.getEmail();
				// ��ù���Ա���ʼ�
				adminx = rsrmi.loadClerk(code, "adminx");
				
				
			} catch (NotBoundException e) {
				e.printStackTrace();
			}
			if (StringUtils.isNotEmpty(mail)) {
				try {
					SendMail sendmail = (SendMail) RmiEntry.iv("sendmail");
					sendmail.send(mail, "��¼������֤��", id);
					alert = "��ö�����֤��, ����" + mail + "�в���!";
				} catch (NotBoundException e) {
					e.printStackTrace();
				}
			} else {
				alert = "δ��ö�����֤�룡�����˻����������Ƿ���ȷ";
			}
			response.setCharacterEncoding("GBK");
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.write(alert);
			out.flush();
			out.close();

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
