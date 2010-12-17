package oe.teach.web.base;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.ServerException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * WEBÉÏÊä³öÈý½Ç
 * 
 * @since 1.0
 * @author chen.jia.xun(Robanco) <br>
 *         <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com <br>
 */
public class TraingleSvl extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse rep)
			throws IOException, ServerException {
		int cycle = 9;
		Triangle triangle = new Triangle(cycle);
		PrintWriter out = rep.getWriter();
		out.print("<html><body>");
		out.print("<div align='center'>");
		for (int i = 0; i <= cycle; i++) {
			for (int j = 0; j <= i; j++) {
				out.print(triangle.recursion(i, j));
				out.print("&nbsp;");
			}
			out.print("<BR>");
		}
		out.print("</div>");
		out.print("</body></html>");
	}
}
