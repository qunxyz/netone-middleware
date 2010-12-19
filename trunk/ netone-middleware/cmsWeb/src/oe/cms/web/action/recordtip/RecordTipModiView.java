package oe.cms.web.action.recordtip;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.cms.cfg.TCmsRecordtip;
import oe.frame.orm.Ormer;
import oe.frame.orm.OrmerEntry;
import oe.security3a.sso.Security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class RecordTipModiView extends Action {
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
		Security ser = new Security(request);
		// if (!ser.checkAdmin()) {
		// request.setAttribute("nologin", "1");
		// return mapping.findForward("RecordTip_Modi");
		// }
		String sqlid = request.getParameter("sqlid");
		String recordid = request.getParameter("recordid");
		String valuedo = (String) request.getParameter("valuedo");

		Ormer orm = OrmerEntry.fetchOrmer();
		TCmsRecordtip tcmsTipTemp = new TCmsRecordtip();
		tcmsTipTemp.setCellid(sqlid);
		tcmsTipTemp.setRecordid(recordid);
		List list = orm.fetchQuerister().queryObjects(tcmsTipTemp, null);
		request.setAttribute("nologin", "2");
		TCmsRecordtip tmpRec = new TCmsRecordtip();
		if (sqlid != null && recordid != null) {
			if (list == null || list.size() < 1) {
				tmpRec.setRecordid(recordid);
				tmpRec.setCellid(sqlid);
				tmpRec.setTipinfo("");
				tmpRec.setWriter(ser.getUserName());
				Date created = new Date(System.currentTimeMillis());
				tmpRec.setCreated(created);
				orm.fetchSerializer().create(tmpRec);
			} else {
				tmpRec = (TCmsRecordtip) list.get(0);
			}
			String username = null;
			if (!ser.checkOnLine()) {
				username = "游客";
			} else {
				username = ser.getUserName();
			}

			String date = (new Timestamp(System.currentTimeMillis()))
					.toString().substring(0, 19);
			String tipInfo = tmpRec.getTipinfo() + "\n\r<br>###作者:" + username
					+ " 于:" + date + " 写道:\n";
			request.setAttribute("tipinfo", tipInfo);
			request.setAttribute("tipid", tmpRec.getTipid());
			request.setAttribute("cellid", sqlid);

		}

		if ("1".equals(valuedo)) {
			return mapping.findForward("RecordTip_Modi2");
		}
		return mapping.findForward("RecordTip_Modi");
	}
}
