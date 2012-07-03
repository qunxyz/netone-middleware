package oe.mid.netone.web;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.env.client.EnvService;
import oe.rmi.client.RmiEntry;

import com.jl.common.workflow.DbTools;

public class ListPicSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ListPicSvl() {
		super();
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String id=request.getParameter("id");
		String sql="select unid from iss.t_file where d_unid='"+id+"'";
		List list=DbTools.queryData(sql);
		EnvService env = null;
		String value =null;
		try {
			env = (EnvService) RmiEntry.iv("envinfo");
			value = env.fetchEnvValue("WEBSER_APPFRAMEX");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		StringBuffer but=new StringBuffer("<html><body>");
		
		but.append("<a href='"+value+"/file.do?method=onMainView&d_unid="+id+"' target='_blank'>[Õº∆¨π‹¿Ì]</a><br>");
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map type = (Map) iterator.next();
			String unid=(String)type.get("unid");
			String url=value+"/file.do?method=onDownLoadFile&isOnLine=0&unid="+unid;
			but.append("<img src='"+url+"'/><br>");
		}
		but.append("</body></html>");
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(but.toString());

	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}

}
