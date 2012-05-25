package oe.mid.web.rspage.pagelist;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.util.WebTip;
import oe.mid.web.rspage.pagelist.util.FetchResource;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ApplicationRmi;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class RightSvl extends HttpServlet {
	static Log log = LogFactory.getLog(RightSvl.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static ResourceBundle messages = ResourceBundle.getBundle(
			"resource", Locale.CHINESE);

	private static ResourceBundle web = ResourceBundle.getBundle("resourceweb",
			Locale.CHINESE);

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
		// 入口参数,该参数主要用于存放用户自定义的业务属性,并且需要反复多次在同一个页面上使用
		String entryvar = request.getParameter("entryvar");
		request.setAttribute("entryvar", entryvar);

		ResourceRmi rsrmi = null;
		try {
			// 读取名为resource的rmi服务
			rsrmi = (ResourceRmi) RmiEntry.iv("resource");
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

		ApplicationRmi app = null;
		try {
			// 读取名为resource的rmi服务
			app = (ApplicationRmi) RmiEntry.iv("application");
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		// 入口参数
		String pagename = request.getParameter("pagename");
		String appname = request.getParameter("appname");
		// 页面参数
		String naturalname = request.getParameter("naturalname");// 名称
		String name = request.getParameter("name");// 中文名称
		String spath = request.getParameter("spath");// 路径
		String spathname = request.getParameter("spathname");// 路径名

		String ext = request.getParameter("ext");// 扩展属性

		if (appname == null || appname.equals("") || appname.equals("null")) {
			appname = messages.getString("defaultrs");
		}

		String appid = request.getParameter("appid");// 应用程序id(这个是可以扩展的,实际上应该是一个路径)
		String id = request.getParameter("id");// 保护对象id

		if (StringUtils.isNotEmpty(pagename)) {
			request.setAttribute("pagename", pagename);
		}
		// if (StringUtils.isNotEmpty(appname)) {
		// if (StringUtils.isEmpty(appid)) {
		// UmsApplication appQ = new UmsApplication();
		// appQ.setNaturalname(appname);
		// List applist = app.queryObjects(appQ, null);
		// // 初始显示
		// if (applist == null || applist.size() == 0) {
		// throw new RuntimeException("没有该应用:" + appname);
		// }
		// appid = ((UmsApplication) applist.get(0)).getId().toString();
		// }
		// }
		UmsProtectedobject upo = null;
		// if (StringUtils.isNotEmpty(appid)) {
		// 初始显示树
		if (StringUtils.isEmpty(id)) {
			upo = new UmsProtectedobject();
			// 如果直接用应用程序的naturalname来访问的话,那么自动给其补充上一个naturalname,那么这个就是保护对象中对应的根元素
			if (StringUtils.indexOf(appname, ".") == -1) {
				appname = appname + "." + appname;
			}
			upo.setNaturalname(appname);
			// upo.setAppid(new Long(appid));
			List upolist = rsrmi.fetchResource(upo, null);
			if (upolist != null && upolist.size() > 0) {
				upo = (UmsProtectedobject) upolist.get(0);
			}
		} else {
			// 点击路径显示树
			upo = rsrmi.loadResourceById(id);
		}
		// }
		request.setAttribute("upo", upo);

		// 模糊查询获取子结点
		UmsProtectedobject upochild = new UmsProtectedobject();
		upochild.setParentdir(upo.getId());
		Map<String, String> map = new HashMap<String, String>();

		if (StringUtils.isNotEmpty(naturalname)) {
			naturalname = StringUtils.trim(naturalname);
			upochild.setNaturalname("%" + naturalname + "%");
			map.put("naturalname", "like");
			request.setAttribute("naturalname", naturalname);
		}
		if (StringUtils.isNotEmpty(name)) {
			name = StringUtils.trim(name);
			upochild.setName("%" + name + "%");
			map.put("name", "like");
			request.setAttribute("name", name);
		}
		if (StringUtils.isNotEmpty(spath)) {
			spath = StringUtils.trim(spath);
			upochild.setId(spath);
			request.setAttribute("spath", spath);
		}

		String condition = null;
		if (StringUtils.isNotEmpty(ext)) {
			condition = StringUtils.trim(ext);
			request.setAttribute("ext", ext);
		}else{
			condition=" order by aggregation";
		}
		log.debug("condition:" + condition);
		List list = rsrmi.fetchResource(upochild, map, condition);
		String rspath = messages.getString("rsLogicPath");

		if (StringUtils.isEmpty(id)) {
			// 第一次不要直接显示数据,让用户进入页面后点查询后再显示
			// 这个的意义在于1:可以减少系统的负担 2:能保证查询与页面上的条件保持一致
			request.setAttribute("list", new ArrayList());
		} else {
			request.setAttribute("list", FetchResource.todo(list, rspath,request));
		}

		// 获得 定制的资源页的地址，默认从配置resourceweb.properties中获得,如果默认中没有那么
		// 转向从资源页中获得
		pagename=pagename + ".list";
		String pageurl = null;
		try {
			pageurl = web.getString(pagename);
		} catch (Exception e) {
		}
		if (pageurl == null) {// 从资源页中获得
			UmsProtectedobject upoPage = null;
			try {
				upoPage = rsrmi.loadResourceByNatural(pagename);
			} catch (Exception e) {

			}
			if (upoPage != null) {
				pageurl = "/" + upoPage.getId() + ".jsp";
			} else {
				response.setCharacterEncoding("GBK");
				WebTip.htmlInfo("pagename：" + pagename + " 无效,非配置信息且非页面信息",
						true, response);
				return;
			}
		}
		request.getRequestDispatcher(pageurl).forward(request, response);
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
