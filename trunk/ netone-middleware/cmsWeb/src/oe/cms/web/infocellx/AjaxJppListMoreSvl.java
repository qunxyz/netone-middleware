package oe.cms.web.infocellx;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.cms.CmsEntry;
import oe.cms.cfg.TCmsJppmidware;
import oe.cms.dao.infocell.JppMiddwareDIY;
import oe.security3a.sso.Security;


/**
 * ���ϵͳ��������й���JPP����Ԫ��
 * 
 * @author chen.jia.xun
 * 
 */
public class AjaxJppListMoreSvl extends HttpServlet {
	
	/**
	 * Constructor of the object.
	 */
	public AjaxJppListMoreSvl() {
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

		// response.setHeader("Pragma", "no-cache");
		// response.setHeader("Cache-Control", "no-cache");
		// response.setHeader("Expires", "0");

		String valuelist = request.getParameter("valuelist");

		String onlyMe = request.getParameter("onlyme");
		Security ser = new Security(request);

		JppMiddwareDIY jppMiddwareDIY = (JppMiddwareDIY) CmsEntry
				.fetchDao("jppMiddwareDIY");
		Map self = jppMiddwareDIY.jppmidwareByUser(ser.getUserLoginName());
		Map otherpublic = jppMiddwareDIY.jppmidwareAllPublic();
		StringBuffer but = new StringBuffer();

		but.append("<option value='0'>--OESEEJ++�ű�ģ��--</option>");
		// ������Լ���JPPģ��
		if (self != null) {
			for (Iterator itr = self.keySet().iterator(); itr.hasNext();) {
				String jmwPreID = (String) itr.next();
				TCmsJppmidware jmwPre = (TCmsJppmidware) self.get(jmwPreID);
				String name = jmwPre.getJppmidname();
				String jmwName = "�Զ���:" + name;
				if (jmwPre.getJppmid().equals(valuelist)) {
					// �ڱ�dyForm��Ӧ�õ�ʱ��,���޸�ѡ�����JPPģ��ʱ,��Ҫ��ʾ֮ǰ�Ѿ�ѡ�����
					but.append("<option value='" + jmwPre.getJppmid()
							+ "' selected>" + jmwName + "</option>");
				} else {
					but.append("<option value='" + jmwPre.getJppmid() + "'>"
							+ jmwName + "</option>");
				}

			}
		}
		if (onlyMe == null) {
			// ����ӱ��˵���Դ
			if (otherpublic != null) {
				for (Iterator itr = otherpublic.keySet().iterator(); itr
						.hasNext();) {
					String jmwPreID = (String) itr.next();
					TCmsJppmidware jmwPre = (TCmsJppmidware) otherpublic
							.get(jmwPreID);
					String participant = jmwPre.getParticipant();
					String name = jmwPre.getJppmidname();
					String designerName = ser.getUserName();
					String jmwName = "�����:" + designerName + name;

					if (jmwPre.getJppmid().equals(valuelist)) {
						// �ڱ�dyForm��Ӧ�õ�ʱ��,���޸�ѡ�����JPPģ��ʱ,��Ҫ��ʾ֮ǰ�Ѿ�ѡ�����
						but.append("<option value='" + jmwPre.getJppmid()
								+ "' selected>" + jmwName + "</option>");
					} else {

						but.append("<option value='" + jmwPre.getJppmid()
								+ "'>" + jmwName + "</option>");
					}
				}
			}
		}

		response.setContentType("text/html; charset=GBK");
		PrintWriter out = response.getWriter();
		out.print("<select id='selJppJsMore' onChange='addScrpitToValueList()'>" + but
				+ "</select>");

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
