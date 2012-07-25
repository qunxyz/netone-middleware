package oe.rmi.message.mail.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class MessageUserSynSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public MessageUserSynSvl() {
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
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=GBK");
		String flag = request.getParameter("flag");
		String key=request.getParameter("key");
		List clerkList = null;
		Synchronization s = new Synchronization();
		if ("byDept".equals(flag)) {
			String id = request.getParameter("id");
			clerkList = s.getClerkList(id);
		}
		if ("byHuman".equals(flag)) {
			String chkid = request.getParameter("chkid");
			String str[] = StringUtils.split(chkid, ",");
			if (str != null && str.length > 0) {
				clerkList = s.getClerkList(request,str);
			}
		}
		if (s.sync(clerkList,key)) {
			response.getWriter().print(
					"<script>alert('同步结束!');window.close();</script>");
		} else {
			response.getWriter().print(
					"<script>alert('没有人员,无法同步!');window.close();</script>");
		}
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
