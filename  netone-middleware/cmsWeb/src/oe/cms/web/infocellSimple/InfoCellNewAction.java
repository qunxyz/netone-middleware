//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.cms.web.infocellSimple;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.cms.CmsEntry;
import oe.cms.cfg.CellInfo;
import oe.cms.cfg.TCmsInfocell;
import oe.cms.dao.infocell.InfoCellDao;
import oe.cms.web.infocell.InfoCellForm;
import oe.security3a.sso.Security;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Wang ting-jie 2006-08-09
 */
public class InfoCellNewAction extends Action {

	// 新增资讯元
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) {
		Security ser = new Security(request);
		InfoCellForm form = (InfoCellForm) actionform;

		String newFlag = request.getParameter("newFlag");

		InfoCellDao infocellDao = (InfoCellDao) CmsEntry
				.fetchDao("infocellDao");
		if ("new".equals(newFlag)) { // 新增资讯元
			try {

				TCmsInfocell cmsinfocell = new TCmsInfocell();

				cmsinfocell.setCellname(form.getCellname());
				// cmsinfocell.setBody(form.getBody());
				cmsinfocell.setExtendattribute(form.getExtendattribute());
				cmsinfocell.setBelongto(form.getBelongto());
				cmsinfocell.setParticipant(ser.getUserLoginName());

				if (CellInfo._IN_TIME.equals(form.getIntime())) {
					cmsinfocell.setIntime(CellInfo._IN_TIME);
				}

				infocellDao.create(cmsinfocell, CellInfo._JPP_HEAD, form
						.getBody());

				request.setAttribute("createFlag", "true");

				form.setCellid(cmsinfocell.getCellid());
				if (CellInfo._IN_TIME.equals(form.getIntime())) {
					form.setIntime("on");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return mapping.findForward("celleditSimple");

		}
		form.setIntime("on");
		form.setBelongto(ser.getUserLoginName());
		return mapping.findForward("cellnewSimple");

	}
}
