package oe.cms.runtime;

import javax.servlet.http.HttpServletRequest;



public interface InfoCellParser {

	String _USE_POTROL = "@potroluse%!";

	/**
	 * ������ѶԪ��ID�����ϢԪ���󣬲��Ұ�����ϢԪ������ص㣬������ ��ϢԪ��Html����
	 * 
	 * @param data
	 *            ��ϢԪ
	 * @return ��ϢԪ��Html��
	 */
	String performInfoCellParser(String bodyinfo);

	void setRequestInfo(HttpServletRequest request);

}
