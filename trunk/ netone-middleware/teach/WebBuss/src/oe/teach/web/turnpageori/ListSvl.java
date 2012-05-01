package oe.teach.web.turnpageori;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.page.PageInfo;
import oe.teach.mid.buss.MyAccountDao;
import oe.teach.mid.bussmap.MyAccuntDaoImpl;

/**
 * ����ҳ��
 * 
 * @author chen.jia.xun
 * 
 */
public class ListSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ListSvl() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	private static int _PRE_PAGE = 10;// Ĭ�Ϸ�ҳ

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
		response.setContentType("text/html;charset=gbk");
		MyAccountDao dao = new MyAccuntDaoImpl();

		String condition = dao.makeCondition(request);

		PageInfo pginfo = null;// ��ҳ��Ϣ

		if (PageInfo.isPageEvent(request)
				&& request.getSession().getAttribute("first") != null) {// ÿ�η�ҳ����

			pginfo = new PageInfo(request, "flist");

		} else { // ��ѯ���ߵ�һ��ҳ�潻��

			int dataNumber = dao.totalNum(condition);// �ܼ�¼��
			pginfo = new PageInfo(dataNumber, _PRE_PAGE, request, "flist");// ��ҳ��Ϣ
			pginfo.setNowpage(1);
			request.getSession().setAttribute("first", "yes");
		}
		// ���ݷ�ҳ�Ĳ�����ѯ��ҳ���������
		List list = dao.query(condition, pginfo.getPageStartIndex(), pginfo
				.getPageItemCount());

		request.setAttribute("listinfo", list);

		request.getRequestDispatcher("/turnpageori/list.jsp").forward(request,
				response);
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
