package oe.mid.web.rspage.pagelist;

import java.io.IOException;
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
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.sso.Security;
import oe.security3a.sso.util.LogUtil;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * 修改资源，在 进入修改界面前 和 保存提交后都进行判断
 * 
 * Mar 8, 2009 7:48:26 PM<br>
 * 
 * modify wu.shang.zhan<br>
 */
public class ModifySvl extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static ResourceBundle web = ResourceBundle.getBundle("resourceweb",
			Locale.CHINESE);

	/**
	 * Constructor of the object.
	 */
	public ModifySvl() {
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
		String chkid = request.getParameter("chkid");// 选择id
		String id = request.getParameter("id");// 编号
		String naturalname = request.getParameter("naturalname");// 名称
		String name = request.getParameter("name");// 中文名称
		String actionurl = request.getParameter("actionurl");// 链接url
		String objecttype = request.getParameter("objecttype");// 类型
		String active = request.getParameter("active");// 有效
		String extendattribute = request.getParameter("extendattribute");// 扩展属性
		String description = request.getParameter("description");
		String aggregation=request.getParameter("aggregation");
		if (StringUtils.isNotEmpty(pagename)) {
			request.setAttribute("pagename", pagename);
		}

		// 进入修改页面
		if ("edit".equals(task)) {
			// 进行权限判断
			UmsProtectedobject upo = rsrmi.loadResourceById(chkid);
			Security sec = new Security(request);
			if (!sec.check(upo.getNaturalname(), LogUtil._EDIT)) {
				// 没有操作权限
				request.setAttribute("ModifySuccess", "noPermission");
			} else {
				request.setAttribute("upo", upo);
			}
			// 执行修改操作
		} else if ("editsave".equals(task)) {
			if (null == id || "".equals(id.trim())) {
				request.setAttribute("ModifySuccess", "n");
			} else {
				UmsProtectedobject upo = rsrmi.loadResourceById(id);

				Security sec = new Security(request);
				if (!sec.check(upo.getNaturalname(), LogUtil._EDIT)) {
					// 没有修改的权限
					request.setAttribute("ModifySuccess", "noPermission");
					// 登记日志
				} else {
					upo.setName(name);
					upo.setNaturalname(naturalname);
					upo.setActionurl(actionurl);
					upo.setExtendattribute(extendattribute);
					upo.setDescription(description);
					if (StringUtils.isEmpty(active)) {
						active = "0";
					}
					upo.setActive(active);
					if (!StringUtils.isEmpty(objecttype)) {
						upo.setObjecttype(objecttype);
					}
					upo.setExtendattribute(extendattribute);
					if(StringUtils.isNotEmpty(aggregation)){
						try{
						Long aggrex=Long.parseLong(aggregation);
						upo.setAggregation(aggrex);
						}catch(Exception e){
							
						}
						
					}
					request.setAttribute("upo", upo);
					if (rsrmi.updateResource(upo)) {
						String serilaizer = request
								.getParameter("needSerilaizer");
						if ("1".equals(serilaizer)) {
							String realpath = getServletContext().getRealPath(
									"");
							boolean security = "1".equals(active);
							WriteIoInfo.todo(objecttype, upo.getId(),
									extendattribute, realpath, security);
						}
						request.setAttribute("ModifySuccess", "y");
						// 登记日志
						sec.log(upo.getNaturalname(), LogUtil._EDIT,
								LogUtil._EDIT_SUCCESS);
					} else {
						request.setAttribute("ModifySuccess", "n");
					}
				}
			}

		}
		// 获得 定制的资源页的地址，默认从配置resourceweb.properties中获得,如果默认中没有那么
		// 转向从资源页中获得

		pagename = pagename + ".modify";
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
