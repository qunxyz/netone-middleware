package oe.cms.web.floatdiv;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.cms.CmsEntry;
import oe.cms.dao.blog.Blog;
import oe.security3a.seucore.obj.ProtectedObjectReference;
import oe.security3a.sso.Security;

/**
 * Oec Netone设计工具上，添加文章使用的
 * 
 * @author chen.jia.xun
 * 
 */
public class AjaxPageItemSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public AjaxPageItemSvl() {
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

		Security ser = new Security(request);

		Blog blog = (Blog) CmsEntry.fetchDao("blog");

		String objType = request.getParameter("objtype");
		if (ProtectedObjectReference._OBJ_TYPE_URL.equalsIgnoreCase(objType)) {
			blog.addUrl(request, response, ser.getUserLoginName());
		} else if (ProtectedObjectReference._OBJ_TYPE_FILE
				.equalsIgnoreCase(objType)) {
			blog.addFile(request, response, ser.getUserLoginName());
		} else if (ProtectedObjectReference._OBJ_TYPE_PIC
				.equalsIgnoreCase(objType)) {
			blog.addPic(request, response, ser.getUserLoginName());
		} else if (ProtectedObjectReference._OBJ_TYPE_CUT
				.equalsIgnoreCase(objType)) {
			blog.addCutFish(request, response, ser.getUserLoginName());
		} else if (ProtectedObjectReference._OBJ_TYPE_ZIP
				.equalsIgnoreCase(objType)) {
			blog.addZip(request, response, ser.getUserLoginName());
		} else if (ProtectedObjectReference._OBJ_TYPE_ARTICLE
				.equalsIgnoreCase(objType)) {
			blog.addArticle(request, response, ser.getUserLoginName());
		} else if ("OUTITEM".equals(objType)) {
			blog.addOutItem(request, response, ser.getUserLoginName());
		} else {
			blog.addOecScript(request, response, ser.getUserLoginName());
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
		// Put your code here
	}

}
