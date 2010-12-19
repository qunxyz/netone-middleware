package oe.cms;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * �ļ�����
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public interface FileHandler {
	/**
	 * ���ļ�
	 * 
	 * @param url
	 * @param input
	 */
	void readFile(String url, OutputStream output);

	/**
	 * ���ļ�
	 * 
	 * @param url
	 * @return
	 */
	String readFileStr(String url);

	/**
	 * ���ļ�
	 * 
	 * @param url
	 * @return
	 */
	InputStream readFile(String url);

	/**
	 * д�ļ����Ա���IO��д ��
	 * 
	 * @param url
	 * @param filestr
	 */
	void writeFile(String url, String filestr);

	/**
	 * д�ļ����Ա���IO��д ��
	 * 
	 * @param url
	 * @param input
	 */
	void writeFile(String url, InputStream input);

	/**
	 * ɾ���ļ�
	 * 
	 * @param url
	 */
	void dropFile(String url);

}
