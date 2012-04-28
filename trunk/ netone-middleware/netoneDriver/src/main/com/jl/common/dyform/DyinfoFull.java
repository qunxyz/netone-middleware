package com.jl.common.dyform;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONBuilder;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.Var;
import org.apache.derby.database.UserUtility;
import org.apache.derby.tools.sysinfo;

import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.bean.logic.form.TCsForm;
import oe.midware.dyform.service.DyFormDesignService;
import oe.midware.dyform.service.DyFormService;
import oe.rmi.client.RmiEntry;

import com.jl.common.JSONUtil2;
import com.sun.org.apache.commons.beanutils.BeanUtils;

public class DyinfoFull extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public DyinfoFull() {
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
		doPost(request, response);

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
		request.setCharacterEncoding("utf-8");
		Enumeration list = request.getParameterNames();

		String formcode = request.getParameter("formcode");
		String ext = request.getParameter("ext");
		String model = request.getParameter("model");
		DyFormData dydata = new DyFormData();
		TCsForm csForm = new TCsForm();

		while (list.hasMoreElements()) {
			try {
				Object key = list.nextElement();
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
		String[] subformlsit = null;
		DyFormService dysc = null;
		try {
			dysc = (DyFormService) RmiEntry.iv("dyhandle");
			csForm = dysc.loadForm(formcode); // 获取ID
			String subform = (csForm.getSubform());
			if(subform!="" || subform!=null){
			subformlsit = subform.split(",");
			}
			int index = DyEntry.iv().queryDataNum(dydata, "");
			listx = DyEntry.iv().queryData(dydata, 0, index, ext);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONArray jsonArray = new JSONArray();
		
		StringBuffer jsonBuffer = new StringBuffer();
		String split = "";
		for (Iterator iterator = listx.iterator(); iterator.hasNext();) {
			DyFormData name = (DyFormData) iterator.next();
			JSONObject jsonStr = (JSONObject) JSONUtil2.fromBean(name);
			for (int i = 0; i < subformlsit.length; i++) {
				String sumformcode=StringUtils.substringBetween(subformlsit[i],
						"[", "]");
				String sunString = subformmethod(sumformcode, name.getLsh());
				jsonStr.element(sumformcode, sunString);
			}
			jsonBuffer.append(split);
			jsonBuffer.append(jsonStr);
			split = ",";
			jsonArray.add(jsonStr);
		}
		response.setContentType("text/html;charset=utf-8");
		if (model.equals("1")) {
			response.getWriter().print(jsonArray.getString(0));
		} else {
			response.getWriter().print(jsonBuffer);
		}
	}
	// 子表单数据
	public String subformmethod(String focd, String lsh) {
		List listx = null;
		DyFormData dydata = new DyFormData();
		dydata.setFormcode(focd);
		dydata.setFatherlsh(lsh);
		try {
			int index = DyEntry.iv().queryDataNum(dydata, "");
			listx = DyEntry.iv().queryData(dydata, 0, index, "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringBuffer jsonBuffer = new StringBuffer();
		String split = "";
		String jsonStr = JSONUtil2.fromBean((DyFormData)listx.get(0)).toString();
		jsonBuffer.append(split);
		jsonBuffer.append(jsonStr);
		return jsonBuffer.toString();
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
