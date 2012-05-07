package oe.mid.netone.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;

import com.jl.common.workflow.worklist.DataObj;
import com.jl.common.workflow.worklist.QueryColumn;
import com.jl.common.workflow.worklist.WlEntry;

public class workcountSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public workcountSvl() {
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

		String commiter = request.getParameter("commiter");
		String appname = request.getParameter("appname");
		String mode_ = request.getParameter("mode");
		String listtype = request.getParameter("listtype");

		QueryColumn queryColumn=null;
		boolean mode = false;
		if (StringUtils.isNotEmpty(mode_)) {
			if ("1".equals(mode_)) {
				mode = true;
			}
		}

		try {
			queryColumn = WlEntry.iv().loadQueryColumn(appname,
					0);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		queryColumn.setValue("");
		queryColumn.setOrder(" order by  w1.starttime  desc");
		int count =0;
		try {
			count = WlEntry.iv().count(commiter, appname, mode,
					listtype, queryColumn);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(count);
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
