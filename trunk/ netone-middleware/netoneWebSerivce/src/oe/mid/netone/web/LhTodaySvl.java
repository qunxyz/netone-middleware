package oe.mid.netone.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.jl.common.workflow.DbTools;

public class LhTodaySvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public LhTodaySvl() {
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

		doPost(request,response);
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

		String userid=request.getParameter("userid");
		String date=request.getParameter("date");

		String sql="select x1.shopname shopname,x1.xoffset xoffset,count(*) picnum,concat('ListPicSvl?id=',x1.lshx) link from (select t1.column5 shopname,t2.column4 xoffset ,t2.lsh lshx"+
		" from dyform.DY_961338295639576  t1,dyform.DY_211340244752515  t2 "+
		"where t1.lsh=t2.fatherlsh and t2.participant='"+userid+"' and t2.created like '"+date+"%')  as x1 left join iss.t_file t3  on x1.lshx=t3.d_unid  group by x1.lshx";
		System.out.println(sql);
		List list=DbTools.queryData(sql);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(JSONArray.fromObject(list).toString());
	}

}
