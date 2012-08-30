package com.jl.common;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;

import org.apache.commons.lang.StringUtils;

public class Soasvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public Soasvl() {
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

		String name = request.getParameter("naturalname");
		if (StringUtils.isEmpty(name)) {
			response.getWriter().write("-1");
		}
		ResourceRmi rs = null;
		try {
			rs = (ResourceRmi) RmiEntry.iv("resource");
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		String script = rs.loadResourceByNatural(name).getExtendattribute();
		if (StringUtils.isEmpty(script)) {
			response.getWriter().write("0");
		}

		Map param = request.getParameterMap();
		for (Iterator iterator = param.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			if (key != null && key.startsWith("sr_")) {
				String keyuse = key;
				String value = request.getParameter(keyuse);
				value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
				script = StringUtils
						.replace(script, "$(" + keyuse + ")", value);
			} else if (key != null && "q".equals(key)) {
				String keyuse = key;
				String value = request.getParameter(keyuse);
				value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
				script = StringUtils
						.replace(script, "$(" + keyuse + ")", value);
			}
		}

		Object obj = ScriptTools.todo(script);
		if (obj == null) {
			response.getWriter().write("0");
		}
		String datatype = request.getParameter("datatype");
		if ("json".equals(datatype)) {
			response.setContentType("text/json;charset=UTF-8");
		} else {
			response.setContentType("text/html;charset=UTF-8");
		}
		
		response.getWriter().write(obj.toString());

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
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
