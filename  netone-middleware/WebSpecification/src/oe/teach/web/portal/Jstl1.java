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

public class Jstl1 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		// 创建参数 1
		request.setAttribute("var1", "data1");
		// 创建参数 2
		request.getSession().setAttribute("var2", "data2");
		// 创建参数 3
		this.getServletContext().setAttribute("var3", "data3");

		// 参数4
		Map map = new HashMap();
		map.put("a", "1");
		map.put("b", "2");
		request.setAttribute("var4", map);

		// 参数5
		Map map2 = new HashMap();
		map2.put("c", "3");
		map.put("c", map2);
		request.setAttribute("var5", map);

		// 参数6
		String[] abc = { "1", "2" };
		request.setAttribute("var6", abc);

		// 参数7
		HumanObj obj = new HumanObj("mike", 20);
		request.setAttribute("var7", obj);

		// 参数8
		List list = new ArrayList();
		HumanObj obj1 = new HumanObj("mike", 20);
		HumanObj obj2 = new HumanObj("jim", 20);
		list.add(obj1);
		list.add(obj2);
		request.setAttribute("var8", list);

		request.getRequestDispatcher("portal/jstl/jstl1.jsp").forward(request,
				response);

	}
}
