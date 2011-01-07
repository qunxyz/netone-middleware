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

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import oe.cms.cfg.CellInfo;
import oe.cms.cfg.TCmsInfocell;
import oe.cms.cfg.TCmsInfomodel;

/**
 * 应用界面,主要用于发布门户信息, 该应用中其实还可以根据参数或者权限来控制每个界面的可见与否,或者能否编辑....
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class ShowFloatDivActionPortal extends Action {

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

		String id = request.getParameter("id");
		String mode = request.getParameter("portalmode");
		TCmsInfomodel usermodel = null;
		try {
			usermodel = (TCmsInfomodel) OrmerEntry.fetchOrmer()
					.fetchQuerister().loadObject(TCmsInfomodel.class,
							new Long(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (usermodel != null) {
			String sytleinfox = usermodel.getUserid();
			if (sytleinfox != null || !sytleinfox.equals("")) {
				String styleinfo = StringUtils.substringBetween(sytleinfox,
						"[", "]");
				usermodel.setStyleinfo(styleinfo);
			}
			creaeViewLayout(request, usermodel);
			if (usermodel.getExtendattribute() == null
					|| usermodel.getExtendattribute().equals("")) {
				usermodel.setExtendattribute("01");
			}
		}

		String description = usermodel.getDescription();
		String layout = StringUtils.substringBetween(description, "$[", "]");

		String modelWidth = "1024";
		String modelHeight = "1000";
		if (layout != null && layout.length() > 0) {
			String width = StringUtils.substringBetween(layout, "width:", ";");
			String height = StringUtils
					.substringBetween(layout, "height:", ";");
			if (width != null && width.length() > 0) {
				modelWidth = width;
			}
			if (height != null && height.length() > 0) {
				modelHeight = height;
			}
		}

		request.setAttribute("modelWidth", modelWidth);
		request.setAttribute("modelHeight", modelHeight);
		request.setAttribute("model", usermodel);
		request.setAttribute("portalmode", mode);
		request.setAttribute("modelid", id);
		
		return mapping.findForward("design");

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
		List list = ormer.fetchQuerister().queryObjects(cinfo, null);
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

}
