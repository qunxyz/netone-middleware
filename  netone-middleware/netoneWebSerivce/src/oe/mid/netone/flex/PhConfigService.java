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
import com.jl.common.resource.Resource;
import com.jl.common.security3a.SecurityEntry;
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
		List list = new ArrayList();
		request.setCharacterEncoding("utf-8");
		String naturalname = request.getParameter("naturalname");
		List quanbushuju = null;
		List file = null;
		try {
			quanbushuju = SecurityEntry.iv().listRsInDir(naturalname, "01", 0,
					1000, "");
			file = SecurityEntry.iv().listDirRs(naturalname);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (quanbushuju.size() > 0) {
			for (Iterator iterator = quanbushuju.iterator(); iterator.hasNext();) {
				Resource resource = (Resource) iterator.next();
				phConfigobj phobj = new phConfigobj();
				String[] strarr = resource.getResourcename().split("/");
				resource.setResourcename(strarr[strarr.length - 1]);
				String sqlstr = "SELECT address FROM iss.t_file WHERE d_unid='"
						+ resource.getId() + "'";
				List list2 = DbTools.queryData(sqlstr);
				for (Iterator iterator2 = list2.iterator(); iterator2.hasNext();) {
					Map map = (Map) iterator2.next();
					String string=(map.get("address")).toString()
					.replaceAll("\\\\", "\\/");
					String [] arr=string.split("/");
					phobj.setImagename(arr[arr.length-1]);
				}
				phobj.setNaturalname(resource.getResourcecode());
				phobj.setTypes(resource.getTypes());
				phobj.setResourcename(resource.getResourcename());
				phobj.setDistinguish(resource.getInclusion());
				if (resource.getInclusion().equals("0")) {
					if (StringUtils.isNotEmpty(resource.getText().trim())) {
						UmsProtecte up = new UmsProtecte();
						UmsProtectedobject upob = up
								.loadUmsProtecteNaturalname(resource.getText()
										.trim());
						if (StringUtils.isNotEmpty(upob.getExtendattribute())) {
							phobj.setExtendattribute(upob.getExtendattribute());
						} else {
							phobj.setExtendattribute("");
						}
					} else {
						phobj.setExtendattribute("");
					}
				}
				if (naturalname.equals(resource.getResourceid())) {
				} else {
					list.add(phobj);
				}
			}
		}
		String json = JSONArray.fromObject(list).toString();
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
