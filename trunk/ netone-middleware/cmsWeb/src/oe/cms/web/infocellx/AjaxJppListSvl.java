package oe.cms.web.infocellx;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.env.client.EnvService;
import oe.rmi.client.RmiEntry;

/**
 * 获得系统定义的所有功能JPP核心元素
 * 
 * @author chen.jia.xun
 * 
 */
public class AjaxJppListSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public AjaxJppListSvl() {
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

		EnvService env = null;
		try {
			env = (EnvService) RmiEntry.iv("envinfo");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String[][] list = env.fetchAllJppScript();

		StringBuffer but = new StringBuffer();

		but.append("<option value='0'>--标准OESEEJ++脚本模板--</option>");
		for (int i = 0; i < list.length; i++) {
			but.append("<option value='" + list[i][0] + "'>" + list[i][1]
					+ "</option>");
		}

		response.setContentType("text/html; charset=GBK");
		PrintWriter out = response.getWriter();
		out.print("<select id='selJppJs'>" + but + "</select>");

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
