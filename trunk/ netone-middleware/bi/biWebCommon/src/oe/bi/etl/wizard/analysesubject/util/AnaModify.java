package oe.bi.etl.wizard.analysesubject.util;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import oe.bi.etl.obj.ChoiceInfo;
import oe.bi.etl.obj.DimensionElement;
import oe.bi.etl.obj.TargetElement;
import oe.bi.etl.obj.TargetFiltObj;
import oe.bi.etl.wizard.analysesubject.AnalyseSubjectForm;
import oe.bi.wizard.WizardDao;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.struts.util.LabelValueBean;


public class AnaModify {
	/**
	 * 进入第一个修改页面
	 * 
	 * @param wd
	 * @param fo
	 * @param request
	 */
	public static void main(WizardDao wd, AnalyseSubjectForm fo, HttpServletRequest request) {
		ResourceRmi rsrmi = null;
		try {
			rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			String chkid = request.getParameter("chkid");
			request.setAttribute("chkid", chkid);
			UmsProtectedobject upo = rsrmi.loadResourceById(chkid);
			String lsh = upo.getExtendattribute();
			ChoiceInfo choiceinfo = wd.fromXml(lsh);
			List DimensionElement = choiceinfo.getDimensionElement();
			fo.setActive(String.valueOf(choiceinfo.isActive()));
			fo.setTgGroup(choiceinfo.getTggroup());
			fo.setSqlview(choiceinfo.getOtherinfo());
			String allorder = "";
			String allchkid2 = "";
			for (Iterator iter = DimensionElement.iterator(); iter.hasNext();) {
				DimensionElement de = (DimensionElement) iter.next();
				if ("start_time".equals(de.getId())) {
					String[] choicenode = de.getChoicenode();
					String tmp = "";
					if (choicenode != null && choicenode.length > 0) {
						for (int i = 0; i < choicenode.length; i++) {
							if (i == 0) {
								tmp = choicenode[i];
							} else {
								tmp = tmp + "," + choicenode[i];
							}
						}
					}
					fo.setTimeResults(tmp);
				} else if ("sys_int_id".equals(de.getId())) {
					String[] choicenode = de.getChoicenode();
					String tmp = "";
					if (choicenode != null && choicenode.length > 0) {
						for (int i = 0; i < choicenode.length; i++) {
							if (i == 0) {
								tmp = choicenode[i];
							} else {
								tmp = tmp + "," + choicenode[i];
							}
						}
					}
					fo.setDimResult(tmp);
				} else {
					if ("order".equals(de.getName())) {
						String[] choicenode = de.getChoicenode();
						String tgfiltvalue = choicenode[0];
						fo.setTgfiltvalue(tgfiltvalue);
						allchkid2 += de.getId() + "{" + de.getChoicenodename()[0] + "}:" + fo.getTgGroup() + ",";
						if (de.isDesc()) {
							allorder += "desc,";
						} else {
							allorder += "asc,";
						}
					}
				}
			}
			fo.setAllchkid2(allchkid2);
			fo.setAllorder(allorder);
			fo.setActcondition(choiceinfo.getActcondition());
			fo.setActurl(choiceinfo.getActurl());
			choiceinfo.setDimensionElement(DimensionElement);
			List TargetElement = choiceinfo.getTargetElement();
			// 指标
			ArrayList tglist = new ArrayList();
			ArrayList tglist2 = new ArrayList();
			for (Iterator iter = TargetElement.iterator(); iter.hasNext();) {
				TargetElement te = (TargetElement) iter.next();
				String id = te.getId();
				String name = te.getName();
				String natived = te.getNativeid();
				LabelValueBean lvb = new LabelValueBean(name, id);
				tglist.add(lvb);
				LabelValueBean lvb2 = new LabelValueBean(name, id + "{" + natived + "}:" + fo.getTgGroup());
				tglist2.add(lvb2);
			}
			request.setAttribute("tglist", tglist);
			request.setAttribute("tglist2", tglist2);
			fo.setLsh(lsh);

			TargetFiltObj tfo = new TargetFiltObj();
			request.setAttribute("_CHOICE_TOP", tfo._CHOICE_TOP);
			request.setAttribute("_CHOICE_TOP_VALUE", tfo._CHOICE_TOP_VALUE);
			request.setAttribute("_CHOICE_ORDER", tfo._CHOICE_ORDER);
			request.setAttribute("_CHOICE_ALARM", tfo._CHOICE_ALARM);
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
