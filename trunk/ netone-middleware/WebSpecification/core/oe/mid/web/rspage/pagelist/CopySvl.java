package oe.mid.web.rspage.pagelist;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.util.WebTip;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.sso.Security;
import oe.security3a.sso.util.LogUtil;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 拷贝创建，这里使用基于naturalname的方式创建，
 * 
 * 增加日志登记功能 Mar 10, 2009 4:02:28 PM<br>
 * 
 * modify wu.shang.zhan<br>
 */
public class CopySvl extends HttpServlet {
	private static ResourceBundle web = ResourceBundle.getBundle("resourceweb",
			Locale.CHINESE);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public CopySvl() {
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
		ResourceRmi rsrmi = null;
		try {
			// 读取名为resource的rmi服务
			rsrmi = (ResourceRmi) RmiEntry.iv("resource");
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

		Security sec = new Security(request);
		// 入口参数
		String pagename = request.getParameter("pagename");
		String task = request.getParameter("task");
		// 页面参数
		String id = request.getParameter("id");// 编号

		if (StringUtils.isNotEmpty(pagename)) {
			request.setAttribute("pagename", pagename);
		}
		// 新建子结点
		if ("copy".equals(task)) {
			String copyrs = request.getParameter("copysource");
			String copylevel = request.getParameter("level");
			if (copylevel == null || copylevel.equals("")) {
				copylevel = "0";
			}
			rsrmi.addFormCopyResource(id, new String[] { copyrs }, Integer
					.parseInt(copylevel));

			WebTip.htmlInfo("拷贝成功", true, true, response);

			addLogWhenCopy(rsrmi, sec, new String[] { copyrs }, Integer
					.parseInt(copylevel), id);

			return;

			// 进入新建页面
		} else {
			request.setAttribute("id", id);
			pagename = pagename + ".copy";
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

	/**
	 * 在拷贝创建的过程中，登记日志
	 * 
	 * @param rsrmi
	 * @param naturalname
	 * @param level
	 * @param id
	 * @param code
	 * @param userName
	 * @param ip
	 * @param parentNatural
	 */
	public void addLogWhenCopy(ResourceRmi rsrmi, Security sec, String[] naturalname,
			int level, String id) {
		try {

			if (level >= 0 && naturalname.length > 0) {
				for (int i = 0; i < naturalname.length; i++) {
					UmsProtectedobject upo = rsrmi
							.loadResourceByNatural(naturalname[i]);
					String xnat = StringUtils.substringAfterLast(upo
							.getNaturalname(), ".");

					UmsProtectedobject uponext = new UmsProtectedobject();
					BeanUtils.copyProperties(uponext, upo);
					uponext.setNaturalname(xnat);
					uponext.setParentdir(id);
					Serializable idnext = rsrmi.addResource(uponext);

					List list = rsrmi.subResource(upo.getId());
					String[] naturalnameNext = new String[list.size()];
					int loop = 0;
					for (Iterator iterator = list.iterator(); iterator
							.hasNext();) {
						UmsProtectedobject object = (UmsProtectedobject) iterator
								.next();
						naturalnameNext[loop++] = object.getNaturalname();
					}
					// 登记 创建 日志
					// 日志内容来源于刚创建成功的资源，使用naturalname
					UmsProtectedobject newUpo = rsrmi.loadResourceById(idnext
							.toString());
					sec.log(newUpo.getNaturalname(), LogUtil._ADD, LogUtil._ADD_SUCCESS);
					addLogWhenCopy(rsrmi,sec, naturalnameNext, --level, idnext
							.toString());
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
