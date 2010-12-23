package oe.bi.web.etl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.bi.BiEntry;
import oe.bi.common.StringUtil;
import oe.bi.dao.ui.obj.NodeObj;
import oe.bi.dao.ui.obj.TargetObj;
import oe.bi.datasource.TargetAct;
import oe.frame.web.form.RequestUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class EtlTargetSelectSvl extends HttpServlet {

	static Log log = LogFactory.getLog(EtlTargetSelectSvl.class);

	/**
	 * Constructor of the object.
	 */
	public EtlTargetSelectSvl() {
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
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html; charset=UTF-8");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Expires", "0");

		String returnstr = "";
		String timeLevel = request.getParameter("timelevel");

		String method = request.getParameter("method");
		TargetAct tgact = (TargetAct) BiEntry.fetchBi("targetAct");
		if (method.equals("targetElementList")) {
			String groupid = StringUtil.iso8859ToUTF8(request.getParameter("groupid"));
			List tglist = tgact.targetElementList(groupid);

			if (tglist != null) {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < tglist.size(); i++) {
					if (i != 0) {
						sb.append(",");
					}
					TargetObj tg = (TargetObj) tglist.get(i);
					sb.append(tg.getTragetid());
					sb.append("=");
					sb.append(tg.getTragetname());
				}
				returnstr = sb.toString();
			}

		} else if (method.equals("targetGroupList")) {
			NodeObj node = (NodeObj) RequestUtil.mappingReqParam(NodeObj.class, request);
			

			String[][] strs = tgact.targetGroupList(node);
			log.debug("timeLevel:" + timeLevel);

			// �����ص�ָ����, ���Ҷ������������
			String[] nameIdSort = new String[strs.length];// ����ؼ�ֵ
			Map mapNameIdAndIdName = new HashMap();// ��������ӳ��
			for (int i = 0; i < strs.length; i++) {
				log.debug("selectLevel:");
				log.debug(strs[i][0] + " " + strs[i][1] + " " + strs[i][2]);
				// ����ʱ�����ȹ���ָ��(ָ���period�ֶο�����������ʱ��ı���)
				if (!timeLevel.equalsIgnoreCase(strs[i][2])) {
					continue;
				}
				// ����ֵ,��name+id ���,��Ϊ�����Ŀ��������ʾ��name��������
				nameIdSort[i] = strs[i][1] + strs[i][0];
				// ����ֵ��Ӧ��ҵ��ֵ��Ϣ
				String realInfo = strs[i][0] + "=" + strs[i][1];
				mapNameIdAndIdName.put(nameIdSort[i], realInfo);
			}
			// ִ������
			// Arrays.sort(nameIdSort);
			StringBuffer sb = new StringBuffer(" ");// Ĭ���и��մ���Ϊ��Ԥ��,û��ָ�����ʱ����������洦�Ĵ���
			for (int i = 0; i < nameIdSort.length; i++) {
				sb.append("," + mapNameIdAndIdName.get(nameIdSort[i]));
			}
			returnstr = sb.substring(1);
			log.debug("targetList Group:" + returnstr);
		}

		PrintWriter out = response.getWriter();
		out.print(returnstr);
		out.flush();
		out.close();
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
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
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
