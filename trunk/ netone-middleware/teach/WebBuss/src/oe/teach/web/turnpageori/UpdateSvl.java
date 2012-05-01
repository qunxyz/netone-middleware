package oe.teach.web.turnpageori;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.util.WebStr;
import oe.teach.mid.buss.MyAccount;
import oe.teach.mid.buss.MyAccountDao;
import oe.teach.mid.bussmap.MyAccuntDaoImpl;

/**
 * 流程页面
 * 
 * @author chen.jia.xun
 * 
 */
public class UpdateSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public UpdateSvl() {
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
		MyAccountDao dao = new MyAccuntDaoImpl();
		// ope的值有两种 1:view ,2:done
		String ope = request.getParameter("ope");
		// 创建操作需要的属性

		String lsh = request.getParameter("lsh");
		MyAccount ac = new MyAccount();

		// 提交创建
		if ("done".equals(ope)) {
			String name = request.getParameter("name");
			name=WebStr.encode(request, name);
			String oriprice = request.getParameter("oriprice");
			String curprice = request.getParameter("curprice");
			String types = request.getParameter("types");
			String description = request.getParameter("description");
			description=WebStr.encode(request, description);
			
			ac.setName(name);
			ac.setOriprice(Double.parseDouble(oriprice));
			ac.setCurprice(Double.parseDouble(curprice));
			ac.setTypes(types);
			ac.setDescription(description);
			ac.setLsh(lsh);
			

			dao.update(ac);
		} else {
			ac = dao.load(lsh);
		}
		// 写回页面的属性
		request.setAttribute("stu", ac);
		request.setAttribute("ope", ope);
		// 打开创建页面
		request.getRequestDispatcher("/turnpageori/update.jsp").forward(
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
