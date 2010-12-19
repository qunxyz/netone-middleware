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
import oe.cms.runtime.InfoCellParser;
import oe.cms.runtime.ruleparser2.ELParser;

/**
 * Oec Netone设计工具上，添加文章使用的
 * 
 * @author chen.jia.xun
 * 
 */
public class JppService extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public JppService() {
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
		InfoCellParser infoCellParser = (InfoCellParser) CmsEntry
				.fetchBean("infoCellParser");
		infoCellParser.setRequestInfo(request);
		String bodyinfo = request.getParameter("bodyinfo");
		try {
			// 先处理EL脚本
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath() + "/";

			bodyinfo = ELParser.parserUseNatrualname(bodyinfo, "", basePath);

			bodyinfo = infoCellParser.performInfoCellParser(bodyinfo);

		} catch (Exception e) {
			bodyinfo = "出错了!<br>" + e.getMessage();
		}
		response.getWriter().write(bodyinfo);
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
