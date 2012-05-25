package oe.mid.netone.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import net.sf.json.JSONArray;
import com.jl.common.app.impl2.AnalysisAppFirst;
import com.jl.common.app.impl2.AppFirst;
import com.jl.common.dyform.DyEntry;
import com.jl.common.dyform.DyFormData;
import com.jl.common.netone.UmsProtecte;
import com.jl.common.resource.Resource;
import com.jl.common.security3a.SecurityEntry;

public class QueryTime extends HttpServlet {

	/**
	 * xuwei(2012-5-4) ²éÑ¯¾­Î³¶È£º
	 */
	public QueryTime() {
		super();
	}

	public String userid;

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
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String time = request.getParameter("time");
		String id = request.getParameter("id");
		userid = request.getParameter("userid");
		List<Photographobj> list1 = new ArrayList<Photographobj>();
		UmsProtecte up = new UmsProtecte();
		UmsProtectedobject upobj = up.loadUmsProtecteID(id);

		if (StringUtils.isNotEmpty(upobj.getNaturalname())) {
			quanbushu(upobj.getNaturalname(), list1, time);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(JSONArray.fromObject(list1).toString());
	}

	public void quanbushu(String naturalname, List<Photographobj> list1,
			String time) {
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
		for (Iterator iterator = quanbushuju.iterator(); iterator.hasNext();) {
			Resource name = (Resource) iterator.next();
			String code1 = name.getResourcecode();
			String Name = name.getResourcename();
			Boolean fal = true;
			for (Iterator iterator2 = file.iterator(); iterator2.hasNext();) {
				Resource resourcefile = (Resource) iterator2.next();
				String code = resourcefile.getResourcecode();
				if (code.equals(code1)) {
					quanbushu(code, list1, time);
					fal = false;
				}
			}
			if (fal) {
				UmsProtecte up = new UmsProtecte();
				UmsProtectedobject upobj = up.loadUmsProtecteNaturalname(code1);
				if (StringUtils.isNotEmpty(upobj.getExtendattribute())) {
					DyFormData bus = new DyFormData();
					bus.setFatherlsh("1");
					bus.setTimex(time);
					bus.setParticipant(userid);
					AnalysisAppFirst appFirst = new AnalysisAppFirst();
					AppFirst app = appFirst.readXML(upobj.getExtendattribute());
					bus.setFormcode(app.getFormcode());
					query(bus, Name, list1);
				}
			}
		}
	}

	public void query(DyFormData bus, String name, List<Photographobj> list1) {
		List list = null;
		try {
			list = DyEntry.iv().queryData(bus, 0, 1000, "");
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				DyFormData dydata = (DyFormData) iterator.next();
				Photographobj ppobj = new Photographobj();
				String[] arr = name.split("/");
				ppobj.setName(arr[arr.length - 1]);
				ppobj.setUserid(dydata.getParticipant());
				ppobj.setLongitude(dydata.getColumn3());
				ppobj.setLatitude(dydata.getColumn4());
				list1.add(ppobj);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
