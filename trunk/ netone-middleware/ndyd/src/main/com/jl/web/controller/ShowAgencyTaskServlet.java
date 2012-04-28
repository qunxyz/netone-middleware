package com.jl.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import com.jl.common.workflow.worklist.DataObj;
import com.jl.common.workflow.worklist.WlEntry;

public class ShowAgencyTaskServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ShowAgencyTaskServlet() {
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
		
		String processName = request.getParameter("processname");
		
		List<DataObj> dataList = null;
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		try {
			
			//字段名称列表
			List fieldList = WlEntry.iv().listQueryColumn("人员增补审批");
			//代办数据
			dataList = WlEntry.iv().worklist("adminx",
					"人员增补审批", false, 0, 500, "01", null);
			
//			for (int i = 0; i < dataList.size(); i++) {
//				DataObj dataObj =  dataList.get(i);
//				String[] datas = dataObj.getData();
//				out.println(datas);
//				
//			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
//			out.println("dataList:  "+dataList);

			JSONArray agencyList= new JSONArray();

			for (int i = 0; i < 15; i++) {
				Map map = new HashMap();
				map.put("流程节点", "申请"+i);
				map.put("申请人", "王某某"+i);
				map.put("创建时间", "2011-10-08 10:"+i+":17");
				agencyList.put(map);
			}

		
		out.println(agencyList);
		out.flush();
		out.close();
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

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
