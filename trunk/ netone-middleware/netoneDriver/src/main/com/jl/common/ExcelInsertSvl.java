package com.jl.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.NotBoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.env.client.EnvService;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

public class ExcelInsertSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ExcelInsertSvl() {
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

		ResourceRmi rs;
		try {
			rs = (ResourceRmi)RmiEntry.iv("resource");
			UmsProtectedobject upo=rs.loadResourceByNatural(request.getParameter("name"));
			EnvService env=(EnvService)RmiEntry.iv("envinfo");
			String envx=env.fetchEnvValue("WEBSER_BIWEB");
			String pageurl=envx+"/PagelistUploadSvl?task=addfile&dirid="+upo.getId()+"&appid=2&pagename=excel";
			
			request.getRequestDispatcher(pageurl).forward(request, response);
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
