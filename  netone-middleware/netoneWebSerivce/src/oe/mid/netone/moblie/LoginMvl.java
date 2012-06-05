package oe.mid.netone.moblie;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.Clerk;
import oe.security4a.severlet.MD5Util;

import org.apache.commons.lang.StringUtils;

public class LoginMvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public LoginMvl() {
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
         response.setContentType("text/html;charset=utf-8");
         String loginname = request.getParameter("name");
 		String passwordto = request.getParameter("password");
 		Map map=new HashMap();
 		List list=new ArrayList();
 		ResourceRmi resourceRmi;
 		try {
 			resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
 			CupmRmi cupmRmi = (CupmRmi) RmiEntry.iv("cupm");

 			Clerk clerk = resourceRmi.loadClerk("0000", loginname);
 			String pass = clerk.getPassword();
 			passwordto = MD5Util.MD5_UTF16LE(passwordto);
 			String rs ="false";
 			if (StringUtils.isNotEmpty(passwordto) && passwordto.equals(pass)) {// 该账户已被禁止登录
 				map.put("usercode", loginname);
 				map.put("name", clerk.getName());
 				list.add(map);
 				response.getWriter().print(JSONArray.fromObject(list).toString());
 			}else{
 				response.getWriter().print(rs);
 			}
 			
 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}

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
