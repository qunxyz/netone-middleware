package oe.teach.web.turnpage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.orm.OrmerEntry;
import oe.frame.web.util.WebTip;
import oe.teach.mid.buss.OecStuDao;


import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class DeleteAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		OecStuDao bussdao = (OecStuDao) OrmerEntry.fetchDAO("bussdao");

		WebForm webForm = (WebForm) form;
		String[] ids = webForm.getStuid().split(",");
		bussdao.delete(ids);

		WebTip
				.htmlInfoOri(
						"<script>alert('É¾³ý³É¹¦');window.close();opener.search();</script>",
						response);
		return null;
	}
}
