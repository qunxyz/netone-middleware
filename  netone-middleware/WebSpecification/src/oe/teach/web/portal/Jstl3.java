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

public class Jstl3 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String type = request.getParameter("type");

		// 创建参数 1
		request.setAttribute("num", new Integer(1));

		// 参数2
		List list = new ArrayList();
		HumanObj obj1 = new HumanObj("mike", 20);
		HumanObj obj2 = new HumanObj("jim", 25);
		HumanObj obj3 = new HumanObj("tomi1", 22);
		HumanObj obj4 = new HumanObj("rob", 40);
		list.add(obj1);
		list.add(obj2);
		list.add(obj3);
		list.add(obj4);
		request.setAttribute("list", list);

		if ("1".equals(type)) {
			request.getRequestDispatcher("portal/jstl/jstl3.jsp").forward(request,
					response);
		} else if ("2".equals(type)) {
			request.getRequestDispatcher("portal/jstl/jstl4.jsp").forward(request,
					response);
		} else if ("3".equals(type)) {
			request.getRequestDispatcher("portal/jstl/jstl5.jsp").forward(request,
					response);
		}

	}
}
