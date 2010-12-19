package oe.cms.web.action.recordtip;

/**
 * 
 *  @author add by liuzl  2006-08-03
 *  
 *  生成查询列表并放入list输出给界面。
 */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import oe.frame.orm.Ormer;
import oe.frame.orm.OrmerEntry;

import oe.cms.cfg.TCmsRecordtip;
import oe.cms.runtime.XHtmlCachepool;

public class RecordTipModi extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String tipinfo = (String) request.getParameter("tipinfo");
		String tipid = (String) request.getParameter("tipid");
		Ormer orm = OrmerEntry.fetchOrmer();

		TCmsRecordtip tcmsTipTemp = (TCmsRecordtip) orm.fetchQuerister()
				.loadObject(TCmsRecordtip.class, tipid);
		tcmsTipTemp.setTipinfo(tipinfo);
	
		request.setAttribute("tipinfo", tipinfo);
		request.setAttribute("tipid", tipid);
		orm.fetchSerializer().update(tcmsTipTemp);
		
		XHtmlCachepool.removeInfoCache(tcmsTipTemp.getCellid());
		String valuedo=(String)request.getParameter("valuedo");
		request.setAttribute("cellid",tcmsTipTemp.getCellid());
		if("1".equals(valuedo)){
			return mapping.findForward("RecordTip_Modi2");
		}
		return mapping.findForward("RecordTip_Modi");
	}
}
