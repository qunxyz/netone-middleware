package oe.bi.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.NotBoundException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.cav.bean.logic.column.TCsColumn;
import oe.frame.web.util.WebTip;
import oe.midware.dyform.service.DyFormDesignService;
import oe.midware.dyform.service.DyFormService;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.ProtectedObjectReference;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

public class DySecuritySvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public DySecuritySvl() {
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
		// 获得资源ID
		String resourceid = request.getParameter("rsid");
		// 资源操作句柄
		ResourceRmi resource=null;
		try {
			resource = (ResourceRmi) RmiEntry.iv("resource");
		} catch (NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		UmsProtectedobject upo = resource.loadResourceById(resourceid);
		if (ProtectedObjectReference._OBJ_INCLUSTION_YES.equals(upo
				.getInclusion())) {
			WebTip.htmlInfo("非表单无法创建权限", true, response);
			return ;
		}
		// 先清除所有已有的字段资源
		resource.dropAllSubResource(resourceid);
		if ("yes".equals(request.getParameter("remove"))) {
			WebTip.htmlInfo("清除权限成功", true, response);
			return ;
		}

		String naturalname = upo.getNaturalname();
		String formcode = upo.getExtendattribute();
		DyFormDesignService dys =null;
		DyFormService dysc =null;
		try {
			dys=(DyFormDesignService) RmiEntry.iv("dydesign");
			dysc= (DyFormService) RmiEntry.iv("dyhandle");
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		// 创建子记录
		List list = dysc.fetchColumnList(formcode);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TCsColumn object = (TCsColumn) iterator.next();
			String columncode = object.getColumncode();
			String columnname = object.getColumname();
			Long aggrigation = object.getIndexvalue();

			UmsProtectedobject upoNew = new UmsProtectedobject();
			upoNew.setNaturalname(columncode);
			upoNew.setName(columnname);
			upoNew.setAggregation(aggrigation);
			upoNew.setInclusion(ProtectedObjectReference._OBJ_INCLUSTION_NO);
			resource.addResource(upoNew, naturalname);
		}
		
		String [][]ope_role={{"add","新增"},{"modi","修改"},{"conf","确认"},{"uconf","反确认"},{"que","查询"}};
		for (int i = 0; i < ope_role.length; i++) {
			UmsProtectedobject upo1 = new UmsProtectedobject();
			upo1.setNaturalname(ope_role[i][0]);
			upo1.setName(ope_role[i][1]);
			upo1.setActionurl("");
			upo1.setObjecttype("DYFROM");
			resource.addResource(upo1, naturalname);
		}

		WebTip.htmlInfo("创建权限成功", true, response);

		return;
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
