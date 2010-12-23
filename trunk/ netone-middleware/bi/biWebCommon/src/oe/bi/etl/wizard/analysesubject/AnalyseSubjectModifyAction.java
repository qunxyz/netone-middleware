//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.bi.etl.wizard.analysesubject;

/**
 * 修改
 */
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.bi.etl.obj.ChoiceInfo;
import oe.bi.etl.obj.TargetElement;
import oe.bi.etl.wizard.analysesubject.util.AnaFinish1;
import oe.bi.etl.wizard.analysesubject.util.AnaFinish2;
import oe.bi.etl.wizard.analysesubject.util.AnaModify;
import oe.bi.wizard.WizardDao;
import oe.rmi.client.RmiEntry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class AnalyseSubjectModifyAction extends Action {

	// --------------------------------------------------------- Instance
	// Variables
	static Log log = LogFactory.getLog(AnalyseSubjectModifyAction.class);

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

		// 进入修改1页面
		if ("Modify".equals(task)) {
			AnaModify.main(wd, fo, request);
			return mapping.findForward("Finish");
			// 执行修改1,进入修改2页面
		} else if ("doFinish".equals(task)) {
			ChoiceInfo choiceinfo = null;
			List<TargetElement> list = new ArrayList<TargetElement>();
			AnaFinish1.main(wd, choiceinfo, list, fo, request);
			String lsh = fo.getLsh();
			// 进入修改2页面
			try {
				choiceinfo = wd.fromXml(lsh);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			if (!choiceinfo.isActive()) {
				request.setAttribute("do", "do");
				// 没有进入修改2页面,直接结束
				return mapping.findForward("Finish");
			}
			AnaFinish1.main2(wd, choiceinfo, list, fo, request);
			return mapping.findForward("Finish2");
			// 执行修改2
		} else if ("doFinish2".equals(task)) {
			AnaFinish2.main(wd, fo, request);
			return mapping.findForward("Finish2");
		}
		return null;
	}
}
