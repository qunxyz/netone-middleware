package oe.cms.web.floatdiv;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.cms.cfg.TCmsInfomodel;
import oe.frame.orm.OrmerEntry;
import oe.security3a.sso.Security;

public class AjaxInfoModelSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public AjaxInfoModelSvl() {
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

		TCmsInfomodel model = new TCmsInfomodel();
		model.setParticipant(ser.getUserLoginName());
		List list = OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(
				model, null);

		StringBuffer but = new StringBuffer();

		but.append("<option value='0'>--请选择--</option>");
		for (Iterator itr = list.iterator(); itr.hasNext();) {
			TCmsInfomodel cellPre = (TCmsInfomodel) itr.next();
			but.append("<option value='" + cellPre.getModelid() + "'>"
					+ cellPre.getModelname() + "</option>");
		}
		String flash = "<input class='butt' type='button' value='刷新' onClick='listModel();'/>";
		String function = "<INPUT class='butt' type='button' value='进入' onClick='editModel();'>"
				+ "<INPUT class='butt' type='button' value='删除' onClick='delModel();' > &nbsp;&nbsp;&nbsp;"
				+ "<input class='butt' type='button' onClick='createModel()' value='新建'/>";

		response.setContentType("text/html; charset=GBK");
		PrintWriter out = response.getWriter();
		out.print(flash + "<select id='selModel' onChange='makeurl()' style='width:100'>" + but
				+ "</select>&nbsp;&nbsp" + function);

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
