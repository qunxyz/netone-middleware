package oe.mid.netone.other;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json;charset=utf-8");
		
		String fromx=request.getParameter("from");
		String tox=request.getParameter("to");
		String userName=request.getParameter("userid");
		String key=request.getParameter("key");
		
		Timestamp from=StringUtils.isEmpty(fromx)?new Timestamp(System.currentTimeMillis()-3600000):Timestamp.valueOf(fromx+" 00:00:00.000");
		Timestamp to=StringUtils.isEmpty(tox)?new Timestamp(System.currentTimeMillis()):Timestamp.valueOf(tox+" 00:00:00.000");
		

		
		if(from==null||to==null||key==null){
			String jsonString="{\"value\":\"-1\"}";
	   		response.getWriter().print(jsonString);
	   		return;
		}
		HealthIfc he=new HealthImpl();

		if("sportTimeTotal".equals(key)){
			Object obj=he.sportTimeTotal(from, to, userName);
			String jsonString="{\"value\":\""+obj+"\"}";
	   		response.getWriter().print(jsonString);
	   		return;
		}else if("caloryUseTotal".equals(key)){
			Object obj=he.caloryUseTotal(from, to, userName);
			String jsonString="{\"value\":\""+obj+"\"}";
	   		response.getWriter().print(jsonString);
	   		return;
		}else if("heightTotal".equals(key)){
			Object obj=he.heightTotal(from, to, userName);
			String jsonString=JSONArray.fromObject(obj).toString();
	   		response.getWriter().print(jsonString);
	   		return;
		}
		else if("trackTotal".equals(key)){
			Object obj=he.trackTotal(from, to, userName);
			String jsonString=JSONArray.fromObject(obj).toString();
	   		response.getWriter().print(jsonString);
	   		return;
		}
		else if("stepTotal".equals(key)){
			Object obj=he.stepTotal(from, to, userName);
			String jsonString="{\"value\":\""+obj+"\"}";
	   		response.getWriter().print(jsonString);
	   		return;
		}
		else if("sportTop".equals(key)){
			String topn=request.getParameter("topn");
			Object obj=he.sportTop(userName,topn);
			String jsonString=JSONArray.fromObject(obj).toString();
	   		response.getWriter().print(jsonString);
	   		return;
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
	
	public static void main(String[] args) {
		
		
   		System.out.println();
	}

}
