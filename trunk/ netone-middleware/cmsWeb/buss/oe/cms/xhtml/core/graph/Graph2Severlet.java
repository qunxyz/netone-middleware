package oe.cms.xhtml.core.graph;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.util.WebStr;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;





/**
 * 传输图表到客户端的Servlet
 * 
 * @author hls
 * 
 */
public class Graph2Severlet extends HttpServlet {

	private static final long serialVersionUID = 7671044262871505595L;

	static Log log = LogFactory.getLog(Graph2Severlet.class);

	/**
	 * Constructor of the object.
	 */
	public Graph2Severlet() {
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

		String dimvaluelist = request.getParameter("dimvaluelist");
		String dimName = request.getParameter("dimName");
		String charttype = request.getParameter("charttype");
		String title = request.getParameter("title");
		dimvaluelist = WebStr.encode(request, dimvaluelist);
		dimName = WebStr.encode(request, dimName);
		title = WebStr.encode(request, title);

		String xoffset = request.getParameter("xoffset");
		String yoffset = request.getParameter("yoffset");

		// 获得左刻度的指标值
		List listInfo = new ArrayList();
		int len = 0;
		for (int i = 0; i < 10; i++) {
			String value = (String) request.getParameter("targetvaluelist" + i);
			if (value == null) {
				len = i;
				break;
			}
			listInfo.add(value);
		}
		String[] targetvaluelist = (String[]) listInfo.toArray(new String[0]);
		// 获得左刻度的指标名字
		String[] targetname = new String[len];
		for (int i = 0; i < len; i++) {
			targetname[i] = request.getParameter("targetname" + i);
			if (targetvaluelist[i] == null) {
				targetvaluelist[i] = "";
			}
			targetname[i] = WebStr.encode(request, targetname[i]);
		}

		// 获得右刻度的指标值
		List listInfoR = new ArrayList();

		int lenR = 0;
		for (int i = 0; i < 10; i++) {
			String value = (String) request
					.getParameter("targetvaluelistR" + i);
			if (value == null) {
				lenR = i;
				break;
			}
			listInfoR.add(value);
		}
		String[] targetvaluelistR = (String[]) listInfoR.toArray(new String[0]);
		// 获得右刻度的指标名字
		String[] targetnameR = new String[lenR];
		for (int i = 0; i < lenR; i++) {
			targetnameR[i] = request.getParameter("targetnameR" + i);
			if (targetvaluelistR[i] == null) {
				targetvaluelistR[i] = "";
			}
			targetnameR[i] = WebStr.encode(request, targetnameR[i]);
		}

		byte[] ci = Graph2Builder.fetchGraph(dimvaluelist,
				targetvaluelist, targetvaluelistR, dimName, targetname,
				targetnameR, charttype, title, xoffset, yoffset, request);

		BufferedOutputStream bos = new BufferedOutputStream(response
				.getOutputStream());

		bos.write(ci);

		bos.flush();
		bos.close();

		// 删除session的图片缓存
		// request.getSession().removeAttribute(imgid);
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
