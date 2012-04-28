// Decompiled by DJ v3.7.7.81 Copyright 2004 Atanas Neshkov  Date: 2012/3/2 21:05:53
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   DataTransSvl.java

package com.jl.common.report;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.NotBoundException;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.apache.commons.lang.StringUtils;

import oe.frame.web.util.WebTip;
import oe.mid.datatools.DataToolIfc;
import oe.mid.datatools.DataToolImplamle;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

public class DataTransSvl extends HttpServlet {

	public DataTransSvl() {
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DataToolIfc dataToolIfc = new DataToolImplamle();
		String name = request.getParameter("name");

		try {
			ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");
			String xmlinfo = rmi.loadResourceByNatural(name)
					.getExtendattribute();
			oe.mid.datatools.obj.DataTrans dataTrans1 = dataToolIfc
					.parser(xmlinfo);
			String extSql = request.getParameter("sqlext");
			if (StringUtils.isNotEmpty(extSql)) {
				String sqlx = dataTrans1.getSql().get("sqlid").getSql();
				dataTrans1.getSql().get("sqlid").setSql(sqlx + extSql);
			}

			dataToolIfc.todo(dataTrans1, false);
			WebTip.htmlInfo("同步成功", true, response);
		} catch (NotBoundException e) {
			WebTip.htmlInfo(e.getMessage(), true, response);
			e.printStackTrace();
		}
	}

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
		out.print(getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}
}