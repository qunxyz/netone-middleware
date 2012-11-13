package com.jl.common.report;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.db.DbTools;

import org.apache.commons.lang.StringUtils;

public class SuperAnaSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public SuperAnaSvl() {
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

		String mode=request.getParameter("mode");//mode=1 是include,mode=2 unclude,mode=0 all;
		String disp=request.getParameter("disp");// disp=gis地图展示 ,disp=rep 报表展示
		
		String form1=request.getParameter("form1");
		String form2=request.getParameter("form2");
		
		String table1=StringUtils.substringAfterLast(form1, ".");
		String table2=StringUtils.substringAfterLast(form2, ".");
		
		String table1_con=request.getParameter("con1");
		if(StringUtils.isEmpty(table1_con)){
			table1_con="1=1";
		}
		String table1_col=request.getParameter("col1");
		if(StringUtils.isEmpty(table1_col)){
			table1_col="*";
		}		
		String table2_con=request.getParameter("con2");
		if(StringUtils.isEmpty(table2_con)){
			table2_con="1=1";
		}
		String table2_col=request.getParameter("col2");
		if(StringUtils.isEmpty(table2_col)){
			table2_col="*";
		}
		
		String sql1=" select "+table1_col+" dyform."+table1+" where " +table1_con;
		String sql2=" select "+table2_col+" dyform."+table2+" where " +table2_con;
		List list1=DbTools.queryData(sql1);
		List list2=DbTools.queryData(sql2);
		
		if("rep".equals(disp)){
			StringBuffer but1=new StringBuffer();
			StringBuffer but2=new StringBuffer();
			for (Iterator iterator = list1.iterator(); iterator.hasNext();) {
				Map object = (Map) iterator.next();
				double gpx1=(Double)object.get("gpx1");
				double gpx2=(Double)object.get("gpx2");
				double gpx3=(Double)object.get("gpx3");
				double gpy1=(Double)object.get("gpy1");
				double gpy2=(Double)object.get("gpy2");
				double gpy3=(Double)object.get("gpy3");
				String name=(String)object.get("name");
				but1.append(gpx1+","+gpy1+","+gpx2+","+gpy2+","+gpx3+","+gpy3+";");
				but2.append(gpx1+","+gpy1+","+name+";");
				for (Iterator iterator2 = list2.iterator(); iterator2.hasNext();) {
					Map object2 = (Map) iterator2.next();
					double poffx=(Double)object.get("poffx");
					double poffy=(Double)object.get("poffy");
					if("1".equals(mode)){
					
					}
				}
			}
		}
		
		if("gis".equals(disp)){
			StringBuffer but1=new StringBuffer();
			StringBuffer but2=new StringBuffer();
			for (Iterator iterator = list1.iterator(); iterator.hasNext();) {
				Map object = (Map) iterator.next();
				double gpx1=(Double)object.get("gpx1");
				double gpx2=(Double)object.get("gpx2");
				double gpx3=(Double)object.get("gpx3");
				double gpy1=(Double)object.get("gpy1");
				double gpy2=(Double)object.get("gpy2");
				double gpy3=(Double)object.get("gpy3");
				String name=(String)object.get("name");
				but1.append(gpx1+","+gpy1+","+gpx2+","+gpy2+","+gpx3+","+gpy3+";");
				but2.append(gpx1+","+gpy1+","+name+";");
				for (Iterator iterator2 = list2.iterator(); iterator2.hasNext();) {
					Map object2 = (Map) iterator2.next();
					double poffx=(Double)object.get("poffx");
					double poffy=(Double)object.get("poffy");
					if("1".equals(mode)){
						
					}
				}
			}
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

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

}
