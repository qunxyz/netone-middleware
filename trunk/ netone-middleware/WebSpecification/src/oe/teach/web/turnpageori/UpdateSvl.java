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
 * ����ҳ��
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
		OecStuDao bussdao = (OecStuDao) OrmerEntry.fetchDAO("bussdao");
		// ope��ֵ������ 1:view ,2:done
		String ope = request.getParameter("ope");
		// ����������Ҫ������
		String stuname = "";
		String description = "";
		String stuid = request.getParameter("stuid");
		OecStudent oecStu = new OecStudent();
		// �ύ����
		if ("done".equals(ope)) {
			stuname = request.getParameter("stuname");
			description = request.getParameter("description");

			oecStu.setStuid(new Long(stuid));
			oecStu.setStuname(stuname);
			oecStu.setDescription(description);

			bussdao.update(oecStu);
		} else {
			oecStu = bussdao.load(stuid);
		}
		// д��ҳ�������
		request.setAttribute("stu", oecStu);
		request.setAttribute("ope", ope);
		// �򿪴���ҳ��
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
