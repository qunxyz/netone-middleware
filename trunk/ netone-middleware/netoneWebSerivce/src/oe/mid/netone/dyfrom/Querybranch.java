package oe.mid.netone.dyfrom;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import oe.cav.bean.logic.column.TCsColumn;
import oe.midware.dyform.service.DyFormDesignService;
import oe.rmi.client.RmiEntry;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.jl.common.app.impl2.AnalysisAppFirst;
import com.jl.common.app.impl2.AppFirst;
import com.jl.common.dyform.DyEntry;
import com.jl.common.dyform.DyFormData;
import com.jl.common.netone.UmsProtecte;

public class Querybranch extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public Querybranch() {
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
		response.setCharacterEncoding("utf-8");
		String formcode =null;
		String appname=request.getParameter("appname");
 
		UmsProtecte up = new UmsProtecte();
		UmsProtectedobject upobj = up.loadUmsProtecteNaturalname(appname);
		if (StringUtils.isNotEmpty(upobj.getExtendattribute())) {
			AnalysisAppFirst appFirst = new AnalysisAppFirst();
			AppFirst app = appFirst.readXML(upobj.getExtendattribute());
			formcode=app.getFormcode();
		}
		
		DyFormData dydata = new DyFormData();
		dydata.setFormcode(formcode);
		dydata.setFatherlsh("1");
		TCsColumn busForm = new TCsColumn();
		busForm.setFormcode(formcode);
		DyFormDesignService dys = null;
		try {
			dys = (DyFormDesignService) RmiEntry.iv("dydesign");
		} catch (NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
 
		List list = new ArrayList();
		List listx = new ArrayList();

		try {
			int index = DyEntry.iv().queryDataNum(dydata, "");
			list = DyEntry.iv().queryData(dydata, 0, index, "");
			List listmame = dys.queryObjects(busForm);
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				DyFormData dyFormData = (DyFormData) iterator.next();
				Map map=new HashMap();
				if(StringUtils.isNotEmpty(dyFormData.getColumn3())){
					map.put("latitude", dyFormData.getColumn3());
				}else{
					map.put("latitude", "");
				}
				if(StringUtils.isNotEmpty(dyFormData.getColumn4())){ 
					map.put("longitude", dyFormData.getColumn4());
					}else{
					map.put("longitude","");
					}
				if(StringUtils.isNotEmpty(dyFormData.getColumn5())){
					map.put("name", dyFormData.getColumn5());
				}else{
					map.put("name", "");
				}
				listx.add(map);
			}
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(JSONArray.fromObject(listx).toString());
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
		doGet(request,response);
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
