package oe.teach.web.turnpageori;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.orm.OrmerEntry;
import oe.frame.web.page.PageInfo;
import oe.teach.mid.buss.OecStuDao;

/**
 * 流程页面
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

	private static int _PRE_PAGE = 10;// 默认分页

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

		String condition = bussdao.listCondition(request);

		PageInfo pginfo = null;// 分页信息

		if (PageInfo.isPageEvent(request)
				&& request.getSession().getAttribute("first") != null) {// 每次翻页交互

			pginfo = new PageInfo(request, "flist");

		} else { // 查询或者第一次页面交互

			int dataNumber = bussdao.totalNum(condition);// 总记录数
			pginfo = new PageInfo(dataNumber, _PRE_PAGE, request, "flist");// 分页信息
			pginfo.setNowpage(1);
			request.getSession().setAttribute("first", "yes");
		}
		// 根据分页的参数查询本页的相关数据
		List list = bussdao.query(condition, pginfo.getPageStartIndex(),_PRE_PAGE);

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
