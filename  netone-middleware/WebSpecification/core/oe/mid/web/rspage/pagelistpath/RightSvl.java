package oe.mid.web.rspage.pagelistpath;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.util.WebTip;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ApplicationRmi;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.sso.Security;
import oe.security3a.sso.util.LogUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * ��Դ����ͬʱ������Դ��ȫ���
 * 
 * Mar 8, 2009 8:12:50 PM<br>
 * 
 * modify wu.shang.zhan<br>
 */
public class RightSvl extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static ResourceBundle messages = ResourceBundle.getBundle(
			"resource", Locale.CHINESE);

	private static ResourceBundle web = ResourceBundle.getBundle("resourceweb",
			Locale.CHINESE);

	static Log log = LogFactory.getLog(RightSvl.class);

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
		// ҳ�����
		String snaturalname = request.getParameter("snaturalname");// ����
		String sname = request.getParameter("sname");// ��������
		String begintime = request.getParameter("begintime");// ��ʼʱ��
		String endtime = request.getParameter("endtime");// ����ʱ��
		String appname = request.getParameter("appname");// Ӧ�ó�������
		if (appname == null || appname.equals("") || appname.equals("null")) {
			appname = messages.getString("defaultrs");
		}

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

		// ����·������
		String naturalname = upo.getNaturalname();
		String ou = upo.getOu();
		StringBuffer sb = new StringBuffer();

		if (StringUtils.isNotEmpty(naturalname)) {
			String[] ss = StringUtils.split(naturalname, ".");
			String[] ous = StringUtils.split(ou, ".");
			if (ss != null && ss.length > 0) {
				for (int i = 1; i < ss.length; i++) {
					if (i == 1) {
						sb.append("<a href=javascript:link('" + ous[i] + "')>"
								+ ss[i] + "</a>");
					} else {
						sb.append(" / <a href=javascript:link('" + ous[i]
								+ "')>" + ss[i] + "</a>");
					}
				}
			}
		}
		request.setAttribute("link", sb.toString());
		request.setAttribute("pathreal", naturalname);

		// ģ����ѯ��ȡ�ӽ��
		String parentid = upo.getId().toString();
		UmsProtectedobject upochild = new UmsProtectedobject();
		upochild.setParentdir(parentid);
		Map<String, String> map = new HashMap<String, String>();
		StringBuffer condi = new StringBuffer();
		if (StringUtils.isNotEmpty(snaturalname)) {
			snaturalname = StringUtils.trim(snaturalname);
			upochild.setNaturalname("%" + snaturalname + "%");
			map.put("naturalname", "like");
			request.setAttribute("snaturalname", snaturalname);
		}
		if (StringUtils.isNotEmpty(sname)) {
			sname = StringUtils.trim(sname);
			upochild.setName("%" + sname + "%");
			map.put("name", "like");
			request.setAttribute("sname", sname);
		}
		if (StringUtils.isNotEmpty(begintime)) {
			condi.append(" and ");
			condi.append(" created >= to_days('" + begintime + "')");
			request.setAttribute("begintime", begintime);
		}
		if (StringUtils.isNotEmpty(endtime)) {
			condi.append(" and ");
			condi.append(" created < to_days('" + endtime + "')");
			request.setAttribute("endtime", endtime);
		}
		String condition = null;
		String ext = request.getParameter("ext");// ��չ����
		log.debug(condition);
		if (StringUtils.isNotEmpty(ext)) {
			condition = StringUtils.trim(ext);
			request.setAttribute("ext", ext);
			condi.append(ext);
		}

		condi.append(" order by inclusion desc");
		List list = rsrmi.fetchResource(upochild, map, condi.toString());
		// ɸѡ���
		List<UmsProtectedobject> newlist = new ArrayList<UmsProtectedobject>();
		try {

			String rsLogicPath = messages.getString("rsLogicPath");
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				UmsProtectedobject tmp = (UmsProtectedobject) iter.next();
				Security sec = new Security(request);
				if (sec.check(tmp.getNaturalname(), LogUtil._READ)) {
					
					if (StringUtils.contains(tmp.getActionurl(), rsLogicPath)) {
						tmp.setActionurl(StringUtils.replace(
								tmp.getActionurl(), rsLogicPath, ""));
					}

					newlist.add(tmp);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		request.setAttribute("list", list);

		// ��� ���Ƶ���Դҳ�ĵ�ַ��Ĭ�ϴ�����resourceweb.properties�л��,���Ĭ����û����ô
		// ת�����Դҳ�л��
		pagename = pagename + ".pathlist";
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
				e.printStackTrace();
			}
			if (upoPage != null) {
				pageurl = "/" + upoPage.getId() + ".jsp";
			} else {
				response.setCharacterEncoding("GBK");
				WebTip.htmlInfo("pagename��" + pagename
						+ " ��Ч,���ô������ȱ��ҳ����߷��ʷ�ʽ����", true, response);
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
