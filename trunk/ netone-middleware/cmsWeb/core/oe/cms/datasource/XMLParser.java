package oe.cms.datasource;

import java.util.List;

public interface XMLParser {

	String _XML_HEAD = "<?xml version=\"1.0\" encoding=\"GB2312\"?>\n";

	String _DEFAULT_BODY = "<bodys></bodys>";

	String _XML_END = "</xml>";

	/**
	 * �������е����Թ�����XML���ʽ
	 * 
	 * @param obj
	 * @return
	 */
	String toXML(Object[] obj);

	/**
	 * ��XML�е���Ϣ��ԭ�ɶ���
	 * 
	 * @param xml
	 * @return
	 */
	List toOBj(String xml);
}
