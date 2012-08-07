package com.jl.common.dyform;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.env.client.EnvService;
import oe.rmi.client.RmiEntry;

public class SinglePicSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public SinglePicSvl() {
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
		EnvService env = null;
		String value =null;
		try {
			env = (EnvService) RmiEntry.iv("envinfo");
			value = env.fetchEnvValue("WEBSER_APPFRAMEX");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		String url=value+"/file.do?method=onDownLoadFile&isOnLine=0&unid="+request.getParameter("unid");
		response.getWriter().print("<html><body><img src='"+url+"'/></body></html>");

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
