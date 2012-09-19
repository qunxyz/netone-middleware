package oe.mid.netone.dyfrom;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jl.common.workflow.DbTools;
import com.sun.xml.internal.bind.v2.runtime.output.Encoded;

import net.sf.json.JSONArray;
import net.sf.json.util.NewBeanInstanceStrategy;
/**
 * 表单视图服务类，用于展示表单使用
 * @author robanco
 *
 */
public class DyViewSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public DyViewSvl() {
		super();
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
		doPost(request, response);
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
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		String tablename=request.getParameter("tablename");
		String columns=request.getParameter("columns");// 传入的字段可能是逗号隔开
		String condition = request.getParameter("condition");
		if("".equals(condition) || condition == null){
			condition = " 1 = 1 ";
		} else {
			condition = new String(request.getParameter("condition").getBytes("ISO-8859-1"),"utf-8");
		}
		String sql = "select " + columns +" from " + tablename + " where " + condition;
		List list = DbTools.queryData(sql);
		String jsonString=JSONArray.fromObject(list).toString();
   		response.getWriter().print(jsonString);
	}
}
