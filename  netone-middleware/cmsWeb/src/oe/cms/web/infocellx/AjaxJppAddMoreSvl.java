package oe.cms.web.infocellx;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import oe.frame.orm.Ormer;
import oe.frame.orm.OrmerEntry;
import oe.frame.web.util.WebStr;


import oe.cms.CmsEntry;
import oe.cms.cfg.TCmsInfocell;
import oe.cms.cfg.TCmsJppmidware;
import oe.cms.dao.blog.PersonRes;
import oe.cms.dao.infocell.InfoCellDao;

/**
 * 在JPP设计文档中加入一个JPPdemo
 * 
 * @author chen.jia.xun
 * 
 */
public class AjaxJppAddMoreSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public AjaxJppAddMoreSvl() {
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

		// response.setHeader("Pragma", "no-cache");
		// response.setHeader("Cache-Control", "no-cache");
		// response.setHeader("Expires", "0");

		String jppid = request.getParameter("jppid");

		TCmsJppmidware tjm = (TCmsJppmidware) OrmerEntry.fetchOrmer()
				.fetchQuerister().loadObject(TCmsJppmidware.class, jppid);
		String script = tjm.getJppmidscriptapi();
		// script = WebStr.encode(request, script);
		if (script != null) {
			String key = StringUtils.substringBetween(script, "$:", "{");
			if (key != null && !key.equals("")) {
				script = StringUtils.replace(script, "$:" + key + "{", "$:"
						+ jppid + "{");
			}
		}
		response.setContentType("text/html; charset=GBK");
		PrintWriter out = response.getWriter();

		out.print(script);

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
