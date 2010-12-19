//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.cms.web.infomodelx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.cms.CmsEntry;
import oe.cms.cfg.CellInfo;
import oe.cms.cfg.TCmsInfocell;
import oe.cms.cfg.TCmsInfomodel;
import oe.cms.dao.infomodel.RichLevel;
import oe.cms.runtime.HtmlStreamHandler;
import oe.frame.orm.Ormer;
import oe.frame.orm.OrmerEntry;
import oe.security3a.sso.Security;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 显示最终的Web界面
 */
public class ShowFloatDivAction extends Action {

	/**
	 * Method execute
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=GBK");

		TCmsInfomodel usermodel = null;

		String modelid = request.getParameter("modelid");

		if (modelid == null) {// 畅想空间登陆,不使用Modelid,通过登陆信息获得末Modelid
			Security ser = new Security(request);
			Ormer ormer = OrmerEntry.fetchOrmer();
			TCmsInfomodel usermodelX = new TCmsInfomodel();
			usermodelX.setParticipant(ser.getUserLoginName());
			List list = ormer.fetchQuerister().queryObjects(usermodelX, null);
			if (list != null && list.size() > 0) {
				usermodel = (TCmsInfomodel) list.get(0);
			} else {
				try {
					response
							.getWriter()
							.print(
									"<script>window.open('/cmsWeb/servlet/BlogSvl');</script>");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}

		} else {// 从管理空间登陆,从列表中选择畅想空间
			if (modelid != null && !modelid.equals("")) {
				Ormer ormer = OrmerEntry.fetchOrmer();
				usermodel = (TCmsInfomodel) ormer.fetchQuerister().loadObject(
						TCmsInfomodel.class, new Long(modelid));
			} else {
				usermodel = getUserInfoModel(request);
			}
		}
		creaeViewLayout(request, usermodel);
		if (usermodel.getExtendattribute() == null
				|| usermodel.getExtendattribute().equals("")) {
			usermodel.setExtendattribute("01");
		}

		request.setAttribute("model", usermodel);

		if (request.getParameter("view") == null) {// 展现设计界面
			Security ser = new Security(request);
			readyForDesign(request, usermodel, ser);
			String opr = request.getParameter("opr");
			return mapping.findForward(opr);
		} else {
			String date = (String) request.getParameter("date");
			if (date != null) {
				request.setAttribute("dateuse", date);
				return mapping.findForward("showHistory");
			} else {
				return mapping.findForward("show");
			}
		}
	}

	private void readyForDesign(HttpServletRequest request,
			TCmsInfomodel usermodel, Security ser) {

		String levelis = usermodel.getLevels();
		String levelname = "";
		String modelidConf = usermodel.getModelid().toString();
		if (RichLevel._LEVEL_A.equals(levelis)) {
			levelname = RichLevel._LEVEL_A_NAME;
		} else if (RichLevel._LEVEL_B.equals(levelis)) {
			levelname = RichLevel._LEVEL_B_NAME;
		} else {
			levelname = RichLevel._LEVEL_C_NAME;
		}
		String wintime = usermodel.getWintime() == null ? "" : usermodel
				.getWintime();
		levelname = levelname + wintime;


		String username = ser.getUserName();
		// String userInfo = username + " IP:" + request.getRemoteHost()
		// + " [水平级别:" + levelname + "] [当前的排名:"
		// + modaldao.fetchOrderIndex(levelis, modelidConf) + "] [支持率:"
		// + modaldao.fetchHitNum(levelis, modelidConf) + "点]";
		String userInfo = username + "  IP:" + request.getRemoteHost();

		request.setAttribute("userinfo", userInfo);
	}

	private void creaeViewLayout(HttpServletRequest request,
			TCmsInfomodel usermodel) {
		Map listmap = new TreeMap();
		List widthlist = null;
		if (usermodel.getExtendattribute() == null
				|| usermodel.getExtendattribute().equals("")) {
			widthlist = new ArrayList();
		} else {
			widthlist = Arrays
					.asList(usermodel.getExtendattribute().split(","));
		}
		request.setAttribute("widthlist", widthlist);
		// 初始化listmap
		for (int i = 0; i < widthlist.size(); i++) {
			listmap.put("boxdiv" + (i + 1), new ArrayList());
		}

		HashSet groupset = new HashSet();
		try {
			List grouplist = usermodel.fetchInfocell();

			Iterator iter = grouplist.iterator();
			while (iter.hasNext()) {
				CellInfo group = (CellInfo) iter.next();
				groupset.add(group.getInfoCellid().trim());
				ArrayList boxlist = (ArrayList) listmap.get("boxdiv"
						+ group.getXoffset());
				addGroupToList(group, boxlist, request);
			}

			request.setAttribute("listmap", listmap);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HashMap idnamemap = new HashMap();

		List alllist = getAllCells();
		if (groupset.size() != 0) {
			Iterator iter = alllist.iterator();
			while (iter.hasNext()) {
				TCmsInfocell cell = (TCmsInfocell) iter.next();
				idnamemap.put(cell.getCellid().toString(), cell.getCellname());
				if (groupset.contains(cell.getCellid().toString())) {
					iter.remove();
				}
			}
		}

		request.setAttribute("idnamemap", idnamemap);
		request.setAttribute("noshowgroup", alllist);
	}

	private List getAllCells() {
		Ormer ormer = OrmerEntry.fetchOrmer();
		TCmsInfocell cinfo = new TCmsInfocell();
		List list = ormer.fetchQuerister().queryObjects(cinfo, null);
		return list;
	}

	private void addGroupToList(CellInfo group, ArrayList list,
			HttpServletRequest rqe) {
		int y = Integer.parseInt(group.getYoffset());
		boolean added = false;
		for (int i = 0; i < list.size(); i++) {
			CellInfo gi = (CellInfo) list.get(i);
			int listy = Integer.parseInt(gi.getYoffset());
			if (y < listy) {
				list.add(i, group);
				added = true;
				break;
			}
		}
		if (!added) {
			addInfo(group, rqe);
			list.add(group);
		}
	}

	private void addInfo(CellInfo group, HttpServletRequest rqe) {


		try {


			HtmlStreamHandler htmlStreamHandler = (HtmlStreamHandler) CmsEntry
					.fetchBean("htmlStreamHandler");
			String str = htmlStreamHandler.toPortal(group.getInfoCellid(), "1", rqe);

			group.setExtendattribute(str);
		} catch (Exception e) {
			System.err.println("该咨讯元已删除");
		}

	}

	private TCmsInfomodel getUserInfoModel(HttpServletRequest req) {
		Ormer ormer = OrmerEntry.fetchOrmer();

		TCmsInfomodel queryicm = new TCmsInfomodel();
		// queryicm.setUserid(userid);
		List list = ormer.fetchQuerister().queryObjects(queryicm, null);
		TCmsInfomodel usermodel = null;
		if (list.size() > 0) {
			usermodel = (TCmsInfomodel) list.get(0);
		} else {
			TCmsInfomodel defmodel = (TCmsInfomodel) ormer.fetchQuerister()
					.loadObject(TCmsInfomodel.class, new Long(1));

			usermodel = new TCmsInfomodel();
			usermodel.setInfoxml(defmodel.getInfoxml());

			usermodel.setAccessmode("1");
			ormer.fetchSerializer().create(usermodel);
		}
		return usermodel;
	}
}
