package oe.mid.netone.web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

import com.jl.common.TimeUtil;
import com.jl.common.app.impl2.AnalysisAppFirst;
import com.jl.common.app.impl2.AppFirst;
import com.jl.common.dyform.DyEntry;
import com.jl.common.dyform.DyFormData;
import com.jl.common.netone.RMI_SER;
import com.jl.common.netone.RMI_SER_obj;
import com.jl.common.netone.UmsProtecte;

public class Querybranchlisting extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public Querybranchlisting() {
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
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String appname = request.getParameter("appname");
		List list = new ArrayList();
		RMI_SER rmi = new RMI_SER();
		RMI_SER_obj rmiobj = rmi.RMI_SER();
		UmsProtecte up = new UmsProtecte();
		UmsProtectedobject upobj = up.loadUmsProtecteNaturalname(appname);
		if (StringUtils.isNotEmpty(upobj.getExtendattribute())) {
			AnalysisAppFirst appFirst = new AnalysisAppFirst();
			AppFirst app = appFirst.readXML(upobj.getExtendattribute());
			DyFormData bus = new DyFormData();
			bus.setFormcode(app.getFormcode());
			bus.setFatherlsh("1");
			try {
				list = DyEntry.iv().queryData(bus, 0,1000,
						" ORDER BY Timex desc ");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List list1=new ArrayList();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				DyFormData dydata = (DyFormData) iterator.next();
				Map map = new HashMap();

				Date dt = null;
				try {
					dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
							.parse(dydata.getTimex());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					try {
						dt = new SimpleDateFormat("yyyy-MM-dd hh")
						.parse(dydata.getTimex());
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				String newStr = new SimpleDateFormat("yyyy-MM-dd").format(dt);

				map.put("time", newStr);
				map.put("name", dydata.getColumn5());
				map.put("url", rmiobj.getWEBSER_APPFRAME()
						+ "/frame.do?method=onEditViewMain&naturalname="
						+ upobj.getNaturalname() + "&query=look&lsh="
						+ dydata.getLsh() + "&readonly=true");
				//map.put("userid", dydata.getParticipant());
				list1.add(map);
			}
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(JSONArray.fromObject(list1).toString());
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
