package oe.cav.web.workflow.resource.soax;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.midware.workflow.service.WorkflowView;
import oe.midware.workflow.xpdl.model.data.DataField;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;

/**
 * 自动展现工作流的相关数据的输入输出在 脚本编辑面板中
 * 
 * @author chenjx
 * 
 */
public class INOUTTools {

	public static void todo(HttpServletResponse response,
			HttpServletRequest request) {
		response.setContentType("text/xml;charset=GBK");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Expires", "0");
		PrintWriter out = null;
		String chkid = request.getParameter("chkid");
		String inout = request.getParameter("inout");
		StringBuffer sb = new StringBuffer();
		try {
			ResourceRmi resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
			UmsProtectedobject upo = resourceRmi.loadResourceById(chkid);
			String natrualname = StringUtils.substringBeforeLast(upo
					.getNaturalname(), ".");
			upo = resourceRmi.loadResourceByNatural(natrualname);
			String extend = upo.getExtendattribute();
			WorkflowView wfview = null;
			wfview = (WorkflowView) RmiEntry.iv("wfview");
			WorkflowProcess wplist = wfview.fetchWorkflowProcess(extend);
			DataField[] df = wplist.getDataField();
			if ("in".equals(inout)) {
				sb.append("\r/* WF ESB 入口参数*/\r");
				for (int i = 0; i < df.length; i++) {
					sb.append("wf.set(runtimeid, \'" + df[i].getId()
							+ "\', null);\r");
				}
			} else if ("out".equals(inout)) {
				sb.append("\r/* WF ESB 出口参数*/\r");
				for (int i = 0; i < df.length; i++) {
					String type = df[i].getDescription();
					if ("number".equalsIgnoreCase(type)) {
						sb.append("double " + df[i].getId()
								+ "=wf.getd(runtimeid, \'" + df[i].getId()
								+ "\');\r");
					} else {
						sb.append("String " + df[i].getId()
								+ "=wf.get(runtimeid, \'" + df[i].getId()
								+ "\');\r");
					}
				}
			}
			out = response.getWriter();
			out.write(sb.toString());
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
