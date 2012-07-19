package oe.frame.bus.res.doc.common;

import java.util.List;

/**
 * 
 * @author hotchaoyi,chen.jia.xun(Robanco)
 * 
 */
public interface XmlHandler {

	// XML子变量的根
	String CHILD_VARS = "ChildVars";

	// XML子变量
	String CHILD_VAR = "ChildVar";

	// XML文档中的根变量
	String DOC_VAR_BEANS = "DocVarBeans";

	// XML文档中的变量
	String DOC_VAR_BEAN = "DocVarBean";

	/**
	 * 写XML文档
	 * 
	 * @param xmlname
	 *            文档名,文件名是不包含路径和扩展名的,程序会自动加入扩展名.xml 同时
	 *            根据core-document.xml文档中的xmlpath中定义的路径信息并入路径
	 * @param list
	 *            需要写入的数值,该数值是XmlObj对象
	 * 
	 * 补充说明: 如果写入的数据过多,会出现内存溢出
	 * 
	 */
	public void writeXml(String xmlname, List list);

	/**
	 * 读Xml文档
	 * 
	 * @param xmlname
	 * @return
	 */
	public List readXml(String xmlname);

	/**
	 * 获得文档配置路径
	 * 
	 * @return
	 */
	public FileRootInfo fetchFileRootInfo();

}
