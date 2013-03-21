package oe.mid.netone.msg;

import java.io.IOException;
import java.io.PrintWriter;
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

import net.sf.json.JSONArray;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

public class DeptSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public DeptSvl() {
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
		//获得根部门，这里 部门只支持2级
		String rootname=request.getParameter("rootname");
		List info=new ArrayList();
		try {
			ResourceRmi rmi=(ResourceRmi)RmiEntry.iv("resource");
			UmsProtectedobject upo=rmi.loadResourceByNatural(rootname);
			List list=rmi.subResource(upo.getId());
			
			
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				UmsProtectedobject object = (UmsProtectedobject) iterator.next();
				Map map=new HashMap();
				map.put("naturalname", object.getNaturalname());
				map.put("name", object.getName());
				List next=rmi.subResource(object.getId());
				
				List nextx=new ArrayList();
				for (Iterator iterator2 = next.iterator(); iterator2.hasNext();) {
					UmsProtectedobject object2 = (UmsProtectedobject) iterator2.next();
					Map mapx=new HashMap();
					mapx.put("naturalname", object2.getNaturalname());
					mapx.put("name", object2.getName());
					nextx.add(mapx);
				}
				
				map.put("sub",nextx );

				info.add(map);

			}
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String jsonString=JSONArray.fromObject(info).toString();
   		response.getWriter().print(jsonString);
		
	}
	
	
	public static void main(String[] args) {
		
		List list=new ArrayList();
		
		Map map=new HashMap();
		map.put("naturalname", "1");
		map.put("name", "1");
		
		
		
		List list2=new ArrayList();
		Map map2=new HashMap();
		map2.put("naturalname", "2");
		map2.put("name", "2");
		list2.add(map2);
		list2.add(map2);
		map.put("sub", list2);
		
		list.add(map);
		list.add(map);
		
		
		String jsonString=JSONArray.fromObject(list).toString();
   		System.out.println(jsonString);
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

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

}
