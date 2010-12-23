//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.bi.etl.wizard.analysesubject;

/**
 * 过滤
 */
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.bi.etl.obj.ChoiceInfo;
import oe.bi.etl.wizard.analysesubject.util.AnaUtil2;
import oe.bi.wizard.WizardDao;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * MyEclipse Struts Creation date: 06-28-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class FilterAction extends Action {

	// --------------------------------------------------------- Instance
	// Variables
	static Log log = LogFactory.getLog(FilterAction.class);

	// --------------------------------------------------------- Methods

	/**
	 * Method execute
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		AnalyseSubjectForm fo = (AnalyseSubjectForm) form;
		request.setAttribute("fo", fo);
		String task = request.getParameter("task");
		String openid = request.getParameter("openid");
		request.setAttribute("openid", openid);
		if ("filter1".equals(task)) {
			AnaUtil2.main(fo, request);
			return mapping.findForward("filter1");
		} else if ("filter2".equals(task)) {
			AnaUtil2.main(fo, request);
			return mapping.findForward("filter2");
		} else if ("filter3".equals(task)) {
			ResourceRmi rsrmi = null;
			try {
				WizardDao wd = (WizardDao) RmiEntry.iv("bihandle");
				rsrmi = (ResourceRmi) RmiEntry.iv("resource");
				String chkid = request.getParameter("openid");
				UmsProtectedobject upo = rsrmi.loadResourceById(chkid);
				String lsh = upo.getExtendattribute();
				ChoiceInfo choiceinfo = wd.fromXml(lsh);
				String actcondition = choiceinfo.getActcondition();
				StringBuffer sb = new StringBuffer();
				if (StringUtils.isNotEmpty(actcondition)) {
					String[] condition = StringUtils.split(actcondition, ";");
					for (int i = 0; i < condition.length; i++) {
						String[] act = StringUtils.split(condition[i], "#");
						if ("text".equals(act[4])) {
							sb.append(act[2]
									+ "&nbsp;&nbsp;<input type='text' id='"
									+ act[0] + "{" + act[1] + "}' title='"
									+ act[3] + "' name='chkid' value=''>");
						}
						if ("time".equals(act[4])) {
							sb
									.append(act[2]
											+ "&nbsp;&nbsp;<input type='text' name='chkid' value='' id='"
											+ act[0] + "{" + act[1]
											+ "}' title='" + act[3]
											+ "' onFocus='calendar();'>");
							sb.append("<select name='timele'>");
							sb.append("<option value='1y'>年</option>");
							sb.append("<option value='1mo'>月</option>");
							sb.append("<option value='1d'>日</option>");
							sb.append("<option value='1h'>时</option>");
							sb.append("<option value='1m'>分</option>");
							sb.append("<option value='1s' selected>秒</option>");
							sb.append("</select>");
						}
						if ("select".equals(act[4])) {
							sb.append(act[2]
									+ "&nbsp;&nbsp;<select name='chkid' id='"
									+ act[0] + "{" + act[1] + "}' title='"
									+ act[3] + "'>");
							sb.append("<option value=''></option>");
							if (act.length == 6) {
								String[] tmp = StringUtils.split(act[5], ",");
								for (int j = 0; j < tmp.length; j++) {
									System.out.println("--------------"+tmp[j]);
									String[] tmp2 = StringUtils.split(tmp[j],
											"=");
									if (tmp2 != null && tmp2.length == 2) {
										sb.append("<option value='" + tmp2[0]
												+ "'>" + tmp2[1] + "</option>");
									}
								}
							}
							sb.append("</select>");
						}
						if ("resource".equals(act[4])) {
							sb
									.append(act[2]
											+ "&nbsp;&nbsp;<input type='text' id='"
											+ act[0]
											+ "{"
											+ act[1]
											+ "}' title='"
											+ act[3]
											+ "' name='chkid' value='' readonly='readonly'>");
							if (act.length == 6) {
								sb
										.append("<input type='button' value='选择' onclick=javascript:document.all.tmp.value='"
												+ act[1]
												+ "';openRS(this,'"
												+ StringUtils.substringBetween(
														act[5], "[", "]")
												+ "');>");
							}
						}
						sb.append("<br>");
					}
				}
				request.setAttribute("sb", sb.toString());
			} catch (NotBoundException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			return mapping.findForward("filter3");
		}
		return null;
	}

}
