//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.cms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class TopAction extends Action {

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
		// List topModelList = new ArrayList();
		// List list = CmsEntry.fetchOrmer().fetchQuerister().queryObjects(
		// new TCmsInfomodel());
		// TCmsInfomodel model = new TCmsInfomodel();
		// for (Iterator itr = list.iterator(); itr.hasNext();) {
		// model = (TCmsInfomodel) itr.next();
		// LabelValueBean lv = new LabelValueBean(model.getModelname(), String
		// .valueOf(model.getModelid()));
		// topModelList.add(lv);
		// }
		// request.setAttribute("topModelList", topModelList);

		// Security ser = new Security(request);
		// if (!ser.checkOnLine()) {
		// WebTip.htmlInfoLogin(response);
		// return null;
		// } else if (!ser.checkHead()) {
		// WebTip.htmlInfoNoAdmin(response);
		// return null;
		// } else {
		// request.setAttribute("nologin", "2");
		return mapping.getInputForward();
		// }
	}
}
