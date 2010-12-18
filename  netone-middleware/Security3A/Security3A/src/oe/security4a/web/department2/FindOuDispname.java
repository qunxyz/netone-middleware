package oe.security4a.web.department2;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;

import org.apache.commons.lang.StringUtils;

public class FindOuDispname extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public FindOuDispname() {
		super();
	}
	
	static Map cache=new HashMap();

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
		
		String ou=request.getParameter("ou");
		String oukey=ou;
		ResourceRmi rs=null;
		try {
			rs = (ResourceRmi)RmiEntry.iv("resource");
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!cache.containsKey(oukey)){
			String ouarr[]=StringUtils.split(ou,".");
			StringBuffer butname=new StringBuffer();
			for (int i = 1; i < ouarr.length; i++) {
				String name=rs.loadResourceById(ouarr[i]).getName();
				butname.append("."+name);
			}
			cache.put(oukey, butname.substring(1));
		}
		response.setContentType("text/html;charset=gbk");
		response.getWriter().print(cache.get(oukey));
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
