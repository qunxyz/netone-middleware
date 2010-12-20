package oe.cav.web.design.column;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.NotBoundException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.env.client.EnvService;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ApplicationRmi;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsApplication;


/**
 * 显示资源列表(管理)
 * 
 * @author chen.jia.xun
 * 
 */
public class TreeManageSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public TreeManageSvl() {
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
		response.setHeader("Cache-Control",
				"must-revalidate, no-store, no-cache");

		ResourceRmi rmis = null;
		ApplicationRmi app = null;
		try {
			rmis = (ResourceRmi) RmiEntry.iv("resource");
			app = (ApplicationRmi) RmiEntry.iv("application");
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		UmsApplication appObj = new UmsApplication();
		List list = app.queryObjects(appObj, null);
		StringBuffer but = new StringBuffer();
		but.append("<option value='0'>--选择--</option>");
		for (Iterator itr = list.iterator(); itr.hasNext();) {
			UmsApplication cellPre = (UmsApplication) itr.next();
			but.append("<option value='" + cellPre.getNaturalname() + "'>"
					+ cellPre.getName() + "</option>");

		}

		// 创建资源的管理功能
		String flash = "<input type='button' onClick='flashTree()' value='刷新'/>";
		String value = "";
		try {
			EnvService envinfo = (EnvService) RmiEntry.iv("envinfo");
			value = envinfo.fetchEnvValue("$resourceman*");
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String input2 = "<input type='button' onClick=\"window.open('" + value
				+ "')\"  value='  定义  '/>";
		response.setContentType("text/html; charset=GBK");
		PrintWriter out = response.getWriter();
		out.print(flash + "<select id='selJs' onChange='addTreeToValueList()'>"
				+ but + "</select>" + input2 + "&nbsp;");

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
