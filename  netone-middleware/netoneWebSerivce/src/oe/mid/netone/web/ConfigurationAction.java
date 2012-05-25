package oe.mid.netone.web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import com.jl.common.netone.UmsProtecte;
import org.apache.commons.lang.StringUtils;
 
public class ConfigurationAction extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ConfigurationAction() {
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
		request.setCharacterEncoding("tuf_8");
		String value=null;
		String appname=request.getParameter("appname");
		 UmsProtecte uProtecte=new UmsProtecte();
		 UmsProtectedobject upob= uProtecte.loadUmsProtecteNaturalname(appname);
		 if(StringUtils.isNotEmpty(upob.getExtendattribute())){
			 value=upob.getExtendattribute(); 	 
		 }else{
			 value=null;
		 }
		 response.setContentType("text/html;charset=utf-8");
		 response.getWriter().print(value);
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
