package oe.teach.web.portal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Jstl2 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		// 创建参数 1
		request.setAttribute("var1", "data1");

		request.setAttribute("var2", new Integer(10));

		request.getRequestDispatcher("portal/jstl/jstl2.jsp").forward(request,
				response);

	}
}
