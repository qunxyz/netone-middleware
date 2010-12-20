package oe.cav.bean.logic.tools;

/**
 * 将xml文档转换为DyObj
 * 
 * @author chen.jia.xun
 * 
 */
public interface DyObjFromXml {
	/**
	 * 依据xml的document创建对象
	 * 
	 * @param url
	 *            xml文档的url地址
	 * @return
	 */
	DyObj parser(String url);
}
