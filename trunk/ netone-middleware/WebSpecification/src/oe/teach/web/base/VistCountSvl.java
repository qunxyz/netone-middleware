package oe.teach.web.base;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.ServerException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * WEB计数器
 * 
 * @since 1.0
 * @author chen.jia.xun(Robanco) <br>
 *         <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com <br>
 */
public class VistCountSvl extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServerException {
		
		response.setContentType("text/html;charset=GBK");
		PrintWriter out = response.getWriter();
		// 计算点击数
		Long num = (Long) this.getServletContext().getAttribute("num");
		if (num == null) {// 第一次
			this.getServletContext().setAttribute("num", new Long(0));
		} else {// 第N次,N>1
			Long num1 = (Long) this.getServletContext().getAttribute("num");
			this.getServletContext().setAttribute("num",
					new Long(num1.longValue() + 1));
		}

		Long numx = (Long) this.getServletContext().getAttribute("num");
		out.print("<html><body>");
		out.print("您是第 :" + numx + " 个访问者");

		out.print("</body></html>");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServerException {
		doGet(request, response);
	}
}
