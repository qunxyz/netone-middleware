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

import org.apache.commons.lang.StringUtils;

import oe.cav.bean.logic.column.TCsColumn;
import oe.midware.dyform.service.DyFormService;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

public class ListDyColumnSelectSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ListDyColumnSelectSvl() {
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
		String appid = request.getParameter("appid");
		DyFormService dy;
		try {
			ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
			UmsProtectedobject upo = rs.loadResourceById(appid);

			String actionurl = upo.getActionurl();
			String formcode = "";
			if (actionurl != null) {
				String[] appinfo = StringUtils.split(actionurl, ";");
				if (appinfo != null && appinfo.length == 2) {
					formcode = StringUtils.substringBetween(appinfo[1], "[",
							"]");
				}
			}
			String thisid = request.getParameter("thisid");
			UmsProtectedobject upox = rs.loadResourceById(thisid);
			String descrpition = StringUtils.substringAfter(upox
					.getDescription(), "#");
			descrpition=StringUtils.replace(descrpition, "@1%", "");
			descrpition=StringUtils.replace(descrpition, "@0%", "");
			dy = (DyFormService) RmiEntry.iv("dyhandle");
			List listcolumn = dy.fetchColumnList(formcode);
			StringBuffer but = new StringBuffer(
					"<select id='selecx' name='selecx' multiple size='6'>");
			for (Iterator iterator = listcolumn.iterator(); iterator.hasNext();) {
				TCsColumn object = (TCsColumn) iterator.next();
				String id = object.getColumnid();
				String name = object.getColumname();

				but.append("<option value='" + name + "[" + id + "]'>" + name
						+ "</option>");
			}

			response
					.getWriter()
					.print(
							"<textarea rows='6' cols='30' name='columnedit' id='columnedit'>"
									+ descrpition
									+ "</textarea>&nbsp;&nbsp;"
									+ but.toString()
									+ "</select>&nbsp;<input type='button' value='Ìí¼Ó' onclick='addselect()'>");
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
		doGet(request, response);
	}

}
