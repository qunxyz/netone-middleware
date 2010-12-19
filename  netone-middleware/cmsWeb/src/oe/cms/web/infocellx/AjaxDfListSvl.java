package oe.cms.web.infocellx;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 获得系统定义的所有功能JPP核心元素
 * 
 * @author chen.jia.xun
 * 
 */
public class AjaxDfListSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public AjaxDfListSvl() {
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

		 response.setHeader("Pragma", "no-cache");
		 response.setHeader("Cache-Control", "no-cache");
		 response.setHeader("Expires", "0");

		String formcode = request.getParameter("formcode");
		response.setContentType("text/html; charset=GBK");
		PrintWriter out = response.getWriter();
		// if ("true".equals(EnvEntry.getEnvInfo("isServer"))) {
		// Blog blog = (Blog) CmsEntry.fetchDao("blog");
		// List formlist = blog.listFm(formcode);
		//
		// StringBuffer but = new StringBuffer();
		//
		// but.append("<option value='0'>--请选择--</option>");
		// for (int i = 0; i < formlist.size(); i++) {
		// Cacheobj formx = (Cacheobj) formlist.get(i);
		// but.append("<option value='" + formx.getValue() + "'>"
		// + formx.getLabel() + "</option>");
		// }
		//
		// out.print("<select id='selJs' onChange='getId()'>" + but
		// + "</select>");
		// } else {
		// out.print(" 您可以 <a
		// href='http://www.oesee.cn:8000/cavserweb/forum.htm'
		// target='_blank'><font
		// color='blue'><strong>进入论坛资源</strong></font></a>通过其中相关页面的URL中信息获得您所需要的参数");
		// }

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
