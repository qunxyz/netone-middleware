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
public class GraphSeverlet extends HttpServlet {

	private static final long serialVersionUID = 7671044262871505595L;

	static Log log = LogFactory.getLog(GraphSeverlet.class);

	/**
	 * Constructor of the object.
	 */
	public GraphSeverlet() {
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
		String xoffset = request.getParameter("xoffset");
		String yoffset = request.getParameter("yoffset");

		String tg = request.getParameter("tg0");

		List listTg = new ArrayList();
		List listNa = new ArrayList();
		int i = 0;

		while (tg != null) {
			listTg.add(tg);
			log.debug("tg:" + tg);
			String na = request.getParameter("na" + i);
			log.debug("na:" + na);
			listNa.add(na);
			i++;
			tg = request.getParameter("tg" + i);
		}
		String[] tgs = (String[]) listTg.toArray(new String[0]);
		String[] nas = (String[]) listNa.toArray(new String[0]);

		dimvaluelist = WebStr.encode(request, dimvaluelist);
		dimName = WebStr.encode(request, dimName);
		title = WebStr.encode(request, title);
		for (int k = 0; k < nas.length; k++) {
			nas[k] = WebStr.encode(request, nas[k]);
		}

		byte[] ci = GraphBuilder.fetchGraph(dimvaluelist, tgs, dimName, nas,
				charttype, title,xoffset,yoffset, request);

		BufferedOutputStream bos = new BufferedOutputStream(response
				.getOutputStream());
		bos.write(ci);

		bos.flush();
		bos.close();

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
