package oe.cms.web.floatdiv;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.cms.CmsEntry;
import oe.cms.cfg.TCmsInfocell;
import oe.cms.dao.blog.Blog;
import oe.cms.dao.infocell.InfoCellDao;
import oe.security3a.sso.Security;


/**
 * Oec Netone设计工具上，添加文章使用的
 * 
 * @author chen.jia.xun
 * 
 */
public class AjaxUpdatePageSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public AjaxUpdatePageSvl() {
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

		// ope的值有两种 1:view ,2:done
		String ope = request.getParameter("ope");
		// 创建操作需要的属性
		String cellid = request.getParameter("id");
		TCmsInfocell cell = null;

		// 提交创建
		if ("done".equals(ope)) {

			Security ser = new Security(request);

			Blog blog = (Blog) CmsEntry.fetchDao("blog");

			blog.updatePage(request, response, ser.getUserLoginName());
			return;
		} else {
			InfoCellDao infocellDao = (InfoCellDao) CmsEntry
					.fetchDao("infocellDao");
			cell = infocellDao.view(cellid);
		}

		// 写回页面的属性
		request.setAttribute("cell", cell);
		request.setAttribute("ope", ope);
		// 打开创建页面
		request.getRequestDispatcher("/blog/UpdatePage.jsp").forward(request,
				response);
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
		// Put your code here
	}

}
