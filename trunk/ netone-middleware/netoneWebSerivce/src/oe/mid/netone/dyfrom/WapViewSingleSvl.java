package oe.mid.netone.dyfrom;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.jl.common.dyform.DyEntry;
import com.jl.common.dyform.DyFormColumn;
import com.jl.common.dyform.DyFormData;
import com.jl.common.workflow.DbTools;

public class WapViewSingleSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public WapViewSingleSvl() {
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
		String info = "<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.0//EN\" \"http://www.wapforum.org/DTD/xhtml-mobile10.dtd\">"
				+ "<html xmlns=\"http://www.w3.org/1999/xhtml\">";

		String formcode = request.getParameter("formcode");
		String lsh = request.getParameter("lsh");
		String basePathX = request.getScheme() + "://"
				+ request.getServerName() + ":8080/";
		String path = request.getContextPath();
		StringBuffer but = new StringBuffer();
		try {
			List list = DyEntry.iv().fetchColumnList(formcode);
			DyFormData data = DyEntry.iv().loadData(formcode, lsh);
			System.out.println("data:" + data.getColumn3() + data.getColumn4());
			String firstPic = "";
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				DyFormColumn object = (DyFormColumn) iterator.next();
				String viewtype = object.getViewtype();
				System.out.println("viewtype" + viewtype);
				if ("15".equals(viewtype)) {
					List listx = DbTools
							.queryData("select unid zzud from iss.t_file where d_unid='"
									+ lsh + "'");
					if (listx.size() > 0) {
						firstPic = "<p><img alt='text' width='80' height='80' localsrc='icon' src='"
								+ basePathX + "scm/file.do?method=onDownLoadFile&isOnLine=0&unid="
								+ ((Map) listx.get(0)).get("zzud") + "'/></p>";
					}
				} else {
					
					if (object.getColumncode().startsWith("column")) {
						Object value=BeanUtils.getProperty(data, object
								.getColumncode());
						if(value==null)continue;
						String valuetmp=value.toString();
						if(valuetmp.equals("")||valuetmp.equals("null"))continue;
						but.append(object.getColumname()
								+ ":"
								+ value + "<br>");
					}

				}
			}
			String infox = firstPic + but;

			but = new StringBuffer(infox+"<br><a href='javascript:history.go(-1)'>[·µ»Ø]</a>");
		} catch (Exception e) {

		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(info);
		out.println("<HEAD></HEAD>");
		out.println("<BODY bgcolor='#FF9933'>");
		out.print(but.toString());

		out.println("  </BODY>");
		out.println("</HTML>");
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

	}

}
