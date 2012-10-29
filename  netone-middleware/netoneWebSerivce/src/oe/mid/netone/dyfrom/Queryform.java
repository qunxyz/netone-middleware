package oe.mid.netone.dyfrom;

import java.io.IOException;
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

import org.apache.commons.lang.StringUtils;

import com.jl.common.app.AppEntry;
import com.jl.common.app.impl2.AnalysisAppFirst;
import com.jl.common.app.impl2.AppFirst;
import com.jl.common.dyform.DyEntry;
import com.jl.common.dyform.DyFormColumn;
import com.jl.common.netone.UmsProtecte;

public class Queryform extends HttpServlet {
	/**
	 * Constructor of the object.
	 */
	public Queryform() {
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
		String formcode = null;
		String appname = request.getParameter("appname");
		List<DyFormColumn> listx=null;
		try {
			formcode = AppEntry.iv().loadApp(appname).getDyformCode_();
			listx=DyEntry.iv().fetchColumnList(formcode);
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		

		List list = new ArrayList();
		for (Iterator iterator = listx.iterator(); iterator.hasNext();) {
			DyFormColumn tCsColumn = (DyFormColumn) iterator.next();

			Map map = new HashMap();
			if (tCsColumn.getColumnid().toUpperCase().equals("BELONGX")
					|| tCsColumn.getColumnid().toUpperCase().equals("TIMEX")) {
			} else {
				if (StringUtils.isNotBlank(tCsColumn.getColumnid())) {
					map.put("columnid", tCsColumn.getColumnid());
				} else {
					map.put("columnid", "");
				}
				if (StringUtils.isNotBlank(tCsColumn.getMusk())) {
					map.put("musk", tCsColumn.getMusk());
				} else {
					map.put("musk", "");
				}
				if (StringUtils.isNotBlank(tCsColumn.getOpemode())) {
					map.put("opemode", tCsColumn.getOpemode());
				} else {
					map.put("opemode", "");
				}
				if (StringUtils.isNotBlank(tCsColumn.getColumname())) {
					map.put("columname", tCsColumn.getColumname());
				} else {
					map.put("columname", "");
				}
				if (StringUtils.isNotBlank(tCsColumn.getViewtype())) {
					map.put("viewtype", tCsColumn.getViewtype());
				} else {
					map.put("viewtype", "");
				}
				if (StringUtils.isNotBlank(tCsColumn.getValuelist())) {
					map.put("valuelist", tCsColumn.getValuelist());
				} else {
					map.put("valuelist", "");
				} 
				map.put("useable", tCsColumn.isUseable());
				list.add(map);
			}
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(JSONArray.fromObject(list).toString());
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
