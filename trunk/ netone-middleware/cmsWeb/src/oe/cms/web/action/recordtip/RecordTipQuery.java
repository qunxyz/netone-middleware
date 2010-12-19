package oe.cms.web.action.recordtip;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.orm.Ormer;
import oe.frame.orm.OrmerEntry;
import oe.frame.web.page.PageInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import oe.cms.cfg.TCmsRecordtip;

public class RecordTipQuery extends Action {
	/**
	 * Method execute
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	Log log = LogFactory.getLog(RecordTipQuery.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RecordTipForm recordTipForm = (RecordTipForm) form;
		Ormer orm = OrmerEntry.fetchOrmer();
		TCmsRecordtip tcmsRec = new TCmsRecordtip();
		String tipid = request.getParameter("tipid");
		if (request.getParameter("optype") != null
				&& request.getParameter("optype").equals("del")) {
			try {
				TCmsRecordtip tmpTcmstip = (TCmsRecordtip) orm.fetchQuerister()
						.loadObject(TCmsRecordtip.class, new Long(tipid));
				orm.fetchSerializer().drop(tmpTcmstip);
				request.setAttribute("delRecord", "RecordTip信息删除成功");
			} catch (Exception e) {
				request.setAttribute("delRecord", e.getMessage());
			}
		}
		// 初始化分页信息

		String sqlid = recordTipForm.getSqlid();
		String recordid = recordTipForm.getRecordid();
		String writer = recordTipForm.getWriter();
		Date created = recordTipForm.getCreated();
		String sql = null;
		if (recordid != null) {
			tcmsRec.setRecordid(recordid);
		}
		if (sqlid != null) {
			tcmsRec.setCellid(sqlid);
		}
		if (writer != null) {
			tcmsRec.setWriter(writer);
		}
		if (created != null) {
			sql = " and to_char(created,'YYYY-MM-DD') = '"
					+ created.toString().substring(0, 10) + "'";
		}
		PageInfo pginfo = null;
		if (!PageInfo.isPageEvent(request)) {
			long size = orm.fetchQuerister().queryObjectsNumber(tcmsRec, null);
			pginfo = new PageInfo((int) size, request, "RecordTipId");
		} else {
			pginfo = new PageInfo(request, "RecordTipId");
		}
		int beginindex = pginfo.getPageStartIndex();
		int endindex = pginfo.getPageItemCount();
		List list = orm.fetchQuerister().queryObjects(tcmsRec, null,
				beginindex, endindex, sql);
		request.setAttribute("listRecordtip", list);
		return mapping.findForward("RecordTip_View");
	}
}
