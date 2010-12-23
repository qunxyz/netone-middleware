package oe.bi.etl.wizard.analysesubject.util;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import oe.bi.etl.wizard.analysesubject.AnalyseSubjectForm;
import oe.bi.view.obj.ext.GraphTypes;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.LabelValueBean;


public class AnaUtil5 {
	/**
	 * ����Step5.jspҳ��
	 * @param fo
	 * @param request
	 */
	public static void main(AnalyseSubjectForm fo, HttpServletRequest request) {
		// ͼ������
		request.setAttribute("graphTypeList", GraphTypes._GH_ALL);
		// ά��
		String[][] dimvalues = new String[2][3];
		dimvalues[0][0] = "name_en";
		dimvalues[1][0] = "start_time";
		dimvalues[0][1] = "����";
		dimvalues[1][1] = "ʱ��";
		String[] dimResultValue = StringUtils.split(fo.getDimResultValue(), ";");
		for (int i = 0; i < dimResultValue.length; i++) {
			if (i == 0) {
				dimvalues[0][2] = StringUtils.substringBetween(dimResultValue[i], "_", ",");
			} else {
				dimvalues[0][2] = dimvalues[0][2] + "," + StringUtils.substringBetween(dimResultValue[i], "_", ",");
			}
		}
		String[] timeResults = StringUtils.split(fo.getTimeResults(), ",");
		for (int i = 0; i < timeResults.length; i++) {
			if (i == 0) {
				dimvalues[1][2] = timeResults[i] + ":00:00";
			} else {
				dimvalues[1][2] = dimvalues[1][2] + "," + timeResults[i] + ":00:00";
			}
		}
		request.setAttribute("dimvalues", dimvalues);
		// ָ��
		ArrayList tglist = new ArrayList();
		String[] tgids = StringUtils.split(fo.getTgids(), ",");
		String[] tgnames = StringUtils.split(fo.getTgnames(), ",");
		if (tgids != null && tgnames != null) {
			for (int i = 0; i < tgids.length; i++) {
				LabelValueBean lvb = new LabelValueBean(tgnames[i], StringUtils.substringBefore(tgids[i], ":"));
				tglist.add(lvb);
			}
		}
		request.setAttribute("tglist", tglist);
	}
}
