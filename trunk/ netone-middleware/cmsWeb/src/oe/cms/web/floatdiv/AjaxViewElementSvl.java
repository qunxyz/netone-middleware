package oe.cms.web.floatdiv;

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
import oe.cms.dao.blog.PersonRes;
import oe.cms.dao.infocell.InfoCellDao;
import oe.frame.orm.Ormer;
import oe.frame.orm.OrmerEntry;
import oe.security3a.sso.Security;

/**
 * Oec Netone设计工具上，配合J++组,获得组中所有的J++元素
 * 
 * @author chen.jia.xun
 * 
 */
public class AjaxViewElementSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public AjaxViewElementSvl() {
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
		String groupid = request.getParameter("groupid");
		String elementType = request.getParameter("elementType");
		Security ser = new Security(request);
		List list = null;
		if (groupid != null) {

			Ormer ormer = OrmerEntry.fetchOrmer();
			TCmsInfocell cell = new TCmsInfocell();
			cell.setBelongto(groupid);
			PersonRes.fetchCondition(ser.getUserLoginName());
			list = ormer.fetchQuerister().queryObjects(cell,null);
		} else {
			InfoCellDao infocellDao = (InfoCellDao) CmsEntry
					.fetchDao("infocellDao");
			list = infocellDao.queryByType(ser.getUserLoginName(), elementType);
		}
		StringBuffer but = new StringBuffer();

		but.append("<option value='0'>--请选择元素--</option>");
		for (Iterator itr = list.iterator(); itr.hasNext();) {
			TCmsInfocell cellPre = (TCmsInfocell) itr.next();
			but.append("<option value='" + cellPre.getCellid() + "'>"
					+ cellPre.getCellname() + "</option>");
		}
		String function = "<INPUT class='butt' type='button' value='添加' onClick='addgroupEle();'>" +
				"<INPUT class='butt' type='button' value='删除' onClick='delEle();'>";

		response.setContentType("text/html; charset=GBK");
		PrintWriter out = response.getWriter();
		out.print(function + "<select id='selJs'>" + but + "</select>");

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
