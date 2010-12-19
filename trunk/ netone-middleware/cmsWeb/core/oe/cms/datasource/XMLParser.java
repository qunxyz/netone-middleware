package oe.cms.datasource;

import java.util.List;

public interface XMLParser {

	String _XML_HEAD = "<?xml version=\"1.0\" encoding=\"GB2312\"?>\n";

	String _DEFAULT_BODY = "<bodys></bodys>";

	String _XML_END = "</xml>";

	/**
	 * 将对象中的属性构建成XML表达式
	 * 
	 * @param obj
	 * @return
	 */
	String toXML(Object[] obj);

	/**
	 * 将XML中的信息还原成对象
	 * 
	 * @param xml
	 * @return
	 */
	List toOBj(String xml);
}
