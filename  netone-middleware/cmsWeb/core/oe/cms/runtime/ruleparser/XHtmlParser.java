package oe.cms.runtime.ruleparser;

import javax.servlet.http.HttpServletRequest;

public interface XHtmlParser {


	/**
	 * ����XHtml�ű�
	 * 
	 * �㷨˵���� 1:���ű��﷨�Ƿ���ȷ��ƥ�����%�Ƿ�һ�� 3:��
	 * 
	 * @param context
	 * @return
	 */
	String xHtmlParser(String context,HttpServletRequest request);

}
