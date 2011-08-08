package oe.frame.bus.workflow.rule;

/**
 * ��Ϣ�ű�����
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjiaxun@oesee.com<br>
 *         tel:13328675083<br>
 */
public interface EnvScriptFunction {

	/**
	 * �ӵ�ǰ�����л������ֵ
	 * 
	 * @param key
	 * @return
	 */
	String value(String key);

	/**
	 * ���ƶ��ĵ�ַ�л������ֵ
	 * 
	 * @param url
	 * @param key
	 * @return
	 */
	String value(String url, String key);

	/**
	 * ����Web��ַ
	 * 
	 * @param url
	 * @return
	 */
	String invokeUrl(String url);

	/**
	 * �����·����д�ļ�
	 * 
	 * @param filename
	 * @param data
	 */
	void writeEnvFile(String filename, String data);

	/**
	 * �����·���ж��ļ���Ϣ
	 * 
	 * @param filename
	 * @return
	 */
	String readEnvFile(String filename);

	/**
	 * ����ִ���ⲿ�ű�
	 * 
	 * @param uri�� ��ʽ, scripttype@url�� ����: tcl:/ext/telnet.tcl, awk:/exe/data.awk
	 * @param param ���ݸ��ű��Ĳ���
	 * @return
	 */
	String exeOuterScript(String uri, String[] param);

}
