package oe.cms.web.outservice;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import oe.frame.orm.Ormer;
import oe.frame.orm.OrmerEntry;
import oe.frame.web.util.WebStr;


import oe.cms.CmsBean;
import oe.cms.CmsEntry;

import oe.cms.cfg.TCmsInfocell;
import oe.cms.dao.blog.Blog;
import oe.cms.dao.infocell.InfoCellDao;
import oe.cms.runtime.HtmlStreamHandler;
import oe.cms.runtime.XHtmlCachepool;

/**
 * Oec Netone设计工具上，添加文章使用的
 * 
 * @author chen.jia.xun
 * 
 */
public class PageService extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public PageService() {
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
		String header = "<html><head><link href='<%=path%>/cms/include/css/portal.css' rel='stylesheet' type='text/css'>"
				+ "<link href='<%=path%>/cms/include/css/css.css' rel='stylesheet' type='text/css'>"
				+ "<link href='<%=path%>/cms/include/css/style.css' rel='stylesheet' type='text/css'></head>";
		header = StringUtils.replace(header, "<%=path%>", request
				.getContextPath());

		String cellid = request.getParameter("cellid");

		InfoCellDao cellDao = (InfoCellDao) CmsEntry.fetchDao("infocellDao");
		TCmsInfocell cell = cellDao.viewPreOperation(cellid,request);

		// hst.setRequestInfo(request);
		HtmlStreamHandler htmlStreamHandler = (HtmlStreamHandler) CmsEntry
				.fetchBean("htmlStreamHandler");
		String str = htmlStreamHandler.toPortal(cellid, "2", request);
	
		// 修改原先定义的Portal的 body的大小比例
		str = StringUtils.replaceOnce(str, "<table border='1'>",
				"<table border='1' width='100%' height='100%'>");
		str = StringUtils.replaceOnce(str, "<td height='" + cell.getHeight()
				+ "' width='" + cell.getWidth() + "' class='BodyColor'>",
				"<td height='100%' width='100%' class='BodyColor'>");
		response.getWriter().write(header + str + "</body></html>");
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
		// Put your code here
	}

}
