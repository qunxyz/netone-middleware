package oe.teach.web.turnpageori;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.orm.OrmerEntry;
import oe.teach.mid.buss.OecStuDao;
import oe.teach.mid.buss.OecStudent;

/**
 * 流程页面
 * 
 * @author chen.jia.xun
 * 
 */
public class CreateSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public CreateSvl() {
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
		// ope的值有两种 1:view ,2:done
		String ope = request.getParameter("ope");
		// 创建操作需要的属性
		String stuname = "";
		String description = "";
		// 提交创建
		if ("done".equals(ope)) {
			stuname = request.getParameter("stuname");

			description = request.getParameter("description");

			OecStudent oecStu = new OecStudent();
			oecStu.setStuname(stuname);
			oecStu.setDescription(description);

			OecStuDao dao = (OecStuDao) OrmerEntry.fetchDAO("bussdao");

			dao.create(oecStu);

		}
		// 写回页面的属性
		request.setAttribute("ope", ope);
		request.setAttribute("stuname", stuname);
		request.setAttribute("description", description);
		// 打开创建页面
		request.getRequestDispatcher("/turnpageori/create.jsp").forward(
				request, response);
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
	 *             if an error occure
	 */
	public void init() throws ServletException {

	}

}
