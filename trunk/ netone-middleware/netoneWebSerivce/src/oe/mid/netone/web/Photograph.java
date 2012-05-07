package oe.mid.netone.web;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;

import com.jl.common.dyform.DyEntry;
import com.jl.common.dyform.DyFormData;
import com.jl.common.netone.UmsProtecte;
import com.jl.common.workflow.DbTools;

public class Photograph extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public Photograph() {
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
          String longitude=request.getParameter("longitude");
          String latitude=request.getParameter("latitude");
          String userid=request.getParameter("userid");
          String lsh=request.getParameter("lsh");
          String id=request.getParameter("id");
          Boolean fal=false;
          UmsProtecte up=new UmsProtecte();
          UmsProtectedobject upobj=up.loadUmsProtecteID(id);
         if(StringUtils.isNotEmpty(upobj.getExtendattribute())){
      	    DyFormData bus=new DyFormData();
      	    bus.setFormcode(upobj.getExtendattribute());
      	    bus.setParticipant("1");
      	    bus.setLsh(lsh);
      	    bus.setParticipant(userid);
      	    bus.setColumn3(longitude);
      	    bus.setColumn4(latitude);
      	    try {
  				fal=DyEntry.iv().modifyData(upobj.getExtendattribute(),bus);
  			} catch (Exception e) {
  				// TODO Auto-generated catch block
  				e.printStackTrace();
  			}
          }
       response.getWriter().print(fal);    
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
