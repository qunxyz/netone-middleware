package oe.mid.netone.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.jl.common.app.AppEntry;
import com.jl.common.workflow.TWfActive;

public class LoadCfgActive extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public LoadCfgActive() {
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
	 * 装载应用配置的活动信息
	 * 
	 * @param naturalname
	 * 
	 * @param actid
	 * 
	 * @param commiter
	 *
	 * @param runtimeid
	 *  流程实例id
	 * @return participant ,人员 name[code],name[code]
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 request.setCharacterEncoding("utf-8");
		 String naturalname=request.getParameter("naturalname");
		 String actid=request.getParameter("actid");
		 String commiter=request.getParameter("commiter");
		 String runtimeid=request.getParameter("runtimeid");
		 TWfActive twf=null;
		 try {
			 twf=AppEntry.iv().loadCfgActive(naturalname, actid, commiter, runtimeid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String participant=twf.getParticipant();
		String []participantx=StringUtils.split(participant,",");
		List list=new ArrayList();
		for (int i = 0; i < participantx.length; i++) {
			Map map=new HashMap();
			map.put("participant", participantx[i]);
			list.add(map);
		}

		
		String jsonstr = JSONArray.fromObject(list).toString();
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
