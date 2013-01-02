package oe.mid.netone.msg;

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

import org.apache.commons.lang.StringUtils;

import com.jl.common.workflow.DbTools;

import net.sf.json.JSONArray;

public class MyGroupSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public MyGroupSvl() {
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
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		
		
		String userid=request.getParameter("userid");
		
		String sql="select column3,column4,lsh from dyform.DY_251356887574360 where participant='"+userid+"'";
		List list=DbTools.queryData(sql);
		
		List listall=new ArrayList();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			String groupid=(String)object.get("lsh");
			String groupname=(String)object.get("column3");
			String users=(String)object.get("column4");
			
			
			Map data=new HashMap();
			data.put("groupid", groupid);
			data.put("groupname", groupname);
			
			String []strx=StringUtils.split(users,",");
			if(strx!=null){
				List userpre=new ArrayList();
				for (int i = 0; i < strx.length; i++) {
					Map map=new HashMap();
					map.put("id", StringUtils.substringBefore(strx[i], "["));
					map.put("name", StringUtils.substringBetween(strx[i], "[","]"));
					userpre.add(map);
				}
				data.put("people", userpre);
			}
			listall.add(data);
		}
		String jsonString=JSONArray.fromObject(listall).toString();
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

		this.doGet(request, response);
	}

}
