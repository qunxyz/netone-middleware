//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.bi.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.bi.view.obj.ViewModel;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;



/** 
 * MyEclipse Struts
 * Creation date: 07-14-2006
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class ExcelAction extends Action {

	// --------------------------------------------------------- Instance Variables

	// --------------------------------------------------------- Methods

	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {
		
		
		ViewModel vm = (ViewModel) request.getSession().getAttribute("viewModelNext");
		
		ReportViewHelper.getInstance().DataToExcelFile(vm, request,
				response, this.getServlet().getServletConfig());
		
		return null;
	}

}

