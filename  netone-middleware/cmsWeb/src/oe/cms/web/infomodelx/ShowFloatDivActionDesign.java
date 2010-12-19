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
import oe.cms.dao.infomodel.RichLevel;
import oe.frame.orm.Ormer;
import oe.frame.orm.OrmerEntry;
import oe.frame.web.util.WebTip;
import oe.security3a.sso.Security;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * 显示最终的Web界面
 */
public class ShowFloatDivActionDesign extends Action {

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
		Security ser = new Security(request);
		if (!ser.checkOnLine()) {
			WebTip.tip(false);
			return null;
		}

		String userid = ser.getUserLoginName();
		String modelx = request.getParameter("model");
		if (!"ok".equals(modelx) && userid != null) {// 说明要强制使用该用户访问,那么需要检查登陆的信息与该用户是否一致,该功能主要用在页面导航中的网站管理中
			if (!userid.endsWith(ser.getUserLoginName())) {// 如果指明的用户不是
				// 登陆用户,那么禁止访问
				WebTip.htmlInfo("对不起您不是该网站的管理员", true, response);
				return null;
			}
		}
		if ("ok".equals(modelx)) {
			userid = request.getParameter("id");
		}

		ModelDao model = (ModelDao) CmsEntry.fetchDao("modelDao");
		TCmsInfomodel usermodel = model.userinfoModel(userid, modelx, request);

		creaeViewLayout(request, usermodel);
		if (usermodel.getExtendattribute() == null
				|| usermodel.getExtendattribute().equals("")) {
			usermodel.setExtendattribute("01");
		}
		request.setAttribute("model", usermodel);

		readyForDesign(request, usermodel);

		return mapping.findForward("design");

	}

	private void readyForDesign(HttpServletRequest request,
			TCmsInfomodel usermodel) {
		Security ser = new Security(request);
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
		ModelDao modaldao = (ModelDao) CmsEntry.fetchDao("modelDao");

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
