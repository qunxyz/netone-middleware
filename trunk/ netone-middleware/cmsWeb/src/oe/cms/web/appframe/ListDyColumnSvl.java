package oe.cms.web.appframe;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.NotBoundException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.cav.bean.logic.column.TCsColumn;
import oe.frame.web.util.WebStr;
import oe.midware.dyform.service.DyFormService;
import oe.rmi.client.RmiEntry;

public class ListDyColumnSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ListDyColumnSvl() {
		super();
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
		response.setContentType("text/html; charset=GBK");
		String formcode = request.getParameter("formcode");
		String columnid = request.getParameter("columnid");
		DyFormService dy;
		try {
			dy = (DyFormService) RmiEntry.iv("dyhandle");
			List listcolumn = dy.fetchColumnList(formcode);
			StringBuffer but = new StringBuffer(
					"<select id='actionurl' name='actionurl'>");
			for (Iterator iterator = listcolumn.iterator(); iterator.hasNext();) {
				TCsColumn object = (TCsColumn) iterator.next();
				String id = object.getColumnid();
				String name = object.getColumname();
				String select = "";
				if (id.equals(columnid)) {
					select = " SELECTED";
				}
				but.append("<option value='" + id + "'" + select + ">" + name
						+ "</option>");
			}

			response.getWriter().print(but.toString() + "</select><input type='button' value='Ë¢ÐÂ' onclick='loadx()'>");
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		this.doGet(request, response);
	}

}
