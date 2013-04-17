package com.jl.common.app;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jl.common.dyform.DyEntry;
import com.jl.common.dyform.DyForm;
import com.jl.common.dyform.DyFormData;

import com.jl.common.resource.RsEntry;

public class DataDocSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public DataDocSvl() {
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

		String appname=request.getParameter("appname");
		String lsh=request.getParameter("lsh");
		try {
			AppObj app=AppEntry.iv().loadApp(appname);
			String templatename=app.getFormtitle();
			String jspName=RsEntry.iv().loadResourceByNaturalname(templatename).getExtendattribute();
			DyFormData obj=DyEntry.iv().loadData(app.getDyformCode_(), lsh);
			DyForm[] forms=DyEntry.iv().loadForm(app.getDyformCode_()).getSubform_();
			
			Map map=new HashMap();
			map.put(app.getDyformCode_(), obj);
			for (int i = 0; i < forms.length; i++) {
				String formcodex=forms[i].getFormcode();
				DyFormData data=new DyFormData();
				data.setFatherlsh("lsh");
				data.setFormcode(formcodex);
				List list=DyEntry.iv().queryData(data, 0, 900, "");
				map.put(formcodex, list);
			}
			request.setAttribute("dy", map);
			String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ "cmsWeb" + "/";
			request.getRequestDispatcher(basePath+templatename+".jsp").forward(request,
					response);

		} catch (Exception e) {
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

	
	}

}
