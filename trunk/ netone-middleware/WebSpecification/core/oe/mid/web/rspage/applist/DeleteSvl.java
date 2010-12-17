package oe.mid.web.rspage.applist;

import java.io.IOException;
import java.rmi.NotBoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.util.WebTip;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ApplicationRmi;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.onlineuser.OnlineUserMgr;

import org.apache.commons.lang.StringUtils;



public class DeleteSvl extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public DeleteSvl() {
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

		String loginuser = oluser.getLoginname();
		boolean isAdmin = false;
		try {
			CupmRmi cupm = (CupmRmi) RmiEntry.iv("cupm");
			String admin = (String) cupm.fetchConfig("admin");
			if (!loginuser.equals(admin)) {
				WebTip.htmlInfo("您不是管理员，无权管理目录结构", true, response);
				return;
			}
		} catch (NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ApplicationRmi rsrmi = null;
		try {
			// 读取名为resource的rmi服务
			rsrmi = (ApplicationRmi) RmiEntry.iv("application");
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		// 入口参数
		String pagename = request.getParameter("pagename");
		// 页面参数
		String delid = request.getParameter("delid");

		System.out.println("deeeee" + delid);

		if (StringUtils.isNotEmpty(pagename)) {
			request.setAttribute("pagename", pagename);
		}
		// 多个删除
		if ("del".equals(request.getParameter("task"))) {
			if (request.getParameter("chkid") != null) {
				String str[] = request.getParameterValues("chkid");
				for (int i = 0; i < str.length; i++) {
					if (!"0".equals(str[i])) {
						if (!rsrmi.drop(new Long(str[i]))) {
							request.setAttribute("DeleteSuccess", "n");
							break;
						}
					}
					request.setAttribute("DelSuccess", "y");
				}
			}
			// 单个删除
		} else if ("delone".equals(request.getParameter("task"))) {
			if (!"0".equals(delid)) {
				if (!rsrmi.drop(new Long(delid))) {
					request.setAttribute("DeleteSuccess", "n");
				} else {
					request.setAttribute("DelSuccess", "y");
				}
			}
		}
		WebTip.htmlInfoOri("<script>window.close();opener.search()</script>", response);
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
