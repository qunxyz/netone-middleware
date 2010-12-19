package oe.cms.web.floatdiv;

import java.io.IOException;
import java.io.PrintWriter;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import oe.cms.CmsEntry;
import oe.cms.cfg.TCmsInfocell;
import oe.cms.dao.infocell.InfoCellDao;
import oe.cms.runtime.HtmlStreamHandler;
import oe.frame.web.form.RequestParamMap;


/**
 * Oec Netone设计工具上，显示的,可修改布局的Web界面
 */
public class DivInnerHtmlPortalSvl extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5380645178065326837L;

	/**
	 * Constructor of the object.
	 */
	public DivInnerHtmlPortalSvl() {
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
		PrintWriter out = response.getWriter();

		String modelid = request.getParameter("modelid");
		System.out.println("modelid"+request.getParameter("modelid"));
		String cellid = request.getParameter("groupid");
		
		System.out.println("modelid"+request.getParameter("modelid"));
		
		String mode = request.getParameter("mode");

		// htmlh.setRequestInfo(request);

		HtmlStreamHandler htmlh = (HtmlStreamHandler) CmsEntry
				.fetchBean("htmlStreamHandler");

		String htmlsrc = htmlh.toPortal(cellid, mode, request);
	
		out.print(htmlsrc);
		out.flush();
		out.close();
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
