package oe.service.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.db.DbTools;
import oe.frame.web.util.WebStr;

public class AccoutAddSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public AccoutAddSvl() {
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
		
		String lsh=UUID.randomUUID().toString().replaceAll("-", "");
		String name=request.getParameter("name");
	
		String types=request.getParameter("types");
		types=WebStr.encode(request, types);
		types=URLDecoder.decode(types, "GBK");
		String oriprice=request.getParameter("oriprice");
		String curprice=request.getParameter("curprice");
		String description=request.getParameter("description");
		
		String sql="insert into myaccount(lsh,name,types,oriprice,curprice,description)values("+
		"'"+lsh+"','"+name+"','"+types+"',"+oriprice+","+curprice+",'"+description+"')";
		
		DbTools.execute(sql);


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
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		
		String car="Æû³µ·¿¶¥ÉÏ";
		String carnew=URLEncoder.encode(car,"GBK");
		System.out.println(carnew);
		
		carnew=URLDecoder.decode(carnew, "GBK");
		System.out.println(carnew);
	}

}
