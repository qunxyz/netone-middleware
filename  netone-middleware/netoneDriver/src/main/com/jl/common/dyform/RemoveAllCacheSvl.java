package com.jl.common.dyform;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.WebCache;
import oe.frame.web.util.WebTip;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;

public class RemoveAllCacheSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public RemoveAllCacheSvl() {
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
		try {
			ResourceRmi rs=(ResourceRmi)RmiEntry.iv("resource");
			List list=rs.subResourceByNaturalname("BUSSFORM.BUSSFORM");
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				UmsProtectedobject object = (UmsProtectedobject) iterator.next();
				if(StringUtils.isNotEmpty(object.getExtendattribute())){
					String formcode=object.getExtendattribute();
					WebCache.removeCache("fetchColumnList"+formcode);
					WebCache.removeCache("queryColumnQ"+formcode);
					WebCache.removeCache("queryColumnX0"+formcode);
					WebCache.removeCache("queryColumnX1"+formcode);
					WebCache.removeCache("queryColumnX2"+formcode);
					WebCache.removeCache("queryColumnX3"+formcode);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebTip.htmlInfo("Ok init!",true,false,response);
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
