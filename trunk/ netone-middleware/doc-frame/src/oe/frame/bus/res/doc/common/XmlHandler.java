package oe.frame.bus.res.doc.common;

import java.util.List;

/**
 * 
 * @author hotchaoyi,chen.jia.xun(Robanco)
 * 
 */
public interface XmlHandler {

	// XML�ӱ����ĸ�
	String CHILD_VARS = "ChildVars";

	// XML�ӱ���
	String CHILD_VAR = "ChildVar";

	// XML�ĵ��еĸ�����
	String DOC_VAR_BEANS = "DocVarBeans";

	// XML�ĵ��еı���
	String DOC_VAR_BEAN = "DocVarBean";

	/**
	 * дXML�ĵ�
	 * 
	 * @param xmlname
	 *            �ĵ���,�ļ����ǲ�����·������չ����,������Զ�������չ��.xml ͬʱ
	 *            ����core-document.xml�ĵ��е�xmlpath�ж����·����Ϣ����·��
	 * @param list
	 *            ��Ҫд�����ֵ,����ֵ��XmlObj����
	 * 
	 * ����˵��: ���д������ݹ���,������ڴ����
	 * 
	 */
	public void writeXml(String xmlname, List list);

	/**
	 * ��Xml�ĵ�
	 * 
	 * @param xmlname
	 * @return
	 */
	public List readXml(String xmlname);

	/**
	 * ����ĵ�����·��
	 * 
	 * @return
	 */
	public FileRootInfo fetchFileRootInfo();

}
