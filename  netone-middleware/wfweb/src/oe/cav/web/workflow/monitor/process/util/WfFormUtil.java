package oe.cav.web.workflow.monitor.process.util;

import oe.frame.bus.workflow.RuntimeInfo;
import oe.midware.workflow.runtime.ormobj.TWfRelevantvar;

public class WfFormUtil {
	/**
	 * ����������������ݵ�Html����
	 * 
	 * @param valuemode
	 *            ���������չ�����ж����valuemode����ֵ��RuntimeInfo._OE_WF_REV_FORM_XXXXX�ж���
	 * @param value
	 *            ���������չ�����ж����value����ֵͨ��ֻ��valuemodeΪRuntimeInfo._OE_WF_REV_FORM_LIST�����Ͳ���
	 * @param rev
	 *            ������������ݶ���
	 * @return
	 */
	public static String generateRelevantDataHtmlXp(String valuemode,
			String value, TWfRelevantvar rev) {
		StringBuffer htmlexp = new StringBuffer();

		if (RuntimeInfo._OE_WF_REV_FORM_LIST.equals(valuemode)) {
			if (value != null && !value.equals("")) {
				htmlexp.append("<select name='" + rev.getDatafieldid()
						+ "_rev'>");
				String[] valueArr = value.split("#");
				for (int i = 0; i < valueArr.length; i++) {
					String[] valuePre = valueArr[i].split("-");
					if (valuePre[0].equals(rev.getValuenow())) {
						htmlexp.append("<option value='" + valuePre[0]
								+ "' selected>" + valuePre[1] + "</option>");
					} else {
						htmlexp.append("<option value='" + valuePre[0] + "'>"
								+ valuePre[1] + "</option>");
					}

				}
				htmlexp.append("</select>");
			}
		} else if (RuntimeInfo._OE_WF_REV_FORM_DYFORM.equals(valuemode)) {
			String valuenow = rev.getValuenow();
			if (valuenow != null) {
				String[] valuearr = valuenow.split(":");
				if (valuearr.length == 2) {
					htmlexp
							.append("<a href='/dyForm/data/showdata/modifyview.do?formcode="
									+ valuearr[1]
									+ "&lsh="
									+ valuearr[0]
									+ "' target='_blank'>[ҵ���]</a>");
				} else {
					// ����������ֶε�ֵ����ֻ��һ������ͨ��ֻ��lsh����ô��ʱ�����Ķ�̬����formcode������չ����
					// ��valueֵ����
					htmlexp
							.append("<a href='/dyForm/data/showdata/modifyview.do?formcode="
									+ value
									+ "&lsh="
									+ valuenow
									+ "' target='_blank'>[ҵ���]</a>");
				}

			}
		} else if (RuntimeInfo._OE_WF_REV_FORM_URL.equals(valuemode)) {
			String valuenow = rev.getValuenow();
			htmlexp.append("<a href='" + value + valuenow
					+ "' target='_blank'>[Ӧ�ñ�]</a>");

		} else {

			htmlexp.append("<input type='text' name='" + rev.getDatafieldid()
					+ "_rev' value='" + rev.getValuenow() + "' size='35'/>");
		}
		return htmlexp.toString();
	}
}
