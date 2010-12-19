package oe.cms.web.action.recordtip;

/**
 * 
 *  @author add by liuzl  2006-08-03
 *  
 *  生成查看界面。
 */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.orm.Ormer;
import oe.frame.orm.OrmerEntry;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import oe.cms.cfg.TCmsRecordtip;

public class ViewRecordTip extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		 String recordTipid = request.getParameter("tipid");
		 Ormer orm = OrmerEntry.fetchOrmer();
		 TCmsRecordtip list = (TCmsRecordtip)orm.fetchQuerister().loadObject(TCmsRecordtip.class, new Long(recordTipid));
	     request.setAttribute("getRecordtip", list);
		 return mapping.findForward("RecordTip_View");
	}
}
