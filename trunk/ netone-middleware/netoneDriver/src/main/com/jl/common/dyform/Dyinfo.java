package com.jl.common.dyform;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.processors.JsonBeanProcessor;

import org.apache.derby.tools.sysinfo;

import com.jl.common.JSONUtil2;
import com.jl.common.workflow.WfEntry;
import com.sun.org.apache.commons.beanutils.BeanUtils;
import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

import oe.cav.bean.logic.bus.FormEntry;
import oe.cav.bean.logic.bus.TCsBus;
import oe.cav.bean.logic.column.TCsColumn;
import oe.midware.dyform.service.DyFormDesignService;
import oe.rmi.client.RmiEntry;

public class Dyinfo extends HttpServlet {

	/**
	 * json 数据的包装  xuwei(2012-3-28)
	 * 
	 */
	public Dyinfo() {
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
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		Enumeration list = request.getParameterNames();
		DyFormData dydata = new DyFormData();
		String ext = request.getParameter("ext");
		String model = request.getParameter("model");
		while (list.hasMoreElements()) {
			Object key = list.nextElement();
			try {
				String value = request.getParameter((String) key);
				BeanUtils.setProperty(dydata, (String) key, value);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		List listx = null;
		try {
			dydata.setParticipant(null);//表示要查询所有数据，不要鉴权
			int index = DyEntry.iv().queryDataNum(dydata, "");
			listx = DyEntry.iv().queryData(dydata, 0, index, ext);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringBuffer jsonBuffer = new StringBuffer();
		String split = "";
		JSONArray jsonArray = new JSONArray();
		for (Iterator iterator = listx.iterator(); iterator.hasNext();) {
			DyFormData name = (DyFormData) iterator.next();
			String jsonStr = JSONUtil2.fromBean(name).toString();
			jsonBuffer.append(split);
			jsonBuffer.append(jsonStr);
			split = ",";
			jsonArray.add(jsonStr);
		}

		response.setContentType("text/json;charset=UTF-8");
		if (model.equals("1")) {
			response.getWriter().print(jsonArray.getString(0));
		} else {
			response.getWriter().print("["+jsonBuffer+"]");
		}

	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
