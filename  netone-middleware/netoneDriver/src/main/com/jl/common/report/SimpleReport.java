package com.jl.common.report;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.cav.bean.logic.column.TCsColumn;

import org.apache.commons.beanutils.BeanUtils;

import com.jl.common.dyform.DyEntry;
import com.jl.common.workflow.DbTools;

/**
* 简单的针对表单数据的展示和导出
*/
public class SimpleReport extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public SimpleReport() {
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
		String formcode=request.getParameter("formcode");
		
		StringBuffer but=new StringBuffer("<table border='1'><tr bgcolor='#999999'>");
		List list=null;
		try {
			list = DyEntry.iv().fetchColumnList(formcode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TCsColumn object = (TCsColumn) iterator.next();
			but.append("<td>"+object.getColumname()+"</td>");
		}
		but.append("</tr>");

		String sql="";
		try {
			sql = "select * from dyform."+DyEntry.iv().loadForm(formcode).getTablename();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List listx=DbTools.queryData(sql);
		for (Iterator iterator = listx.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			but.append("<tr>");
			for (Iterator iteratorx = list.iterator(); iteratorx.hasNext();) {
				TCsColumn objectx = (TCsColumn) iteratorx.next();
				Object data=null;
				try {
					data = BeanUtils.getProperty(object, objectx.getColumncode());
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				but.append("<td>"+data+"</td>");
			}
			but.append("</tr>");
			
		}
		String excel=request.getParameter("excel");
		response.setContentType("text/html;charset=GBK");
		if("1".equals(excel)){
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition","attachment; filename="+formcode+".xls");
		}

		PrintWriter out = response.getWriter();
		out.print(but);
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
