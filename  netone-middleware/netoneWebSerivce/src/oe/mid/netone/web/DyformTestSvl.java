package oe.mid.netone.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import oe.security3a.seucore.obj.db.UmsProtectedobject;

import com.jl.common.app.impl2.AnalysisAppFirst;
import com.jl.common.app.impl2.AppFirst;
import com.jl.common.dyform.DyEntry;
import com.jl.common.dyform.DyFormData;
import com.jl.common.netone.UmsProtecte;

public class DyformTestSvl extends HttpServlet {

	/**
	 * xuwei(2012-5-4)
	 * 手机上传经纬度，同时添加一既记录时间
	 */
	public DyformTestSvl() {
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
		String id = request.getParameter("id");
		String longitude = request.getParameter("longitude");
		String latitude = request.getParameter("latitude");
		String userid = request.getParameter("userid");
		String lsh = null;
		UmsProtecte up = new UmsProtecte();
		UmsProtectedobject upobj = up.loadUmsProtecteID(id);
		if (StringUtils.isNotEmpty(upobj.getExtendattribute())) {
			AnalysisAppFirst appFirst = new AnalysisAppFirst();
			AppFirst app = appFirst.readXML(upobj.getExtendattribute());

			DyFormData bus = new DyFormData();
			bus.setFormcode(app.getFormcode());
			bus.setParticipant("1");
			bus.setFatherlsh("1");
			bus.setParticipant(userid);
			bus.setColumn3(longitude);
			bus.setColumn4(latitude);
			try {
				lsh = DyEntry.iv().addData(app.getFormcode(), bus);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Map map=new HashMap();
		map.put("lsh", lsh);
		response.getWriter().print(JSONObject.fromObject(map).toString());
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
