/**
 * 
 */
package com.test;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.JFreeChart;

import com.report.chart.common.ChartCommon;

/**
 * @author Don(cai.you.dun)
 * @version 1.0.0 2012-1-9 ÉÏÎç11:31:19
 * @history
 */
public class ChartTestSvl extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String format = request.getParameter("format");
		OutputStream out = response.getOutputStream();
		JFreeChart chart = null;
		if ("line".equals(format)) {
			chart = LineTest.todo();
		} else if ("line3D".equals(format)) {
			chart = Line3DTest.todo();
		} else if ("bar".equals(format)) {
			chart = BarTest.todo();
		} else if ("bar3D".equals(format)) {
			chart = Bar3DTest.todo();
		} else if ("pie".equals(format)) {
			chart = PieTest.todo();
		} else if ("pie3D".equals(format)) {
			chart = Pie3DTest.todo();
		}
		ChartCommon.writeChartAsPNG(out, chart, 800, 600);
		out.flush();
		out.close();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

}
