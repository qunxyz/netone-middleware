package com.jl.common.netone;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.cav.bean.logic.form.TCsForm;
import oe.frame.web.util.WebTip;
import oe.midware.dyform.service.DyFormDesignService;
import oe.midware.dyform.service.DyFormService;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

public class DyisTake extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public DyisTake() {
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
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
           request.setCharacterEncoding("utf-8");
           String formcode=request.getParameter("formcode");
           String pagepath = (String) request.getParameter("pagepath");
           DyFormDesignService dys = null;
   		   DyFormService dysc = null;
   		   try {
   			// 获取对象
   			dys = (DyFormDesignService) RmiEntry.iv("dydesign");
   			dysc = (DyFormService) RmiEntry.iv("dyhandle");
   		  } catch (Exception e) {
   			e.printStackTrace();
   		  }
           TCsForm busForm = dysc.loadForm(formcode);
   	       String tablename = busForm.getTablename();
          UmsProtecte up=new UmsProtecte();
          UmsProtectedobject upobj= up.loadUmsProtecteNaturalname(pagepath+"."+tablename);
          upobj.setActive("1");
          up.UpdateUmsProtecte(upobj);
         WebTip.htmlInfo("OK", true, true, response);
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

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
