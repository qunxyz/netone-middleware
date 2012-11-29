package oe.mid.netone.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.NotBoundException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;

import com.jl.common.workflow.DbTools;

/**
 * 获取所有用户
 * @author robanco
 *
 */
public class AllUserSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public AllUserSvl() {
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

		try {
			ResourceRmi rs=(ResourceRmi)RmiEntry.iv("resource");
			String naturalname=request.getParameter("naturalname");
			String systemid=rs.loadResourceByNatural(naturalname).getId();
			List user=null;
			if(StringUtils.isNotEmpty(systemid)){
				String sql="select usercode,name from netone.t_cs_user where systemid='"+systemid+"'";
				user=DbTools.queryData(sql);
			}else{
				String sql="select usercode,name from netone.t_cs_user limit 0,100";
				user=DbTools.queryData(sql);
			}

			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(JSONArray.fromObject(user).toString());
		} catch (NotBoundException e) {
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

}
