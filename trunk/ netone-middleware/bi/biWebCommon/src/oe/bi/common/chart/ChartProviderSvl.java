package oe.bi.common.chart;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ChartProviderSvl extends HttpServlet {

	static Log log = LogFactory.getLog(ChartProviderSvl.class);

	/**
	 * Constructor of the object.
	 */
	public ChartProviderSvl() {
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

		response.setContentType("img/png");
		String imgid = request.getParameter("imgid");
		if (imgid != null && !imgid.equals("")) {
			imgid = "jfreechart" + imgid;
			Object obj = request.getSession().getAttribute(imgid);
			if (obj != null) {
				CachedChart cchart = (CachedChart) obj;
				BufferedOutputStream bos = new BufferedOutputStream(response
						.getOutputStream());
				bos.write(cchart.getImg());
				bos.flush();
				bos.close();
				// 删除session的图片缓存
				// request.getSession().removeAttribute(imgid);
			} else {
				log.error("id为：" + imgid + "的图片在session中没有找到！");
			}
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
	 *             if an error occure
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
