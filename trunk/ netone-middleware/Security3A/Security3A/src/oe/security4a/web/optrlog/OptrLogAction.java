package oe.security4a.web.optrlog;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.orm.Ormer;

import oe.frame.web.form.RequestParamMap;
import oe.frame.web.form.RequestUtil;
import oe.frame.web.page.PageInfo;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.seucore.obj.db.UmsOperationLog;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class OptrLogAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RequestParamMap reqmap = RequestUtil.setParamMapToRequest(request);
		UmsOperationLog uolog = new UmsOperationLog();
		StringBuffer condi = new StringBuffer();
		String userid = reqmap.getParameter("userid");
		String beginTime = reqmap.getParameter("beginTime");
		String endTime = reqmap.getParameter("endTime");
		String operationid = reqmap.getParameter("operationid");
		String resultinfo = reqmap.getParameter("resultinfo");
		
		Map<String, String> map = new HashMap<String, String>();
		if (StringUtils.isNotEmpty(userid)) {
			map.put("userid", "like");
			uolog.setUserid("%" + userid + "%");
		}
		if (StringUtils.isNotEmpty(operationid)) {
			map.put("operationid", "like");
			uolog.setOperationid("%" + operationid + "%");
		}
		if (StringUtils.isNotEmpty(resultinfo)) {
			uolog.setResultinfo(resultinfo);
		}
		// if (beginTime != null && !beginTime.equals("")) {
		// condi.append(" and ");
		// condi.append(" operatetime>=to_date('" + beginTime
		// + "','yyyy-mm-dd hh24:mi:ss')");
		// }
		if (beginTime != null && !beginTime.equals("")) {
			condi.append(" and ");
			condi.append(" operatetime >= '" + beginTime + "'");
		}
		if (endTime != null && !endTime.equals("")) {
			condi.append(" and ");
			condi.append(" operatetime <= '" + endTime + "'");
		}
		condi.append(" order by operatetime desc ");

		CupmRmi cupm = null;
		try {
			cupm = (CupmRmi) RmiEntry.iv("cupm");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// ´¦Àí·ÖÒ³
		PageInfo pginfo;
		if (PageInfo.isPageEvent(request)) {
			pginfo = new PageInfo(request, "optrlog");
		} else {
			long num = 0;
			try {
				num = cupm.logsNumber(uolog, map, condi.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pginfo = new PageInfo((int) num, request, "optrlog");
			pginfo.setNowpage(1);
		}

		List objlist = null;
		try {
			objlist = cupm.logList(uolog, map, condi.toString(), pginfo
					.getPageStartIndex(), pginfo.getPageEndIndex() + 1
					- pginfo.getPageStartIndex());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("rslist", objlist);
		return mapping.findForward("index");
	}

}
