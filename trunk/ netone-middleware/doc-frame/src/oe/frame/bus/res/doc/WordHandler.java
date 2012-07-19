package oe.frame.bus.res.doc;

import java.io.OutputStream;
import java.util.List;

/**
 * Word�ĵ�(Excel�ĵ�) ��ģ���е���Ƕ�ű�Ԫ��
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public interface WordHandler {
	// ////��������/////////
	String TYPE_VAR = "var";// ��ͨ����

	String TYPE_VARX = "varx";// ��ݱ���

	String TYPE_VARS = "vars";// ��������

	String TYPE_LOOPS = "loops";// ѭ��

	// ////////////////////

	// ��ͨ����
	String VAR = "$var(";

	// ��ݱ���
	String VARX = "$varx(";

	// ��������
	String VARS = "$vars(";

	// ѭ��
	String LOOPS = "$loops{";

	// ������־
	String _END_MARK = ")";

	// ѭ��������־
	String _LOOP_END_MARK = "}";

	int LOOPS_LEN = 7;

	String LOSE_VALUE = "%%@Error!!";

	String LOOP_SYMBOL_TMP = "$tmploops{";

	/**
	 * ����ģ��д���飨��ݣ�
	 * 
	 * @param bussObjs
	 *            ҵ�����List
	 * @param multilBussObjs
	 *            ����ĵ���ҵ��ֵ
	 * @param templatename
	 *            ģ���ļ����ƣ�ͨ����Word���ɵ�.htm�ļ�������ֻ��Ҫ�����ļ�����������չ��������ȫ·�������ɴ����·������
	 *            ��Ϊ������Զ������չ��.htm,�Զ���Ӹ�·����
	 * @param output
	 *            ����� ����˵��:��� output����WEB response
	 *            response.setContentType("application/msword;charset=GBK");//
	 *            ����ContentType String expfileName = System.currentTimeMillis() +
	 *            .zip; response.setHeader("Content-Disposition", "attachment;
	 *            filename=\"" + expfileName + "\""); <br>
	 *            <br>
	 *            ���ģ������varx�����÷������Բ���n������,ÿ�����鶼��zip��ʽ,������ ���ĵ��Ͷ�Ӧ����չ�ļ���
	 */
	public void writeDocUseMultiBussObj(List value, String templateId,
			OutputStream output);

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
}
