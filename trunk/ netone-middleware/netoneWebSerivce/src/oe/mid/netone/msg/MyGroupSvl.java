package oe.mid.netone.msg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		
		
		List list=new ArrayList();
		Map  map1=new HashMap();
		map1.put("groupid", "01");
		map1.put("groupname", "群组1");
		List listmen=new ArrayList();
		listmen.add("adminx[管理员]");
		listmen.add("cuicui[翠翠]");
		listmen.add("liming[黎民]");
		map1.put("people", listmen);
		list.add(map1);
		
		Map  map2=new HashMap();
		map2.put("groupid", "02");
		map2.put("groupname", "群组2");
		List listmen2=new ArrayList();
		listmen2.add("adminx[管理员2]");
		listmen2.add("cuicui[翠翠2]");
		listmen2.add("liming[黎民2]");
		map2.put("people", listmen2);
		list.add(map2);
		
		Map  map3=new HashMap();
		map3.put("groupid", "03");
		map3.put("groupname", "群组3");
		map3.put("people", "adminx[管理员2],cuicui[翠翠2],liming[黎民2]");
		list.add(map3);
		
		String jsonString=JSONArray.fromObject(list).toString();
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
