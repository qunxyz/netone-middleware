package oe.cav.bean.logic.tools;

/**
 * ��xml�ĵ�ת��ΪDyObj
 * 
 * @author chen.jia.xun
 * 
 */
public interface DyObjFromXml {
	/**
	 * ����xml��document��������
	 * 
	 * @param url
	 *            xml�ĵ���url��ַ
	 * @return
	 */
	DyObj parser(String url);
}
