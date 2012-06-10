package oe.mid.netone.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.jl.common.dyform.DyFormBuildHtml;
import com.jl.common.dyform.DyFormComp;
import com.jl.common.netone.RMI_SER;
import com.jl.common.netone.RMI_SER_obj;
import com.jl.common.netone.UmsProtecte;
import com.jl.common.workflow.TWfWorklistExt;
import com.jl.common.workflow.WfEntry;
import com.jl.common.workflow.worklist.DataObj;
import com.jl.common.workflow.worklist.QueryColumn;
import com.jl.common.workflow.worklist.WlEntry;
import com.jl.common.workflow.worklist.WorklistVIewImpl;
import com.jl.entity.User;

public class worklistSvl extends HttpServlet {

	/**
	 * xuwei(2012-5-4) 数据列表 获得待办 listtype={01 代办、02以办未归档、03 已办且归档、04全部工单} Mode=1
	 * 代办 mode=0 待阅
	 */
	public worklistSvl() {
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
		String commiter = request.getParameter("commiter");
		String appname = request.getParameter("appname");
		String mode_ = request.getParameter("mode");
		String listtype = request.getParameter("listtype");

		boolean mode = false;
		List<DataObj> list = null;
		List list1 = new ArrayList();
		QueryColumn queryColumn = null;
		RMI_SER rmi = new RMI_SER();
		RMI_SER_obj rmiobj = rmi.RMI_SER();
		if (StringUtils.isNotEmpty(mode_)) {
			if ("1".equals(mode_)) {
				mode = true;
			}
		}
		try {
			queryColumn = WlEntry.iv().loadQueryColumn(appname, 0);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		queryColumn.setValue("");
		queryColumn.setOrder(" order by  w1.starttime  desc");

		try {
			list = WlEntry.iv().worklist(commiter, appname, mode, 0, 100,
					listtype, queryColumn);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			DataObj dataobj = (DataObj) iterator.next();
			Map map = new HashMap();
			map.put("name", dataobj.getData()[0]);
			map
					.put("workname",
							dataobj.getData()[dataobj.getData().length - 3]);
			map.put("commitername",
					dataobj.getData()[dataobj.getData().length - 2]);
			map.put("starttime",
					dataobj.getData()[dataobj.getData().length - 1]);
			JSONObject json = JSONObject.fromObject(dataobj.getExt());
			map.put("lsh", json.get("bussid"));
			map.put("url", rmiobj.getWEBSER_APPFRAME() +dataobj.getUrl());

			String str = StringUtils.substringBetween(dataobj.getUrl(),
					"&naturalname=", "&lsh=");
			UmsProtecte up = new UmsProtecte();
			UmsProtectedobject upobj = up.loadUmsProtecteNaturalname(str + "."
					+ dataobj.getId()[0]);
			map.put("promptname", upobj.getName());
			list1.add(map);
		}

		String jsonstr = JSONArray.fromObject(list1).toString();
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(jsonstr);
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
		doGet(request, response);
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
