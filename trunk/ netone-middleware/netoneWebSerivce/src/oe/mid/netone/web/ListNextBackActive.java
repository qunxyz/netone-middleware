package oe.mid.netone.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.jl.common.workflow.TWfActivePass;
import com.jl.common.workflow.WfEntry;

public class ListNextBackActive extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ListNextBackActive() {
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
		doPost(request,response);
	}

	/**
	 * 获得流程中所有完成的节点
	 * 
	 * @param runtimeid
	 * @return     流程实例id
	 * @throws Exception
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		  request.setCharacterEncoding("utf-8");
		  String runtimeid=request.getParameter("runtimeid");
		  List<TWfActivePass> list=null;
		  try {
			  list=WfEntry.iv().listNextBackActive(runtimeid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List list2=new ArrayList();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TWfActivePass wfActivePass = (TWfActivePass) iterator.next();
			Map map=new HashMap();
			map.put("id", wfActivePass.getId());
			map.put("name", wfActivePass.getName());
			list2.add(map);
		}
		String jsonstr = JSONArray.fromObject(list2).toString();
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(jsonstr);
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
