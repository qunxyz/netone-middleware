package oe.cms.web.floatdiv;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.orm.Ormer;
import oe.frame.orm.OrmerEntry;
import oe.frame.web.form.RequestParamMap;

import oe.cms.CmsEntry;
import oe.cms.cfg.TCmsInfocell;
import oe.cms.runtime.HtmlStreamHandler;
import oe.cms.runtime.XHtmlCachepool;
import oe.cms.runtime.XHtmlHistoryCachePool;

public class DivInnerHtmlSvlHistory extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5380645178065326837L;

	/**
	 * Constructor of the object.
	 */
	public DivInnerHtmlSvlHistory() {
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

		RequestParamMap paramMap = new RequestParamMap(request);
		String cellid = paramMap.getParameter("cellid");
		String date = paramMap.getParameter("date");

		Ormer ormer = OrmerEntry.fetchOrmer();
		TCmsInfocell cellObj = null;
		try {
			cellObj = (TCmsInfocell) ormer.fetchQuerister().loadObject(
					TCmsInfocell.class, cellid);
		} catch (Exception e) {
			System.err.println("该咨讯元已删除");
		}
		HtmlStreamHandler htmlh = (HtmlStreamHandler) CmsEntry
				.fetchBean("htmlStreamHandler");
		// htmlh.setRequestInfo(request);
		String htmlsrc = XHtmlHistoryCachePool.fetchHistory(cellObj, date);

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
