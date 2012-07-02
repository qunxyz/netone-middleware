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

import com.jl.common.app.AppEntry;
import com.jl.common.app.impl2.AnalysisAppFirst;
import com.jl.common.app.impl2.AppFirst;
import com.jl.common.dyform.DyEntry;
import com.jl.common.dyform.DyFormData;
import com.jl.common.netone.UmsProtecte;

public class QuerySvl extends HttpServlet {

	/**
	 * xu wei  装载字段的服务
	 * 
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

		String appname = request.getParameter("appname");
		String parentId = request.getParameter("parentId");
		String ext = request.getParameter("ext");
		String lsh = request.getParameter("lsh");

		if (StringUtils.isEmpty(parentId)) {
			parentId = "1";
		}

		String formcode=null;
		try {
			formcode = AppEntry.iv().loadApp(appname).getDyformCode_();
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
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
				Map map = new HashMap();
				map.put("fatherlsh", dyFormData.getFatherlsh());
				map.put("formcode", dyFormData.getFormcode());
				map.put("participant", dyFormData.getParticipant());
				map.put("lsh", dyFormData.getLsh());
				map.put("created", dyFormData.getCreated());
				for (Iterator iterator2 = listmame.iterator(); iterator2
						.hasNext();) {
					TCsColumn tCsColumn = (TCsColumn) iterator2.next();
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column3")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn3())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn3());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column4")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn4())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn4());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column5")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn5())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn5());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column6")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn6())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn6());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column7")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn7())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn7());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column8")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn8())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn8());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column9")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn9())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn9());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column10")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn10())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn10());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column11")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn11())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn11());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column12")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn12())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn12());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column13")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn13())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn13());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column14")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn14())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn14());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column15")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn15())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn15());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column16")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn16())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn16());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column17")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn17())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn17());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column18")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn18())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn18());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column19")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn19())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn19());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column20")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn20())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn20());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column21")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn21())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn21());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column22")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn22())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn22());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column23")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn23())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn23());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column24")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn24())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn24());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column25")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn25())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn25());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column26")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn26())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn26());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column27")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn27())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn27());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column28")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn28())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn28());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column29")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn29())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn29());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column30")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn30())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn30());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column31")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn31())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn31());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column32")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn32())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn32());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column33")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn33())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn33());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column34")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn34())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn34());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column35")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn35())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn35());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column36")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn36())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn36());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column37")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn37())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn37());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column38")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn38())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn38());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column39")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn39())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn39());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column40")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn40())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn40());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column41")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn41())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn41());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column42")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn42())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn42());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column43")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn43())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn43());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column44")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn44())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn44());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column45")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn45())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn45());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column46")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn46())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn46());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column47")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn47())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn47());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column48")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn48())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn48());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column49")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn49())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn49());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
					}
					if (tCsColumn.getColumncode().toLowerCase().equals(
							"column50")) {
						if (StringUtils.isNotEmpty(dyFormData.getColumn50())) {
							map.put(tCsColumn.getColumncode(), dyFormData
									.getColumn50());
						} else {
							map.put(tCsColumn.getColumncode(), "");
						}
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
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
