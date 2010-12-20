package oe.cav.bean.logic.tools;

import org.dom4j.Document;

/**
 * 将DyObj转换为xml文档
 * 
 * @author chen.jia.xun
 * 
 */
public interface DyObjToXML {

	/**
	 * 根据对象创建xml的document
	 * 
	 * @param dyf
	 * @return
	 */
	Document parser(DyObj dyf);

}
