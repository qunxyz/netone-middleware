package oe.cms.web.infocellx;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.cms.CmsEntry;
import oe.cms.cfg.TCmsInfocell;
import oe.cms.dao.infocell.InfoCellDao;
import oe.security3a.sso.Security;

/**
 * 分类显示界面元素
 * 
 * @author chen.jia.xun
 * 
 */
public class AjaxCellListSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public AjaxCellListSvl() {
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

		String elementType = request.getParameter("elementType");
		Security ser = new Security(request);

		InfoCellDao infocellDao = (InfoCellDao) CmsEntry
				.fetchDao("infocellDao");
		List list = infocellDao
				.queryByType(ser.getUserLoginName(), elementType);

		StringBuffer but = new StringBuffer();

		but.append("<option value='0'>--选择已有的脚本--</option>");
		for (Iterator itr = list.iterator(); itr.hasNext();) {
			TCmsInfocell cellPre = (TCmsInfocell) itr.next();
			but.append("<option value='" + cellPre.getCellid() + "'>"
					+ cellPre.getCellname() + "</option>");
		}

		response.setContentType("text/html; charset=GBK");
		PrintWriter out = response.getWriter();
		out.print("<select id='selJs' onChange='getId()'>" + but + "</select>");

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
