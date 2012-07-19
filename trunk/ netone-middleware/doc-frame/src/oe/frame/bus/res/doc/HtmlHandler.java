package oe.frame.bus.res.doc;

import java.util.List;
import java.util.Map;

/**
 * ͨ���ĵ��������(�����Ǵ���Word����excel������ͨ���ó�����Ϊ�н���ɴ���)
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public interface HtmlHandler {

	/**
	 * д����ģ��
	 * 
	 * @param bussObjs
	 *            ҵ�����Map����
	 * @param templatename
	 *            ����ģ����
	 * @return
	 */
	public String[] generateMultiDocByTemplate(List bussObjs,
			String templatename);

	/**
	 * д����ģ��
	 * 
	 * @param bussObj
	 *            ҵ�����Map
	 * @param templatename
	 *            ����ģ����
	 * @param needpagesplit
	 *            �Ƿ���Ҫ��ҳ��ʾ
	 * @return
	 */
	public String generateDocByTemplate(Map bussObj, String templatename);

	/**
	 * ��ȡXML�ο�ģ�壨ע�⣺�����ǰδִ�й�fetchColumnInfo����ô��ȡ�Ľ��Ϊ�գ�
	 * 
	 * @param templateId
	 * @return
	 */
	public List fetchColumnInfo(String templateId);

	/**
	 * ����XML�ο���Ϣ
	 * 
	 * @param templateId
	 * @return
	 */
	public void generateXmlReference(String templateId);

	/**
	 * ���ģ�����չ�ļ�,��ģ������ͼƬ�ȸ�����ʱ��,����һ����չ�ļ���
	 * 
	 * @param templateId
	 * @return
	 */
	public String templateModelExtFile(String templateId);
}
