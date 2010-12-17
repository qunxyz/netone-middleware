package oe.mid.web.rspage.applist;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.page.PageInfo;
import oe.frame.web.util.WebTip;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ApplicationRmi;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.seucore.obj.db.UmsApplication;
import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.onlineuser.OnlineUserMgr;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RightSvl extends HttpServlet {

	static Log log = LogFactory.getLog(RightSvl.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static int _PRE_PAGE = 15;// 默认分页

	private static ResourceBundle web = ResourceBundle.getBundle(
			"applicationweb", Locale.CHINESE);

	/**
	 * Constructor of the object.
	 */
	public RightSvl() {
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

		// 入口参数,该参数主要用于存放用户自定义的业务属性,并且需要反复多次在同一个页面上使用
		String entryvar = request.getParameter("entryvar");
		request.setAttribute("entryvar", entryvar);

		ApplicationRmi rsrmi = null;
		try {
			// 读取名为resource的rmi服务
			rsrmi = (ApplicationRmi) RmiEntry.iv("application");
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		// 入口参数
		String pagename = request.getParameter("pagename");
		// 查询参数
		String naturalname = request.getParameter("naturalname");// 名称
		String name = request.getParameter("name");// 中文名称
		String ext = request.getParameter("ext");// 由前台JS创建的SQL查询条件

		if (StringUtils.isNotEmpty(pagename)) {
			request.setAttribute("pagename", pagename);
		}
		// 模糊查询
		UmsApplication ua = new UmsApplication();
		ua.setActive("1");// 默认只显示有效的
		Map<String, String> map = new HashMap<String, String>();
		if (StringUtils.isNotEmpty(naturalname)) {
			naturalname = StringUtils.trim(naturalname);
			ua.setNaturalname("%" + naturalname + "%");
			map.put("naturalname", "like");
			request.setAttribute("naturalname", naturalname);
		}
		if (StringUtils.isNotEmpty(name)) {
			name = StringUtils.trim(name);
			ua.setName("%" + name + "%");
			map.put("name", "like");
			request.setAttribute("name", name);
		}
		String condition = null;
		if (StringUtils.isNotEmpty(ext)) {
			condition = ext;
			request.setAttribute("ext", ext);
		}
		log.debug("condition:" + ext);
		List list = null;
		// 分页信息
		PageInfo pageinfo = null;
		if (PageInfo.isPageEvent(request)) {
			pageinfo = new PageInfo(request, "app");
		} else {
			long dataNumber = rsrmi.queryObjectsNumber(ua, map, condition);
			pageinfo = new PageInfo((int) dataNumber, _PRE_PAGE, request, "app");// 分页信息
		}
		// 根据分页的参数查询本页的相关数据
		int from = pageinfo.getPageStartIndex();
		int to = pageinfo.getPageEndIndex();
		list = rsrmi.queryObjects(ua, map, from, to + 1 - from, condition);

		request.setAttribute("list", list);
		request.getRequestDispatcher(web.getString(pagename + ".list"))
				.forward(request, response);
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
