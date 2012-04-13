package oe.midware.dyform.service;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.bean.logic.form.TCsForm;
import oe.frame.web.util.WebTip;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

public class DyFormCopySvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public DyFormCopySvl() {
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

		doPost(request, response);
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

		String formid= (String) request.getParameter("formcode");//获取要复制的formcode
		String pagepath = (String)request.getParameter("pagepath");
		
		DyFormDesignService dys=null;
		DyFormService dysc=null;
		try {
			 //获取对象
			 dys = (DyFormDesignService) RmiEntry.iv("dydesign");
			 dysc = (DyFormService) RmiEntry.iv("dyhandle");
		} catch (Exception e) {
			e.printStackTrace();
		}
		List list =dysc.fetchColumnList(formid);
		TCsForm tcf =dysc.loadForm(formid);
		
		
		String datastr []=dys.create(tcf, pagepath);

		
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TCsColumn object = (TCsColumn) iterator.next();
		 	if( !object.getColumnid().equalsIgnoreCase("belongx")
					&&  !object.getColumnid().equalsIgnoreCase("timex")){
				object.setFormcode(datastr[1]);
				dys.addColumn(object);
		 	}
		}
		//创建资源信息
		ResourceRmi rsrmi = null;
		try {
			rsrmi =(ResourceRmi)RmiEntry.iv("resource");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	
		UmsProtectedobject upo = rsrmi.loadResourceByNatural(pagepath+"."+datastr[0]);
		upo.setName(upo.getName()+"-复制");

		rsrmi.updateResource(upo);
		
		WebTip.htmlInfo("OK", true, true, response);
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
