package oe.cav.web.workflow.monitor.process.util;

import oe.frame.bus.workflow.RuntimeInfo;
import oe.midware.workflow.runtime.ormobj.TWfRelevantvar;

public class WfFormUtil {
	/**
	 * 生成流程中相关数据的Html代码
	 * 
	 * @param valuemode
	 *            相关数据扩展属性中定义的valuemode，该值在RuntimeInfo._OE_WF_REV_FORM_XXXXX中定义
	 * @param value
	 *            相关数据扩展属性中定义的value，该值通常只有valuemode为RuntimeInfo._OE_WF_REV_FORM_LIST的类型才有
	 * @param rev
	 *            工作流相关数据对象
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
									+ "' target='_blank'>[业务表单]</a>");
				} else {
					// 如果表单关联字段的值里面只有一个数据通常只放lsh，那么这时关联的动态表单的formcode，在扩展属性
					// 的value值里面
					htmlexp
							.append("<a href='/dyForm/data/showdata/modifyview.do?formcode="
									+ value
									+ "&lsh="
									+ valuenow
									+ "' target='_blank'>[业务表单]</a>");
				}

			}
		} else if (RuntimeInfo._OE_WF_REV_FORM_URL.equals(valuemode)) {
			String valuenow = rev.getValuenow();
			htmlexp.append("<a href='" + value + valuenow
					+ "' target='_blank'>[应用表单]</a>");

		} else {

			htmlexp.append("<input type='text' name='" + rev.getDatafieldid()
					+ "_rev' value='" + rev.getValuenow() + "' size='35'/>");
		}
		return htmlexp.toString();
	}
}
