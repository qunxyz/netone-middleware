package oe.cav.bean.logic.tools;

import org.dom4j.Document;

/**
 * ��DyObjת��Ϊxml�ĵ�
 * 
 * @author chen.jia.xun
 * 
 */
public interface DyObjToXML {

	/**
	 * ���ݶ��󴴽�xml��document
	 * 
	 * @param dyf
	 * @return
	 */
	Document parser(DyObj dyf);

}
