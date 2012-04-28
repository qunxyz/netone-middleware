package oe.mid.netone.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.jl.common.workflow.DbTools;

public class workflowSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public workflowSvl() {
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
		request.setCharacterEncoding("utf-8");
		String model=request.getParameter("model");
		if(model==null){
			model="0";
		}
		String sqlStr=null;
		 if(model.equals("0")){
			 String naturalname=request.getParameter("naturalname");
			 sqlStr="SELECT NAME,id FROM  netone.ums_protectedobject WHERE parentdir = (SELECT id " +
			 		"FROM netone.ums_protectedobject WHERE naturalname = '"+naturalname+"')";
		 }
		 if(model.equals("1")){
			 String parentdir =request.getParameter("parentdir");
			 sqlStr="select naturalname,name,id,actionurl from netone.ums_protectedobject where parentdir='"+parentdir+"'  LIMIT 20";
		 }
		List list=DbTools.queryData(sqlStr);
		String jsonString=JSONArray.fromObject(list).toString();
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(jsonString);
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
		doGet(request,response);
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
