package oe.cms.web.common;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import oe.cms.CmsEntry;

import oe.cms.cfg.TCmsJppmidware;

import oe.cms.runtime.InfoCellParser;
import oe.cms.runtime.ruleparser2.ELParser;

import oe.frame.orm.OrmerEntry;

/**
 * Portal 模板脚本执行服务
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjiaxun@oesee.com<br>
 *         tel:13328675083<br>
 */
public class NetScriptCalcalateSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public NetScriptCalcalateSvl() {
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

		String funcname = request.getParameter("#naturalname");
		String paraminfo = request.getParameter("#paraminfo");

		String rs = "";
		try {
			paraminfo = paraminfo == null ? "" : paraminfo;
			String[] paraminfoArr = paraminfo.split(",");
			StringBuffer but = new StringBuffer();
			for (int i = 0; i < paraminfoArr.length; i++) {
				String value = request.getParameter(paraminfoArr[i]);
				but.append(paraminfoArr[i] + ":=" + value + "&+");
			}
			String expression = "$:" + funcname + "{" + but + "}";

			InfoCellParser infoCellParser = (InfoCellParser) CmsEntry
					.fetchBean("infoCellParser");
			infoCellParser.setRequestInfo(request);

			rs = infoCellParser.performInfoCellParser(expression);
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath() + "/";

			expression = ELParser.parserUseNatrualname(expression, ""
					+ System.currentTimeMillis(), basePath);

			rs = infoCellParser.performInfoCellParser(expression);

		} catch (Exception e) {
			e.printStackTrace();
			rs = "脚本出错" + e.getMessage();
		}
		response.setContentType("text/html; charset=GBK");
		PrintWriter out = response.getWriter();
		out.print(rs);

		out.flush();
		out.close();

	}

	private static String scriptExpress(String jppid) {
		if (jppid == null || jppid.equals("")) {
			return "";
		}
		TCmsJppmidware tjm = (TCmsJppmidware) OrmerEntry.fetchOrmer()
				.fetchQuerister().loadObject(TCmsJppmidware.class, jppid);
		String script = tjm.getJppmidscriptapi();
		// script = WebStr.encode(request, script);
		if (script != null) {
			String key = StringUtils.substringBetween(script, "$:", "{");
			if (key != null && !key.equals("")) {
				script = StringUtils.replace(script, "$:" + key + "{", "$:"
						+ jppid + "{");
			}
		}
		return script;
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
