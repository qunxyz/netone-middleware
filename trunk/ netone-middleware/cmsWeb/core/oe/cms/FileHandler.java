package oe.cms;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 文件管理
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public interface FileHandler {
	/**
	 * 读文件
	 * 
	 * @param url
	 * @param input
	 */
	void readFile(String url, OutputStream output);

	/**
	 * 读文件
	 * 
	 * @param url
	 * @return
	 */
	String readFileStr(String url);

	/**
	 * 读文件
	 * 
	 * @param url
	 * @return
	 */
	InputStream readFile(String url);

	/**
	 * 写文件（对本地IO的写 ）
	 * 
	 * @param url
	 * @param filestr
	 */
	void writeFile(String url, String filestr);

	/**
	 * 写文件（对本地IO的写 ）
	 * 
	 * @param url
	 * @param input
	 */
	void writeFile(String url, InputStream input);

	/**
	 * 删除文件
	 * 
	 * @param url
	 */
	void dropFile(String url);

}
