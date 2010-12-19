package oe.cav.web.workflow.resource.soax;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.cav.bean.logic.column.TCsColumn;
import oe.mid.soa.bean.BeanService;
import oe.mid.soa.bean.ClassProperty;
import oe.mid.soa.bean.SoaBean;
import oe.midware.dyform.service.DyFormService;
import oe.midware.workflow.service.WorkflowView;
import oe.midware.workflow.xpdl.model.data.DataField;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;

/**
 * 自动加入 中间件内部对象
 * 
 * @author chenjx
 * 
 */
public class NetoneObjTools {

	public static void todo(HttpServletResponse response,
			HttpServletRequest request) {

		response.setContentType("text/xml;charset=GBK");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Expires", "0");
		PrintWriter out = null;
		StringBuffer sb = new StringBuffer(
				"<select onclick='document.all.tmptxt.value=this.value'>");
		String tmpcheck = request.getParameter("tmpcheck");
		String extend = request.getParameter("extend");
		try {
			ResourceRmi resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
			UmsProtectedobject upo = resourceRmi.loadResourceByNatural(extend);
			extend = upo.getExtendattribute();
			if ("dy".equals(tmpcheck)) {
				DyFormService dy = (DyFormService) RmiEntry.iv("dyhandle");
				List list = dy.fetchColumnList(extend);
				if (list != null && list.size() > 0) {
					for (Iterator iter = list.iterator(); iter.hasNext();) {
						TCsColumn element = (TCsColumn) iter.next();
						sb = sb.append("<option value=" + element.getColumnid()
								+ ">");
						sb = sb.append(element.getColumname() + "{"
								+ element.getHtmltype() + "}");
						sb = sb.append("</option>");
					}
					sb.append("</select><input type='text' name='tmptxt'>");
				}
				out = response.getWriter();
				out.write(sb.toString());
				out.close();
			} else if ("wf".equals(tmpcheck)) {
				WorkflowView wfview = null;
				wfview = (WorkflowView) RmiEntry.iv("wfview");
				WorkflowProcess wplist = wfview.fetchWorkflowProcess(extend);
				DataField[] df = wplist.getDataField();
				for (int i = 0; i < df.length; i++) {
					sb = sb.append("<option value=" + df[i].getId() + ">");
					sb = sb.append(df[i].getName() + "{"
							+ df[i].getDataType().getType() + "}");
					sb = sb.append("</option>");
				}
				sb.append("</select><input type='text' name='tmptxt'>");
				out = response.getWriter();
				out.write(sb.toString());
				out.close();
			} else if ("be".equals(tmpcheck)) {
				String[] extendDetail = null;
				if (extend != null) {
					extendDetail = StringUtils.split(extend, "#");
					if (extendDetail.length != 2) {
						return;
					}
				}
				BeanService beansv = (BeanService) RmiEntry
						.ivCore(extendDetail[0]);
				SoaBean beaIn = beansv.inParamDescription(extendDetail[1]);
				SoaBean beaOut = beansv.outParamDescription(extendDetail[1]);
				for (Iterator iter = beaIn.getClassproperty().keySet()
						.iterator(); iter.hasNext();) {
					String columnid = (String) iter.next();
					ClassProperty objpro = beaIn.getClassproperty().get(
							columnid);
					sb = sb.append("<option value=" + columnid + ">");
					sb = sb.append(objpro.getName() + "{" + objpro.getType()
							+ "}");
					sb = sb.append("</option>");
				}
				sb
						.append("</select><input type='text' name='tmptxt'><select onclick='document.all.tmptxt2.value=this.value'>");
				for (Iterator iter = beaOut.getClassproperty().keySet()
						.iterator(); iter.hasNext();) {
					String columnid = (String) iter.next();
					ClassProperty objpro = beaOut.getClassproperty().get(
							columnid);
					sb = sb.append("<option value=" + columnid + ">");
					sb = sb.append(objpro.getName() + "{" + objpro.getType()
							+ "}");
					sb = sb.append("</option>");
				}
				sb.append("</select><input type='text' name='tmptxt2'>");
				out = response.getWriter();
				out.write(sb.toString());
				out.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
