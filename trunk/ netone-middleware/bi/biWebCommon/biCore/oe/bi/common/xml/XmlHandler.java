package oe.bi.common.xml;

/**
 * 关于XML文档处理公共包 基于Digest工具,自动完成XML到Java对象的映射,其中需要约束的是, (约束1)一个XML文档中只能存放一个Java对象
 * 强烈的反对,在一个XML中定义多个Java对象,这将给对象的查询和系统性能带来很大的隐患 (约束2) xml文档基于路径模式存储和读取,而不是数据库
 * 
 * @author chen.jia.xun( Robanco)
 * 
 */
public interface XmlHandler {
	
	

	/**
	 * 写XML文档(需要维护索引目录,XML文档的ID由方法自动填写)
	 * 
	 * @param path
	 *            路径名
	 * @param list
	 *            需要写入的数值,该数值是DataModelObj对象
	 * 
	 * 补充说明: xml文档的ID，程序自动生成，用Systemcurrentime
	 * 
	 */
	void writeXml(String path, Object[] list);

	void writeXml(String pathname, Object obj);

	/**
	 * 获得指定目录下的XML索引目录信息
	 * 
	 * @param path
	 *            路径名 补充说明: xml文档的ID，程序自动生成，用Systemcurrentime
	 * 
	 * @return 返回值中 [i][0]是modelID,[i][1]是modelname
	 */
	String[][] readDisplayInfo(String path);

	/**
	 * 读Xml文档中的某个对象(需要维护索引目录)
	 * 
	 * @param xmlname
	 * @return
	 */
	Object readXml(String xmlfullname);

	/**
	 * 删除XML文档(需要维护索引目录)
	 * 
	 * @param xmlname
	 */
	void dropXml(String xmlfullname);

}
