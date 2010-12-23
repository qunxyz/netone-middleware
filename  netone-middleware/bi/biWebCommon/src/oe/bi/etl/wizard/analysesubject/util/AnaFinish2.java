package oe.bi.etl.wizard.analysesubject.util;

import java.rmi.RemoteException;

import javax.servlet.http.HttpServletRequest;

import oe.bi.etl.obj.ChoiceInfo;
import oe.bi.etl.wizard.analysesubject.AnalyseSubjectForm;
import oe.bi.wizard.WizardDao;

import org.apache.commons.lang.StringUtils;


public class AnaFinish2 {
	/**
	 * 执行第二个修改页面的修改
	 * 
	 * @param wd
	 * @param fo
	 * @param request
	 */
	public static void main(WizardDao wd, AnalyseSubjectForm fo, HttpServletRequest request) {
		String lsh = fo.getLsh();
		if (StringUtils.isNotEmpty(lsh)) {
			ChoiceInfo choiceinfo=null;
			try {
				choiceinfo = wd.fromXml(lsh);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (StringUtils.isEmpty(request.getParameter("showactive"))) {
				choiceinfo.setShowactive("1");
			} else {
				choiceinfo.setShowactive(request.getParameter("showactive"));
			}
			choiceinfo.setSelcharttype(request.getParameter("selcharttype"));
			choiceinfo.setSeldatatype(request.getParameter("seldatatype"));
			choiceinfo.setMaxvalue((request.getParameter("maxvalue")));
			choiceinfo.setMultichart(request.getParameter("multichart"));
			choiceinfo.setPngwidth(request.getParameter("pngwidth"));
			choiceinfo.setShowvalue(request.getParameter("showvalue"));
			choiceinfo.setPictitle(request.getParameter("pictitle"));
			choiceinfo.setPiccolor(request.getParameter("piccolor"));
			choiceinfo.setXqingxie(request.getParameter("xqingxie"));
			String str = request.getParameter("str");
			String[] seldim = new String[2];
			if (StringUtils.isEmpty(str)) {
				choiceinfo.setName_en("-1");
				choiceinfo.setStart_time("-1");
			} else {
				seldim = StringUtils.split(str, ",");
				for (int j = 0; j < seldim.length; j++) {
					String tmp = StringUtils.substringBefore(seldim[j], "=");
					if ("name_en".equals(tmp)) {
						choiceinfo.setName_en(StringUtils.substringAfter(seldim[j], "="));
					} else if ("start_time".equals(tmp)) {
						choiceinfo.setStart_time(StringUtils.substringAfter(seldim[j], "="));
					}
				}
			}
			String tgstr = request.getParameter("tgstr");
			String[] seltg = new String[1];
			if (StringUtils.isNotEmpty(tgstr)) {
				seltg = StringUtils.split(tgstr, ",");
			} else {
				seltg[0] = "";
			}
			choiceinfo.setSeltg(seltg);
			try {
				wd.modify(choiceinfo);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		request.setAttribute("do", "do");
	}
}
