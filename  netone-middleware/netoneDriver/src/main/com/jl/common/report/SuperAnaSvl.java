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

import com.jl.common.workflow.DbTools;

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
		
		String sql1=" select "+table1_col+" from dyform."+table1+" where " +table1_con;
		String sql2=" select "+table2_col+" from dyform."+table2+" where " +table2_con;
		System.out.println("sql1:" + sql1);
		System.out.println("sql2:" + sql2);
		List list1=DbTools.queryData(sql1);
		List list2=DbTools.queryData(sql2);
		if("rep".equals(disp)){
			StringBuffer but1=new StringBuffer();
			but1.append("<table border=\"1\">" +
					"		<tr>" +
					"			<td>基站名称</td><td>符合条件节点数量</td>" +
					"		</tr>");
			for (Iterator iterator = list1.iterator(); iterator.hasNext();) {
				Map object = (Map) iterator.next();
				double gpx1=((java.math.BigDecimal)object.get("gpx1")).doubleValue();
				double gpx2=((java.math.BigDecimal)object.get("gpx2")).doubleValue();
				double gpx3=((java.math.BigDecimal)object.get("gpx3")).doubleValue();
				double gpy1=((java.math.BigDecimal)object.get("gpy1")).doubleValue();
				double gpy2=((java.math.BigDecimal)object.get("gpy2")).doubleValue();
				double gpy3=((java.math.BigDecimal)object.get("gpy3")).doubleValue();
				String name=(String)object.get("name");
				int flag = 0;
				for (Iterator iterator2 = list2.iterator(); iterator2.hasNext();) {
					Map object2 = (Map) iterator2.next();
					double poffx=((java.math.BigDecimal)object2.get("poffx")).doubleValue();
					double poffy=((java.math.BigDecimal)object2.get("poffy")).doubleValue();
					if("1".equals(mode)){
						if(inTriangle(poffx, poffy, gpx1, gpx2, gpx3, gpy1, gpy2, gpy3))
							flag++;
					} else if ("2".equals(mode)){
						if(!inTriangle(poffx, poffy, gpx1, gpx2, gpx3, gpy1, gpy2, gpy3))
							flag++;
					} else {
						flag++;
					}
				}
				but1.append("<tr>" +
						"		<td>"+ name +"</td>" +
						"		<td>"+ flag +"</td>" +
						"	</tr>");
			}
			but1.append("</table>");
			response.setContentType("text/html");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out
					.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
			out.println("<HTML>");
			out.println("  <HEAD><TITLE>分析结果</TITLE></HEAD>");
			out.println("  <BODY>");
			out.print(but1.toString());
			out.println("  </BODY>");
			out.println("</HTML>");
			out.flush();
			out.close();
		}
		
		if("gis".equals(disp)){
			StringBuffer but1=new StringBuffer();
			StringBuffer but2=new StringBuffer();
			for (Iterator iterator = list1.iterator(); iterator.hasNext();) {
				Map object = (Map) iterator.next();
				Double gpx1=((java.math.BigDecimal)object.get("gpx1")).doubleValue();
				Double gpx2=((java.math.BigDecimal)object.get("gpx2")).doubleValue();
				Double gpx3=((java.math.BigDecimal)object.get("gpx3")).doubleValue();
				Double gpy1=((java.math.BigDecimal)object.get("gpy1")).doubleValue();
				Double gpy2=((java.math.BigDecimal)object.get("gpy2")).doubleValue();
				Double gpy3=((java.math.BigDecimal)object.get("gpy3")).doubleValue();
				String name=(String)object.get("name");
				//基站描图 
				but1.append(gpx1.intValue()+","+gpy1.intValue()+","+gpx2.intValue()+","+gpy2.intValue()+","+gpx3.intValue()+","+gpy3.intValue()+"\n");
				but2.append(gpx1.intValue()+","+gpy1.intValue()+","+name+"\n");
				for (Iterator iterator2 = list2.iterator(); iterator2.hasNext();) {
					Map object2 = (Map) iterator2.next();
					Double poffx=((java.math.BigDecimal)object2.get("poffx")).doubleValue();
					Double poffy=((java.math.BigDecimal)object2.get("poffy")).doubleValue();
					if("1".equals(mode)){
						if(inTriangle(poffx, poffy, gpx1, gpx2, gpx3, gpy1, gpy2, gpy3))
							but2.append(poffx.intValue()+","+poffy.intValue()+",,"+"http://42.120.52.168:82/mapapp/images/mark.gif,\n");
					} else if ("2".equals(mode)){
						if(!inTriangle(poffx, poffy, gpx1, gpx2, gpx3, gpy1, gpy2, gpy3))
							but2.append(poffx.intValue()+","+poffy.intValue()+",,"+"http://42.120.52.168:82/mapapp/images/mark.gif,\n");
					} else {
						but2.append(poffx.intValue()+","+poffy.intValue()+",,"+"http://42.120.52.168:82/mapapp/images/mark.gif,\n");
					}
				}
			}
			System.out.println("基站：\n" + but1.toString());
			System.out.println("节点：\n" + but2.toString());
			request.setAttribute("coords", but1.toString());
			request.setAttribute("mappoint", but2.toString());
			request.setAttribute("picurl", "http://pic1.sc.chinaz.com/files/pic/pic9/201210/xpic8159.jpg");
			request.getRequestDispatcher("/map.jsp").forward(request, response);
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

		doGet(request, response);
	}
	
	public double triangleArea(double gpx1, double gpx2, double gpx3, double gpy1, double gpy2, double gpy3) {
		double result = Math.abs((gpx1 * gpy2 + gpx2 * gpy3 + gpx3 * gpy1
	            - gpx2 * gpy1 - gpx3 * gpy2 - gpx1 * gpy3) / 2.0D);
	    return result;
	}
	
	public boolean inTriangle(double poffx, double poffy, double gpx1, double gpx2, double gpx3, double gpy1, double gpy2, double gpy3) {
	    double triangleArea = triangleArea(gpx1, gpx2, gpx3, gpy1, gpy2, gpy3);
	    double area = triangleArea(poffx, gpx1, gpx2, poffy, gpy1, gpy2);
	    area += triangleArea(poffx, gpx1, gpx3, poffy, gpy1, gpy3);
	    area += triangleArea(poffx, gpx2, gpx3, poffy, gpy2, gpy3);
	    double epsilon = 0.0001;
	    if (Math.abs(triangleArea - area) < epsilon) {
	        return true;
	    }
	    return false;
	}
}
