//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.bi.etl.wizard.summarydata;

/**
 * 同步
 */
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.bi.etl.wizard.summarydata.util.SumSyn;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class SummaryDataSynAction extends Action {

	// --------------------------------------------------------- Instance
	// Variables
	static Log log = LogFactory.getLog(SummaryDataSynAction.class);

	// --------------------------------------------------------- Methods

	/**
	 * Method execute
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		SummaryDataForm so = (SummaryDataForm) form;
		request.setAttribute("so", so);
		// 跳转页面
		String task = request.getParameter("task");
		if ("Synbefore".equals(task)) {
			if (StringUtils.isNotEmpty(request.getParameter("chkid"))) {
				so.setChkid(request.getParameter("chkid"));
			}
			return mapping.findForward("Synbefore");
		} else if ("Synbefore2".equals(task)) {
			return mapping.findForward("Synbefore2");
			// 进入Syn.jsp
		} else if ("Syn".equals(task)) {
			SumSyn.main(so, request);
			return mapping.findForward("Syn");
		}
		return null;
	}

}
