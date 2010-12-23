package oe.bi.common.xml;

/**
 * ����XML�ĵ��������� ����Digest����,�Զ����XML��Java�����ӳ��,������ҪԼ������, (Լ��1)һ��XML�ĵ���ֻ�ܴ��һ��Java����
 * ǿ�ҵķ���,��һ��XML�ж�����Java����,�⽫������Ĳ�ѯ��ϵͳ���ܴ����ܴ������ (Լ��2) xml�ĵ�����·��ģʽ�洢�Ͷ�ȡ,���������ݿ�
 * 
 * @author chen.jia.xun( Robanco)
 * 
 */
public interface XmlHandler {
	
	

	/**
	 * дXML�ĵ�(��Ҫά������Ŀ¼,XML�ĵ���ID�ɷ����Զ���д)
	 * 
	 * @param path
	 *            ·����
	 * @param list
	 *            ��Ҫд�����ֵ,����ֵ��DataModelObj����
	 * 
	 * ����˵��: xml�ĵ���ID�������Զ����ɣ���Systemcurrentime
	 * 
	 */
	void writeXml(String path, Object[] list);

	void writeXml(String pathname, Object obj);

	/**
	 * ���ָ��Ŀ¼�µ�XML����Ŀ¼��Ϣ
	 * 
	 * @param path
	 *            ·���� ����˵��: xml�ĵ���ID�������Զ����ɣ���Systemcurrentime
	 * 
	 * @return ����ֵ�� [i][0]��modelID,[i][1]��modelname
	 */
	String[][] readDisplayInfo(String path);

	/**
	 * ��Xml�ĵ��е�ĳ������(��Ҫά������Ŀ¼)
	 * 
	 * @param xmlname
	 * @return
	 */
	Object readXml(String xmlfullname);

	/**
	 * ɾ��XML�ĵ�(��Ҫά������Ŀ¼)
	 * 
	 * @param xmlname
	 */
	void dropXml(String xmlfullname);

}
