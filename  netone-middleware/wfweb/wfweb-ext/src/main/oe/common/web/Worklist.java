package oe.common.web;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import oe.common.security3a.Client3A;
import oe.common.security3a.SecurityEntry;
import oe.common.workflow.WfEntry;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;

public class Worklist extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public Worklist() {
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

		try {
			Client3A client = SecurityEntry.iv().onlineUser(request);
			// 获得登陆用户信息

			// 获得该用户的代办任务
			List list = WfEntry.iv().worklist(client.getClientId());

			Map map = WfEntry.iv().allAvailableProcess();
			StringBuffer but = new StringBuffer();
			for (Iterator iterator = map.keySet().iterator(); iterator
					.hasNext();) {
				String processid = (String) iterator.next();
				WorkflowProcess wf = (WorkflowProcess) map.get(processid);

				String name = StringUtils.substringBetween(wf.getName(), "[",
						"]");

				but.append("<option value=\"" + name + "\">" + name
						+ "</option>");

			}
			String selectScript = "<select name=\"filterOption\" id=\"filterOption\">"
					+ "<option value=\"\" selected=\"selected\">--显示所有流程--</option>"
					+ but.toString() + "</select>";
			request.setAttribute("processselect", selectScript);

			request.setAttribute("workLists", list);
			request.setAttribute("reloadtime", request.getParameter("reloadtime"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String basePath = "/common/worklist.jsp";

		request.getRequestDispatcher(basePath).forward(request, response);
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
