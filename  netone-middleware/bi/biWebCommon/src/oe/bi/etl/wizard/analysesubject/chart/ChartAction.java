//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.bi.etl.wizard.analysesubject.chart;

/**
 * 流程创建与修改
 */
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.bi.etl.obj.ChoiceInfo;
import oe.bi.etl.wizard.analysesubject.AnalyseSubjectForm;
import oe.bi.etl.wizard.analysesubject.util.AnaFinish1;
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

public class ChartAction extends Action {

	// --------------------------------------------------------- Instance
	// Variables
	static Log log = LogFactory.getLog(ChartAction.class);

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
		WizardDao wd = null;
		try {
			wd = (WizardDao) RmiEntry.iv("bihandle");

		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (RemoteException e1) {
			e1.printStackTrace();
		} catch (NotBoundException e1) {
		}
		AnalyseSubjectForm fo = (AnalyseSubjectForm) form;
		request.setAttribute("fo", fo);
		String report = request.getParameter("report");
		String chkid = request.getParameter("chkid");
		request.setAttribute("report", report);
		request.setAttribute("chkid", chkid);
		// 跳转页面
		String task = request.getParameter("task");
		if ("Modify".equals(task)) {
			try {
				// 读取名为resource的rmi服务
				ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
				UmsProtectedobject upo = rsrmi.loadResourceById(chkid);
				String extendattribute = upo.getExtendattribute();
				String datasource = "";
				if (StringUtils.isNotEmpty(extendattribute)) {
					String[] extend = StringUtils.split(extendattribute, ";");
					if (extend != null && extend.length > 0) {
						for (int i = 0; i < extend.length; i++) {
							String[] d = StringUtils.split(extend[i], "@");
							String value = StringUtils.substringBetween(d[1],
									"$", "$");
							if ("selcharttype".equals(d[0])) {
								fo.setSelcharttype(value);
							} else if ("seldatatype".equals(d[0])) {
								fo.setSeldatatype(value);
							} else if ("str".equals(d[0])) {
								fo.setStr(value);
							} else if ("tgstr".equals(d[0])) {
								fo.setTgstr(value);
							} else if ("multichart".equals(d[0])) {
								fo.setMultichart(value);
							} else if ("showactive".equals(d[0])) {
								fo.setShowactive(value);
							} else if ("maxvalue".equals(d[0])) {
								fo.setMaxvalue(value);
							} else if ("pngwidth".equals(d[0])) {
								fo.setPngwidth(value);
							} else if ("showvalue".equals(d[0])) {
								fo.setShowvalue(value);
							} else if ("pictitle".equals(d[0])) {
								fo.setPictitle(value);
							} else if ("piccolor".equals(d[0])) {
								fo.setPiccolor(value);
							} else if ("xqingxie".equals(d[0])) {
								fo.setXqingxie(value);
							} else if ("datasource".equals(d[0])) {
								datasource = value;
								//fo.setNaturalname(value);
								request.setAttribute("report", datasource);
							}
						}
					}
				}
				ChoiceInfo choiceinfo = null;
				upo = rsrmi.loadResourceByNatural(datasource);
				String lsh = upo.getExtendattribute();
				choiceinfo = wd.fromXml(lsh);
				List list = choiceinfo.getTargetElement();
				AnaFinish1.main2(wd, choiceinfo, list, fo, request);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mapping.findForward("Modify");
		}
		// 进入First.jsp
		if ("First".equals(task)) {
			return mapping.findForward("First");
			// 进入Next.jsp
		} else if ("Next".equals(task)) {
			try {
				// 读取名为resource的rmi服务
				ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
				UmsProtectedobject upo = new UmsProtectedobject();
				upo.setNaturalname("CHART.CHART."
						+ fo.getNaturalname().toUpperCase());
				List list = rsrmi.fetchResource(upo, null);
				if (list == null || list.isEmpty()) {
					return mapping.findForward("Next");
				} else {
					request.setAttribute("error", "名称已存在!!!");
					return mapping.findForward("First");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if ("Done".equals(task)) {
			try {
				ChoiceInfo choiceinfo = null;
				ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
				String naturalname = StringUtils.substringBefore(report, "[");
				UmsProtectedobject upo = rsrmi
						.loadResourceByNatural(naturalname);
				String lsh = upo.getExtendattribute();
				choiceinfo = wd.fromXml(lsh);
				List list = choiceinfo.getTargetElement();
				AnaFinish1.main2(wd, choiceinfo, list, fo, request);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (NotBoundException e) {
				e.printStackTrace();
			}
			return mapping.findForward("Done");
		} else if ("Finish".equals(task)) {
			String selcharttype = fo.getSelcharttype();
			String seldatatype = fo.getSeldatatype();
			String str = fo.getStr();
			String tgstr = fo.getTgstr();
			String multichart = fo.getMultichart();
			String showactive = fo.getShowactive();
			String maxvalue = fo.getMaxvalue();
			String pngwidth = fo.getPngwidth();
			String showvalue = fo.getShowvalue();
			String pictitle = fo.getPictitle();
			String piccolor = fo.getPiccolor();
			String xqingxie = fo.getXqingxie();
			String naturalname = StringUtils.substringBefore(report, "[");

			StringBuffer sb = new StringBuffer();
			sb.append("datasource@$" + naturalname + "$;");
			sb.append("selcharttype@$" + selcharttype + "$;");
			sb.append("seldatatype@$" + seldatatype + "$;");
			sb.append("str@$" + str + "$;");
			sb.append("tgstr@$" + tgstr + "$;");
			sb.append("multichart@$" + multichart + "$;");
			sb.append("showactive@$" + showactive + "$;");
			sb.append("maxvalue@$" + maxvalue + "$;");
			sb.append("pngwidth@$" + pngwidth + "$;");
			sb.append("showvalue@$" + showvalue + "$;");
			sb.append("pictitle@$" + pictitle + "$;");
			sb.append("piccolor@$" + piccolor + "$;");
			sb.append("xqingxie@$" + xqingxie + "$;");
			try {
				String pagepath = request.getParameter("pagepath");
				ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
				// 新增
				if (StringUtils.isEmpty(chkid)) {
					UmsProtectedobject upo = new UmsProtectedobject();
					upo.setNaturalname(fo.getNaturalname());
					upo.setName(fo.getName());
					upo.setActionurl("");
					upo.setExtendattribute(sb.toString());
					if (pagepath == null) {
						pagepath = "CHART.CHART";
					}
					rsrmi.addResource(upo, pagepath);
					request.setAttribute("success", "success");
					return mapping.findForward("Done");
					// 修改
				} else {
					UmsProtectedobject upo = rsrmi.loadResourceById(chkid);
					upo.setExtendattribute(sb.toString());
					rsrmi.updateResource(upo);
					request.setAttribute("do", "do");
					return mapping.findForward("Modify");
				}
			} catch (NotBoundException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
