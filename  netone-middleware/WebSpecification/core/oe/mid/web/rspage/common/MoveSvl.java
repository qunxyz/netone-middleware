package oe.mid.web.rspage.common;

import java.io.IOException;
import java.rmi.NotBoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.util.WebTip;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.ProtectedObjectReference;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.sso.Security;
import oe.security3a.sso.util.LogUtil;

import org.apache.commons.lang.StringUtils;

/**
 * 上下移动 增加安全检查
 * 
 * Mar 9, 2009 7:05:01 PM<br>
 * 
 * mofify wu.shang.zhan<br>
 */

public class MoveSvl extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public MoveSvl() {
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
		// 页面参数
		String value = request.getParameter("value");// 选中节点
		String value2 = request.getParameter("value2");// 节点上面或者下面的节点
		if (StringUtils.isNotEmpty(pagename)) {
			request.setAttribute("pagename", pagename);
		}
		UmsProtectedobject upo = rsrmi.loadResourceById(value);
		UmsProtectedobject upo2 = rsrmi.loadResourceById(value2);
		Long agg1 = upo.getAggregation();
		Long agg2 = upo2.getAggregation();

		if (agg1 == null
				|| agg2 == null
				|| ProtectedObjectReference._OBJ_INCLUSTION_YES.equals(upo
						.getInclusion())
				|| ProtectedObjectReference._OBJ_INCLUSTION_YES.equals(upo2
						.getInclusion())) {
			response.setContentType("text/html;charset=gbk");
			response.getWriter().print("<script>alert('无法移动!');</script>");
		} else {
			// 上下交换
			//2009-3-9 加入权限判断
			boolean b1 = sec.check(upo.getNaturalname(), LogUtil._EDIT);
			boolean b2 = sec.check(upo2.getNaturalname(), LogUtil._EDIT);
			if (b1 && b2) {
				upo.setAggregation(agg2);
				upo2.setAggregation(agg1);
				boolean rs1 = rsrmi.updateResource(upo);
				boolean rs2 = rsrmi.updateResource(upo2);
				if (rs1 && rs2) {
					sec.log(upo.getNaturalname(), LogUtil._EDIT, LogUtil._EDIT_SUCCESS);
					sec.log(upo2.getNaturalname(), LogUtil._EDIT, LogUtil._EDIT_SUCCESS);
				}
			} else {
				response.setContentType("text/html;charset=gbk");
				response.getWriter().print(
						"<script>alert('没有权限，无法移动!');</script>");
			}

		}
		WebTip.htmlInfoOri("<script>window.close();opener.search();</script>",
				response);
		// request.getRequestDispatcher("PagelistRightSvl").forward(request,
		// response);
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
