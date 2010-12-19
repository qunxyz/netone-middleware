package oe.cav.web.workflow.monitor.process;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.cav.web.workflow.monitor.process.util.WfFormUtil;
import oe.frame.bus.workflow.RuntimeInfo;
import oe.midware.workflow.runtime.ormobj.TWfRelevantvar;
import oe.midware.workflow.service.WorkflowConsole;
import oe.midware.workflow.service.WorkflowView;
import oe.midware.workflow.xpdl.model.data.DataField;
import oe.rmi.client.RmiEntry;
import oe.security3a.sso.Security;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 相关数据处理
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 * 
 */
public class RDataAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String runtimeid = request.getParameter("runtimeid"); // 标志位
		String debug = request.getParameter("debug"); // 标志位
		WorkflowView wfview = null;
		WorkflowConsole console = null;

		try {
			wfview = (WorkflowView) RmiEntry.iv("wfview");
			console = (WorkflowConsole) RmiEntry.iv("wfhandle");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List list = null;
		DataField[] datafiled = null;
		try {
			list = wfview.fetchRelevantVar(runtimeid);
			String processid = wfview.loadRuntime(runtimeid).getProcessid();
			datafiled = wfview.loadProcess(processid).getDataField();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String ope = request.getParameter("ope"); // 标志位
		if ("update".equals(ope)) {

			int i = 0;
			for (Iterator itr = list.iterator(); itr.hasNext();) {
				TWfRelevantvar rev = (TWfRelevantvar) itr.next();
				String value = request.getParameter(rev.getDatafieldid()
						+ "_rev");
				if (value != null) {
					rev.setValuenow(value);
				}
			}
			try {
				console.updateRelevantvars(list);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		List listRev = new ArrayList();
		for (Iterator itr = list.iterator(); itr.hasNext();) {
			TWfRelevantvar rev = (TWfRelevantvar) itr.next();

			String ext = rev.getExtendattribute();
			// 如果是隐蔽字段忽略之
			String hidden = StringUtils.substringBetween(ext,
					RuntimeInfo._OE_WF_REV_EXT_COLUMN_HIDDEN + ":", ";");

			boolean hiddenIs = "1".equals(hidden);
			if (hiddenIs&&!"y".equals(debug)) {
				continue;
			}
			String name = StringUtils.substringBetween(ext,
					RuntimeInfo._OE_WF_REV_EXT_COLUMN_NAME + ":", ";");
			String valuemode = StringUtils.substringBetween(ext,
					RuntimeInfo._OE_WF_REV_EXT_COLUMN_VALUEMODE + ":", ";");
			String value = StringUtils.substringBetween(ext,
					RuntimeInfo._OE_WF_REV_EXT_COLUMN_VALUE + ":", ";");
		
			

			rev.setNameExt(name);
			// 动态构造可直接用于表单上展现的html信息（可能是输入框、可能是下拉列表框、可能是link）
			rev.setExtendattribute(WfFormUtil.generateRelevantDataHtmlXp(
					valuemode, value, rev));

			listRev.add(rev);
		}

		request.setAttribute("listinfo", listRev);
		request.setAttribute("runtimeid", runtimeid);

		String action = request.getParameter("processid") + "."
				+ request.getParameter("actid");
		Security ser = new Security(request);
		int actvalue = ser.getUserAction(action);
		
		System.out.println("perxxx"+action+","+actvalue);

		// 往页面上会访问控制权限
		request.setAttribute("permission", actvalue);

		return mapping.getInputForward();
	}
}
