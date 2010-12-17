package oe.teach.web.base;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.ServerException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ��ʾ���,��Ӧ����Tableacter.html���н���
 * 
 * @since 1.0
 * @author chen.jia.xun(Robanco) <br>
 *         <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com <br>
 */
public class ViewTableSvl extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServerException {
		// �����Tableacter.htmlҳ�潻���Ĳ���
		String numGet = request.getParameter("num");
		int numValue = 0;
		if (numGet != null) {
			numValue = Integer.parseInt(numGet);
		}
		// ������������Ϣ
		PrintWriter out = response.getWriter();
		out.print("<html><body>");
		// ���ͷ��Ϣ
		out.print("<table border='1' width='60%'>");
		// ��������Ϣ
		out
				.print("<tr bgcolor='#CCCCCC'><td>NAME</td><td>AGE</td><td>DESCRIPTION</td></tr>");
		// ����еļ�¼��Ϣ
		for (int i = 0; i < numValue; i++) {
			out.print("<tr><td>name" + i + "</td><td>" + i
					+ "</td><td>description" + i + "</td></tr>");
		}
		out.print("</table>");
		out.print("</body></html>");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServerException {
		doGet(request, response);
	}
}
