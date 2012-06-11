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
 * @version 1.0.0 2012-1-29 ����02:55:12
 * @history
 */
public class SampleChartAction extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1.ʵ��ʵ����
		ChartService service = (ChartService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(
						request.getSession().getServletContext()).getBean(
						"flashChartService");

		// 2.ʵ��ͼ�����ݶ���
		ChartDataObj data = new ChartDataObj();
		data.setXAxisLabel("X��label");//
		data.setYAxisLabel("Y��label");//

		String endpoint = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + request.getContextPath()
				+ "/";
		if (request.getScheme().equalsIgnoreCase("https")) {// https
			endpoint += "messagebroker/amfsecure";
		} else {// http
			endpoint += "messagebroker/amf";
		}
		data.setEndpoint(endpoint);
		data.setTitle("����");//
		data.setParams("{\"name1\":\""+"1111111111111"+"\",\"name2\":\""+"fdfdfdf"+"\"}");
		// 2.ָ����������ʽ ��������鿴com.report.flashchart.entity.ChartCompVar.java
		String json = service.entry(ChartCompVar.COMP_PIE_LINK_CHART, data);

		// 3.�������
		service.output(request, json, data.getTitle());

		// 4.ָ�����ҳ�� ҳ���ַ���ܸ��ı�����/flashchart/entry.jsp ���ʹ��MVC��ܣ�������ؿ����д
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/flashchart/entry.jsp");
		dispatcher.forward(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

}
