package com.jl.common.app;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.util.WebTip;

import org.apache.commons.lang.StringUtils;

import com.jl.common.dyform.DyEntry;
import com.jl.common.workflow.DbTools;

public class InitDataSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public InitDataSvl() {
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
		String name=request.getParameter("name");
		String processid=null;
		try {
			processid = AppEntry.iv().loadApp(name).getWorkflowCode_();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("start init:"+name+" processid:"+processid);
		int  count=0;
		if(StringUtils.isNotEmpty(processid)&&processid.startsWith("BUSSWF")){
			String allprocessid="select runtimeid runtimex from netone.t_wf_runtime where processid='"+processid+"'";
			
			List data=DbTools.queryData(allprocessid);
			
			for (Iterator iterator = data.iterator(); iterator.hasNext();) {
				Map object = (Map) iterator.next();
				String runtimeid=(String)object.get("runtimex");
				String delworklist="delete from netone.t_wf_worklist where runtimeid='"+runtimeid+"'";
				String delvar="delete from netone.t_wf_relevantvar where runtimeid='"+runtimeid+"'";
				String delvartmp="delete from netone.t_wf_relevantvar_tmp where runtimeid='"+runtimeid+"'";
				String participant="delete from netone.t_wf_participant where workcode in (select workcode from netone.t_wf_worklist where runtimeid='"+runtimeid+"')";
				
				int partcont=DbTools.execute(participant);
				int delvartmpcont=DbTools.execute(delvartmp);
				int delvarcont=DbTools.execute(delvar);
				int delworklistcont=DbTools.execute(delworklist);
			}
			String runtime="delete from netone.t_wf_runtime where processid='"+processid+"'";
			count=DbTools.execute(runtime);

		}
		WebTip.htmlInfo("init "+count, true, response);
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

}
