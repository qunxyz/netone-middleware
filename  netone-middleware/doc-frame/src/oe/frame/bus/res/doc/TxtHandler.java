package oe.frame.bus.res.doc;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Txt文档处理
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public interface TxtHandler {

	/**
	 * 写文本
	 * 
	 * @param value
	 *            填写数据
	 * @param column
	 *            输出的字段(字段名，样式属性)
	 * @param linesplitSymbol
	 *            行分割符号
	 * @param columnSplitSymbol
	 *            列分割符号
	 * @param outstream
	 *            输出流
	 */
	public void writeTxt(List value, List column, OutputStream outstream);

	/**
	 * 读文本
	 * 
	 * @param in
	 * @return
	 */
	public List readTxt(InputStream in);

	/**
	 * 根据readTxt方法的返回值,创建XMlObj list
	 * 
	 * @param value
	 * @return
	 */
	public List metaData(List value);
}
