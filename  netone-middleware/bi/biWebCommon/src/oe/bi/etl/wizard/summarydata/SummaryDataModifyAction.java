//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.bi.etl.wizard.summarydata;

/**
 * �޸�
 */
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.bi.etl.wizard.summarydata.util.SumModify;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class SummaryDataModifyAction extends Action {

	// --------------------------------------------------------- Instance
	// Variables
	static Log log = LogFactory.getLog(SummaryDataModifyAction.class);

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
		// ��תҳ��
		String task = request.getParameter("task");
		// ����MStep2.jsp�����޸�
		if ("Modify".equals(task)) {
			SumModify.main(so, request);
			return mapping.findForward("MStep2");
			// ִ���޸�
		} else if ("DoModify".equals(task)) {
			SumModify.main2(so, request);
			return mapping.findForward("MStep2");
		}
		return null;
	}

}
