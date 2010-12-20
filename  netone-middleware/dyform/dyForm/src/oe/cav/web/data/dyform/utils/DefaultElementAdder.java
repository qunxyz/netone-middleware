package oe.cav.web.data.dyform.utils;

import com.rongji.webframework.struts.DynaUIForm;
import com.rongji.webframework.ui.html.Hidden;

public class DefaultElementAdder {
//	public static void addDefaultColumn(Table table, DynaUIForm dyform,
//			String systemid) {
//		
//		DynamicFormElementAdder.addDatetimeRow(table, "创建时间",
//				ColumnExtendInfo._HTML_TYPE_DATE, "created", null, true, true,
//				true);
//		
//		String participant = BusAbout.getParticipantSelect(systemid);
//		if (participant != null &&!participant.equals("")) {
//			DynamicFormUtil.addSelectRow(table, "执行者",
//					TCsColumnExtend._HTML_TYPE_SELECT, "participant",
//					participant, "", true, true, true, checkMan, manLogin);
//		}
//
//		String traclist = BusAbout.getTracSelect(systemid);
//		if (traclist != null && !traclist.equals("")) {
//			DynamicFormUtil.addSelectRow(table, "所属流程环节",
//					TCsColumnExtend._HTML_TYPE_SELECT, "traccode", traclist,
//					"", true, true, true, checkMan, manLogin);
//		}
//
//		String formlist = BusAbout.getFormSelect(systemid);
//		if (formlist != null && !formlist.equals("")) {
//			DynamicFormUtil.addSelectRow(table, "所属表单",
//					TCsColumnExtend._HTML_TYPE_SELECT, "formcode", formlist,
//					"", true, true, true, checkMan, manLogin);
//		}
//	}
	
	public static void addHideColumn(DynaUIForm dyform, String key,String value) {
		Hidden hidden = new Hidden();
		hidden.setName(key);
		hidden.setValue(value);
		dyform.addControl(hidden);
	}
	
//	public static void addHideColumn(DynaUIForm dyform, TCsBus bus) {
//		Hidden hidden = new Hidden();
//		hidden.setName("id");
//		hidden.setValue(bus.getLsh());
//		dyform.addControl(hidden);
//
//		Hidden hidden1 = new Hidden();
//		hidden1.setName("formcode");
//		hidden1.setValue(bus.getFormcode());
//		dyform.addControl(hidden1);
//
//		Hidden hidden2 = new Hidden();
//		hidden2.setName("participant");
//		hidden2.setValue(bus.getParticipant());
//		dyform.addControl(hidden2);
//
//		Hidden hidden3 = new Hidden();
//		hidden3.setName("created");
//		hidden3.setValue(bus.getCreated());
//		dyform.addControl(hidden3);
//
//		Hidden hidden4 = new Hidden();
//		hidden4.setName("taskcode");
//		hidden4.setValue(bus.getTaskcode());
//		dyform.addControl(hidden4);
//	}
}
