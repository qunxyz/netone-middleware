package oe.mid.web.rspage.pagelist;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.util.WebTip;
import oe.mid.web.rspage.common.WriteIoInfo;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.ProtectedObjectReference;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.sso.Security;
import oe.security3a.sso.util.LogUtil;

import org.apache.commons.lang.StringUtils;

/**
 * 增加资源的时候进行权限判断 <br>
 * 1、进入增加页面的前，进行判断，如果没有权限，给予提示 <br>
 * 2、进行保存后，要保存资源，进行判断，如果没有权限，不给予保存<br>
 * 
 * 进行日志登记 Mar 8, 2009 7:39:42 PM<br>
 * 
 * modify wu.shang.zhan<br>
 */

public class AddSvl extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static ResourceBundle web = ResourceBundle.getBundle("resourceweb",
			Locale.CHINESE);

	/**
	 * Constructor of the object.
	 */
	public AddSvl() {
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
		// 入口参数
		String pagename = request.getParameter("pagename");
		String task = request.getParameter("task");
		// 页面参数
		String id = request.getParameter("id");// 编号
		String naturalname = request.getParameter("naturalname");// 名称
		String name = request.getParameter("name");// 中文名称
		String actionurl = request.getParameter("actionurl");// 链接url
		String appid = request.getParameter("appid");// 应用程序id
		String ou = request.getParameter("ou");// ou
		String extendattribute = request.getParameter("extendattribute");// 其他
		String active = request.getParameter("active");// 其他
		String objecttype = request.getParameter("objecttype");// 其他
		String inclusion = request.getParameter("inclusion");// 目录属性
		String description = request.getParameter("description");

		Security sec = new Security(request);

		if (StringUtils.isNotEmpty(pagename)) {
			request.setAttribute("pagename", pagename);
		}
		request.setAttribute("objecttype", objecttype);
		request.setAttribute("inclusion", inclusion);
		// 新建子结点
		if ("addsave".equals(task)) {
			if (null == id || "".equals(id.trim())) {
				request.setAttribute("CreateSuccess", "n");
				request.setAttribute("errorinfo", "创建出现错误,无权");
			} else {
				UmsProtectedobject upotemp = rsrmi.loadResourceById(id);
				if (!sec.check(upotemp.getNaturalname(), LogUtil._READ)) {
					// 没有写权限
					request.setAttribute("CreateSuccess", "noPermission");
					request.setAttribute("errorinfo", "无权创建，创建失败");
				} else {
					UmsProtectedobject upo = new UmsProtectedobject();
					upo.setNaturalname(naturalname);
					upo.setName(name);
					upo.setActionurl(actionurl);
					upo.setExtendattribute(extendattribute);
					upo.setObjecttype(objecttype);
					upo.setDescription(description);

					if (StringUtils.isEmpty(inclusion)) {
						inclusion = ProtectedObjectReference._OBJ_INCLUSTION_NO;
					}
					upo.setInclusion(inclusion);

					if (StringUtils.isEmpty(active)) {
						active = "0";
					}
					upo.setActive(active);
					if (StringUtils.isEmpty(id)) {
						upo.setParentdir("0");
					} else {
						upo.setParentdir(id);
					}
					if (StringUtils.isNotEmpty(appid)) {
						upo.setAppid(new Long(appid));
					}

					try {

						Serializable idcreated = rsrmi.addResource(upo);

						if (idcreated != null) {
							request.setAttribute("id", id);
							request.setAttribute("appid", appid);
							request.setAttribute("ou", ou);

							request.setAttribute("CreateSuccess", "y");

							String serilaizer = request
									.getParameter("needSerilaizer");
							if ("1".equals(serilaizer)) {
								String realpath = getServletContext()
										.getRealPath("");
								boolean security = "1".equals(active);
								WriteIoInfo.todo(objecttype, idcreated,
										extendattribute, realpath, security);
							}
							// 从创建出来的资源中获得naturalname,登记创建资源的日志
							UmsProtectedobject newupo = rsrmi
									.loadResourceById((String) idcreated);
							sec.log(newupo.getNaturalname(),  LogUtil._ADD, LogUtil._ADD_SUCCESS);
						} else {
							request.setAttribute("CreateSuccess", "n");
							request.setAttribute("errorinfo", "名称已经存在");
						}

					} catch (Exception e) {
						e.printStackTrace();
						request.setAttribute("CreateSuccess", "n");
						request.setAttribute("errorinfo", e.getMessage());
					}
				}
			}

			// 进入新建页面
		} else {
			// 进入时就进行鉴权
			UmsProtectedobject upotemp = rsrmi.loadResourceById(id);
			if (sec.check(upotemp.getNaturalname(), LogUtil._ADD)) {
				request.setAttribute("id", id);
				request.setAttribute("appid", appid);
				request.setAttribute("ou", ou);
			} else {
				request.setAttribute("CreateSuccess", "noPermission");
				request.setAttribute("errorinfo", "无权创建");
			}

		}
		pagename = pagename + ".create";
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
