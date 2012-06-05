package oe.mid.netone.web;

import java.io.IOException;
import java.rmi.NotBoundException;
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

import net.sf.json.JSONArray;

import oe.frame.web.WebCache;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import com.jl.common.netone.UmsProtecte;
import com.jl.common.workflow.DbTools;
import com.jl.entity.User;

public class Personnel extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public Personnel() {
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
		ResourceRmi rsResourceRmi=null;
		try {
			rsResourceRmi = (ResourceRmi)RmiEntry.iv("resource");
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Clerk clerk=null;
 
		String sqlstr = "select u_unid from  iss.t_file where f_type = '1' GROUP BY u_unid ";
		List list2 = DbTools.queryData(sqlstr);
		List listx=new ArrayList();

			 for (Iterator iterator = list2.iterator(); iterator.hasNext();) {
					Map map = (Map) iterator.next();
					System.out.println((String)map.get("u_unid"));
					clerk=rsResourceRmi.loadClerk("0000",(String)map.get("u_unid"));
					try {
						Map map2=new HashMap();
						map2.put("name",(String)clerk.getName());
						map2.put("usercode", (String)map.get("u_unid"));
						listx.add(map2);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						System.out.println("有用户不存在");
				        return;
					}
				}   
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
