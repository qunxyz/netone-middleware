package oe.cms.runtime;

import javax.servlet.http.HttpServletRequest;



public interface InfoCellParser {

	String _USE_POTROL = "@potroluse%!";

	/**
	 * 根据咨讯元的ID获得信息元对象，并且按照信息元对象的特点，构建出 信息元的Html流程
	 * 
	 * @param data
	 *            信息元
	 * @return 信息元的Html流
	 */
	String performInfoCellParser(String bodyinfo);

	void setRequestInfo(HttpServletRequest request);

}
