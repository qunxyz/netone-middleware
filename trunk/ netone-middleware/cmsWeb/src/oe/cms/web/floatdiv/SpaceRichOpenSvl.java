package oe.cms.web.floatdiv;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.cms.CmsEntry;
import oe.cms.dao.blog.Blog;
import oe.security3a.sso.Security;

public class SpaceRichOpenSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public SpaceRichOpenSvl() {
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

		response.setContentType("text/html; charset=GBK");

		Security ser = new Security(request);
		if (!ser.checkOnLine()) {
			response.getWriter().print(
					"<script>alert('您还未登陆');window.close();</script>");
			return;
		}
		Blog blog = (Blog) CmsEntry.fetchDao("blog");
		Long modelid = blog.regeidtBlog(ser.getUserLoginName());
		if (modelid == null) {// 已经开通过
			response
					.getWriter()
					.print(
							"<script>alert('您的OESEE空间已经开通过了!');window.close();</script>");
			return;
		}
		// String url = "/cmsWeb/showFloatDiv.do?modelid=" + modelid
		// + "&opr=design";

		// 目前处于性能的考虑，资源不共享
		//blog.unOpenRs(ser.getUserLoginName());

		response
				.getWriter()
				.print(
						"<script>alert('已经成功您的OESEE空间!');window.open('/cmsWeb/templateModel.do','_self')</script>");

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
