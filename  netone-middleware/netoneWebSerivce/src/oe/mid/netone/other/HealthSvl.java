package oe.mid.netone.other;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HealthSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public HealthSvl() {
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
		
		String from=request.getParameter("from");
		String to=request.getParameter("to");
		String username=request.getParameter("username");
		String type=request.getParameter("type");
		String equi=request.getParameter("equi");
		String key=request.getParameter("key");
		
		if(from==null||to==null||type==null||equi==null||key==null){
			response.getWriter().print(0);
		}
		HealthIfc he=new HealthImpl();
		if("sportTimeTotal".equalsIgnoreCase(key)){
			if(username==null){
				response.getWriter().print(he.sportTimeTotal(Timestamp.valueOf(from), Timestamp.valueOf(to), type, equi));
			}else{
				response.getWriter().print(he.sportTimeTotal(Timestamp.valueOf(from), Timestamp.valueOf(to),username, type, equi));
			}
		}else if("sportDistanceTotal".equalsIgnoreCase(key)){
			if(username==null){
				response.getWriter().print(he.sportDistanceTotal(Timestamp.valueOf(from), Timestamp.valueOf(to), type, equi));
			}else{
				response.getWriter().print(he.sportDistanceTotal(Timestamp.valueOf(from), Timestamp.valueOf(to),username, type, equi));
			}
		}else if("caloryUseTotal".equals(key)){
			if(username==null){
				response.getWriter().print(he.caloryUseTotal(Timestamp.valueOf(from), Timestamp.valueOf(to), type, equi));
			}else{
				response.getWriter().print(he.caloryUseTotal(Timestamp.valueOf(from), Timestamp.valueOf(to),username, type, equi));
			}
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
		this.doGet(request, response);
	}

}
