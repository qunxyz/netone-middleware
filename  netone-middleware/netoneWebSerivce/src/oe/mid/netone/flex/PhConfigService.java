package oe.mid.netone.flex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import org.apache.commons.lang.StringUtils;
import com.jl.common.netone.UmsProtecte;
import com.jl.common.workflow.DbTools;

public class PhConfigService extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public PhConfigService() {
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
		doPost(request, response);
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
		List listx = new ArrayList();
		request.setCharacterEncoding("utf-8");
		String naturalname = request.getParameter("naturalname");
		UmsProtecte up = new UmsProtecte();
		UmsProtectedobject upobj = up.loadUmsProtecteNaturalname(naturalname);

		String sqlStr = "SELECT naturalname,name,objectType,id,extendattribute,inclusion,ACTIVE FROM netone.ums_protectedobject WHERE PARENTDIR='"
				+ upobj.getId() + "'ORDER BY aggregation ";
		List list = DbTools.queryData(sqlStr);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map map = (Map) iterator.next();

			phConfigobj phobj = new phConfigobj();
			phobj.setDistinguish((String) map.get("inclusion"));
			phobj.setNaturalname((String) map.get("naturalname"));
			phobj.setTypes((String) map.get("objectType"));
			phobj.setResourcename((String) map.get("name"));
			String sqlstr1 = "SELECT address FROM iss.t_file WHERE d_unid='"
					+ (String) map.get("id") + "'";
			List list2 = DbTools.queryData(sqlstr1);
			for (Iterator iterator2 = list2.iterator(); iterator2.hasNext();) {
				Map map1 = (Map) iterator2.next();
				String string = (map1.get("address")).toString().replaceAll(
						"\\\\", "\\/");
				String[] arr = string.split("/");
				phobj.setImagename(arr[arr.length - 1]);
			}
			if (((String) map.get("inclusion")).equals("0")) {
				if (StringUtils
						.isNotEmpty(((String) map.get("extendattribute"))
								.trim())) {
					UmsProtectedobject upob = up
							.loadUmsProtecteNaturalname(((String) map
									.get("extendattribute")).trim());
					if (upob!=null&&StringUtils.isNotEmpty(upob.getExtendattribute())) {
						phobj.setExtendattribute(upob.getExtendattribute());
					} else {
						phobj.setExtendattribute("");
					}
				} else {
					phobj.setExtendattribute("");
				}
			}
			if (((String) map.get("ACTIVE")).equals("1")) {
				if (naturalname.equals((String) map.get("naturalname"))) {
				} else {
					listx.add(phobj);
				}
			}

		}
		String json = JSONArray.fromObject(listx).toString();
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(json);
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
