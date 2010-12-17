package oe.frame.web;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * WEB�ļ���Դ��������
 * @author chen.jia.xun(Robanco) support by Oesee.org
 * 
 */
public interface FileTransfers {
	/**
	 * �ļ�����
	 * 
	 * @param webFile
	 *            web�ļ��������(ʹ��Struts�е�Formfile����)
	 * @param webPath
	 *            �ļ��ϴ�������·��
	 * @param fileName
	 *            �ļ���
	 * @throws Exception
	 */
	public void uploadFile(InputStream input, String webPath, String fileName)
			throws Exception;

	/**
	 * �ļ�����
	 * 
	 * @param output
	 *            �ļ������(��ʹ�� response.getOutputstream())
	 * @param webPath
	 *            �ļ��洢������·��
	 * @param fileName
	 *            �ļ���
	 * @throws Exception
	 */
	public void downloadFile(OutputStream output, String webPath,
			String fileName) throws Exception;

	/**
	 * �ļ�����
	 * 
	 * @param output
	 *            �ļ������(��ʹ�� response.getOutputstream())
	 * @param webPath
	 *            �ļ��洢������·��
	 * @throws Exception
	 */
	public void downloadFile(OutputStream output, String webPath)
			throws Exception;

	/**
	 * ɾ���ļ�
	 * 
	 * @param webPath
	 *            �ļ��洢������·��
	 * @param fileName
	 *            �ļ���
	 * @throws Exception
	 */
	public void deleteFile(String webPath, String fileName) throws Exception;

	/**
	 * ɾ���ļ�
	 * 
	 * @param webPath
	 *            �ļ��洢������·��
	 * @throws Exception
	 */
	public void deleteFile(String webPath) throws Exception;

}
