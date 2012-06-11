/**
 * 
 */
package com.report.flashchart.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.report.flashchart.entity.ChartCompVar;
import com.report.flashchart.entity.ChartDataObj;
import com.report.flashchart.ifc.ChartService;

/**
 * @author Don(cai.you.dun)
 * @version 1.0.0 2012-1-29 下午02:55:12
 * @history
 */
public class SampleChartAction extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1.实例实现类
		ChartService service = (ChartService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(
						request.getSession().getServletContext()).getBean(
						"flashChartService");

		// 2.实现图表数据对象
		ChartDataObj data = new ChartDataObj();
		data.setXAxisLabel("X轴label");//
		data.setYAxisLabel("Y轴label");//

		String endpoint = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + request.getContextPath()
				+ "/";
		if (request.getScheme().equalsIgnoreCase("https")) {// https
			endpoint += "messagebroker/amfsecure";
		} else {// http
			endpoint += "messagebroker/amf";
		}
		data.setEndpoint(endpoint);
		data.setTitle("标题");//
		data.setParams("{\"name1\":\""+"1111111111111"+"\",\"name2\":\""+"fdfdfdf"+"\"}");
		// 2.指定输出报表格式 具体变量查看com.report.flashchart.entity.ChartCompVar.java
		String json = service.entry(ChartCompVar.COMP_PIE_LINK_CHART, data);

		// 3.输出参数
		service.output(request, json, data.getTitle());

		// 4.指定输出页面 页面地址不能更改必须是/flashchart/entry.jsp 如果使用MVC框架，根据相关框架重写
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/flashchart/entry.jsp");
		dispatcher.forward(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

}
