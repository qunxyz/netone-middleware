//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.cms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.cms.CmsEntry;
import oe.cms.cfg.TCmsInfomodel;
import oe.cms.dao.infomodel.ModelDao;
import oe.frame.orm.OrmerEntry;
import oe.frame.orm.util.IdServer;
import oe.security3a.sso.Security;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 空间模板确定
 * 
 * @author chen.jia.xun(Robanco) 2006-08-18
 * 
 */

public class TemplateSelectDoneAction extends Action {

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
		String modeltemplate = request.getParameter("modeltemplate");
		if (modeltemplate != null) {

			TCmsInfomodel ii = (TCmsInfomodel) OrmerEntry.fetchOrmer()
					.fetchQuerister().loadObject(TCmsInfomodel.class,
							new Long(modeltemplate));

			Security ser = new Security(request);
			String idx = IdServer.xnumID();
			ii.setNaturalname(ser.getUserLoginName() + idx);
			ii.setModelname(ser.getUserName() + idx);

			ModelDao modelDao = (ModelDao) CmsEntry.fetchDao("modelDao");

			String path = request.getParameter("pagepath");

			modelDao.create(ii, path);

			return mapping.getInputForward();
		}
		return null;
	}
}
