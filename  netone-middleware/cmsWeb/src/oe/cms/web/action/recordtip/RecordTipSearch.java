package oe.cms.web.action.recordtip;

/**
 * 
 *  @author add by liuzl  2006-08-03
 *  
 *  查询tip信息的界面。
 */
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class RecordTipSearch extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("RecordTip_Search");
	}

}
