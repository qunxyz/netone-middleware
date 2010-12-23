//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.bi.etl.wizard.analysesubject;

/**
 * 流程创建与修改
 */
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.bi.etl.wizard.analysesubject.util.AnaUtil2;
import oe.bi.etl.wizard.analysesubject.util.AnaUtil5;
import oe.bi.wizard.WizardDao;
import oe.rmi.client.RmiEntry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class AnalyseSubjectAction extends Action {

	// --------------------------------------------------------- Instance
	// Variables
	static Log log = LogFactory.getLog(AnalyseSubjectAction.class);

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
		WizardDao wd = null;

		try {
			wd = (WizardDao) RmiEntry.iv("bihandle");
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (RemoteException e1) {
			e1.printStackTrace();
		} catch (NotBoundException e1) {
			e1.printStackTrace();
		}
		AnalyseSubjectForm fo = (AnalyseSubjectForm) form;
		request.setAttribute("fo", fo);
		// 跳转页面
		String task = request.getParameter("task");
		// 进入Zero.jsp
		if ("Zero".equals(task)) {
			fo.setPagepath(request.getParameter("pagepath"));
			return mapping.findForward("Zero");
			// 进入BeforeFirst.jsp
		} else if ("BeforeFirst".equals(task)) {
			String naturalname = fo.getNaturalname();
			boolean checkrs = false;
			try {
				checkrs = wd.checkExist(naturalname);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			if (checkrs) {
				return mapping.findForward("BeforeFirst");
			} else {
				request.setAttribute("error", "名称已存在!!!");
				return mapping.findForward("Zero");
			}
			// 进入First.jsp
		} else if ("First".equals(task)) {
			return mapping.findForward("First");
			// 进入Step.jsp
		} else if ("Step".equals(task)) {
			return mapping.findForward("Step");
			// 进入Step1.jsp
		} else if ("Step1".equals(task)) {
			return mapping.findForward("Step1");
			// 进入Step2.jsp
		} else if ("Step2".equals(task)) {
			AnaUtil2.main(fo, request);
			if ("1".equals(fo.getFiltertype())) {
				return mapping.findForward("Step2");
			} else if ("2".equals(fo.getFiltertype())) {
				return mapping.findForward("Step21");
			} else if ("3".equals(fo.getFiltertype())) {
				return mapping.findForward("Step22");
			}
		} else if ("Step3".equals(task)) {
			if ("1".equals(fo.getTimetype())) {
				return mapping.findForward("Step3");
			} else if ("2".equals(fo.getTimetype())) {
				return mapping.findForward("Step31");
			} else if ("3".equals(fo.getTimetype())) {
				return mapping.findForward("Step32");
			}
			// 进入Step4.jsp
		} else if ("Step4".equals(task)) {
			return mapping.findForward("Step4");
			// 进入Step5.jsp
		} else if ("Step5".equals(task)) {
			AnaUtil5.main(fo, request);
			return mapping.findForward("Step5");
			// 进入Done.jsp
		} else if ("Done".equals(task)) {
			AnaUtil2.main(fo, request);
			return mapping.findForward("Done");
			// 进入Done2.jsp
		} else if ("Done2".equals(task)) {
			return mapping.findForward("Done2");
			// 进入Done3.jsp
		} else if ("Done3".equals(task)) {
			return mapping.findForward("Done3");
		}
		return null;
	}
}
