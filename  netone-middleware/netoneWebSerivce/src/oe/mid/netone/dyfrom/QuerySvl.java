package oe.mid.netone.dyfrom;

import java.io.IOException;
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

public class QuerySvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public QuerySvl() {
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
		response.setCharacterEncoding("utf-8");
		String formcode =null;
		String appname=request.getParameter("appname");
		String parentId = request.getParameter("parentId");
        String ext=request.getParameter("ext");
        String lsh=request.getParameter("lsh");
        
		if (StringUtils.isEmpty(parentId)) {
			parentId = "1";
		}
		UmsProtecte up = new UmsProtecte();
		UmsProtectedobject upobj = up.loadUmsProtecteNaturalname(appname);
		if (StringUtils.isNotEmpty(upobj.getExtendattribute())) {
			AnalysisAppFirst appFirst = new AnalysisAppFirst();
			AppFirst app = appFirst.readXML(upobj.getExtendattribute());
			formcode=app.getFormcode();
		}
		
		DyFormData dydata = new DyFormData();
		dydata.setFormcode(formcode);
		dydata.setFatherlsh(parentId);
		dydata.setLsh(lsh);
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
		for (int i = 3; i < 50; i++) {
			String columnId = "column" + i;
			String value = request.getParameter(columnId);
			if (StringUtils.isNotEmpty(value)) {
				try {
					BeanUtils.setProperty(dydata, columnId, value);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		try {
			int index = DyEntry.iv().queryDataNum(dydata, "");
			list = DyEntry.iv().queryData(dydata, 0, index, ext);
			List listmame = dys.queryObjects(busForm);
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				DyFormData dyFormData = (DyFormData) iterator.next();
				Map map=new HashMap();
				map.put("fatherlsh", dyFormData.getFatherlsh());
				map.put("formcode", dyFormData.getFormcode());
				map.put("participant", dyFormData.getParticipant());
				map.put("lsh", dyFormData.getLsh());
				map.put("created",dyFormData.getCreated());
				for (Iterator iterator2 = listmame.iterator(); iterator2
						.hasNext();) {
					TCsColumn tCsColumn = (TCsColumn) iterator2.next();
					if(tCsColumn.getColumncode().toLowerCase().equals("column3")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn3());	
					}
					if(tCsColumn.getColumncode().toLowerCase().equals("column4")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn4());	
					}
					if(tCsColumn.getColumncode().toLowerCase().equals("column5")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn3());	
					}
					if(tCsColumn.getColumncode().toLowerCase().equals("column5")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn3());	
					}
					if(tCsColumn.getColumncode().toLowerCase().equals("column6")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn6());	
					}
					if(tCsColumn.getColumncode().toLowerCase().equals("column7")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn7());	
					}
					if(tCsColumn.getColumncode().toLowerCase().equals("column8")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn8());	
					}
					if(tCsColumn.getColumncode().toLowerCase().equals("column3")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn3());	
					}
					if(tCsColumn.getColumncode().toLowerCase().equals("column9")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn9());	
					}if(tCsColumn.getColumncode().toLowerCase().equals("column10")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn10());	
					}			
					if(tCsColumn.getColumncode().toLowerCase().equals("column11")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn11());	
					}
					if(tCsColumn.getColumncode().toLowerCase().equals("column12")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn12());	
					}
					if(tCsColumn.getColumncode().toLowerCase().equals("column13")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn13());	
					}
					if(tCsColumn.getColumncode().toLowerCase().equals("column14")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn14());	
					}
					if(tCsColumn.getColumncode().toLowerCase().equals("column3")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn3());	
					}
					if(tCsColumn.getColumncode().toLowerCase().equals("column15")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn15());	
					}
					if(tCsColumn.getColumncode().toLowerCase().equals("column16")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn16());	
					}
					if(tCsColumn.getColumncode().toLowerCase().equals("column17")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn17());	
					}
					if(tCsColumn.getColumncode().toLowerCase().equals("column18")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn18());	
					}
					if(tCsColumn.getColumncode().toLowerCase().equals("column19")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn19());	
					}
					if(tCsColumn.getColumncode().toLowerCase().equals("column20")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn20());	
					}
					if(tCsColumn.getColumncode().toLowerCase().equals("column21")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn21());	
					}
					if(tCsColumn.getColumncode().toLowerCase().equals("column22")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn22());	
					}
					if(tCsColumn.getColumncode().toLowerCase().equals("column23")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn23());	
					}if(tCsColumn.getColumncode().toLowerCase().equals("column24")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn24());	
					}if(tCsColumn.getColumncode().toLowerCase().equals("column25")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn25());	
					}if(tCsColumn.getColumncode().toLowerCase().equals("column26")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn26());	
					}if(tCsColumn.getColumncode().toLowerCase().equals("column27")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn27());	
					}if(tCsColumn.getColumncode().toLowerCase().equals("column28")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn28());	
					}if(tCsColumn.getColumncode().toLowerCase().equals("column29")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn29());	
					}if(tCsColumn.getColumncode().toLowerCase().equals("column30")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn30());	
					}if(tCsColumn.getColumncode().toLowerCase().equals("column31")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn31());	
					}if(tCsColumn.getColumncode().toLowerCase().equals("column32")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn32());	
					}if(tCsColumn.getColumncode().toLowerCase().equals("column33")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn33());	
					}if(tCsColumn.getColumncode().toLowerCase().equals("column34")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn34());	
					}if(tCsColumn.getColumncode().toLowerCase().equals("column35")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn35());	
					}if(tCsColumn.getColumncode().toLowerCase().equals("column36")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn36());	
					}if(tCsColumn.getColumncode().toLowerCase().equals("column37")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn37());	
					}if(tCsColumn.getColumncode().toLowerCase().equals("column38")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn38());	
					}if(tCsColumn.getColumncode().toLowerCase().equals("column39")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn39());	
					}if(tCsColumn.getColumncode().toLowerCase().equals("column40")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn40());	
					}if(tCsColumn.getColumncode().toLowerCase().equals("column41")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn41());	
					}if(tCsColumn.getColumncode().toLowerCase().equals("column42")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn42());	
					}if(tCsColumn.getColumncode().toLowerCase().equals("column43")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn43());	
					}if(tCsColumn.getColumncode().toLowerCase().equals("column44")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn44());	
					}if(tCsColumn.getColumncode().toLowerCase().equals("column45")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn45());	
					}if(tCsColumn.getColumncode().toLowerCase().equals("column46")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn46());	
					}if(tCsColumn.getColumncode().toLowerCase().equals("column47")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn47());	
					}if(tCsColumn.getColumncode().toLowerCase().equals("column48")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn48());	
					}if(tCsColumn.getColumncode().toLowerCase().equals("column49")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn49());	
					}if(tCsColumn.getColumncode().toLowerCase().equals("column50")){
						map.put(tCsColumn.getColumncode(), dyFormData.getColumn50());	
					}
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
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
