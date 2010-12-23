package oe.bi.etl.wizard.analysesubject.util;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import oe.bi.etl.obj.ChoiceInfo;
import oe.bi.etl.obj.DimensionElement;
import oe.bi.etl.obj.TargetElement;
import oe.bi.etl.wizard.analysesubject.AnalyseSubjectForm;
import oe.bi.view.obj.ext.GraphTypes;
import oe.bi.wizard.WizardDao;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.LabelValueBean;


public class AnaFinish1 {

	/**
	 * 执行第一个修改页面的修改
	 * 
	 * @param wd
	 * @param choiceinfo
	 * @param list
	 * @param fo
	 * @param request
	 */
	public static void main(WizardDao wd, ChoiceInfo choiceinfo, List<TargetElement> list, AnalyseSubjectForm fo,
			HttpServletRequest request) {
		request.setAttribute("chkid", request.getParameter("chkid"));
		String otherinfo = request.getParameter("sqlview");
		String allchkid2 = request.getParameter("allchkid2");
		String allorder = request.getParameter("allorder");
		String tgfiltvalue = request.getParameter("tgfiltvalue");
		String[] order = StringUtils.split(allorder, ",");
		String[] chkid2 = StringUtils.split(allchkid2, ",");

		String lsh = fo.getLsh();
		if (StringUtils.isNotEmpty(lsh)) {
			try {
				choiceinfo = wd.fromXml(lsh);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			choiceinfo.setOtherinfo(otherinfo);
			List delist = new ArrayList();
			List dimensionElement = choiceinfo.getDimensionElement();
			for (Iterator iter = dimensionElement.iterator(); iter.hasNext();) {
				DimensionElement de = (DimensionElement) iter.next();
				if ("start_time".equals(de.getId())) {
					de.setChoicenode(StringUtils.split(fo.getTimeResults(), ","));
					delist.add(de);
				} else if ("sys_int_id".equals(de.getId())) {
					de.setChoicenode(StringUtils.split(fo.getDimResult(), ","));
					delist.add(de);
				} else if (!"order".equals(de.getName())) {
					delist.add(de);
				}
			}
			if (allchkid2 != null && chkid2.length > 0) {
				for (int i = 0; i < chkid2.length; i++) {
					DimensionElement de = new DimensionElement();
					de.setLevelcolumnid("@");
					String[] choicenode = new String[1];
					String[] choicenodename = new String[1];
					choicenodename[0] = StringUtils.substringBetween(chkid2[i], "{", "}");
					de.setChoicenodename(choicenodename);
					de.setId(StringUtils.substringBefore(chkid2[i], "{"));
					// 最值
					if (StringUtils.isNotEmpty(tgfiltvalue)) {
						choicenode[0] = tgfiltvalue;
						// 排序
					} else {
						choicenode[0] = "";
					}
					de.setName("order");
					de.setOrder(true);
					if ("asc".equals(order[i])) {
						de.setDesc(false);
					} else if ("desc".equals(order[i])) {
						de.setDesc(true);
					}
					de.setChoicenode(choicenode);
					delist.add(de);
				}
			}
			choiceinfo.setDimensionElement(delist);
			List TargetElement = choiceinfo.getTargetElement();
			String tgothername = fo.getTgothername();
			String[] othername = StringUtils.split(tgothername, "#");
			for (int i = 0; i < othername.length; i++) {
				String tgid = StringUtils.substringBefore(othername[i], "=");
				for (Iterator iter = TargetElement.iterator(); iter.hasNext();) {
					TargetElement te = (TargetElement) iter.next();
					String id = te.getId();
					if (tgid.equals(id)) {
						te.setName(StringUtils.substringAfter(othername[i], "="));
						list.add(te);
					}
				}
			}
			choiceinfo.setTargetElement(list);
			choiceinfo.setActive(Boolean.parseBoolean(request.getParameter("active")));
			choiceinfo.setActcondition(fo.getActcondition());
			// 执行修改
			try {
				wd.modify(choiceinfo);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 进入第2个修改页面
	 * 
	 * @param wd
	 * @param choiceinfo
	 * @param list
	 * @param fo
	 * @param request
	 */
	public static void main2(WizardDao wd, ChoiceInfo choiceinfo, List<TargetElement> list, AnalyseSubjectForm fo,
			HttpServletRequest request) {
		String lsh = fo.getLsh();
		// 维度
		String[][] dimvalues = new String[2][3];
		dimvalues[0][0] = "name_en";
		dimvalues[1][0] = "start_time";
		dimvalues[0][1] = "类型";
		dimvalues[1][1] = "时间";
		List dimensionElement = choiceinfo.getDimensionElement();
		if(choiceinfo.isDynamicDim()){
			request.setAttribute("isDynamic", "is");
		}
		for (Iterator iter = dimensionElement.iterator(); iter.hasNext();) {
			DimensionElement de = (DimensionElement) iter.next();
			String level = de.getLevelcolumnid();
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
				String[] timeResults = StringUtils.split(fo.getTimeResults(), ",");
				if (!StringUtils.contains(timeResults[0], "#")) {
					request.setAttribute("show", "show");
				}
				for (int i = 0; i < timeResults.length; i++) {
					if (i == 0) {
						dimvalues[1][2] = timeResults[i] + ":00:00";
					} else {
						dimvalues[1][2] = dimvalues[1][2] + "," + timeResults[i] + ":00:00";
					}
				}
			} else if ("sys_int_id".equals(de.getId())) {
				String[] choicenode = de.getChoicenode();
				for (int i = 0; i < choicenode.length; i++) {
					if (i == 0) {
						dimvalues[0][2] = choicenode[i];
					} else {
						dimvalues[0][2] = dimvalues[0][2] + "," + choicenode[i];
					}
				}
			}
		}
		request.setAttribute("dimvalues", dimvalues);
		// String name_en = choiceinfo.getName_en();
		// String start_time = choiceinfo.getStart_time();
		// String str = "name_en=" + name_en + ",start_time=" + start_time +
		// ",";
		// fo.setStr(str);
		choiceinfo.setDimensionElement(dimensionElement);
		// 指标
		ArrayList tglist = new ArrayList();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			TargetElement te = (TargetElement) iter.next();
			String id = te.getId();
			String name = te.getName();
			if("number".equals(te.getType())){
				LabelValueBean lvb = new LabelValueBean(name, id);
				tglist.add(lvb);
			}
		}
		request.setAttribute("tglist", tglist);
		fo.setLsh(lsh);

		// 图表类型
		request.setAttribute("graphTypeList", GraphTypes._GH_ALL);
		// fo.setActive(String.valueOf(choiceinfo.isActive()));
		// fo.setShowvalue(choiceinfo.getShowvalue());
		// fo.setShowactive(choiceinfo.getShowactive());
		// fo.setSelcharttype(choiceinfo.getSelcharttype());
		// fo.setSeldatatype(choiceinfo.getSeldatatype());
		// fo.setMultichart(choiceinfo.getMultichart());
		// fo.setMaxvalue(choiceinfo.getMaxvalue());
		// fo.setPngwidth(choiceinfo.getPngwidth());
		// fo.setPictitle(choiceinfo.getPictitle());
		// fo.setPiccolor(choiceinfo.getPiccolor());
		// fo.setXqingxie(choiceinfo.getXqingxie());
		// String[] seltgs = choiceinfo.getSeltg();
		// String tgstr = "";
		// if (seltgs != null && seltgs.length > 0) {
		// for (int i = 0; i < seltgs.length; i++) {
		// tgstr += seltgs[i] + ",";
		// }
		// }
		// fo.setTgstr(tgstr);
	}
}
