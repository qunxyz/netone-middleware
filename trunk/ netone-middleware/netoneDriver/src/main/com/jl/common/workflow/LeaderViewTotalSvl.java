package com.jl.common.workflow;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import oe.frame.web.WebCache;

import org.apache.commons.lang.StringUtils;

public class LeaderViewTotalSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public LeaderViewTotalSvl() {
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

		String processid=request.getParameter("processid");
		Object data=WebCache.getCache("lv_proc"+processid);
		String jsonpcallback=request.getParameter("jsonpcallback");
		if(data==null){
			data="";
		}
		response.setContentType("text/json;charset=gbk");
		String info=JSONArray.fromObject(data).toString();
		response.getWriter().write(jsonpcallback + "(" + info + ")");
		response.getWriter().flush();
		response.getWriter().close();
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
		this.doGet(request, response);
	}

}
