package oe.teach.web.turnpage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.orm.OrmerEntry;
import oe.teach.mid.buss.OecStuDao;
import oe.teach.mid.buss.OecStudent;


import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class UpdateAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		OecStuDao bussdao = (OecStuDao) OrmerEntry.fetchDAO("bussdao");
		// ope的值有两种 1:view ,2:done
		String ope = request.getParameter("ope");
		if ("done".equals(ope)) {
			OecStudent oecstu = new OecStudent();
			UtilMap.toMap(oecstu, form);
			bussdao.update(oecstu);
		} else {
			WebForm webForm = (WebForm) form;
			OecStudent oecStu = bussdao.load(webForm.getStuid());
			UtilMap.toMap(webForm, oecStu);
		}
		return mapping.getInputForward();
	}

}
