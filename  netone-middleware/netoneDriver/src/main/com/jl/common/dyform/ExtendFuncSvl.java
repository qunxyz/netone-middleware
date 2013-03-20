package com.jl.common.dyform;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.cav.bean.logic.bus.TCsBus;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;

import com.jl.common.app.AppEntry;
import com.jl.common.app.AppObj;

public class ExtendFuncSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ExtendFuncSvl() {
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
		response.setContentType("text/html;charset=GBK");
		String lsh=request.getParameter("lsh");
		String appname=request.getParameter("appname");
		String ope=request.getParameter("ope");
		try{
		AppObj app=(AppObj)AppEntry.iv().loadApp(appname);

		String formcode=app.getDyformCode_();
		ResourceRmi rs=(ResourceRmi)RmiEntry.iv("resource");
		UmsProtectedobject upo=new UmsProtectedobject();
		upo.setExtendattribute(formcode);
		List list=rs.queryObjectProtectedObj(upo, null,0,1, "");
		String formname="";
		if(list!=null&&list.size()>0){
			formname=((UmsProtectedobject)list.get(0)).getNaturalname();
		}
		String opeName=(formname+"."+ope).toUpperCase();
		String ext=rs.loadResourceByNatural(opeName).getActionurl();
		String script=StringUtils.substringBetween(ext, "<script>", "</script>");
		TCsBus bus=DyEntry.iv().loadData(formcode, lsh);
		DyAnalysisXml dxl=new DyAnalysisXml();
		Object obj=dxl.executeScript(script, bus);
		if(obj!=null){
			response.getWriter().print(obj.toString());
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		response.getWriter().print("");
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
