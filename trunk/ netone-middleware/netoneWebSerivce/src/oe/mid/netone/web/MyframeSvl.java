package oe.mid.netone.web;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.security3a.seucore.obj.db.UmsProtectedobject;
import net.sf.json.JSONArray;

import com.jl.common.netone.UmsProtecte;
import com.jl.common.workflow.DbTools;

public class MyframeSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public MyframeSvl() {
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
		String username = request.getParameter("commiter");
		String model= request.getParameter("model");
		if(model==null){
		 model="0";
		}
		String sqlStr=null;
		String json=null;
		//我的代办列表
		if(model.equals("0")){
			sqlStr="select w3.lsh lsh,w3.appname naturalname,w2.workcode " +
				"workcode,w3.d0 actname,w1.starttime starttime,w2.commitername " +
				"commitername,w2.commitercode  commiter from netone.t_wf_worklist " +
				"w1 left join netone.t_wf_participant w2 on  w1.workcode=w2.WORKCODE " +
				"left join netone.t_wf_relevantvar_tmp w3 on w3.runtimeid=w1.runtimeid" +
				" where w1.EXECUTESTATUS='01' and w2.usercode='"+username+"' and w2.statusnow='01'" +
				"  and w2.types in('01','02') order by w1.starttime desc";
		}
		//当前代办工单数
		if(model.equals("1")){
			sqlStr= "select  count(*) as countnum from netone.t_wf_worklist w1 left join netone.t_wf_participant " +
					"w2 on w1.workcode=w2.WORKCODE where w1.EXECUTESTATUS='01' and w2.usercode='"+username+"' and w2.statusnow='01' " +
					"and w2.types in ('01','02')";
		}
		//当前代办工单数
		if(model.equals("1")){
			sqlStr= "select  count(*) as countnum from netone.t_wf_worklist w1 left join netone.t_wf_participant " +
					"w2 on w1.workcode=w2.WORKCODE where w1.EXECUTESTATUS='01' and w2.usercode='"+username+"' and w2.statusnow='01' " +
					"and w2.types in ('01','02')";
		}
		//已办工单数
		if(model.equals("2")){
			sqlStr="select  count(*) as countnum3 from netone.t_wf_worklist w1 left join netone.t_wf_participant w2 on " +
					"w1.workcode=w2.WORKCODE where w2.usercode='"+username+"' and w2.statusnow='02' and w2.types in ('01','02')";
			
		}
		  List list = DbTools.queryData(sqlStr);
		  json = JSONArray.fromObject(list).toString();
		  response.setContentType("text/html;charset=utf-8");
		  response.getWriter().print(json);
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
