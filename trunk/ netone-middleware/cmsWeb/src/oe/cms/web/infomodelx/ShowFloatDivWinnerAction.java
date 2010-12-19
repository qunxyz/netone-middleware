//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.cms.web.infomodelx;

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

import oe.frame.orm.Ormer;
import oe.frame.orm.OrmerEntry;


import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import oe.cms.CmsEntry;
import oe.cms.cfg.CellInfo;
import oe.cms.cfg.TCmsInfocell;
import oe.cms.cfg.TCmsInfomodel;
import oe.cms.dao.infomodel.ModelDao;
import oe.cms.dao.infomodel.RichLevel;

/**
 * 显示最终的Web界面
 */
public class ShowFloatDivWinnerAction extends Action {

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

		ModelDao modeldao = (ModelDao) CmsEntry.fetchDao("modelDao");

		TCmsInfomodel usermodel = modeldao.fetchWinner();
		if (usermodel == null) {
			usermodel = new TCmsInfomodel();
		}

		creaeViewLayout(request, usermodel);
		if (usermodel.getExtendattribute() == null
				|| usermodel.getExtendattribute().equals("")) {
			usermodel.setExtendattribute("01");
		}

		request.setAttribute("model", usermodel);

		winnerinfo(request, usermodel);
		return mapping.findForward("showWinner");

	}

	private void winnerinfo(HttpServletRequest request, TCmsInfomodel usermodel) {

		String levelis = usermodel.getLevels();

		String modelidConf = usermodel.getModelid().toString();
		String userid = usermodel.getParticipant();

		String levelname = RichLevel._LEVEL_WINNER_NAME
				+ usermodel.getWintime();
		ModelDao modaldao = (ModelDao) CmsEntry.fetchDao("modelDao");
		String userInfo = "<font color='red' size='2'><strong>本届 " + levelname +

		"&nbsp;" + usermodel.getUserid() + "&nbsp;&nbsp; [支持率:"
				+ modaldao.fetchHitNum(levelis, modelidConf)
				+ "点]</strong></font>";

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
				addGroupToList(group, boxlist);
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
		List list = ormer.fetchQuerister().queryObjects(cinfo,null);
		return list;
	}

	private void addGroupToList(CellInfo group, ArrayList list) {
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
			list.add(group);
		}
	}

	private TCmsInfomodel getUserInfoModel(HttpServletRequest req) {
		Ormer ormer = OrmerEntry.fetchOrmer();

		TCmsInfomodel queryicm = new TCmsInfomodel();
		// queryicm.setUserid(userid);
		List list = ormer.fetchQuerister().queryObjects(queryicm,null);
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
