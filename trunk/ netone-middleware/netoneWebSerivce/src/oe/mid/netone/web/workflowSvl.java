package oe.mid.netone.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import net.sf.json.JSONArray;
import com.jl.common.netone.UmsProtecte;
import com.jl.common.workflow.DbTools;

public class workflowSvl extends HttpServlet {

	/**
	 * xuwei(2012-5-4) 查询流程数据的
	 * 
	 */
	public workflowSvl() {
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
		request.setCharacterEncoding("utf-8");
		String model = request.getParameter("model");
		if (model == null) {
			model = "0";
		}
		String sqlStr = null;
		String jsonString = null;
		List list1 = new ArrayList();
		if (model.equals("0")) {
			String naturalname = request.getParameter("naturalname");
			sqlStr = "SELECT name,id,naturalname FROM  netone.ums_protectedobject WHERE parentdir = (SELECT id "
					+ "FROM netone.ums_protectedobject WHERE naturalname = '"
					+ naturalname + "')";
		}
		if (model.equals("1")) {
			String parentdir = request.getParameter("parentdir");
			sqlStr = "select name,actionurl,naturalname from netone.ums_protectedobject where parentdir='"
					+ parentdir + "'  LIMIT 20";
		}
		List list = DbTools.queryData(sqlStr);
		if (model.equals("1")) {
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Map map = (Map) iterator.next();
				String url = (String) map.get("actionurl");
				String[] strarr = url.split("&appname=");
				map.put("appname", strarr[1]);
				map.remove("actionurl");
				list1.add(map);
			}
			jsonString = JSONArray.fromObject(list1).toString();
		} else {
			if (model.equals("0")) {
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					Map map = (Map) iterator.next();
					UmsProtecte up = new UmsProtecte();
					UmsProtectedobject umobj = up
							.loadUmsProtecteNaturalname((String) map
									.get("naturalname"));
					if (StringUtils.isNotEmpty(umobj.getInclusion())) {
						if (umobj.getInclusion().equals("1")) {
							map.put("key", umobj.getInclusion());
						} else {
							map.put("config", "1$2$3");
							map.put("key", umobj.getInclusion());
						}
					} else {
						map.put("config", "1$2$3");
						map.put("key", "0");
					}
					list1.add(map);
				}
			}
			jsonString = JSONArray.fromObject(list1).toString();
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(jsonString);
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
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
