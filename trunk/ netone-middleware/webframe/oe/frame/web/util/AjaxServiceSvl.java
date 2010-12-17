package oe.frame.web.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import oe.frame.web.form.RequestParamMap;

/**
 * 通用Ajax实现逻辑
 * 
 * @author hls 
 * 
 */
public class AjaxServiceSvl extends HttpServlet {

	private Log log = LogFactory.getLog(AjaxServiceSvl.class);

	/**
	 * Constructor of the object.
	 */
	public AjaxServiceSvl() {
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
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Expires", "0");
		PrintWriter out = response.getWriter();
		RequestParamMap reqmap = new RequestParamMap(request);
		String className = reqmap.getParameter("class");
		if (className != null && !"".equals(className)) {
			try {
				ClassLoader cl = Thread.currentThread().getContextClassLoader();
				Class c = Class.forName(className, false, cl);
				IAjaxService ajax = (IAjaxService) c.newInstance();
				String restr = ajax.execute(request, reqmap);
				out.print(restr);
			} catch (Exception e) {
				log.error("调用Ajax的服务：" + className + "出错!", e);
				throw new ServletException(e);
			}
		} else {
			throw new ServletException("request中没有找到class参数的值。");
		}
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
