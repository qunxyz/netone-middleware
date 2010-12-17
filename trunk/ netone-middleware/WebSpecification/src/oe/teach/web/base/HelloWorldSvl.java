package oe.teach.web.base;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.ServerException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Serlvet Simple Sample
 * 
 * @since 1.0
 * @author chen.jia.xun(Robanco) <br>
 *         <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com <br>
 */
public class HelloWorldSvl extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServerException {

		PrintWriter out = response.getWriter();

		out.print("<html><body>");
		out.print("<h1>Hello world</h1>");
		out.print("</body></html>");

		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServerException {
		doGet(request, response);
	}
}
