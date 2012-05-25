package oe.mid.netone.web;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.jl.common.workflow.DbTools;

public class Allimage extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public Allimage() {
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
         String appname=request.getParameter("appname");
         
         String sqlStr="SELECT 	ID FROM  netone.ums_protectedobject  WHERE NATURALNAME LIKE '%"+appname+"%'";
		 List list= DbTools.queryData(sqlStr);
		 List listx=new ArrayList();
		 for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map  map = (Map) iterator.next();
			String sqladdress="SELECT  address  FROM   iss.t_file  WHERE d_unid='"+map.get("ID")+"'";
			List list1= DbTools.queryData(sqladdress);
			for (Iterator iterator2 = list1.iterator(); iterator2.hasNext();) {
				Map map1 = (Map) iterator2.next();
				String address=(map1.get("address")).toString()
				.replaceAll("\\\\", "\\/");
				map1.remove("address");
				map1.put("address", address);
				listx.add(map1);
			}
		}
		 response.setContentType("text/html;charset=utf-8");
		 response.getWriter().print(JSONArray.fromObject(listx).toString());
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
