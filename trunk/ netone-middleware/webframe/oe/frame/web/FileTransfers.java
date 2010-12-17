package oe.frame.web;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * WEB文件资源基础处理
 * @author chen.jia.xun(Robanco) support by Oesee.org
 * 
 */
public interface FileTransfers {
	/**
	 * 文件传输
	 * 
	 * @param webFile
	 *            web文件处理对象(使用Struts中的Formfile对象)
	 * @param webPath
	 *            文件上传的网络路径
	 * @param fileName
	 *            文件名
	 * @throws Exception
	 */
	public void uploadFile(InputStream input, String webPath, String fileName)
			throws Exception;

	/**
	 * 文件下载
	 * 
	 * @param output
	 *            文件输出流(可使用 response.getOutputstream())
	 * @param webPath
	 *            文件存储的网络路径
	 * @param fileName
	 *            文件名
	 * @throws Exception
	 */
	public void downloadFile(OutputStream output, String webPath,
			String fileName) throws Exception;

	/**
	 * 文件下载
	 * 
	 * @param output
	 *            文件输出流(可使用 response.getOutputstream())
	 * @param webPath
	 *            文件存储的网络路径
	 * @throws Exception
	 */
	public void downloadFile(OutputStream output, String webPath)
			throws Exception;

	/**
	 * 删除文件
	 * 
	 * @param webPath
	 *            文件存储的网络路径
	 * @param fileName
	 *            文件名
	 * @throws Exception
	 */
	public void deleteFile(String webPath, String fileName) throws Exception;

	/**
	 * 删除文件
	 * 
	 * @param webPath
	 *            文件存储的网络路径
	 * @throws Exception
	 */
	public void deleteFile(String webPath) throws Exception;

}
