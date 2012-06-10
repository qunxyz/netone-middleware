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

import com.jl.common.workflow.TWfActive;
import com.jl.common.workflow.WfEntry;

public class ListNextRouteActive extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ListNextRouteActive() {
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
	 * 获得流程设计中默认的下一步节点 注意：流程图中设计的下一步节点不涉及路由选择，系统会忽略路径上 的路由选择条件。
	 * 
	 * @param processid
	 *            流程ID
	 * @param activeid
	 *            活动ID
	 * @param runtimeid
	 *            流程实例id
	 * @param commiter
	 *            流程提交者
	 * @return
	 */
 
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		  request.setCharacterEncoding("utf-8");
		  String processid=request.getParameter("processid");
		  String activeid=request.getParameter("activeid");
		  String runtimeid=request.getParameter("runtimeid");
		  String commiter=request.getParameter("commiter");
		  List<TWfActive> list=null;
		  try {
			  list=WfEntry.iv().listNextRouteActive(processid, activeid, runtimeid, commiter);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List list2=new ArrayList();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TWfActive wfActive = (TWfActive) iterator.next();
			Map map=new HashMap();
			map.put("id", wfActive.getId());
			map.put("name", wfActive.getName());
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
