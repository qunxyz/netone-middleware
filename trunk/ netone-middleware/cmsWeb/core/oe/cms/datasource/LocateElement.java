package oe.cms.datasource;

import java.util.List;



/**
 * ��ѯԪ��
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public interface LocateElement {
	/**
	 * SI SQLԪ��ID��ǰ׺, ��Ӧ TCmsColumn��
	 */
	String _SI = "si";

	/**
	 * FI �ļ�Ԫ��ID��ǰ׺,��Ӧ TCmsFile��
	 */
	String _FI = "fi";

	/**
	 * web ҳ��Ԫ��
	 */
	String _WI = "wi";

	/**
	 * ȫ��Ԫ���б�
	 * { _SI, "SQL����Դ" }, { _FI, "�ļ�����Դ" },
		{ _WI, "ҳ������Դ" }
	 */
	String[][] _ELEMENT = { { _SI, "SQL����Դ" }, { _FI, "�ļ�����Դ" }};



}
