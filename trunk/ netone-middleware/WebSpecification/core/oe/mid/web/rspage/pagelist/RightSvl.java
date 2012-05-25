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
		// ��ڲ���,�ò�����Ҫ���ڴ���û��Զ����ҵ������,������Ҫ���������ͬһ��ҳ����ʹ��
		String entryvar = request.getParameter("entryvar");
		request.setAttribute("entryvar", entryvar);

		ResourceRmi rsrmi = null;
		try {
			// ��ȡ��Ϊresource��rmi����
			rsrmi = (ResourceRmi) RmiEntry.iv("resource");
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

		ApplicationRmi app = null;
		try {
			// ��ȡ��Ϊresource��rmi����
			app = (ApplicationRmi) RmiEntry.iv("application");
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		// ��ڲ���
		String pagename = request.getParameter("pagename");
		String appname = request.getParameter("appname");
		// ҳ�����
		String naturalname = request.getParameter("naturalname");// ����
		String name = request.getParameter("name");// ��������
		String spath = request.getParameter("spath");// ·��
		String spathname = request.getParameter("spathname");// ·����

		String ext = request.getParameter("ext");// ��չ����

		if (appname == null || appname.equals("") || appname.equals("null")) {
			appname = messages.getString("defaultrs");
		}

		String appid = request.getParameter("appid");// Ӧ�ó���id(����ǿ�����չ��,ʵ����Ӧ����һ��·��)
		String id = request.getParameter("id");// ��������id

		if (StringUtils.isNotEmpty(pagename)) {
			request.setAttribute("pagename", pagename);
		}
		// if (StringUtils.isNotEmpty(appname)) {
		// if (StringUtils.isEmpty(appid)) {
		// UmsApplication appQ = new UmsApplication();
		// appQ.setNaturalname(appname);
		// List applist = app.queryObjects(appQ, null);
		// // ��ʼ��ʾ
		// if (applist == null || applist.size() == 0) {
		// throw new RuntimeException("û�и�Ӧ��:" + appname);
		// }
		// appid = ((UmsApplication) applist.get(0)).getId().toString();
		// }
		// }
		UmsProtectedobject upo = null;
		// if (StringUtils.isNotEmpty(appid)) {
		// ��ʼ��ʾ��
		if (StringUtils.isEmpty(id)) {
			upo = new UmsProtectedobject();
			// ���ֱ����Ӧ�ó����naturalname�����ʵĻ�,��ô�Զ����䲹����һ��naturalname,��ô������Ǳ��������ж�Ӧ�ĸ�Ԫ��
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
			// ���·����ʾ��
			upo = rsrmi.loadResourceById(id);
		}
		// }
		request.setAttribute("upo", upo);

		// ģ����ѯ��ȡ�ӽ��
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
			// ��һ�β�Ҫֱ����ʾ����,���û�����ҳ�����ѯ������ʾ
			// �������������1:���Լ���ϵͳ�ĸ��� 2:�ܱ�֤��ѯ��ҳ���ϵ���������һ��
			request.setAttribute("list", new ArrayList());
		} else {
			request.setAttribute("list", FetchResource.todo(list, rspath,request));
		}

		// ��� ���Ƶ���Դҳ�ĵ�ַ��Ĭ�ϴ�����resourceweb.properties�л��,���Ĭ����û����ô
		// ת�����Դҳ�л��
		pagename=pagename + ".list";
		String pageurl = null;
		try {
			pageurl = web.getString(pagename);
		} catch (Exception e) {
		}
		if (pageurl == null) {// ����Դҳ�л��
			UmsProtectedobject upoPage = null;
			try {
				upoPage = rsrmi.loadResourceByNatural(pagename);
			} catch (Exception e) {

			}
			if (upoPage != null) {
				pageurl = "/" + upoPage.getId() + ".jsp";
			} else {
				response.setCharacterEncoding("GBK");
				WebTip.htmlInfo("pagename��" + pagename + " ��Ч,��������Ϣ�ҷ�ҳ����Ϣ",
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
