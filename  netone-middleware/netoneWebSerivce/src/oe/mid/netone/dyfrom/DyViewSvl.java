package oe.mid.netone.dyfrom;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
/**
 * ����ͼ�����࣬����չʾ��ʹ��
 * @author robanco
 *
 */
public class DyViewSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public DyViewSvl() {
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

		doGet(request, response);
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

		String tablename=request.getParameter("tablename");
		String columns=request.getParameter("columns");// ������ֶο����Ƕ��Ÿ���
		
		List list=null;
		
		String jsonString=JSONArray.fromObject(list).toString();  
   		response.getWriter().print(jsonString);
	
	}

}
