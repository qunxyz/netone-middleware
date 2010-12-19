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

import oe.cms.CmsEntry;
import oe.cms.cfg.CellInfo;
import oe.cms.cfg.TCmsInfocell;
import oe.cms.cfg.TCmsInfomodel;
import oe.cms.dao.infomodel.ModelDao;
import oe.cms.runtime.HtmlStreamHandler;
import oe.cms.runtime.XHtmlCachepool;
import oe.frame.orm.Ormer;
import oe.frame.orm.OrmerEntry;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 显示最终的Web界面
 */
public class ShowFloatDivActionView extends Action {

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

		String userid = request.getParameter("id");
		String modelx = request.getParameter("model");
		ModelDao model = (ModelDao) CmsEntry.fetchDao("modelDao");
		TCmsInfomodel usermodel = model.userinfoModel(userid, modelx, request);
		creaeViewLayout(request, usermodel);
		if (usermodel.getExtendattribute() == null
				|| usermodel.getExtendattribute().equals("")) {
			usermodel.setExtendattribute("01");
		}

		request.setAttribute("model", usermodel);
		request.setAttribute("title", usermodel.getModelname());
		request.setAttribute("description", usermodel.getDescription());

		return mapping.findForward("show");

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
				String boxid = "boxdiv" + group.getXoffset();
				List boxlist = (ArrayList) listmap.get(boxid);
				addGroupToList(group, boxlist, boxid, request);
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

	private void addGroupToList(CellInfo group, List list, String key,
			HttpServletRequest request) {
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
			addInfo(group, key, request);
			list.add(group);
		}
	}

	private void addInfo(CellInfo group, String key, HttpServletRequest request) {


		String htmlsrc = "";
		String groupid = group.getInfoCellid();
		if (groupid.indexOf(CellInfo._CUT_HEAD) == 0) {// 如果是捕鱼的信息,不能做缓存
			htmlsrc = "<script type=\"text/javascript\">addDiv(\"" + groupid
					+ "\",\"" + groupid + "\",\"" + key + "\");</script>";
		} else {
			try {

				HtmlStreamHandler htmlStreamHandler = (HtmlStreamHandler) CmsEntry
						.fetchBean("htmlStreamHandler");
				htmlsrc = htmlStreamHandler.toPortal(groupid, "2", request);

			} catch (Exception e) {
				System.err.println("该咨讯元已删除");
			}
		}
		group.setExtendattribute(htmlsrc);

	}

}
